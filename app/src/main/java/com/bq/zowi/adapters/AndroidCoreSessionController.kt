package com.bq.zowi.adapters

import com.bq.zowi.api.KeyValueStore
import com.bq.zowi.api.SessionController

class AndroidCoreSessionController(
    private val store: KeyValueStore,
    private val defaultZowiName: String = "Zowi"
) : SessionController {

    companion object {
        private const val KEY_ACTIVE_ADDRESS = "activeZowiDeviceAddress"
        private const val KEY_ACTIVE_NAME = "activeZowiName"
        private const val KEY_WIZARD_DISMISSED = "wizardDismissed"
    }

    override fun loadActiveZowiDeviceAddress(): String? {
        return store.getString(KEY_ACTIVE_ADDRESS, null)
    }

    override fun loadActiveZowiName(): String {
        val name = store.getString(KEY_ACTIVE_NAME, defaultZowiName)
        return if (name == "true" || name == "false" || name.isNullOrEmpty()) {
            defaultZowiName
        } else {
            name
        }
    }

    override fun loadDefaultZowiName(): String = defaultZowiName

    override fun hasDismissedWizard(): Boolean {
        return store.getBoolean(KEY_WIZARD_DISMISSED, false)
    }

    override fun resetActiveZowi() {
        store.remove(KEY_ACTIVE_ADDRESS)
        store.remove(KEY_ACTIVE_NAME)
        store.commit()
    }

    override fun saveActiveZowiDeviceAddress(address: String) {
        store.putString(KEY_ACTIVE_ADDRESS, address)
        store.commit()
    }

    override fun saveActiveZowiName(name: String) {
        store.putString(KEY_ACTIVE_NAME, name)
        store.commit()
    }

    override fun saveWizardDismissed(dismissed: Boolean) {
        store.putBoolean(KEY_WIZARD_DISMISSED, dismissed)
        store.commit()
    }
}
