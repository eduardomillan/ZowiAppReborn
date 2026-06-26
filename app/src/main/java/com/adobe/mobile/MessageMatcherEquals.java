package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
class MessageMatcherEquals extends MessageMatcher {
    MessageMatcherEquals() {
    }

    @Override // com.adobe.mobile.MessageMatcher
    protected boolean matches(Object value) {
        for (Object potentialMatch : this.values) {
            if ((potentialMatch instanceof String) && (value instanceof String)) {
                if (potentialMatch.toString().compareToIgnoreCase(value.toString()) == 0) {
                    return true;
                }
            } else if ((potentialMatch instanceof Number) && (value instanceof Number)) {
                if (((Number) potentialMatch).doubleValue() == ((Number) value).doubleValue()) {
                    return true;
                }
            } else if ((potentialMatch instanceof Number) && (value instanceof String)) {
                Double valueAsDouble = tryParseDouble(value);
                if (valueAsDouble != null && ((Number) potentialMatch).doubleValue() == tryParseDouble(value).doubleValue()) {
                    return true;
                }
            } else if ((potentialMatch instanceof String) && (value instanceof Number) && potentialMatch.toString().compareToIgnoreCase(value.toString()) == 0) {
                return true;
            }
        }
        return false;
    }
}
