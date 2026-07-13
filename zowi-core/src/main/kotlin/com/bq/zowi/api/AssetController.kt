package com.bq.zowi.api

import java.io.IOException
import java.io.InputStream

interface AssetController {
    @Throws(IOException::class)
    fun openAsset(path: String): InputStream
}
