package com.bq.zowi.adapters

import com.bq.zowi.api.AssetController
import com.bq.zowi.api.AssetProvider
import java.io.IOException
import java.io.InputStream

class AndroidCoreAssetController(private val assetProvider: AssetProvider) : AssetController {

    @Throws(IOException::class)
    override fun openAsset(path: String): InputStream {
        return assetProvider.openAsset(path)
    }
}
