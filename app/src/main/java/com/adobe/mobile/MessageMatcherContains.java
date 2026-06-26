package com.adobe.mobile;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
class MessageMatcherContains extends MessageMatcher {
    MessageMatcherContains() {
    }

    @Override // com.adobe.mobile.MessageMatcher
    protected boolean matches(Object value) {
        boolean valueIsString = value instanceof String;
        boolean valueIsNumber = value instanceof Number;
        if (!valueIsString && !valueIsNumber) {
            return false;
        }
        String valueToSearchFor = value.toString();
        for (Object v : this.values) {
            if ((v instanceof String) && Pattern.compile(Pattern.quote(v.toString()), 2).matcher(valueToSearchFor).find()) {
                return true;
            }
        }
        return false;
    }
}
