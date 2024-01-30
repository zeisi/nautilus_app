package org.acra.attachment;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.file.Directory;
import org.acra.http.HttpUtils;

public class AcraContentProvider extends ContentProvider {
    private static final String[] COLUMNS = {"_display_name", "_size"};
    private String authority;

    public boolean onCreate() {
        this.authority = getAuthority(getContext());
        if (!ACRA.DEV_LOGGING) {
            return true;
        }
        ACRA.log.d(ACRA.LOG_TAG, "Registered content provider for authority " + this.authority);
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Query: " + uri);
        }
        File file = getFileForUri(uri);
        if (file == null) {
            return null;
        }
        if (projection == null) {
            projection = COLUMNS;
        }
        Map<String, Object> columnValueMap = new LinkedHashMap<>();
        for (String column : projection) {
            if (column.equals("_display_name")) {
                columnValueMap.put("_display_name", file.getName());
            } else if (column.equals("_size")) {
                columnValueMap.put("_size", Long.valueOf(file.length()));
            }
        }
        MatrixCursor cursor = new MatrixCursor((String[]) columnValueMap.keySet().toArray(new String[columnValueMap.size()]), 1);
        cursor.addRow(columnValueMap.values());
        return cursor;
    }

    @Nullable
    private File getFileForUri(Uri uri) {
        if (!"content".equals(uri.getScheme()) || !this.authority.equals(uri.getAuthority())) {
            return null;
        }
        List<String> segments = new ArrayList<>(uri.getPathSegments());
        if (segments.size() < 2) {
            return null;
        }
        try {
            return Directory.valueOf(segments.remove(0).toUpperCase()).getFile(getContext(), TextUtils.join(File.separator, segments));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return HttpUtils.guessMimeType(uri);
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("No insert supported");
    }

    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("No delete supported");
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("No update supported");
    }

    @Nullable
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        File file = getFileForUri(uri);
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("File represented by uri " + uri + " could not be found");
        }
        if (ACRA.DEV_LOGGING) {
            if (Build.VERSION.SDK_INT >= 19) {
                ACRA.log.d(ACRA.LOG_TAG, getCallingPackage() + " opened " + file.getPath());
            } else {
                ACRA.log.d(ACRA.LOG_TAG, file.getPath() + " was opened by an application");
            }
        }
        return ParcelFileDescriptor.open(file, 268435456);
    }

    private static String getAuthority(@NonNull Context context) {
        return context.getPackageName() + ".acra";
    }

    public static Uri getUriForFile(@NonNull Context context, @NonNull File file) {
        return getUriForFile(context, Directory.ROOT, file.getPath());
    }

    public static Uri getUriForFile(@NonNull Context context, @NonNull Directory directory, @NonNull String relativePath) {
        return new Uri.Builder().scheme("content").authority(getAuthority(context)).path(directory.name().toLowerCase() + (relativePath.charAt(0) == File.separatorChar ? "" : File.separator) + relativePath).build();
    }
}
