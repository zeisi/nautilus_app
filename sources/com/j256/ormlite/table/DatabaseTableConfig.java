package com.j256.ormlite.table;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfig<T> {
    private Constructor<T> constructor;
    private Class<T> dataClass;
    private List<DatabaseFieldConfig> fieldConfigs;
    private FieldType[] fieldTypes;
    private String tableName;

    public DatabaseTableConfig() {
    }

    public DatabaseTableConfig(Class<T> dataClass2, List<DatabaseFieldConfig> fieldConfigs2) {
        this(dataClass2, extractTableName(dataClass2), fieldConfigs2);
    }

    public DatabaseTableConfig(Class<T> dataClass2, String tableName2, List<DatabaseFieldConfig> fieldConfigs2) {
        this.dataClass = dataClass2;
        this.tableName = tableName2;
        this.fieldConfigs = fieldConfigs2;
    }

    private DatabaseTableConfig(Class<T> dataClass2, String tableName2, FieldType[] fieldTypes2) {
        this.dataClass = dataClass2;
        this.tableName = tableName2;
        this.fieldTypes = fieldTypes2;
    }

    public void initialize() {
        if (this.dataClass == null) {
            throw new IllegalStateException("dataClass was never set on " + getClass().getSimpleName());
        } else if (this.tableName == null) {
            this.tableName = extractTableName(this.dataClass);
        }
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public void setDataClass(Class<T> dataClass2) {
        this.dataClass = dataClass2;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public void setFieldConfigs(List<DatabaseFieldConfig> fieldConfigs2) {
        this.fieldConfigs = fieldConfigs2;
    }

    public void extractFieldTypes(ConnectionSource connectionSource) throws SQLException {
        if (this.fieldTypes != null) {
            return;
        }
        if (this.fieldConfigs == null) {
            this.fieldTypes = extractFieldTypes(connectionSource, this.dataClass, this.tableName);
        } else {
            this.fieldTypes = convertFieldConfigs(connectionSource, this.tableName, this.fieldConfigs);
        }
    }

    public FieldType[] getFieldTypes(DatabaseType databaseType) throws SQLException {
        if (this.fieldTypes != null) {
            return this.fieldTypes;
        }
        throw new SQLException("Field types have not been extracted in table config");
    }

    public List<DatabaseFieldConfig> getFieldConfigs() {
        return this.fieldConfigs;
    }

    public Constructor<T> getConstructor() {
        if (this.constructor == null) {
            this.constructor = findNoArgConstructor(this.dataClass);
        }
        return this.constructor;
    }

    public void setConstructor(Constructor<T> constructor2) {
        this.constructor = constructor2;
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        String tableName2 = extractTableName(clazz);
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            tableName2 = tableName2.toUpperCase();
        }
        return new DatabaseTableConfig<>(clazz, tableName2, extractFieldTypes(connectionSource, clazz, tableName2));
    }

    public static <T> String extractTableName(Class<T> clazz) {
        DatabaseTable databaseTable = (DatabaseTable) clazz.getAnnotation(DatabaseTable.class);
        if (databaseTable != null && databaseTable.tableName() != null && databaseTable.tableName().length() > 0) {
            return databaseTable.tableName();
        }
        String name = JavaxPersistence.getEntityName(clazz);
        if (name == null) {
            return clazz.getSimpleName().toLowerCase();
        }
        return name;
    }

    public static <T> Constructor<T> findNoArgConstructor(Class<T> dataClass2) {
        try {
            for (Constructor<T> con : dataClass2.getDeclaredConstructors()) {
                if (con.getParameterTypes().length == 0) {
                    if (!con.isAccessible()) {
                        try {
                            con.setAccessible(true);
                        } catch (SecurityException e) {
                            throw new IllegalArgumentException("Could not open access to constructor for " + dataClass2);
                        }
                    }
                    return con;
                }
            }
            if (dataClass2.getEnclosingClass() == null) {
                throw new IllegalArgumentException("Can't find a no-arg constructor for " + dataClass2);
            }
            throw new IllegalArgumentException("Can't find a no-arg constructor for " + dataClass2 + ".  Missing static on inner class?");
        } catch (Exception e2) {
            throw new IllegalArgumentException("Can't lookup declared constructors for " + dataClass2, e2);
        }
    }

    private static <T> FieldType[] extractFieldTypes(ConnectionSource connectionSource, Class<T> clazz, String tableName2) throws SQLException {
        List<FieldType> fieldTypes2 = new ArrayList<>();
        for (Class<T> cls = clazz; cls != null; cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                FieldType fieldType = FieldType.createFieldType(connectionSource, tableName2, field, clazz);
                if (fieldType != null) {
                    fieldTypes2.add(fieldType);
                }
            }
        }
        if (!fieldTypes2.isEmpty()) {
            return (FieldType[]) fieldTypes2.toArray(new FieldType[fieldTypes2.size()]);
        }
        throw new IllegalArgumentException("No fields have a " + DatabaseField.class.getSimpleName() + " annotation in " + clazz);
    }

    private FieldType[] convertFieldConfigs(ConnectionSource connectionSource, String tableName2, List<DatabaseFieldConfig> fieldConfigs2) throws SQLException {
        List<FieldType> fieldTypes2 = new ArrayList<>();
        for (DatabaseFieldConfig fieldConfig : fieldConfigs2) {
            FieldType fieldType = null;
            Class cls = this.dataClass;
            while (true) {
                if (cls == null) {
                    break;
                }
                try {
                    Field field = cls.getDeclaredField(fieldConfig.getFieldName());
                    if (field != null) {
                        fieldType = new FieldType(connectionSource, tableName2, field, fieldConfig, this.dataClass);
                        break;
                    }
                    cls = cls.getSuperclass();
                } catch (NoSuchFieldException e) {
                }
            }
            if (fieldType == null) {
                throw new SQLException("Could not find declared field with name '" + fieldConfig.getFieldName() + "' for " + this.dataClass);
            }
            fieldTypes2.add(fieldType);
        }
        if (!fieldTypes2.isEmpty()) {
            return (FieldType[]) fieldTypes2.toArray(new FieldType[fieldTypes2.size()]);
        }
        throw new SQLException("No fields were configured for class " + this.dataClass);
    }
}
