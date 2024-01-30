package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;
import com.ua.sdk.cache.database.LegacyEntityDatabase;
import java.lang.Enum;

public class EnumColumnDefinition<T extends Enum<T>> extends ColumnDefinition<T> {
    private Class<T> clazz;

    public EnumColumnDefinition(int columnIndex, String columnName, Class<T> clazz2) {
        super(columnIndex, columnName);
        this.clazz = clazz2;
    }

    public String getDbType() {
        return "TEXT";
    }

    public Class<T> getObjectType() {
        return this.clazz;
    }

    public T read(Cursor cursor) {
        return LegacyEntityDatabase.readEnum(getColumnIndex(), cursor, this.clazz);
    }

    public void write(T value, ContentValues contentValues) {
        LegacyEntityDatabase.writeEnum(contentValues, getColumnName(), value);
    }
}
