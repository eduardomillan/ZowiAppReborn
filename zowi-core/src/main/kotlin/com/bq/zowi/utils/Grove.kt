package com.bq.zowi.utils

import com.bq.zowi.api.Log

/**
 * Platform-agnostic logging facade. Delegates to [com.bq.zowi.api.Log.delegate].
 * This is the Kotlin equivalent of the Java Grove class.
 */
object Grove {
    fun d(message: String, vararg args: Any) = Log.d(message, *args)
    fun d(t: Throwable, message: String, vararg args: Any) = Log.d(t, message, *args)
    fun e(message: String, vararg args: Any) = Log.e(message, *args)
    fun e(t: Throwable, message: String, vararg args: Any) = Log.e(t, message, *args)
    fun i(message: String, vararg args: Any) = Log.i(message, *args)
    fun w(message: String, vararg args: Any) = Log.w(message, *args)
}
