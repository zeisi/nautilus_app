package org.acra.http;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.acra.ACRAConstants;

public final class HttpUtils {
    private HttpUtils() {
    }

    @NonNull
    public static byte[] uriToByteArray(@NonNull Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) {
            throw new FileNotFoundException("Could not open " + uri.toString());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        while (true) {
            int length = inputStream.read(buffer);
            if (length <= 0) {
                return outputStream.toByteArray();
            }
            outputStream.write(buffer, 0, length);
        }
    }

    @NonNull
    public static String getParamsAsFormString(@NonNull Map<?, ?> parameters) throws UnsupportedEncodingException {
        Object value;
        StringBuilder dataBfr = new StringBuilder();
        for (Map.Entry<?, ?> entry : parameters.entrySet()) {
            if (dataBfr.length() != 0) {
                dataBfr.append('&');
            }
            Object preliminaryValue = entry.getValue();
            if (preliminaryValue == null) {
                value = "";
            } else {
                value = preliminaryValue;
            }
            dataBfr.append(URLEncoder.encode(entry.getKey().toString(), ACRAConstants.UTF8));
            dataBfr.append('=');
            dataBfr.append(URLEncoder.encode(value.toString(), ACRAConstants.UTF8));
        }
        return dataBfr.toString();
    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex("_display_name"));
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        if (result != null) {
            return result;
        }
        String result2 = uri.getPath();
        int cut = result2.lastIndexOf(47);
        if (cut != -1) {
            return result2.substring(cut + 1);
        }
        return result2;
    }

    public static String getMimeType(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            return context.getContentResolver().getType(uri);
        }
        return guessMimeType(uri);
    }

    public static String guessMimeType(Uri uri) {
        String type = null;
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        if (fileExtension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        if (type == null) {
            return "application/octet-stream";
        }
        return type;
    }
}
