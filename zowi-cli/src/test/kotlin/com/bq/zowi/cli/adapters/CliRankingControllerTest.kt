package com.bq.zowi.cli.adapters

import com.bq.zowi.api.GameController
import com.bq.zowi.cli.storage.JsonKeyValueStore
import com.bq.zowi.models.RankingEntry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CliRankingControllerTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private lateinit var store: JsonKeyValueStore
    private lateinit var controller: CliRankingController

    @Before
    fun setUp() {
        store = JsonKeyValueStore(File(tempDir.root, "ranking.json"))
        controller = CliRankingController(store)
    }

    @Test
    fun `empty ranking by default`() {
        val ranking = controller.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).blockingGet()
        assertTrue(ranking.isEmpty())
    }

    @Test
    fun `saveRankingEntry adds entry`() {
        val entry = RankingEntry(points = 100, playerName = "Alice")
        val ranking = controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, entry).blockingGet()
        assertEquals(1, ranking.size)
        assertEquals("Alice", ranking[0].playerName)
        assertEquals(100, ranking[0].points)
    }

    @Test
    fun `ranking sorted by points descending`() {
        controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, RankingEntry(50, "Low")).blockingGet()
        controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, RankingEntry(200, "High")).blockingGet()
        controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, RankingEntry(100, "Mid")).blockingGet()

        val ranking = controller.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).blockingGet()
        assertEquals("High", ranking[0].playerName)
        assertEquals("Mid", ranking[1].playerName)
        assertEquals("Low", ranking[2].playerName)
    }

    @Test
    fun `max 10 entries maintained`() {
        for (i in 1..15) {
            controller.saveRankingEntry(
                GameController.GAME_ID.ZOWI_SAYS_GAME_ID,
                RankingEntry(points = i, playerName = "Player$i")
            ).blockingGet()
        }
        val ranking = controller.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).blockingGet()
        assertEquals(10, ranking.size)
        assertEquals(15, ranking[0].points)
        assertEquals(6, ranking[9].points)
    }

    @Test
    fun `isScoreInTop10 true when ranking has fewer than 10 entries`() {
        controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, RankingEntry(100, "A")).blockingGet()
        val inTop = controller.isScoreInTop10(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, 1).blockingGet()
        assertTrue(inTop)
    }

    @Test
    fun `isScoreInTop10 false when score is below 10th entry`() {
        for (i in 1..10) {
            controller.saveRankingEntry(
                GameController.GAME_ID.ZOWI_SAYS_GAME_ID,
                RankingEntry(points = i * 10, playerName = "P$i")
            ).blockingGet()
        }
        val inTop = controller.isScoreInTop10(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, 1).blockingGet()
        assertFalse(inTop)
    }

    @Test
    fun `isScoreInTop10 true when score beats 10th entry`() {
        for (i in 1..10) {
            controller.saveRankingEntry(
                GameController.GAME_ID.ZOWI_SAYS_GAME_ID,
                RankingEntry(points = i * 10, playerName = "P$i")
            ).blockingGet()
        }
        val inTop = controller.isScoreInTop10(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, 101).blockingGet()
        assertTrue(inTop)
    }

    @Test
    fun `resetAllRankings clears all`() {
        controller.saveRankingEntry(GameController.GAME_ID.ZOWI_SAYS_GAME_ID, RankingEntry(100, "A")).blockingGet()
        controller.saveRankingEntry(GameController.GAME_ID.MOUTHS_GAME_ID, RankingEntry(200, "B")).blockingGet()

        try {
            controller.resetAllRankings().blockingGet()
        } catch (_: Throwable) { }

        assertTrue(controller.getRanking(GameController.GAME_ID.ZOWI_SAYS_GAME_ID).blockingGet().isEmpty())
        assertTrue(controller.getRanking(GameController.GAME_ID.MOUTHS_GAME_ID).blockingGet().isEmpty())
    }
}
