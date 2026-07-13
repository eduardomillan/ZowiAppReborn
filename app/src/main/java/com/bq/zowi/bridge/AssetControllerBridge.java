package com.bq.zowi.bridge;

import com.bq.zowi.controllers.AssetController;
import java.io.IOException;
import java.io.InputStream;

public class AssetControllerBridge implements AssetController {
    private final com.bq.zowi.api.AssetController core;

    public AssetControllerBridge(com.bq.zowi.api.AssetController core) {
        this.core = core;
    }

    @Override
    public InputStream openAsset(String path) throws IOException {
        return core.openAsset(path);
    }
}
