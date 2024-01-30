package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;
import java.util.Date;

public class DateColumnDefinition extends ColumnDefinition<Date> {
    public DateColumnDefinition(int columnIndex, String columnName) {
        super(columnIndex, columnName);
    }

    public String getDbType() {
        return "INTEGER";
    }

    public Class<Date> getObjectType() {
        return Date.class;
    }

    public Date read(Cursor cursor) {
        return LegacyEntityDatabase.readDate(getColumnIndex(), cursor);
    }

    public void write(Date value, ContentValues contentValues) {
        LegacyEntityDatabase.writeDate(contentValues, getColumnName(), value);
    }
}
