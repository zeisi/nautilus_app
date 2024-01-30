package com.buddybuild.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public final class ImageUtils {
    public static Bitmap cropBitmap(Bitmap bitmap, Rect rect) {
        Bitmap croppedBitmap = Bitmap.createBitmap(rect.right - rect.left, rect.bottom - rect.top, Bitmap.Config.ARGB_8888);
        new Canvas(croppedBitmap).drawBitmap(bitmap, (float) (-rect.left), (float) (-rect.top), (Paint) null);
        return croppedBitmap;
    }

    public static String convertToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), 0);
    }
}
