package com.nautilus.omni.customviews.graphics;

import android.content.Context;

public class GraphicUtils {
    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int dpToPx(Context context, int dp) {
        return Math.round(((float) dp) * (context.getResources().getDisplayMetrics().xdpi / 160.0f));
    }
}
