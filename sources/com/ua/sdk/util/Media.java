package com.ua.sdk.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import com.nautilus.omni.util.Constants;

public class Media {
    public static Bitmap getVideoThumbnail(Context context, Uri video) {
        return ThumbnailUtils.createVideoThumbnail(getPath(context, video), 1);
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        boolean isKitKat;
        if (Build.VERSION.SDK_INT >= 19) {
            isKitKat = true;
        } else {
            isKitKat = false;
        }
        if (!isKitKat || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, (String) null, (String[]) null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(Constants.COLON_SEPARATOR);
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
            return null;
        } else if (isDownloadsDocument(uri)) {
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), (String) null, (String[]) null);
        } else if (!isMediaDocument(uri)) {
            return null;
        } else {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(Constants.COLON_SEPARATOR);
            String type = split2[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, contentUri, "_id=?", new String[]{split2[1]});
        }
    }

    protected static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, (String) null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    protected static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    protected static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    protected static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
