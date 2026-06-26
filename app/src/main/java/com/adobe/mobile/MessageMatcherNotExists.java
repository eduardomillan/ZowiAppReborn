package com.adobe.mobile;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class MessageMatcherNotExists extends MessageMatcherExists {
    MessageMatcherNotExists() {
    }

    @Override // com.adobe.mobile.MessageMatcherExists, com.adobe.mobile.MessageMatcher
    protected boolean matchesInMaps(Map<String, Object>... maps) {
        return !super.matchesInMaps(maps);
    }
}
