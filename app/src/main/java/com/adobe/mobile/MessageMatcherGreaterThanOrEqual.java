package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
final class MessageMatcherGreaterThanOrEqual extends MessageMatcher {
    MessageMatcherGreaterThanOrEqual() {
    }

    @Override // com.adobe.mobile.MessageMatcher
    protected boolean matches(Object value) {
        Double valueAsDouble = tryParseDouble(value);
        if (valueAsDouble == null) {
            return false;
        }
        for (Object v : this.values) {
            if ((v instanceof Number) && valueAsDouble.doubleValue() >= ((Number) v).doubleValue()) {
                return true;
            }
        }
        return false;
    }
}
