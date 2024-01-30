package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class StringColumnDefinition extends ColumnDefinition<String> {
    public StringColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "TEXT";
    }

    public Class<String> getObjectType() {
        return String.class;
    }

    public String read(Cursor cursor) {
        return LegacyEntityDatabase.readString(getColumnIndex(), cursor);
    }

    public void write(String value, ContentValues contentValues) {
        LegacyEntityDatabase.writeString(contentValues, getColumnName(), value);
    }
}
