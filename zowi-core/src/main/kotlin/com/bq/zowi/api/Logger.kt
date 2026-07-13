package com.bq.zowi.api

/**
 * Platform-agnostic logging abstraction.
 * Replaces Grove (Timber-like) in the core module.
 * Implementations: Android Grove tree, CLI stdout logger.
 */
interface Logger {
    fun d(message: String, vararg args: Any)
    fun d(t: Throwable, message: String, vararg args: Any)
    fun e(message: String, vararg args: Any)
    fun e(t: Throwable, message: String, vararg args: Any)
    fun i(message: String, vararg args: Any)
    fun w(message: String, vararg args: Any)
}

/**
 * Global logger instance. Platform modules must set this at startup.
 */
object Log {
    var delegate: Logger = NoopLogger

    fun d(message: String, vararg args: Any) = delegate.d(message, *args)
    fun d(t: Throwable, message: String, vararg args: Any) = delegate.d(t, message, *args)
    fun e(message: String, vararg args: Any) = delegate.e(message, *args)
    fun e(t: Throwable, message: String, vararg args: Any) = delegate.e(t, message, *args)
    fun i(message: String, vararg args: Any) = delegate.i(message, *args)
    fun w(message: String, vararg args: Any) = delegate.w(message, *args)
}

private object NoopLogger : Logger {
    override fun d(message: String, vararg args: Any) {}
    override fun d(t: Throwable, message: String, vararg args: Any) {}
    override fun e(message: String, vararg args: Any) {}
    override fun e(t: Throwable, message: String, vararg args: Any) {}
    override fun i(message: String, vararg args: Any) {}
    override fun w(message: String, vararg args: Any) {}
}
