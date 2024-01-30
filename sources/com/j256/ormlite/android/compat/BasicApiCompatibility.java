package com.j256.ormlite.android.compat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.compat.ApiCompatibility;

public class BasicApiCompatibility implements ApiCompatibility {
    public Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs, ApiCompatibility.CancellationHook cancellationHook) {
        return db.rawQuery(sql, selectionArgs);
    }

    public ApiCompatibility.CancellationHook createCancellationHook() {
        return null;
    }
}
