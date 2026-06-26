package com.adobe.mobile;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class MessageMatcherStartsWith extends MessageMatcher {
    MessageMatcherStartsWith() {
    }

    @Override // com.adobe.mobile.MessageMatcher
    protected boolean matches(Object value) {
        boolean valueIsString = value instanceof String;
        boolean valueIsNumber = value instanceof Number;
        if (!valueIsString && !valueIsNumber) {
            return false;
        }
        String stringToMatch = value.toString();
        for (Object v : this.values) {
            if ((v instanceof String) && stringToMatch.matches("(?i)" + Pattern.quote(v.toString()) + ".*")) {
                return true;
            }
        }
        return false;
    }
}
