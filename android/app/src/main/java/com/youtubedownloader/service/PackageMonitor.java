package com.youtubedownloader.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.youtubedownloader.R;

/**
 * This class monitors which packages are in the foreground.
 * */
public class PackageMonitor extends ReactContextBaseJavaModule {

    private ReactApplicationContext mContext;

    //TODO: handle null context
    public PackageMonitor(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "PackageMonitor";
    }

    /**
     * @return the package name of the foreground activity.
     * @throws UsageStatsPermissionNotGrantedException if Usage Stats permission is not granted.
     * */
    public String getCurrentForegroundActivity() throws UsageStatsPermissionNotGrantedException {
        if (!isUsageStatsPermissionGranted()) {
            throw new UsageStatsPermissionNotGrantedException();
        }
        if (AndroidVersionUtils.isLollipop()) {
            return lollipopForegroundPackage();
        } else if (AndroidVersionUtils.isPreLollipop()) {
            return preLollipopForegroundPackage();
        }
        //TODO: add support for M and above
        return null;
    }

    /**
     * checks for usage stats permission status.
     *
     * @return true IFF Usage Stats permission is granted for the app.
     * */
    private boolean isUsageStatsPermissionGranted() {
        AppOpsManager appOps = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), mContext.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    /**
     * @return name of the foreground package for OS version Lollipop
     * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private String lollipopForegroundPackage() {
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) mContext.getSystemService(Service.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();

        UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 3600, time);
        UsageEvents.Event event = new UsageEvents.Event();
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if(event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                return event.getPackageName();
            }
        }
        return null;
    }

    /**
     * @return name of the foreground package for OS version pre Lollipop
     * */
    private String preLollipopForegroundPackage() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Service.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
        String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
        PackageManager pm = mContext.getPackageManager();
        PackageInfo foregroundAppPackageInfo = null;
        try {
            foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String foregroundApp = null;
        if(foregroundAppPackageInfo != null)
            foregroundApp = foregroundAppPackageInfo.applicationInfo.packageName;

        return foregroundApp;
    }

    /**
     * This exception must be thrown if trying to access an entity that requires usage-stats permission
     * and user still haven't granted the app the permission yet.
     * */
    class UsageStatsPermissionNotGrantedException extends RuntimeException {
        @Override
        public String getMessage() {
            return  mContext.getResources().getString(R.string.usage_stats_exception_message);
        }
    }
}
