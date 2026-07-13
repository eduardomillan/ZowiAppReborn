package com.bq.zowi.api

/**
 * Platform-agnostic key-value store abstraction.
 * Replaces Android SharedPreferences in the core module.
 * Implementations: AndroidKeyValueStore (SharedPreferences), JsonFileStore (CLI).
 */
interface KeyValueStore {
    fun getString(key: String, defaultValue: String?): String?
    fun putString(key: String, value: String?)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getLong(key: String, defaultValue: Long): Long
    fun putLong(key: String, value: Long)
    fun getInt(key: String, defaultValue: Int): Int
    fun putInt(key: String, value: Int)
    fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>?
    fun putStringSet(key: String, values: Set<String>?)
    fun getAll(): Map<String, *>
    fun remove(key: String)
    fun commit()
}
