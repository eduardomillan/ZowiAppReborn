package com.bq.zowi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class ResourceResolver {
    private static final String DRAWABLE_RESOURCE_TYPE = "drawable";
    private static final String STRING_RESOURCE_TYPE = "string";

    public static String getStringByResourceId(String resourceName, Resources resources, String packageName) {
        return resources.getString(resources.getIdentifier(resourceName, STRING_RESOURCE_TYPE, packageName));
    }

    public static CharSequence getTextByResourceId(String resourceName, Resources resources, String packageName) {
        return resources.getText(resources.getIdentifier(resourceName, STRING_RESOURCE_TYPE, packageName));
    }

    public static Drawable getDrawableByResourceId(String resourceName, Context context) {
        return ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName, DRAWABLE_RESOURCE_TYPE, context.getPackageName()));
    }
}
