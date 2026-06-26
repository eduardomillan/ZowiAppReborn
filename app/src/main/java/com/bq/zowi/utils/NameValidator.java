package com.bq.zowi.utils;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class NameValidator {
    private static final String NAME_VALIDATOR_PATTERN = "^[A-Za-z]+$";

    public static boolean isNameValid(String name) {
        return Pattern.matches(NAME_VALIDATOR_PATTERN, name) && name.length() <= 10;
    }
}
