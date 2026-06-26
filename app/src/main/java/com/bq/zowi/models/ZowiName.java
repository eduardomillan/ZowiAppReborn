package com.bq.zowi.models;

/* JADX INFO: loaded from: classes.dex */
public class ZowiName {
    private static final String FACTORY_ZOWI_NAME = "#";
    private String name;

    public ZowiName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static boolean isFactoryName(String name) {
        return name.equals(getFactoryName());
    }

    public static String getFactoryName() {
        return FACTORY_ZOWI_NAME;
    }
}
