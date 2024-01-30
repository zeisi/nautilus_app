package com.j256.ormlite.misc;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionManager {
    private static final String SAVE_POINT_PREFIX = "ORMLITE";
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) TransactionManager.class);
    private static AtomicInteger savePointCounter = new AtomicInteger();
    private ConnectionSource connectionSource;

    public TransactionManager() {
    }

    public TransactionManager(ConnectionSource connectionSource2) {
        this.connectionSource = connectionSource2;
        initialize();
    }

    public void initialize() {
        if (this.connectionSource == null) {
            throw new IllegalStateException("dataSource was not set on " + getClass().getSimpleName());
        }
    }

    public <T> T callInTransaction(Callable<T> callable) throws SQLException {
        return callInTransaction(this.connectionSource, callable);
    }

    public static <T> T callInTransaction(ConnectionSource connectionSource2, Callable<T> callable) throws SQLException {
        DatabaseConnection connection = connectionSource2.getReadWriteConnection();
        try {
            return callInTransaction(connection, connectionSource2.saveSpecialConnection(connection), connectionSource2.getDatabaseType(), callable);
        } finally {
            connectionSource2.clearSpecialConnection(connection);
            connectionSource2.releaseConnection(connection);
        }
    }

    public static <T> T callInTransaction(DatabaseConnection connection, DatabaseType databaseType, Callable<T> callable) throws SQLException {
        return callInTransaction(connection, false, databaseType, callable);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (r12.isNestedSavePointsSupported() != false) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0012, code lost:
        r0 = r10.isAutoCommit();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T callInTransaction(com.j256.ormlite.support.DatabaseConnection r10, boolean r11, com.j256.ormlite.db.DatabaseType r12, java.util.concurrent.Callable<T> r13) throws java.sql.SQLException {
        /*
            r9 = 1
            r0 = 0
            r3 = 0
            r5 = 0
            if (r11 != 0) goto L_0x000c
            boolean r6 = r12.isNestedSavePointsSupported()     // Catch:{ all -> 0x006c }
            if (r6 == 0) goto L_0x004a
        L_0x000c:
            boolean r6 = r10.isAutoCommitSupported()     // Catch:{ all -> 0x006c }
            if (r6 == 0) goto L_0x0023
            boolean r0 = r10.isAutoCommit()     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x0023
            r6 = 0
            r10.setAutoCommit(r6)     // Catch:{ all -> 0x006c }
            com.j256.ormlite.logger.Logger r6 = logger     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "had to set auto-commit to false"
            r6.debug(r7)     // Catch:{ all -> 0x006c }
        L_0x0023:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            r6.<init>()     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "ORMLITE"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x006c }
            java.util.concurrent.atomic.AtomicInteger r7 = savePointCounter     // Catch:{ all -> 0x006c }
            int r7 = r7.incrementAndGet()     // Catch:{ all -> 0x006c }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x006c }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x006c }
            java.sql.Savepoint r5 = r10.setSavePoint(r6)     // Catch:{ all -> 0x006c }
            if (r5 != 0) goto L_0x0060
            com.j256.ormlite.logger.Logger r6 = logger     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "started savePoint transaction"
            r6.debug(r7)     // Catch:{ all -> 0x006c }
        L_0x0049:
            r3 = 1
        L_0x004a:
            java.lang.Object r4 = r13.call()     // Catch:{ SQLException -> 0x007a, Exception -> 0x008a }
            if (r3 == 0) goto L_0x0053
            commit(r10, r5)     // Catch:{ SQLException -> 0x007a, Exception -> 0x008a }
        L_0x0053:
            if (r0 == 0) goto L_0x005f
            r10.setAutoCommit(r9)
            com.j256.ormlite.logger.Logger r6 = logger
            java.lang.String r7 = "restored auto-commit to true"
            r6.debug(r7)
        L_0x005f:
            return r4
        L_0x0060:
            com.j256.ormlite.logger.Logger r6 = logger     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "started savePoint transaction {}"
            java.lang.String r8 = r5.getSavepointName()     // Catch:{ all -> 0x006c }
            r6.debug((java.lang.String) r7, (java.lang.Object) r8)     // Catch:{ all -> 0x006c }
            goto L_0x0049
        L_0x006c:
            r6 = move-exception
            if (r0 == 0) goto L_0x0079
            r10.setAutoCommit(r9)
            com.j256.ormlite.logger.Logger r7 = logger
            java.lang.String r8 = "restored auto-commit to true"
            r7.debug(r8)
        L_0x0079:
            throw r6
        L_0x007a:
            r1 = move-exception
            if (r3 == 0) goto L_0x0080
            rollBack(r10, r5)     // Catch:{ SQLException -> 0x0081 }
        L_0x0080:
            throw r1     // Catch:{ all -> 0x006c }
        L_0x0081:
            r2 = move-exception
            com.j256.ormlite.logger.Logger r6 = logger     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "after commit exception, rolling back to save-point also threw exception"
            r6.error((java.lang.Throwable) r1, (java.lang.String) r7)     // Catch:{ all -> 0x006c }
            goto L_0x0080
        L_0x008a:
            r1 = move-exception
            if (r3 == 0) goto L_0x0090
            rollBack(r10, r5)     // Catch:{ SQLException -> 0x0097 }
        L_0x0090:
            java.lang.String r6 = "Transaction callable threw non-SQL exception"
            java.sql.SQLException r6 = com.j256.ormlite.misc.SqlExceptionUtil.create(r6, r1)     // Catch:{ all -> 0x006c }
            throw r6     // Catch:{ all -> 0x006c }
        L_0x0097:
            r2 = move-exception
            com.j256.ormlite.logger.Logger r6 = logger     // Catch:{ all -> 0x006c }
            java.lang.String r7 = "after commit exception, rolling back to save-point also threw exception"
            r6.error((java.lang.Throwable) r1, (java.lang.String) r7)     // Catch:{ all -> 0x006c }
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.misc.TransactionManager.callInTransaction(com.j256.ormlite.support.DatabaseConnection, boolean, com.j256.ormlite.db.DatabaseType, java.util.concurrent.Callable):java.lang.Object");
    }

    public void setConnectionSource(ConnectionSource connectionSource2) {
        this.connectionSource = connectionSource2;
    }

    private static void commit(DatabaseConnection connection, Savepoint savePoint) throws SQLException {
        String name = savePoint == null ? null : savePoint.getSavepointName();
        connection.commit(savePoint);
        if (name == null) {
            logger.debug("committed savePoint transaction");
        } else {
            logger.debug("committed savePoint transaction {}", (Object) name);
        }
    }

    private static void rollBack(DatabaseConnection connection, Savepoint savePoint) throws SQLException {
        String name = savePoint == null ? null : savePoint.getSavepointName();
        connection.rollback(savePoint);
        if (name == null) {
            logger.debug("rolled back savePoint transaction");
        } else {
            logger.debug("rolled back savePoint transaction {}", (Object) name);
        }
    }
}
