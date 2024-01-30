package com.buddybuild.sdk.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.utils.ImageUtils;
import java.lang.ref.WeakReference;

public class FeedbackUtils {
    public static Bitmap getViewScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(524288);
        view.buildDrawingCache(true);
        Bitmap drawingCache = view.getDrawingCache(true);
        Bitmap bitmap = null;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
        } else if (view.getWidth() > 0 && view.getHeight() > 0) {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas can = new Canvas(bitmap);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(can);
        }
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap getActivityScreenshot(WeakReference<Activity> weakReference) {
        Activity activity = (Activity) weakReference.get();
        if (activity == null) {
            return null;
        }
        View view = activity.getWindow().getDecorView();
        Rect visibleDisplayFrame = new Rect();
        view.getWindowVisibleDisplayFrame(visibleDisplayFrame);
        Bitmap rawBitmap = getViewScreenshot(view);
        if (rawBitmap != null) {
            return ImageUtils.cropBitmap(rawBitmap, visibleDisplayFrame);
        }
        return null;
    }

    public static Bitmap getIntentFeedbackScreenshot(WeakReference<Activity> weakReference) {
        Activity activity = (Activity) weakReference.get();
        Bitmap bitmap = null;
        if (activity != null) {
            String screenshotCacheKey = activity.getIntent().getStringExtra(Constants.BUDDYBUILD_FEEDBACK_SCREENSHOT_CACHE_KEY_EXTRA);
            if (screenshotCacheKey != null) {
                bitmap = ScreenshotCache.instance().get(screenshotCacheKey);
            }
            if (bitmap == null) {
                Log.w(Constants.BUDDYBUILD_TAG, String.format("Failed to find an activity screenshot for %s. Will be using default screenshot.", new Object[]{screenshotCacheKey}));
            }
        }
        if (bitmap == null) {
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }
        return bitmap;
    }

    public static void showFeedbackActivity(Context context, String screenshotCacheKey) {
        if (context != null && screenshotCacheKey != null) {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.setClass(context, FeedbackActivity.class);
            intent.putExtra(Constants.BUDDYBUILD_FEEDBACK_SCREENSHOT_CACHE_KEY_EXTRA, screenshotCacheKey);
            context.startActivity(intent);
        }
    }
}
