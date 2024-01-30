package com.buddybuild.sdk.properties;

import android.content.Context;
import android.util.Log;
import com.buddybuild.sdk.Constants;
import java.io.IOException;
import java.util.Properties;

public class BuddyBuildProperties {
    public static String BUDDYBUILD_APPLICATION_VARIANT_NAME = null;
    public static String BUDDYBUILD_APP_ID = null;
    private static final String BUDDYBUILD_ASSET_FILE_NAME = "buddybuild.properties";
    public static String BUDDYBUILD_BUILD_ID = null;
    public static String BUDDYBUILD_BUILD_NUMBER = null;
    public static String BUDDYBUILD_EMAIL = null;
    public static Boolean BUDDYBUILD_ENABLE_AUTOUPDATE = false;
    public static Boolean BUDDYBUILD_ENABLE_CRASHREPORT = false;
    public static Boolean BUDDYBUILD_ENABLE_DEMO = false;
    public static Boolean BUDDYBUILD_ENABLE_FEEDBACK = false;
    public static String BUDDYBUILD_ENDPOINT = null;
    public static String BUDDYBUILD_ENVIRONMENT = null;
    private static boolean IS_LOADED = false;
    private static final Properties sProperties = new Properties();

    public static void loadFromContext(Context context) {
        if (!IS_LOADED) {
            try {
                sProperties.load(context.getAssets().open(BUDDYBUILD_ASSET_FILE_NAME));
                Log.d(Constants.BUDDYBUILD_TAG, "The buddybuild asset file: buddybuild.properties has properties: " + sProperties.toString());
                BUDDYBUILD_APP_ID = getStrValue("BUDDYBUILD_APP_ID");
                BUDDYBUILD_BUILD_ID = getStrValue("BUDDYBUILD_BUILD_ID");
                BUDDYBUILD_BUILD_NUMBER = getStrValue("BUDDYBUILD_BUILD_NUMBER");
                BUDDYBUILD_APPLICATION_VARIANT_NAME = getStrValue("BUDDYBUILD_APPLICATION_VARIANT_NAME");
                BUDDYBUILD_ENDPOINT = getStrValue("BUDDYBUILD_ENDPOINT");
                BUDDYBUILD_ENVIRONMENT = getStrValue("BUDDYBUILD_ENVIRONMENT");
                BUDDYBUILD_EMAIL = getStrValue("BUDDYBUILD_EMAIL");
                BUDDYBUILD_ENABLE_FEEDBACK = Boolean.valueOf(Boolean.parseBoolean(getBoolValue("BUDDYBUILD_ENABLE_FEEDBACK")));
                BUDDYBUILD_ENABLE_CRASHREPORT = Boolean.valueOf(Boolean.parseBoolean(getBoolValue("BUDDYBUILD_ENABLE_CRASHREPORT")));
                BUDDYBUILD_ENABLE_AUTOUPDATE = Boolean.valueOf(Boolean.parseBoolean(getBoolValue("BUDDYBUILD_ENABLE_AUTOUPDATE")));
                BUDDYBUILD_ENABLE_DEMO = Boolean.valueOf(Boolean.parseBoolean(getBoolValue("BUDDYBUILD_ENABLE_DEMO")));
            } catch (IOException e) {
                Log.e(Constants.BUDDYBUILD_TAG, "Exception thrown when accessing asset: buddybuild.properties");
                e.printStackTrace();
            } finally {
                IS_LOADED = true;
            }
        }
    }

    private static String getStrValue(String name) {
        return sProperties.getProperty(name, "");
    }

    private static String getBoolValue(String name) {
        return sProperties.getProperty(name, "false");
    }
}
