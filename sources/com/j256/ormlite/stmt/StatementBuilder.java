package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.mapped.MappedPreparedStmt;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class StatementBuilder<T, ID> {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) StatementBuilder.class);
    protected boolean addTableName;
    protected final Dao<T, ID> dao;
    protected final DatabaseType databaseType;
    protected final TableInfo<T, ID> tableInfo;
    protected final String tableName;
    protected StatementType type;
    protected Where<T, ID> where = null;

    /* access modifiers changed from: protected */
    public abstract void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> list) throws SQLException;

    /* access modifiers changed from: protected */
    public abstract void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list) throws SQLException;

    public StatementBuilder(DatabaseType databaseType2, TableInfo<T, ID> tableInfo2, Dao<T, ID> dao2, StatementType type2) {
        this.databaseType = databaseType2;
        this.tableInfo = tableInfo2;
        this.tableName = tableInfo2.getTableName();
        this.dao = dao2;
        this.type = type2;
        if (!type2.isOkForStatementBuilder()) {
            throw new IllegalStateException("Building a statement from a " + type2 + " statement is not allowed");
        }
    }

    public Where<T, ID> where() {
        this.where = new Where<>(this.tableInfo, this, this.databaseType);
        return this.where;
    }

    public void setWhere(Where<T, ID> where2) {
        this.where = where2;
    }

    /* access modifiers changed from: protected */
    public MappedPreparedStmt<T, ID> prepareStatement(Long limit) throws SQLException {
        List<ArgumentHolder> argList = new ArrayList<>();
        String statement = buildStatementString(argList);
        ArgumentHolder[] selectArgs = (ArgumentHolder[]) argList.toArray(new ArgumentHolder[argList.size()]);
        FieldType[] resultFieldTypes = getResultFieldTypes();
        FieldType[] argFieldTypes = new FieldType[argList.size()];
        for (int selectC = 0; selectC < selectArgs.length; selectC++) {
            argFieldTypes[selectC] = selectArgs[selectC].getFieldType();
        }
        if (!this.type.isOkForStatementBuilder()) {
            throw new IllegalStateException("Building a statement from a " + this.type + " statement is not allowed");
        }
        return new MappedPreparedStmt<>(this.tableInfo, statement, argFieldTypes, resultFieldTypes, selectArgs, this.databaseType.isLimitSqlSupported() ? null : limit, this.type);
    }

    public String prepareStatementString() throws SQLException {
        return buildStatementString(new ArrayList<>());
    }

    public StatementInfo prepareStatementInfo() throws SQLException {
        List<ArgumentHolder> argList = new ArrayList<>();
        return new StatementInfo(buildStatementString(argList), argList);
    }

    @Deprecated
    public void clear() {
        reset();
    }

    public void reset() {
        this.where = null;
    }

    /* access modifiers changed from: protected */
    public String buildStatementString(List<ArgumentHolder> argList) throws SQLException {
        StringBuilder sb = new StringBuilder(128);
        appendStatementString(sb, argList);
        String statement = sb.toString();
        logger.debug("built statement {}", (Object) statement);
        return statement;
    }

    /* access modifiers changed from: protected */
    public void appendStatementString(StringBuilder sb, List<ArgumentHolder> argList) throws SQLException {
        appendStatementStart(sb, argList);
        appendWhereStatement(sb, argList, WhereOperation.FIRST);
        appendStatementEnd(sb, argList);
    }

    /* access modifiers changed from: protected */
    public boolean appendWhereStatement(StringBuilder sb, List<ArgumentHolder> argList, WhereOperation operation) throws SQLException {
        if (this.where != null) {
            operation.appendBefore(sb);
            this.where.appendSql(this.addTableName ? this.tableName : null, sb, argList);
            operation.appendAfter(sb);
            return false;
        } else if (operation == WhereOperation.FIRST) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldPrependTableNameToColumns() {
        return false;
    }

    /* access modifiers changed from: protected */
    public FieldType[] getResultFieldTypes() {
        return null;
    }

    /* access modifiers changed from: protected */
    public FieldType verifyColumnName(String columnName) {
        return this.tableInfo.getFieldTypeByColumnName(columnName);
    }

    /* access modifiers changed from: package-private */
    public StatementType getType() {
        return this.type;
    }

    public enum StatementType {
        SELECT(true, true, false, false),
        SELECT_LONG(true, true, false, false),
        SELECT_RAW(true, true, false, false),
        UPDATE(true, false, true, false),
        DELETE(true, false, true, false),
        EXECUTE(false, false, false, true);
        
        private final boolean okForExecute;
        private final boolean okForQuery;
        private final boolean okForStatementBuilder;
        private final boolean okForUpdate;

        private StatementType(boolean okForStatementBuilder2, boolean okForQuery2, boolean okForUpdate2, boolean okForExecute2) {
            this.okForStatementBuilder = okForStatementBuilder2;
            this.okForQuery = okForQuery2;
            this.okForUpdate = okForUpdate2;
            this.okForExecute = okForExecute2;
        }

        public boolean isOkForStatementBuilder() {
            return this.okForStatementBuilder;
        }

        public boolean isOkForQuery() {
            return this.okForQuery;
        }

        public boolean isOkForUpdate() {
            return this.okForUpdate;
        }

        public boolean isOkForExecute() {
            return this.okForExecute;
        }
    }

    public static class StatementInfo {
        private final List<ArgumentHolder> argList;
        private final String statement;

        private StatementInfo(String statement2, List<ArgumentHolder> argList2) {
            this.argList = argList2;
            this.statement = statement2;
        }

        public String getStatement() {
            return this.statement;
        }

        public List<ArgumentHolder> getArgList() {
            return this.argList;
        }
    }

    protected enum WhereOperation {
        FIRST("WHERE ", (int) null),
        AND("AND (", ") "),
        OR("OR (", ") ");
        
        private final String after;
        private final String before;

        private WhereOperation(String before2, String after2) {
            this.before = before2;
            this.after = after2;
        }

        public void appendBefore(StringBuilder sb) {
            if (this.before != null) {
                sb.append(this.before);
            }
        }

        public void appendAfter(StringBuilder sb) {
            if (this.after != null) {
                sb.append(this.after);
            }
        }
    }
}
