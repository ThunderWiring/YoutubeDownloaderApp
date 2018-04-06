package com.youtubedownloader.service;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.youtubedownloader.R;

/**
 * This class monitors which packages are in the foreground.
 * */
public class PackageMonitor extends ReactContextBaseJavaModule {

    private ReactApplicationContext mContext;

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
        return null;
    }

    /**
     * checks for usage stats permission status.
     *
     * @return true IFF Usage Stats permission is granted for the app.
     * */
    private boolean isUsageStatsPermissionGranted() {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
