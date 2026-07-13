package com.bq.zowi.cli.adapters

import com.bq.zowi.api.AppController
import com.bq.zowi.api.KeyValueStore
import io.reactivex.Single
import java.util.Calendar

class CliAppController(private val store: KeyValueStore) : AppController {

    companion object {
        private const val KEY_APP_LOGS = "key:appLogs"
    }

    override fun getDaysOfUse(): Single<Int> {
        return Single.fromCallable {
            val logs = store.getStringSet(KEY_APP_LOGS, emptySet()) ?: emptySet()
            logs.size
        }
    }

    override fun isFirstUsage(): Single<Boolean> {
        return Single.fromCallable {
            val logs = store.getStringSet(KEY_APP_LOGS, emptySet()) ?: emptySet()
            logs.isEmpty()
        }
    }

    override fun logAppStarted(): Single<Void> {
        @Suppress("UNCHECKED_CAST")
        return Single.fromCallable {
            val logs = (store.getStringSet(KEY_APP_LOGS, emptySet()) ?: emptySet()).toMutableSet()
            logs.add(getDayNormalizedTimestamp())
            store.putStringSet(KEY_APP_LOGS, logs)
            store.commit()
            null as Void
        }
    }

    override fun resetAppLogs(): Single<Void> {
        @Suppress("UNCHECKED_CAST")
        return Single.fromCallable {
            store.remove(KEY_APP_LOGS)
            store.commit()
            null as Void
        }
    }

    private fun getDayNormalizedTimestamp(): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis.toString()
    }
}
