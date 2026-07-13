package com.bq.zowi.cli.adapters

import com.bq.zowi.cli.storage.JsonKeyValueStore
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CliSessionControllerTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private lateinit var store: JsonKeyValueStore
    private lateinit var controller: CliSessionController

    @Before
    fun setUp() {
        store = JsonKeyValueStore(File(tempDir.root, "session.json"))
        controller = CliSessionController(store)
    }

    @Test
    fun `loadActiveZowiDeviceAddress returns null by default`() {
        assertNull(controller.loadActiveZowiDeviceAddress())
    }

    @Test
    fun `save and load active Zowi device address`() {
        controller.saveActiveZowiDeviceAddress("AA:BB:CC:DD:EE:FF")
        assertEquals("AA:BB:CC:DD:EE:FF", controller.loadActiveZowiDeviceAddress())
    }

    @Test
    fun `save and load active Zowi name`() {
        controller.saveActiveZowiName("CustomZowi")
        assertEquals("CustomZowi", controller.loadActiveZowiName())
    }

    @Test
    fun `loadActiveZowiName returns default when stored name is true`() {
        controller.saveActiveZowiName("true")
        assertEquals("Zowi", controller.loadActiveZowiName())
    }

    @Test
    fun `loadActiveZowiName returns default when stored name is false`() {
        controller.saveActiveZowiName("false")
        assertEquals("Zowi", controller.loadActiveZowiName())
    }

    @Test
    fun `loadActiveZowiName returns default when stored name is empty`() {
        controller.saveActiveZowiName("")
        assertEquals("Zowi", controller.loadActiveZowiName())
    }

    @Test
    fun `loadActiveZowiName returns default when nothing stored`() {
        assertEquals("Zowi", controller.loadActiveZowiName())
    }

    @Test
    fun `custom defaultZowiName is used`() {
        val custom = CliSessionController(store, "MiZowi")
        assertEquals("MiZowi", custom.loadActiveZowiName())
    }

    @Test
    fun `hasDismissedWizard returns false by default`() {
        assertFalse(controller.hasDismissedWizard())
    }

    @Test
    fun `saveWizardDismissed persists state`() {
        controller.saveWizardDismissed(true)
        assertTrue(controller.hasDismissedWizard())
        controller.saveWizardDismissed(false)
        assertFalse(controller.hasDismissedWizard())
    }

    @Test
    fun `resetActiveZowi clears address and name`() {
        controller.saveActiveZowiDeviceAddress("11:22:33:44:55:66")
        controller.saveActiveZowiName("MyZowi")
        controller.resetActiveZowi()
        assertNull(controller.loadActiveZowiDeviceAddress())
        assertEquals("Zowi", controller.loadActiveZowiName())
    }

    @Test
    fun `data persists across controller instances`() {
        controller.saveActiveZowiDeviceAddress("AA:BB:CC:DD:EE:FF")
        controller.saveActiveZowiName("PersistentZowi")

        val controller2 = CliSessionController(store)
        assertEquals("AA:BB:CC:DD:EE:FF", controller2.loadActiveZowiDeviceAddress())
        assertEquals("PersistentZowi", controller2.loadActiveZowiName())
    }
}
