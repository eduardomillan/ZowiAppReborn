package com.bq.zowi.cli.storage

import com.bq.zowi.api.AssetProvider
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

class FileAssetProvider(private val assetsDir: File) : AssetProvider {

    @Throws(IOException::class)
    override fun openAsset(path: String): InputStream {
        val file = File(assetsDir, path)
        if (!file.exists()) {
            throw IOException("Asset not found: $path")
        }
        return FileInputStream(file)
    }
}
