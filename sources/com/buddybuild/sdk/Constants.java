package com.buddybuild.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

public class Constants {
    public static String ANDROID_VERSION = null;
    public static String APP_PACKAGE = null;
    public static String APP_VERSION = null;
    public static String APP_VERSION_NAME = null;
    public static final String AUTO_UPDATE_PARTIAL_URL = "/api/sdk/check-version/android";
    public static File BUDDYBUILD_EXTERNAL_STORAGE_DIR = null;
    public static final String BUDDYBUILD_FEEDBACK_SCREENSHOT_CACHE_KEY_EXTRA = "BUDDYBUILD_FEEDBACK_SCREENSHOT_CACHE_KEY";
    public static String BUDDYBUILD_FILES_PATH = null;
    public static final String BUDDYBUILD_TAG = "BuddyBuild";
    public static String BUDDYBUILD_USER_AGENT = System.getProperty("http.agent", "Dalvik");
    public static final String CRASHREPORT_PARTIAL_URL = "/api/feedback/crashreportAndroid";
    public static final String FEEDBACK_PARTIAL_URL = "/api/feedback";
    private static boolean IS_LOADED = false;
    public static boolean IS_VENDOR_APP = false;
    public static final String LAUNCHED_PARTIAL_URL = "/api/sdk/open-event";
    public static String PHONE_MANUFACTURER = null;
    public static String PHONE_MODEL = null;
    public static final String SHARED_PREFERENCES_KEY = "buddybuild_shared_preferences";

    public static void loadFromContext(Context context) {
        if (!IS_LOADED) {
            ANDROID_VERSION = Build.VERSION.RELEASE;
            PHONE_MODEL = Build.MODEL;
            PHONE_MANUFACTURER = Build.MANUFACTURER;
            loadStoragePaths(context);
            loadPackageData(context);
            loadInstallerData(context);
            IS_LOADED = true;
        }
    }

    private static void loadStoragePaths(Context context) {
        if (context != null) {
            try {
                File file = context.getFilesDir();
                if (file != null) {
                    BUDDYBUILD_FILES_PATH = file.getAbsolutePath();
                }
                BUDDYBUILD_EXTERNAL_STORAGE_DIR = new File(Environment.getExternalStorageDirectory(), BUDDYBUILD_TAG);
                BUDDYBUILD_EXTERNAL_STORAGE_DIR.mkdirs();
            } catch (Exception e) {
                Log.e(BUDDYBUILD_TAG, "Exception thrown when loading storage paths:");
                e.printStackTrace();
            }
        }
    }

    private static void loadPackageData(Context context) {
        if (context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                APP_PACKAGE = packageInfo.packageName;
                APP_VERSION = "" + packageInfo.versionCode;
                APP_VERSION_NAME = packageInfo.versionName;
                BUDDYBUILD_USER_AGENT = String.format("%s/%s/%s %s", new Object[]{APP_PACKAGE, APP_VERSION, APP_VERSION_NAME, BUDDYBUILD_USER_AGENT});
            } catch (Exception e) {
                Log.e(BUDDYBUILD_TAG, "Exception thrown when accessing the package info:");
                e.printStackTrace();
            }
        }
    }

    private static void loadInstallerData(Context context) {
        boolean z = true;
        if (context != null) {
            try {
                String packageName = context.getPackageName();
                String installer = context.getPackageManager().getInstallerPackageName(packageName);
                Log.d(BUDDYBUILD_TAG, String.format("The installer for the package: '%s' is: '%s'", new Object[]{packageName, installer}));
                if (TextUtils.isEmpty(installer)) {
                    z = false;
                }
                IS_VENDOR_APP = z;
            } catch (Exception e) {
                Log.e(BUDDYBUILD_TAG, "Exception thrown when determining installer info:");
                e.printStackTrace();
            }
        }
    }
}
