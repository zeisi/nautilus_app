package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class SerializableType extends BaseDataType {
    private static final SerializableType singleTon = new SerializableType();

    public static SerializableType getSingleton() {
        return singleTon;
    }

    private SerializableType() {
        super(SqlType.SERIALIZABLE, new Class[0]);
    }

    protected SerializableType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        throw new SQLException("Default values for serializable types are not supported");
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getBytes(columnPos);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b A[SYNTHETIC, Splitter:B:15:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object sqlArgToJava(com.j256.ormlite.field.FieldType r7, java.lang.Object r8, int r9) throws java.sql.SQLException {
        /*
            r6 = this;
            byte[] r8 = (byte[]) r8
            r0 = r8
            byte[] r0 = (byte[]) r0
            r2 = 0
            java.io.ObjectInputStream r3 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x001a }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x001a }
            r4.<init>(r0)     // Catch:{ Exception -> 0x001a }
            r3.<init>(r4)     // Catch:{ Exception -> 0x001a }
            java.lang.Object r4 = r3.readObject()     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            if (r3 == 0) goto L_0x0019
            r3.close()     // Catch:{ IOException -> 0x004f }
        L_0x0019:
            return r4
        L_0x001a:
            r1 = move-exception
        L_0x001b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0048 }
            r4.<init>()     // Catch:{ all -> 0x0048 }
            java.lang.String r5 = "Could not read serialized object from byte array: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0048 }
            java.lang.String r5 = java.util.Arrays.toString(r0)     // Catch:{ all -> 0x0048 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0048 }
            java.lang.String r5 = "(len "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0048 }
            int r5 = r0.length     // Catch:{ all -> 0x0048 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0048 }
            java.lang.String r5 = ")"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0048 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0048 }
            java.sql.SQLException r4 = com.j256.ormlite.misc.SqlExceptionUtil.create(r4, r1)     // Catch:{ all -> 0x0048 }
            throw r4     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ IOException -> 0x0051 }
        L_0x004e:
            throw r4
        L_0x004f:
            r5 = move-exception
            goto L_0x0019
        L_0x0051:
            r5 = move-exception
            goto L_0x004e
        L_0x0053:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0056:
            r1 = move-exception
            r2 = r3
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.field.types.SerializableType.sqlArgToJava(com.j256.ormlite.field.FieldType, java.lang.Object, int):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038 A[SYNTHETIC, Splitter:B:18:0x0038] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object javaToSqlArg(com.j256.ormlite.field.FieldType r7, java.lang.Object r8) throws java.sql.SQLException {
        /*
            r6 = this;
            r1 = 0
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x001c }
            r3.<init>()     // Catch:{ Exception -> 0x001c }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x001c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x001c }
            r2.writeObject(r8)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            r2.close()     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            r1 = 0
            byte[] r4 = r3.toByteArray()     // Catch:{ Exception -> 0x001c }
            if (r1 == 0) goto L_0x001b
            r1.close()     // Catch:{ IOException -> 0x003c }
        L_0x001b:
            return r4
        L_0x001c:
            r0 = move-exception
        L_0x001d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            r4.<init>()     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = "Could not write serialized object to byte array: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ all -> 0x0035 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0035 }
            java.sql.SQLException r4 = com.j256.ormlite.misc.SqlExceptionUtil.create(r4, r0)     // Catch:{ all -> 0x0035 }
            throw r4     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r4 = move-exception
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ IOException -> 0x003e }
        L_0x003b:
            throw r4
        L_0x003c:
            r5 = move-exception
            goto L_0x001b
        L_0x003e:
            r5 = move-exception
            goto L_0x003b
        L_0x0040:
            r4 = move-exception
            r1 = r2
            goto L_0x0036
        L_0x0043:
            r0 = move-exception
            r1 = r2
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.field.types.SerializableType.javaToSqlArg(com.j256.ormlite.field.FieldType, java.lang.Object):java.lang.Object");
    }

    public boolean isValidForField(Field field) {
        return Serializable.class.isAssignableFrom(field.getType());
    }

    public boolean isStreamType() {
        return true;
    }

    public boolean isComparable() {
        return false;
    }

    public boolean isAppropriateId() {
        return false;
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) throws SQLException {
        throw new SQLException("Serializable type cannot be converted from string to Java");
    }

    public Class<?> getPrimaryClass() {
        return Serializable.class;
    }
}
