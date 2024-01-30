package com.buddybuild.sdk.feedback;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.util.LruCache;
import com.buddybuild.sdk.Constants;

@TargetApi(12)
public class ScreenshotCache {
    private static LruCache<String, Bitmap> sMemoryCache;

    static {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
        int cacheSize = maxMemory / 10;
        Log.d(Constants.BUDDYBUILD_TAG, "Screenshot Cache: (maxmem, cachesize) " + maxMemory + "," + cacheSize);
        sMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, Bitmap bitmap) {
                int bitmapSize = bitmap.getByteCount() / 1024;
                Log.d(Constants.BUDDYBUILD_TAG, "Bitmap size is: " + bitmapSize);
                return bitmapSize;
            }
        };
    }

    public static LruCache<String, Bitmap> instance() {
        return sMemoryCache;
    }
}
