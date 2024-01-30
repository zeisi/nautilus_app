package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.List;

public class MappedUpdate<T, ID> extends BaseMappedStatement<T, ID> {
    private final FieldType versionFieldType;
    private final int versionFieldTypeIndex;

    private MappedUpdate(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, FieldType versionFieldType2, int versionFieldTypeIndex2) {
        super(tableInfo, statement, argFieldTypes);
        this.versionFieldType = versionFieldType2;
        this.versionFieldTypeIndex = versionFieldTypeIndex2;
    }

    public static <T, ID> MappedUpdate<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) throws SQLException {
        int argFieldC;
        FieldType idField = tableInfo.getIdField();
        if (idField == null) {
            throw new SQLException("Cannot update " + tableInfo.getDataClass() + " because it doesn't have an id field");
        }
        StringBuilder sb = new StringBuilder(64);
        appendTableName(databaseType, sb, "UPDATE ", tableInfo.getTableName());
        boolean first = true;
        int argFieldC2 = 0;
        FieldType versionFieldType2 = null;
        int versionFieldTypeIndex2 = -1;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (isFieldUpdatable(fieldType, idField)) {
                if (fieldType.isVersion()) {
                    versionFieldType2 = fieldType;
                    versionFieldTypeIndex2 = argFieldC2;
                }
                argFieldC2++;
            }
        }
        int argFieldC3 = argFieldC2 + 1;
        if (versionFieldType2 != null) {
            argFieldC3++;
        }
        FieldType[] argFieldTypes = new FieldType[argFieldC3];
        FieldType[] arr$ = tableInfo.getFieldTypes();
        int len$ = arr$.length;
        int i$ = 0;
        int argFieldC4 = 0;
        while (i$ < len$) {
            FieldType fieldType2 = arr$[i$];
            if (!isFieldUpdatable(fieldType2, idField)) {
                argFieldC = argFieldC4;
            } else {
                if (first) {
                    sb.append("SET ");
                    first = false;
                } else {
                    sb.append(", ");
                }
                appendFieldColumnName(databaseType, sb, fieldType2, (List<FieldType>) null);
                argFieldC = argFieldC4 + 1;
                argFieldTypes[argFieldC4] = fieldType2;
                sb.append("= ?");
            }
            i$++;
            argFieldC4 = argFieldC;
        }
        sb.append(' ');
        appendWhereFieldEq(databaseType, idField, sb, (List<FieldType>) null);
        int argFieldC5 = argFieldC4 + 1;
        argFieldTypes[argFieldC4] = idField;
        if (versionFieldType2 != null) {
            sb.append(" AND ");
            appendFieldColumnName(databaseType, sb, versionFieldType2, (List<FieldType>) null);
            sb.append("= ?");
            argFieldTypes[argFieldC5] = versionFieldType2;
            int i = argFieldC5 + 1;
        }
        return new MappedUpdate<>(tableInfo, sb.toString(), argFieldTypes, versionFieldType2, versionFieldTypeIndex2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0058, code lost:
        r8 = r17.idField.extractJavaFieldValue(r19);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int update(com.j256.ormlite.support.DatabaseConnection r18, T r19, com.j256.ormlite.dao.ObjectCache r20) throws java.sql.SQLException {
        /*
            r17 = this;
            r0 = r17
            com.j256.ormlite.field.FieldType[] r12 = r0.argFieldTypes     // Catch:{ SQLException -> 0x00b5 }
            int r12 = r12.length     // Catch:{ SQLException -> 0x00b5 }
            r13 = 1
            if (r12 > r13) goto L_0x000a
            r11 = 0
        L_0x0009:
            return r11
        L_0x000a:
            r0 = r17
            r1 = r19
            java.lang.Object[] r2 = r0.getFieldObjects(r1)     // Catch:{ SQLException -> 0x00b5 }
            r10 = 0
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.versionFieldType     // Catch:{ SQLException -> 0x00b5 }
            if (r12 == 0) goto L_0x0039
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.versionFieldType     // Catch:{ SQLException -> 0x00b5 }
            r0 = r19
            java.lang.Object r10 = r12.extractJavaFieldValue(r0)     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.versionFieldType     // Catch:{ SQLException -> 0x00b5 }
            java.lang.Object r10 = r12.moveToNextValue(r10)     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            int r12 = r0.versionFieldTypeIndex     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            com.j256.ormlite.field.FieldType r13 = r0.versionFieldType     // Catch:{ SQLException -> 0x00b5 }
            java.lang.Object r13 = r13.convertJavaFieldToSqlArgValue(r10)     // Catch:{ SQLException -> 0x00b5 }
            r2[r12] = r13     // Catch:{ SQLException -> 0x00b5 }
        L_0x0039:
            r0 = r17
            java.lang.String r12 = r0.statement     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            com.j256.ormlite.field.FieldType[] r13 = r0.argFieldTypes     // Catch:{ SQLException -> 0x00b5 }
            r0 = r18
            int r11 = r0.update(r12, r2, r13)     // Catch:{ SQLException -> 0x00b5 }
            if (r11 <= 0) goto L_0x0095
            if (r10 == 0) goto L_0x0056
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.versionFieldType     // Catch:{ SQLException -> 0x00b5 }
            r13 = 0
            r14 = 0
            r0 = r19
            r12.assignField(r0, r10, r13, r14)     // Catch:{ SQLException -> 0x00b5 }
        L_0x0056:
            if (r20 == 0) goto L_0x0095
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.idField     // Catch:{ SQLException -> 0x00b5 }
            r0 = r19
            java.lang.Object r8 = r12.extractJavaFieldValue(r0)     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            java.lang.Class r12 = r0.clazz     // Catch:{ SQLException -> 0x00b5 }
            r0 = r20
            java.lang.Object r4 = r0.get(r12, r8)     // Catch:{ SQLException -> 0x00b5 }
            if (r4 == 0) goto L_0x0095
            r0 = r19
            if (r4 == r0) goto L_0x0095
            r0 = r17
            com.j256.ormlite.table.TableInfo r12 = r0.tableInfo     // Catch:{ SQLException -> 0x00b5 }
            com.j256.ormlite.field.FieldType[] r3 = r12.getFieldTypes()     // Catch:{ SQLException -> 0x00b5 }
            int r9 = r3.length     // Catch:{ SQLException -> 0x00b5 }
            r7 = 0
        L_0x007c:
            if (r7 >= r9) goto L_0x0095
            r6 = r3[r7]     // Catch:{ SQLException -> 0x00b5 }
            r0 = r17
            com.j256.ormlite.field.FieldType r12 = r0.idField     // Catch:{ SQLException -> 0x00b5 }
            if (r6 == r12) goto L_0x0092
            r0 = r19
            java.lang.Object r12 = r6.extractJavaFieldValue(r0)     // Catch:{ SQLException -> 0x00b5 }
            r13 = 0
            r0 = r20
            r6.assignField(r4, r12, r13, r0)     // Catch:{ SQLException -> 0x00b5 }
        L_0x0092:
            int r7 = r7 + 1
            goto L_0x007c
        L_0x0095:
            com.j256.ormlite.logger.Logger r12 = logger     // Catch:{ SQLException -> 0x00b5 }
            java.lang.String r13 = "update data with statement '{}' and {} args, changed {} rows"
            r0 = r17
            java.lang.String r14 = r0.statement     // Catch:{ SQLException -> 0x00b5 }
            int r15 = r2.length     // Catch:{ SQLException -> 0x00b5 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ SQLException -> 0x00b5 }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r11)     // Catch:{ SQLException -> 0x00b5 }
            r12.debug((java.lang.String) r13, (java.lang.Object) r14, (java.lang.Object) r15, (java.lang.Object) r16)     // Catch:{ SQLException -> 0x00b5 }
            int r12 = r2.length     // Catch:{ SQLException -> 0x00b5 }
            if (r12 <= 0) goto L_0x0009
            com.j256.ormlite.logger.Logger r12 = logger     // Catch:{ SQLException -> 0x00b5 }
            java.lang.String r13 = "update arguments: {}"
            r12.trace((java.lang.String) r13, (java.lang.Object) r2)     // Catch:{ SQLException -> 0x00b5 }
            goto L_0x0009
        L_0x00b5:
            r5 = move-exception
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Unable to run update stmt on object "
            java.lang.StringBuilder r12 = r12.append(r13)
            r0 = r19
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r13 = ": "
            java.lang.StringBuilder r12 = r12.append(r13)
            r0 = r17
            java.lang.String r13 = r0.statement
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.sql.SQLException r12 = com.j256.ormlite.misc.SqlExceptionUtil.create(r12, r5)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.stmt.mapped.MappedUpdate.update(com.j256.ormlite.support.DatabaseConnection, java.lang.Object, com.j256.ormlite.dao.ObjectCache):int");
    }

    private static boolean isFieldUpdatable(FieldType fieldType, FieldType idField) {
        if (fieldType == idField || fieldType.isForeignCollection() || fieldType.isReadOnly()) {
            return false;
        }
        return true;
    }
}
