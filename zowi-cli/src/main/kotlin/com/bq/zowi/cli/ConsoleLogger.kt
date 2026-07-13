package com.bq.zowi.cli

import com.bq.zowi.api.Log
import com.bq.zowi.api.Logger

class ConsoleLogger : Logger {
    override fun d(message: String, vararg args: Any) {
        println("[DEBUG] ${format(message, *args)}")
    }

    override fun d(t: Throwable, message: String, vararg args: Any) {
        println("[DEBUG] ${format(message, *args)}: ${t.message}")
    }

    override fun e(message: String, vararg args: Any) {
        System.err.println("[ERROR] ${format(message, *args)}")
    }

    override fun e(t: Throwable, message: String, vararg args: Any) {
        System.err.println("[ERROR] ${format(message, *args)}: ${t.message}")
    }

    override fun i(message: String, vararg args: Any) {
        println("[INFO] ${format(message, *args)}")
    }

    override fun w(message: String, vararg args: Any) {
        println("[WARN] ${format(message, *args)}")
    }

    private fun format(message: String, vararg args: Any): String {
        return try {
            if (args.isEmpty()) message else String.format(message, *args)
        } catch (e: Exception) {
            message
        }
    }
}
