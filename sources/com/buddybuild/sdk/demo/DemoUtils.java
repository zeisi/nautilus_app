package com.buddybuild.sdk.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.properties.BuddyBuildProperties;
import com.buddybuild.sdk.utils.ContextUtils;

public final class DemoUtils {
    private static String sDemoPreferenceKey = "buddybuild_demo_shown";

    public static boolean showDemoActivity(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, 0);
        Boolean hasShownDemo = Boolean.valueOf(sharedPref.getBoolean(sDemoPreferenceKey, false));
        if (!BuddyBuildProperties.BUDDYBUILD_ENABLE_FEEDBACK.booleanValue() || hasShownDemo.booleanValue() || !BuddyBuildProperties.BUDDYBUILD_ENABLE_DEMO.booleanValue() || ContextUtils.IsInternalContext(context)) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(sDemoPreferenceKey, true);
        editor.commit();
        Intent intent = new Intent(context, DemoActivity.class);
        intent.setFlags(268435456);
        context.startActivity(intent);
        return true;
    }
}
