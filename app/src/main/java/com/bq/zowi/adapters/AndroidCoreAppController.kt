package com.bq.zowi.adapters

import com.bq.zowi.api.AppController
import com.bq.zowi.api.KeyValueStore
import io.reactivex.Single
import java.util.Calendar

class AndroidCoreAppController(private val store: KeyValueStore) : AppController {

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

    @Suppress("UNCHECKED_CAST")
    override fun logAppStarted(): Single<Void> {
        return Single.fromCallable {
            val logs = (store.getStringSet(KEY_APP_LOGS, emptySet()) ?: emptySet()).toMutableSet()
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            logs.add(cal.timeInMillis.toString())
            store.putStringSet(KEY_APP_LOGS, logs)
            store.commit()
            null as Void
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun resetAppLogs(): Single<Void> {
        return Single.fromCallable {
            store.remove(KEY_APP_LOGS)
            store.commit()
            null as Void
        }
    }
}
