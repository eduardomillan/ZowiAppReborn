package com.bq.zowi.cli.storage

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class JsonKeyValueStoreTest {

    @Rule
    @JvmField
    val tempDir = TemporaryFolder()

    private fun createStore(name: String = "test.json"): JsonKeyValueStore {
        return JsonKeyValueStore(File(tempDir.root, name))
    }

    @Test
    fun `putString and getString return correct value`() {
        val store = createStore()
        store.putString("key", "hello")
        assertEquals("hello", store.getString("key", null))
    }

    @Test
    fun `getString returns default when key missing`() {
        val store = createStore()
        assertEquals("fallback", store.getString("missing", "fallback"))
    }

    @Test
    fun `getString returns null when key missing and default is null`() {
        val store = createStore()
        assertNull(store.getString("missing", null))
    }

    @Test
    fun `putBoolean and getBoolean return correct value`() {
        val store = createStore()
        store.putBoolean("flag", true)
        assertTrue(store.getBoolean("flag", false))
        store.putBoolean("flag", false)
        assertFalse(store.getBoolean("flag", true))
    }

    @Test
    fun `getBoolean returns default when key missing`() {
        val store = createStore()
        assertTrue(store.getBoolean("missing", true))
    }

    @Test
    fun `putLong and getLong return correct value`() {
        val store = createStore()
        store.putLong("counter", 9876543210L)
        assertEquals(9876543210L, store.getLong("counter", 0L))
    }

    @Test
    fun `getLong returns default when key missing`() {
        val store = createStore()
        assertEquals(42L, store.getLong("missing", 42L))
    }

    @Test
    fun `putInt and getInt return correct value`() {
        val store = createStore()
        store.putInt("num", 42)
        assertEquals(42, store.getInt("num", 0))
    }

    @Test
    fun `getInt returns default when key missing`() {
        val store = createStore()
        assertEquals(7, store.getInt("missing", 7))
    }

    @Test
    fun `putStringSet and getStringSet return correct value`() {
        val store = createStore()
        val set = setOf("a", "b", "c")
        store.putStringSet("tags", set)
        assertEquals(set, store.getStringSet("tags", null))
    }

    @Test
    fun `getStringSet returns default when key missing`() {
        val store = createStore()
        val default = setOf("x")
        assertEquals(default, store.getStringSet("missing", default))
    }

    @Test
    fun `putString null removes key`() {
        val store = createStore()
        store.putString("key", "value")
        assertEquals("value", store.getString("key", null))
        store.putString("key", null)
        assertNull(store.getString("key", null))
    }

    @Test
    fun `putStringSet null removes key`() {
        val store = createStore()
        store.putStringSet("tags", setOf("a"))
        assertNotNull(store.getStringSet("tags", null))
        store.putStringSet("tags", null)
        assertNull(store.getStringSet("tags", null))
    }

    @Test
    fun `commit persists to disk and reload preserves data`() {
        val file = File(tempDir.root, "persist.json")
        val store1 = JsonKeyValueStore(file)
        store1.putString("name", "Zowi")
        store1.putBoolean("active", true)
        store1.putLong("score", 100L)
        store1.commit()

        val store2 = JsonKeyValueStore(file)
        assertEquals("Zowi", store2.getString("name", null))
        assertTrue(store2.getBoolean("active", false))
        assertEquals(100L, store2.getLong("score", 0L))
    }

    @Test
    fun `getAll returns all entries`() {
        val store = createStore()
        store.putString("a", "1")
        store.putInt("b", 2)
        store.putBoolean("c", true)

        val all = store.getAll()
        assertEquals(3, all.size)
        assertEquals("1", all["a"])
        assertEquals(2, all["b"])
        assertEquals(true, all["c"])
    }

    @Test
    fun `remove deletes a key`() {
        val store = createStore()
        store.putString("key", "value")
        store.remove("key")
        assertNull(store.getString("key", null))
    }

    @Test
    fun `commit creates parent directories`() {
        val file = File(tempDir.root, "sub/dir/store.json")
        val store = JsonKeyValueStore(file)
        store.putString("key", "value")
        store.commit()
        assertTrue(file.exists())
    }
}
