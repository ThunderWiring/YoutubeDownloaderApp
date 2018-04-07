package com.youtubedownloader.service;

import android.os.Build;

/**
 * Provides info regarding the Android API level of the device
 * */
public class AndroidVersionUtils {
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M
                && !Build.VERSION.RELEASE.equalsIgnoreCase("n");
    }

    public static boolean isMarshmallowOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNOrAbove() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M
                || Build.VERSION.RELEASE.equalsIgnoreCase("n");
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isPreLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}
