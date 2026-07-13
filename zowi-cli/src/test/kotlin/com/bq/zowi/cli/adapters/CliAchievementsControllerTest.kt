package com.bq.zowi.cli.adapters

import com.bq.zowi.api.AssetProvider
import com.bq.zowi.cli.storage.JsonKeyValueStore
import com.bq.zowi.models.Achievement
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito.*
import java.io.ByteArrayInputStream
import java.io.File

class CliAchievementsControllerTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private lateinit var store: JsonKeyValueStore
    private lateinit var assetProvider: AssetProvider
    private lateinit var controller: CliAchievementsController

    private val defaultAchievementJson = """
        [
            {"id":"crusaito","type":"movement","unlocked":false},
            {"id":"ascending_turn","type":"movement","unlocked":true},
            {"id":"super_happy","type":"animation","unlocked":false}
        ]
    """.trimIndent()

    @Before
    fun setUp() {
        store = JsonKeyValueStore(File(tempDir.root, "achievements.json"))
        assetProvider = mock(AssetProvider::class.java)
        `when`(assetProvider.openAsset("achievements/initial_list.json")).thenAnswer {
            ByteArrayInputStream(defaultAchievementJson.toByteArray())
        }
        controller = CliAchievementsController(store, assetProvider)
    }

    @Test
    fun `default list loaded from asset when no stored data`() {
        val list = controller.getAchievementsList().blockingGet()
        assertEquals(3, list.size)
    }

    @Test
    fun `ascending_turn is unlocked by default from initial list`() {
        val list = controller.getAchievementsList().blockingGet()
        val asc = list.first { it.id == "ascending_turn" }
        assertTrue(asc.unlocked)
    }

    @Test
    fun `other achievements are locked by default`() {
        val list = controller.getAchievementsList().blockingGet()
        val crusaito = list.first { it.id == "crusaito" }
        assertFalse(crusaito.unlocked)
    }

    @Test
    fun `unlockAchievement sets unlocked to true`() {
        val achievement = controller.unlockAchievement(Achievement.Id.crusaito).blockingGet()
        assertTrue(achievement.unlocked)
        val retrieved = controller.getAchievement(Achievement.Id.crusaito).blockingGet()
        assertTrue(retrieved.unlocked)
    }

    @Test
    fun `getAchievement retrieves by id`() {
        val achievement = controller.getAchievement(Achievement.Id.super_happy).blockingGet()
        assertEquals("super_happy", achievement.id)
        assertEquals("animation", achievement.type)
    }

    @Test
    fun `unlockAllAchievements unlocks everything`() {
        try {
            controller.unlockAllAchievements().blockingGet()
        } catch (_: Throwable) { }

        val list = controller.getAchievementsList().blockingGet()
        assertTrue(list.all { it.unlocked })
    }

    @Test
    fun `achievements persist across controller instances`() {
        controller.unlockAchievement(Achievement.Id.crusaito).blockingGet()
        val controller2 = CliAchievementsController(store, assetProvider)
        val achievement = controller2.getAchievement(Achievement.Id.crusaito).blockingGet()
        assertTrue(achievement.unlocked)
    }

    @Test
    fun `assetProvider fallback to default list on error`() {
        val failingProvider = mock(AssetProvider::class.java)
        `when`(failingProvider.openAsset(anyString())).thenThrow(java.io.IOException("no asset"))

        val ctrl = CliAchievementsController(store, failingProvider)
        val list = ctrl.getAchievementsList().blockingGet()
        assertEquals(Achievement.Id.values().size, list.size)
    }

    private fun anyString(): String = org.mockito.ArgumentMatchers.anyString()
}
