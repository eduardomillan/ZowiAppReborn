package com.bq.zowi.cli.storage

import com.bq.zowi.api.KeyValueStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonKeyValueStore(private val file: File) : KeyValueStore {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private var data: MutableMap<String, Any> = mutableMapOf()

    init {
        load()
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return data[key] as? String ?: defaultValue
    }

    override fun putString(key: String, value: String?) {
        if (value == null) {
            data.remove(key)
        } else {
            data[key] = value
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return (data[key] as? Boolean) ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        data[key] = value
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return (data[key] as? Number)?.toLong() ?: defaultValue
    }

    override fun putLong(key: String, value: Long) {
        data[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return (data[key] as? Number)?.toInt() ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        data[key] = value
    }

    override fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        val raw = data[key]
        return when (raw) {
            is List<*> -> raw.filterIsInstance<String>().toSet()
            else -> defaultValue
        }
    }

    override fun putStringSet(key: String, values: Set<String>?) {
        if (values == null) {
            data.remove(key)
        } else {
            data[key] = ArrayList(values)
        }
    }

    override fun getAll(): Map<String, *> = HashMap(data)

    override fun remove(key: String) {
        data.remove(key)
    }

    override fun commit() {
        file.parentFile?.mkdirs()
        file.writeText(gson.toJson(data))
    }

    private fun load() {
        if (file.exists()) {
            try {
                val type = object : TypeToken<MutableMap<String, Any>>() {}.type
                data = gson.fromJson(file.readText(), type) ?: mutableMapOf()
            } catch (e: Exception) {
                data = mutableMapOf()
            }
        }
    }
}
