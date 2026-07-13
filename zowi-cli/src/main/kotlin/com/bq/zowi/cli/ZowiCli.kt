package com.bq.zowi.cli

import com.bq.zowi.api.GameController
import com.bq.zowi.api.Log
import com.bq.zowi.cli.adapters.*
import com.bq.zowi.cli.storage.FileAssetProvider
import com.bq.zowi.cli.storage.JsonKeyValueStore
import com.bq.zowi.models.RankingEntry
import com.bq.zowi.models.commands.*
import com.bq.zowi.usecases.*
import com.bq.zowi.utils.Grove
import com.google.gson.Gson
import java.io.File

class ZowiCli(
    private val dataDir: File = File(System.getProperty("user.home"), ".zowi"),
    private val assetsDir: File = File(dataDir, "assets")
) {
    private val store = JsonKeyValueStore(File(dataDir, "zowi.json"))
    private val gson = Gson()

    private val btAdapter = CliBtAdapterController()
    private val btConnection = CliBtConnectionController()
    private val sessionController = CliSessionController(store)
    private val appController = CliAppController(store)
    private val gameController = CliGameController(store, gson)
    private val rankingController = CliRankingController(store, gson)
    private val assetProvider = FileAssetProvider(assetsDir)
    val achievementsController = CliAchievementsController(store, assetProvider, gson)
    private val projectController = CliProjectController(store, assetProvider, gson)
    private val assetController = CliAssetController(assetProvider)
    private val zowiDataController = CliZowiDataController(btConnection)
    private val kitonNetworkController = CliKitonNetworkController(gson)

    private val findZowisInteractor = FindZowisInteractorImpl(btAdapter)
    private val connectToZowiInteractor = ConnectToZowiInteractorImpl(
        btConnection, btAdapter, zowiDataController, kitonNetworkController
    )
    private val sendCommandInteractor = SendCommandToZowiInteractorImpl(btConnection)
    private val sendAppInteractor = SendAppToZowiInteractorImpl(
        connectToZowiInteractor, btConnection, assetController,
        sessionController, zowiDataController, kitonNetworkController
    )
    private val changeNameInteractor = ChangeZowiNameInteractorImpl(sendCommandInteractor)
    private val batteryInteractor = MeasureZowiBatteryLevelInteractorImpl(zowiDataController)
    private val forgetZowiInteractor = ForgetZowiInteractorImpl(
        changeNameInteractor, sessionController, btConnection
    )
    private val forgetHistoryInteractor = ForgetPlayingHistoryInteractorImpl(
        projectController, appController, gameController, rankingController, achievementsController
    )

    fun run(args: Array<String>) {
        Log.delegate = ConsoleLogger()
        dataDir.mkdirs()

        if (args.isEmpty()) {
            printUsage()
            return
        }

        when (args[0]) {
            "scan" -> cmdScan()
            "connect" -> cmdConnect(args)
            "send" -> cmdSend(args)
            "name" -> cmdName(args)
            "battery" -> cmdBattery()
            "status" -> cmdStatus()
            "achievements" -> cmdAchievements()
            "rankings" -> cmdRankings(args)
            "games" -> cmdGames()
            "forget" -> cmdForget(args)
            "session" -> cmdSession()
            "help" -> printUsage()
            else -> {
                println("Unknown command: ${args[0]}")
                printUsage()
            }
        }
    }

    private fun printUsage() {
        println("""
Zowi CLI - Command line interface for Zowi robot

Usage: zowi-cli <command> [options]

Commands:
  scan                     Scan for available Bluetooth serial ports
  connect <address>        Connect to Zowi via serial port
  send <command> [args]    Send a command to Zowi
    Commands: walk <f|b>, turn <l|r>, stop, happy, sad, jump,
              mouth <matrix>, tone <freq> <duration>, led <matrix>
  name [new_name]          Get or set Zowi name
  battery                  Read Zowi battery level
  status                   Show connection and session status
  achievements             Show achievement progress
  rankings <game>          Show rankings (game: zowi_says|mouths|timeline|gamepad)
  games                    Show game progress
  forget zowi|history      Forget active Zowi or all game history
  session                  Show session information
  help                     Show this help message
        """.trimIndent())
    }

    private fun cmdScan() {
        println("Scanning for serial ports...")
        val ports = CliBtAdapterController.listSerialPorts()
        if (ports.isEmpty()) {
            println("No serial ports found.")
        } else {
            println("Available serial ports:")
            ports.forEach { port ->
                println("  ${port.address}  (${port.name ?: "unknown"})")
            }
        }
    }

    private fun cmdConnect(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: zowi-cli connect <address>")
            return
        }
        val address = args[1]
        println("Connecting to $address...")
        connectToZowiInteractor.connectToZowi(address, true)
            .subscribe(
                {
                    sessionController.saveActiveZowiDeviceAddress(address)
                    println("Connected successfully!")
                },
                { e -> println("Connection failed: ${e.message}") }
            )
    }

    private fun cmdSend(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: zowi-cli send <command> [args]")
            return
        }
        if (!btConnection.isConnected()) {
            println("Not connected. Use 'connect <address>' first.")
            return
        }

        val command = buildCommand(args)
        if (command == null) {
            println("Unknown or invalid command: ${args[1]}")
            return
        }

        sendCommandInteractor.sendCommandToZowi(command)
            .subscribe(
                { println("Command sent: ${command.getCommandValue().trim()}") },
                { e -> println("Send failed: ${e.message}") }
            )
    }

    private fun buildCommand(args: Array<String>): Command? {
        return when (args[1]) {
            "stop" -> StopCommand()
            "walk" -> {
                if (args.size < 3) return null
                val dir = when (args[2]) {
                    "f" -> Command.Direction.FORWARD
                    "b" -> Command.Direction.BACKWARD
                    else -> return null
                }
                ForwardBackwardCommand(Command.Action.WALK, dir, 1000L)
            }
            "turn" -> {
                if (args.size < 3) return null
                val dir = when (args[2]) {
                    "l" -> Command.Direction.LEFT
                    "r" -> Command.Direction.RIGHT
                    else -> return null
                }
                LeftRightCommand(Command.Action.TURN, dir, 1000L)
            }
            "happy" -> MouthCommand(Command.Action.MOUTH_HAPPY_OPEN)
            "sad" -> MouthCommand(Command.Action.MOUTH_SAD)
            "jump" -> StaticCommand(Command.Action.JUMP, 1000L)
            "mouth" -> {
                if (args.size < 3) return null
                MouthCommand(Command.Action.MOUTH_SMILE)
            }
            "tone" -> {
                if (args.size < 4) return null
                val freq = args[2].toIntOrNull() ?: return null
                val dur = args[3].toLongOrNull() ?: return null
                ToneCommand(freq, dur)
            }
            else -> null
        }
    }

    private fun cmdName(args: Array<String>) {
        if (args.size < 2) {
            if (!btConnection.isConnected()) {
                println("Not connected.")
                return
            }
            zowiDataController.getZowiName()
                .subscribe(
                    { name -> println("Zowi name: $name") },
                    { e -> println("Error: ${e.message}") }
                )
        } else {
            val newName = args.drop(1).joinToString(" ")
            changeNameInteractor.changeZowiName(newName)
                .subscribe(
                    { println("Name changed to: $newName") },
                    { e -> println("Error: ${e.message}") }
                )
        }
    }

    private fun cmdBattery() {
        if (!btConnection.isConnected()) {
            println("Not connected.")
            return
        }
        batteryInteractor.measureAndManageZowiBatteryLevel()
            .subscribe(
                { level -> println("Battery: ${if (level) "OK" else "LOW"}") },
                { e -> println("Battery check failed: ${e.message}") }
            )
    }

    private fun cmdStatus() {
        println("=== Session ===")
        println("  Active Zowi: ${sessionController.loadActiveZowiName()}")
        println("  Address: ${sessionController.loadActiveZowiDeviceAddress() ?: "none"}")
        println("  Wizard dismissed: ${sessionController.hasDismissedWizard()}")
        println()
        println("=== Connection ===")
        println("  Connected: ${btConnection.isConnected()}")
        println("  Sending hex: ${btConnection.isSendingHexToZowi()}")
        println()
        println("=== App ===")
        appController.getDaysOfUse().subscribe { days ->
            println("  Days of use: $days")
        }
    }

    private fun cmdAchievements() {
        achievementsController.getAchievementsList()
            .subscribe({ list ->
                val unlocked = list.count { it.unlocked }
                println("=== Achievements ($unlocked/${list.size} unlocked) ===")
                list.forEach { a ->
                    val status = if (a.unlocked) "UNLOCKED" else "locked"
                    println("  ${a.id.padEnd(20)} [${a.type}] $status")
                }
            }, { e -> println("Error: ${e.message}") })
    }

    private fun cmdRankings(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: zowi-cli rankings <game_id>")
            println("Valid games: ZOWI_SAYS_GAME_ID, MOUTHS_GAME_ID, TIMELINE_GAME_ID, GAMEPAD_GAME_ID")
            return
        }
        val gameId = try {
            GameController.GAME_ID.valueOf(args[1].uppercase())
        } catch (e: Exception) {
            println("Invalid game ID. Valid: ZOWI_SAYS_GAME_ID, MOUTHS_GAME_ID, TIMELINE_GAME_ID, GAMEPAD_GAME_ID")
            return
        }

        rankingController.getRanking(gameId)
            .subscribe({ ranking ->
                println("=== Ranking: $gameId (${ranking.size} entries) ===")
                if (ranking.isEmpty()) {
                    println("  (no entries)")
                } else {
                    ranking.forEachIndexed { i, entry ->
                        println("  ${i + 1}. ${entry.playerName} - ${entry.points} pts")
                    }
                }
            }, { e -> println("Error: ${e.message}") })
    }

    private fun cmdGames() {
        println("=== Game Progress ===")
        for (gameId in GameController.GAME_ID.values()) {
            gameController.isFirstPlay(gameId, false).subscribe { firstPlay ->
                val status = if (firstPlay) "never played" else "played"
                println("  $gameId: $status")
            }
        }
    }

    private fun cmdForget(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: zowi-cli forget <zowi|history>")
            return
        }
        when (args[1]) {
            "zowi" -> {
                forgetZowiInteractor.forgetZowi()
                    .subscribe(
                        { println("Active Zowi forgotten.") },
                        { e -> println("Error: ${e.message}") }
                    )
            }
            "history" -> {
                forgetHistoryInteractor.forgetPlayingHistory()
                    .subscribe(
                        { println("All game history forgotten.") },
                        { e -> println("Error: ${e.message}") }
                    )
            }
            else -> println("Unknown target: ${args[1]}. Use 'zowi' or 'history'.")
        }
    }

    private fun cmdSession() {
        println("=== Session Info ===")
        println("  Zowi name: ${sessionController.loadActiveZowiName()}")
        println("  Default name: ${sessionController.loadDefaultZowiName()}")
        println("  Device address: ${sessionController.loadActiveZowiDeviceAddress() ?: "not set"}")
        println("  Wizard dismissed: ${sessionController.hasDismissedWizard()}")
        println("  Data dir: ${dataDir.absolutePath}")
        println("  Store file: ${File(dataDir, "zowi.json").absolutePath}")
    }
}

fun main(args: Array<String>) {
    ZowiCli().run(args)
}
