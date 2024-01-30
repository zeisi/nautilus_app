package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class LocalIdColumnDefinition extends ColumnDefinition<Long> {
    public LocalIdColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    }

    public Class<Long> getObjectType() {
        return Long.class;
    }

    public Long read(Cursor cursor) {
        return LegacyEntityDatabase.readLong(getColumnIndex(), cursor);
    }

    public void write(Long value, ContentValues contentValues) {
        LegacyEntityDatabase.writeLong(contentValues, getColumnName(), value);
    }
}
