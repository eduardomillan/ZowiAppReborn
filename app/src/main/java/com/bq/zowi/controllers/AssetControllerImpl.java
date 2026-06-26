package com.bq.zowi.controllers;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class AssetControllerImpl implements AssetController {
    private final AssetManager assetManager;

    public AssetControllerImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override // com.bq.zowi.controllers.AssetController
    public InputStream openAsset(String assetPath) throws IOException {
        return this.assetManager.open(assetPath);
    }
}
