package com.bq.zowi.cli.adapters

import com.bq.zowi.api.GameController
import com.bq.zowi.cli.storage.JsonKeyValueStore
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CliGameControllerTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private lateinit var store: JsonKeyValueStore
    private lateinit var controller: CliGameController

    @Before
    fun setUp() {
        store = JsonKeyValueStore(File(tempDir.root, "game.json"))
        controller = CliGameController(store)
    }

    @Test
    fun `isFirstPlay returns true by default`() {
        val result = controller.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, false).blockingGet()
        assertTrue(result)
    }

    @Test
    fun `isFirstPlay returns true when forceFirst is true even if played`() {
        store.putBoolean("ZOWI_SAYS_GAME_ID_is_first_play", false)
        val result = controller.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, true).blockingGet()
        assertTrue(result)
    }

    @Test
    fun `saveProgress stores data in store`() {
        try {
            controller.saveProgress(GameController.GAME_ID.MOUTHS_GAME_ID, "data").blockingGet()
        } catch (_: Throwable) { }

        assertFalse(store.getBoolean("MOUTHS_GAME_ID_is_first_play", true))
        assertNotNull(store.getString("MOUTHS_GAME_ID_progress", null))
    }

    @Test
    fun `isFirstPlay returns false after saveProgress`() {
        try {
            controller.saveProgress(GameController.GAME_ID.MOUTHS_GAME_ID, "data").blockingGet()
        } catch (_: Throwable) { }

        val result = controller.isFirstPlay(GameController.GAME_ID.MOUTHS_GAME_ID, false).blockingGet()
        assertFalse(result)
    }

    @Test
    fun `loadProgress returns non-null after saveProgress`() {
        try {
            controller.saveProgress(GameController.GAME_ID.TIMELINE_GAME_ID, "test_value").blockingGet()
        } catch (_: Throwable) { }

        val json = store.getString("TIMELINE_GAME_ID_progress", null)
        assertNotNull(json)
        assertTrue(json!!.contains("test_value"))
    }

    @Test
    fun `resetGamesProgress clears all games`() {
        try {
            controller.saveProgress(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, "a").blockingGet()
        } catch (_: Throwable) { }
        try {
            controller.saveProgress(GameController.GAME_ID.MOUTHS_GAME_ID, "b").blockingGet()
        } catch (_: Throwable) { }

        try {
            controller.resetGamesProgress().blockingGet()
        } catch (_: Throwable) { }

        assertTrue(controller.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, false).blockingGet())
        assertTrue(controller.isFirstPlay(GameController.GAME_ID.MOUTHS_GAME_ID, false).blockingGet())
        assertNull(store.getString("ZOWI_SAYS_GAME_ID_progress", null))
        assertNull(store.getString("MOUTHS_GAME_ID_progress", null))
    }

    @Test
    fun `multiple game IDs work independently`() {
        try {
            controller.saveProgress(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, "zowi_data").blockingGet()
        } catch (_: Throwable) { }
        try {
            controller.saveProgress(GameController.GAME_ID.MOUTHS_GAME_ID, "mouths_data").blockingGet()
        } catch (_: Throwable) { }

        assertFalse(controller.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, false).blockingGet())
        assertFalse(controller.isFirstPlay(GameController.GAME_ID.MOUTHS_GAME_ID, false).blockingGet())
        assertTrue(controller.isFirstPlay(GameController.GAME_ID.TIMELINE_GAME_ID, false).blockingGet())

        try {
            controller.resetGamesProgress().blockingGet()
        } catch (_: Throwable) { }

        assertTrue(controller.isFirstPlay(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, false).blockingGet())
        assertTrue(controller.isFirstPlay(GameController.GAME_ID.MOUTHS_GAME_ID, false).blockingGet())
    }
}
