package com.bq.zowi.api

import io.reactivex.Single

interface SessionController {
    fun loadActiveZowiDeviceAddress(): String?
    fun loadActiveZowiName(): String
    fun loadDefaultZowiName(): String
    fun hasDismissedWizard(): Boolean
    fun resetActiveZowi()
    fun saveActiveZowiDeviceAddress(address: String)
    fun saveActiveZowiName(name: String)
    fun saveWizardDismissed(dismissed: Boolean)
}
