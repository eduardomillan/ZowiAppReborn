package com.bq.zowi.adapters

import android.content.res.AssetManager
import com.bq.zowi.api.AssetProvider
import java.io.IOException
import java.io.InputStream

class AndroidAssetProvider(private val assetManager: AssetManager) : AssetProvider {

    @Throws(IOException::class)
    override fun openAsset(path: String): InputStream {
        return assetManager.open(path)
    }
}
