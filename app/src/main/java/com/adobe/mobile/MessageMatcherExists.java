package com.adobe.mobile;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class MessageMatcherExists extends MessageMatcher {
    MessageMatcherExists() {
    }

    @Override // com.adobe.mobile.MessageMatcher
    protected boolean matchesInMaps(Map<String, Object>... maps) {
        Object value = null;
        if (maps == null || maps.length <= 0) {
            return false;
        }
        int length = maps.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Map<String, Object> map = maps[i];
            if (map == null || !map.containsKey(this.key)) {
                i++;
            } else {
                value = map.get(this.key);
                break;
            }
        }
        return value != null;
    }
}
