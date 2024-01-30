package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class DoubleColumnDefinition extends ColumnDefinition<Double> {
    public DoubleColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "REAL";
    }

    public Class<Double> getObjectType() {
        return Double.class;
    }

    public Double read(Cursor cursor) {
        return LegacyEntityDatabase.readDouble(getColumnIndex(), cursor);
    }

    public void write(Double value, ContentValues contentValues) {
        LegacyEntityDatabase.writeDouble(contentValues, getColumnName(), value);
    }
}
