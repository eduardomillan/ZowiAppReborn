package com.bq.zowi.api

import java.io.IOException
import java.io.InputStream

/**
 * Platform-agnostic asset reader abstraction.
 * Replaces Android AssetManager in the core module.
 * Implementations: AndroidAssetProvider (AssetManager), FileAssetProvider (CLI).
 */
interface AssetProvider {
    @Throws(IOException::class)
    fun openAsset(path: String): InputStream
}
