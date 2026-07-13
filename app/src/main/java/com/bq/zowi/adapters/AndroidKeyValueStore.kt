package com.bq.zowi.adapters

import android.content.SharedPreferences
import com.bq.zowi.api.KeyValueStore

class AndroidKeyValueStore(private val prefs: SharedPreferences) : KeyValueStore {

    override fun getString(key: String, defaultValue: String?): String? {
        return prefs.getString(key, defaultValue)
    }

    override fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return prefs.getLong(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    override fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        return prefs.getStringSet(key, defaultValue)
    }

    override fun putStringSet(key: String, values: Set<String>?) {
        prefs.edit().putStringSet(key, values).apply()
    }

    override fun getAll(): Map<String, *> {
        return prefs.all
    }

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    override fun commit() {
        prefs.edit().commit()
    }
}
