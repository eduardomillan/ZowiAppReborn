package com.comscore.utils;

import java.io.File;
import java.io.FilenameFilter;

/* JADX INFO: loaded from: classes.dex */
final class b implements FilenameFilter {
    b() {
    }

    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return str.startsWith(Constants.CACHE_FILENAME);
    }
}
