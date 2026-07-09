package com.bq.zowi.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class ResourceResolver {
    private static final String COLOR_RESOURCE_TYPE = "color";
    private static final String DRAWABLE_RESOURCE_TYPE = "drawable";
    private static final String DIMEN_RESOURCE_TYPE = "dimen";
    private static final String INTEGER_RESOURCE_TYPE = "integer";
    private static final String STRING_RESOURCE_TYPE = "string";

    public static String getStringByResourceId(String resourceName, Resources resources, String packageName) {
        int resourceId = resolveResourceId(resourceName, STRING_RESOURCE_TYPE, resources, packageName);
        return resourceId != 0 ? resources.getString(resourceId) : "";
    }

    public static CharSequence getTextByResourceId(String resourceName, Resources resources, String packageName) {
        int resourceId = resolveResourceId(resourceName, STRING_RESOURCE_TYPE, resources, packageName);
        return resourceId != 0 ? resources.getText(resourceId) : "";
    }

    public static Drawable getDrawableByResourceId(String resourceName, Context context) {
        return ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName, DRAWABLE_RESOURCE_TYPE, context.getPackageName()));
    }

    public static int getColorByResourceId(String resourceName, Context context) {
        return ContextCompat.getColor(context, context.getResources().getIdentifier(resourceName, COLOR_RESOURCE_TYPE, context.getPackageName()));
    }

    public static ColorStateList getColorStateListByResourceId(String resourceName, Context context) {
        return ContextCompat.getColorStateList(context, context.getResources().getIdentifier(resourceName, COLOR_RESOURCE_TYPE, context.getPackageName()));
    }

    public static int getIntegerByResourceId(String resourceName, Context context) {
        return context.getResources().getInteger(context.getResources().getIdentifier(resourceName, INTEGER_RESOURCE_TYPE, context.getPackageName()));
    }

    public static float getDimensionByResourceId(String resourceName, Context context) {
        return context.getResources().getDimension(context.getResources().getIdentifier(resourceName, DIMEN_RESOURCE_TYPE, context.getPackageName()));
    }

    private static int resolveResourceId(String resourceName, String resourceType, Resources resources, String packageName) {
        if (resourceName == null) {
            Grove.e("Trying to resolve a null %s resource for package %s", resourceType, packageName);
            return 0;
        }
        String normalizedResourceName = resourceName.trim();
        int resourceId = resources.getIdentifier(normalizedResourceName, resourceType, packageName);
        if (resourceId == 0) {
            resourceId = resolveResourceIdFromRClass(normalizedResourceName, resourceType, packageName);
        }
        if (resourceId == 0) {
            Grove.e("Could not resolve %s resource '%s' for package %s", resourceType, resourceName, packageName);
        }
        return resourceId;
    }

    private static int resolveResourceIdFromRClass(String resourceName, String resourceType, String packageName) {
        try {
            Class<?> resourceClass = Class.forName(packageName + ".R$" + resourceType);
            return resourceClass.getField(resourceName).getInt(null);
        } catch (Exception e) {
            Grove.d(e, "Could not resolve %s '%s' from generated R class", resourceType, resourceName);
            return 0;
        }
    }
}
