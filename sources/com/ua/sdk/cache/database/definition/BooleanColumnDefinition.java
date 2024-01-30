package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class BooleanColumnDefinition extends ColumnDefinition<Boolean> {
    public BooleanColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "INTEGER";
    }

    public Class<Boolean> getObjectType() {
        return Boolean.class;
    }

    public Boolean read(Cursor cursor) {
        return LegacyEntityDatabase.readBoolean(getColumnIndex(), cursor);
    }

    public void write(Boolean value, ContentValues contentValues) {
        LegacyEntityDatabase.writeBoolean(contentValues, getColumnName(), value);
    }
}
