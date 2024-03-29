package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseDateType extends BaseDataType {
    protected static final DateStringFormatConfig defaultDateFormatConfig = new DateStringFormatConfig("yyyy-MM-dd HH:mm:ss.SSSSSS");

    protected BaseDateType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    protected static DateStringFormatConfig convertDateStringConfig(FieldType fieldType, DateStringFormatConfig defaultDateFormatConfig2) {
        DateStringFormatConfig configObj;
        if (fieldType == null || (configObj = (DateStringFormatConfig) fieldType.getDataTypeConfigObj()) == null) {
            return defaultDateFormatConfig2;
        }
        return configObj;
    }

    protected static Date parseDateString(DateStringFormatConfig formatConfig, String dateStr) throws ParseException {
        return formatConfig.getDateFormat().parse(dateStr);
    }

    protected static String normalizeDateString(DateStringFormatConfig formatConfig, String dateStr) throws ParseException {
        DateFormat dateFormat = formatConfig.getDateFormat();
        return dateFormat.format(dateFormat.parse(dateStr));
    }

    protected static class DateStringFormatConfig {
        final String dateFormatStr;
        private final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
            /* access modifiers changed from: protected */
            public DateFormat initialValue() {
                return new SimpleDateFormat(DateStringFormatConfig.this.dateFormatStr);
            }
        };

        public DateStringFormatConfig(String dateFormatStr2) {
            this.dateFormatStr = dateFormatStr2;
        }

        public DateFormat getDateFormat() {
            return this.threadLocal.get();
        }

        public String toString() {
            return this.dateFormatStr;
        }
    }

    public boolean isValidForVersion() {
        return true;
    }

    public Object moveToNextValue(Object currentValue) {
        long newVal = System.currentTimeMillis();
        if (currentValue == null) {
            return new Date(newVal);
        }
        if (newVal == ((Date) currentValue).getTime()) {
            return new Date(1 + newVal);
        }
        return new Date(newVal);
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Date.class;
    }
}
