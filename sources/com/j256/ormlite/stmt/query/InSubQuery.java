package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;

public class InSubQuery extends BaseComparison {
    private final boolean in;
    private final QueryBuilder.InternalQueryBuilderWrapper subQueryBuilder;

    public /* bridge */ /* synthetic */ void appendSql(DatabaseType x0, String x1, StringBuilder x2, List x3) throws SQLException {
        super.appendSql(x0, x1, x2, x3);
    }

    public /* bridge */ /* synthetic */ String getColumnName() {
        return super.getColumnName();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public InSubQuery(String columnName, FieldType fieldType, QueryBuilder.InternalQueryBuilderWrapper subQueryBuilder2, boolean in2) throws SQLException {
        super(columnName, fieldType, (Object) null, true);
        this.subQueryBuilder = subQueryBuilder2;
        this.in = in2;
    }

    public void appendOperation(StringBuilder sb) {
        if (this.in) {
            sb.append("IN ");
        } else {
            sb.append("NOT IN ");
        }
    }

    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> argList) throws SQLException {
        sb.append('(');
        this.subQueryBuilder.appendStatementString(sb, argList);
        FieldType[] resultFieldTypes = this.subQueryBuilder.getResultFieldTypes();
        if (resultFieldTypes != null) {
            if (resultFieldTypes.length != 1) {
                throw new SQLException("There must be only 1 result column in sub-query but we found " + resultFieldTypes.length);
            } else if (this.fieldType.getSqlType() != resultFieldTypes[0].getSqlType()) {
                throw new SQLException("Outer column " + this.fieldType + " is not the same type as inner column " + resultFieldTypes[0]);
            }
        }
        sb.append(") ");
    }
}
