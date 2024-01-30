package com.buddybuild.sdk.utils;

import android.content.Context;
import android.util.Log;
import com.buddybuild.sdk.BuildConfig;

public final class ContextUtils {
    public static boolean IsInternalContext(Context context) {
        if (context == null) {
            return false;
        }
        Log.d("mytag", context.getClass().getName());
        return context.getClass().getName().contains(BuildConfig.APPLICATION_ID);
    }
}
