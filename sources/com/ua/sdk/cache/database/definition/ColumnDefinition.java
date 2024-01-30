package com.ua.sdk.cache.database.definition;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class ColumnDefinition<T> {
    private int columnIndex;
    private String columnName;

    public abstract String getDbType();

    public abstract Class<T> getObjectType();

    public abstract T read(Cursor cursor);

    public abstract void write(T t, ContentValues contentValues);

    public ColumnDefinition(int columnIndex2, String columnName2) {
        this.columnIndex = columnIndex2;
        this.columnName = columnName2;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String toString() {
        return getColumnName();
    }
}
