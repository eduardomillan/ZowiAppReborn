package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
final class MessageMatcherNotContains extends MessageMatcherContains {
    MessageMatcherNotContains() {
    }

    @Override // com.adobe.mobile.MessageMatcherContains, com.adobe.mobile.MessageMatcher
    protected boolean matches(Object value) {
        return !super.matches(value);
    }
}
