package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.LocalDate;
import com.ua.sdk.cache.database.LegacyEntityDatabase;

public class LocalDateColumnDefinition extends ColumnDefinition<LocalDate> {
    public LocalDateColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "TEXT";
    }

    public Class<LocalDate> getObjectType() {
        return LocalDate.class;
    }

    public LocalDate read(Cursor cursor) {
        return LegacyEntityDatabase.readLocalDate(getColumnIndex(), cursor);
    }

    public void write(LocalDate value, ContentValues contentValues) {
        LegacyEntityDatabase.writeLocalDate(contentValues, getColumnName(), value);
    }
}
