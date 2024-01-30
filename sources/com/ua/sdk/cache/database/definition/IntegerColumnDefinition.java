package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class IntegerColumnDefinition extends ColumnDefinition<Integer> {
    public IntegerColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "INTEGER";
    }

    public Class<Integer> getObjectType() {
        return Integer.class;
    }

    public Integer read(Cursor cursor) {
        return LegacyEntityDatabase.readInteger(getColumnIndex(), cursor);
    }

    public void write(Integer value, ContentValues contentValues) {
        LegacyEntityDatabase.writeInteger(contentValues, getColumnName(), value);
    }
}
