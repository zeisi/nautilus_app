package com.j256.ormlite.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class BaseDaoImpl<T, ID> implements Dao<T, ID> {
    private static final ThreadLocal<List<BaseDaoImpl<?, ?>>> daoConfigLevelLocal = new ThreadLocal<List<BaseDaoImpl<?, ?>>>() {
        /* access modifiers changed from: protected */
        public List<BaseDaoImpl<?, ?>> initialValue() {
            return new ArrayList(10);
        }
    };
    private static ReferenceObjectCache defaultObjectCache;
    protected ConnectionSource connectionSource;
    protected final Class<T> dataClass;
    protected DatabaseType databaseType;
    private boolean initialized;
    protected CloseableIterator<T> lastIterator;
    private ObjectCache objectCache;
    protected ObjectFactory<T> objectFactory;
    protected StatementExecutor<T, ID> statementExecutor;
    protected DatabaseTableConfig<T> tableConfig;
    protected TableInfo<T, ID> tableInfo;

    protected BaseDaoImpl(Class<T> dataClass2) throws SQLException {
        this((ConnectionSource) null, dataClass2, (DatabaseTableConfig) null);
    }

    protected BaseDaoImpl(ConnectionSource connectionSource2, Class<T> dataClass2) throws SQLException {
        this(connectionSource2, dataClass2, (DatabaseTableConfig) null);
    }

    protected BaseDaoImpl(ConnectionSource connectionSource2, DatabaseTableConfig<T> tableConfig2) throws SQLException {
        this(connectionSource2, tableConfig2.getDataClass(), tableConfig2);
    }

    private BaseDaoImpl(ConnectionSource connectionSource2, Class<T> dataClass2, DatabaseTableConfig<T> tableConfig2) throws SQLException {
        this.dataClass = dataClass2;
        this.tableConfig = tableConfig2;
        if (connectionSource2 != null) {
            this.connectionSource = connectionSource2;
            initialize();
        }
    }

    public void initialize() throws SQLException {
        BaseDaoImpl<?, ?> dao;
        if (!this.initialized) {
            if (this.connectionSource == null) {
                throw new IllegalStateException("connectionSource was never set on " + getClass().getSimpleName());
            }
            this.databaseType = this.connectionSource.getDatabaseType();
            if (this.databaseType == null) {
                throw new IllegalStateException("connectionSource is getting a null DatabaseType in " + getClass().getSimpleName());
            }
            if (this.tableConfig == null) {
                this.tableInfo = new TableInfo<>(this.connectionSource, this, this.dataClass);
            } else {
                this.tableConfig.extractFieldTypes(this.connectionSource);
                this.tableInfo = new TableInfo<>(this.databaseType, this, this.tableConfig);
            }
            this.statementExecutor = new StatementExecutor<>(this.databaseType, this.tableInfo, this);
            List<BaseDaoImpl<?, ?>> daoConfigList = daoConfigLevelLocal.get();
            daoConfigList.add(this);
            if (daoConfigList.size() <= 1) {
                int i = 0;
                while (i < daoConfigList.size()) {
                    try {
                        dao = daoConfigList.get(i);
                        DaoManager.registerDao(this.connectionSource, dao);
                        for (FieldType fieldType : dao.getTableInfo().getFieldTypes()) {
                            fieldType.configDaoInformation(this.connectionSource, dao.getDataClass());
                        }
                        dao.initialized = true;
                        i++;
                    } catch (SQLException e) {
                        DaoManager.unregisterDao(this.connectionSource, dao);
                        throw e;
                    } catch (Throwable th) {
                        daoConfigList.clear();
                        daoConfigLevelLocal.remove();
                        throw th;
                    }
                }
                daoConfigList.clear();
                daoConfigLevelLocal.remove();
            }
        }
    }

    public T queryForId(ID id) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForId(connection, id, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public T queryForFirst(PreparedQuery<T> preparedQuery) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForFirst(connection, preparedQuery, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public List<T> queryForAll() throws SQLException {
        checkForInitialized();
        return this.statementExecutor.queryForAll(this.connectionSource, this.objectCache);
    }

    public List<T> queryForEq(String fieldName, Object value) throws SQLException {
        return queryBuilder().where().eq(fieldName, value).query();
    }

    public QueryBuilder<T, ID> queryBuilder() {
        checkForInitialized();
        return new QueryBuilder<>(this.databaseType, this.tableInfo, this);
    }

    public UpdateBuilder<T, ID> updateBuilder() {
        checkForInitialized();
        return new UpdateBuilder<>(this.databaseType, this.tableInfo, this);
    }

    public DeleteBuilder<T, ID> deleteBuilder() {
        checkForInitialized();
        return new DeleteBuilder<>(this.databaseType, this.tableInfo, this);
    }

    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        checkForInitialized();
        return this.statementExecutor.query(this.connectionSource, preparedQuery, this.objectCache);
    }

    public List<T> queryForMatching(T matchObj) throws SQLException {
        return queryForMatching(matchObj, false);
    }

    public List<T> queryForMatchingArgs(T matchObj) throws SQLException {
        return queryForMatching(matchObj, true);
    }

    public List<T> queryForFieldValues(Map<String, Object> fieldValues) throws SQLException {
        return queryForFieldValues(fieldValues, false);
    }

    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) throws SQLException {
        return queryForFieldValues(fieldValues, true);
    }

    public T queryForSameId(T data) throws SQLException {
        ID id;
        checkForInitialized();
        if (data == null || (id = extractId(data)) == null) {
            return null;
        }
        return queryForId(id);
    }

    public int create(T data) throws SQLException {
        checkForInitialized();
        if (data == null) {
            return 0;
        }
        if (data instanceof BaseDaoEnabled) {
            data.setDao(this);
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.create(connection, data, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public T createIfNotExists(T data) throws SQLException {
        if (data == null) {
            return null;
        }
        T existing = queryForSameId(data);
        if (existing != null) {
            return existing;
        }
        create(data);
        return data;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(T data) throws SQLException {
        if (data == null) {
            return new Dao.CreateOrUpdateStatus(false, false, 0);
        }
        ID id = extractId(data);
        if (id == null || !idExists(id)) {
            return new Dao.CreateOrUpdateStatus(true, false, create(data));
        }
        return new Dao.CreateOrUpdateStatus(false, true, update(data));
    }

    public int update(T data) throws SQLException {
        checkForInitialized();
        if (data == null) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.update(connection, data, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int updateId(T data, ID newId) throws SQLException {
        checkForInitialized();
        if (data == null) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.updateId(connection, data, newId, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.update(connection, preparedUpdate);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int refresh(T data) throws SQLException {
        checkForInitialized();
        if (data == null) {
            return 0;
        }
        if (data instanceof BaseDaoEnabled) {
            data.setDao(this);
        }
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.refresh(connection, data, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int delete(T data) throws SQLException {
        checkForInitialized();
        if (data == null) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.delete(connection, data, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int deleteById(ID id) throws SQLException {
        checkForInitialized();
        if (id == null) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteById(connection, id, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int delete(Collection<T> datas) throws SQLException {
        checkForInitialized();
        if (datas == null || datas.isEmpty()) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteObjects(connection, datas, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int deleteIds(Collection<ID> ids) throws SQLException {
        checkForInitialized();
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteIds(connection, ids, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.delete(connection, preparedDelete);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public CloseableIterator<T> iterator() {
        return iterator(-1);
    }

    public CloseableIterator<T> closeableIterator() {
        return iterator(-1);
    }

    public CloseableIterator<T> iterator(int resultFlags) {
        checkForInitialized();
        this.lastIterator = createIterator(resultFlags);
        return this.lastIterator;
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() {
            public Iterator<T> iterator() {
                return closeableIterator();
            }

            public CloseableIterator<T> closeableIterator() {
                try {
                    return BaseDaoImpl.this.createIterator(-1);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build iterator for " + BaseDaoImpl.this.dataClass, e);
                }
            }
        });
    }

    public CloseableWrappedIterable<T> getWrappedIterable(final PreparedQuery<T> preparedQuery) {
        checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() {
            public Iterator<T> iterator() {
                return closeableIterator();
            }

            public CloseableIterator<T> closeableIterator() {
                try {
                    return BaseDaoImpl.this.createIterator(preparedQuery, -1);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build prepared-query iterator for " + BaseDaoImpl.this.dataClass, e);
                }
            }
        });
    }

    public void closeLastIterator() throws SQLException {
        if (this.lastIterator != null) {
            this.lastIterator.close();
            this.lastIterator = null;
        }
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) throws SQLException {
        return iterator(preparedQuery, -1);
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int resultFlags) throws SQLException {
        checkForInitialized();
        this.lastIterator = createIterator(preparedQuery, resultFlags);
        return this.lastIterator;
    }

    public GenericRawResults<String[]> queryRaw(String query, String... arguments) throws SQLException {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, query, arguments, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + query, e);
        }
    }

    public <GR> GenericRawResults<GR> queryRaw(String query, RawRowMapper<GR> mapper, String... arguments) throws SQLException {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, query, mapper, arguments, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + query, e);
        }
    }

    public <UO> GenericRawResults<UO> queryRaw(String query, DataType[] columnTypes, RawRowObjectMapper<UO> mapper, String... arguments) throws SQLException {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, query, columnTypes, mapper, arguments, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + query, e);
        }
    }

    public GenericRawResults<Object[]> queryRaw(String query, DataType[] columnTypes, String... arguments) throws SQLException {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, query, columnTypes, arguments, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + query, e);
        }
    }

    public long queryRawValue(String query, String... arguments) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            long queryForLong = this.statementExecutor.queryForLong(connection, query, arguments);
            this.connectionSource.releaseConnection(connection);
            return queryForLong;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw value query for " + query, e);
        } catch (Throwable th) {
            this.connectionSource.releaseConnection(connection);
            throw th;
        }
    }

    public int executeRaw(String statement, String... arguments) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            int executeRaw = this.statementExecutor.executeRaw(connection, statement, arguments);
            this.connectionSource.releaseConnection(connection);
            return executeRaw;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not run raw execute statement " + statement, e);
        } catch (Throwable th) {
            this.connectionSource.releaseConnection(connection);
            throw th;
        }
    }

    public int executeRawNoArgs(String statement) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            int executeRawNoArgs = this.statementExecutor.executeRawNoArgs(connection, statement);
            this.connectionSource.releaseConnection(connection);
            return executeRawNoArgs;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not run raw execute statement " + statement, e);
        } catch (Throwable th) {
            this.connectionSource.releaseConnection(connection);
            throw th;
        }
    }

    public int updateRaw(String statement, String... arguments) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            int updateRaw = this.statementExecutor.updateRaw(connection, statement, arguments);
            this.connectionSource.releaseConnection(connection);
            return updateRaw;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not run raw update statement " + statement, e);
        } catch (Throwable th) {
            this.connectionSource.releaseConnection(connection);
            throw th;
        }
    }

    public <CT> CT callBatchTasks(Callable<CT> callable) throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.callBatchTasks(connection, this.connectionSource.saveSpecialConnection(connection), callable);
        } finally {
            this.connectionSource.clearSpecialConnection(connection);
            this.connectionSource.releaseConnection(connection);
        }
    }

    public String objectToString(T data) {
        checkForInitialized();
        return this.tableInfo.objectToString(data);
    }

    public boolean objectsEqual(T data1, T data2) throws SQLException {
        checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (!fieldType.getDataPersister().dataIsEqual(fieldType.extractJavaFieldValue(data1), fieldType.extractJavaFieldValue(data2))) {
                return false;
            }
        }
        return true;
    }

    public ID extractId(T data) throws SQLException {
        checkForInitialized();
        FieldType idField = this.tableInfo.getIdField();
        if (idField != null) {
            return idField.extractJavaFieldValue(data);
        }
        throw new SQLException("Class " + this.dataClass + " does not have an id field");
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public FieldType findForeignFieldType(Class<?> clazz) {
        checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (fieldType.getType() == clazz) {
                return fieldType;
            }
        }
        return null;
    }

    public boolean isUpdatable() {
        return this.tableInfo.isUpdatable();
    }

    public boolean isTableExists() throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return connection.isTableExists(this.tableInfo.getTableName());
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public long countOf() throws SQLException {
        checkForInitialized();
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForCountStar(connection);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public long countOf(PreparedQuery<T> preparedQuery) throws SQLException {
        checkForInitialized();
        if (preparedQuery.getType() != StatementBuilder.StatementType.SELECT_LONG) {
            throw new IllegalArgumentException("Prepared query is not of type " + StatementBuilder.StatementType.SELECT_LONG + ", did you call QueryBuilder.setCountOf(true)?");
        }
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForLong(connection, preparedQuery);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public void assignEmptyForeignCollection(T parent, String fieldName) throws SQLException {
        makeEmptyForeignCollection(parent, fieldName);
    }

    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String fieldName) throws SQLException {
        return makeEmptyForeignCollection((Object) null, fieldName);
    }

    public void setObjectCache(boolean enabled) throws SQLException {
        if (enabled) {
            if (this.objectCache != null) {
                return;
            }
            if (this.tableInfo.getIdField() == null) {
                throw new SQLException("Class " + this.dataClass + " must have an id field to enable the object cache");
            }
            synchronized (BaseDaoImpl.class) {
                if (defaultObjectCache == null) {
                    defaultObjectCache = ReferenceObjectCache.makeWeakCache();
                }
                this.objectCache = defaultObjectCache;
            }
            this.objectCache.registerClass(this.dataClass);
        } else if (this.objectCache != null) {
            this.objectCache.clear(this.dataClass);
            this.objectCache = null;
        }
    }

    public void setObjectCache(ObjectCache objectCache2) throws SQLException {
        if (objectCache2 != null) {
            if (!(this.objectCache == null || this.objectCache == objectCache2)) {
                this.objectCache.clear(this.dataClass);
            }
            if (this.tableInfo.getIdField() == null) {
                throw new SQLException("Class " + this.dataClass + " must have an id field to enable the object cache");
            }
            this.objectCache = objectCache2;
            this.objectCache.registerClass(this.dataClass);
        } else if (this.objectCache != null) {
            this.objectCache.clear(this.dataClass);
            this.objectCache = null;
        }
    }

    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public void clearObjectCache() {
        if (this.objectCache != null) {
            this.objectCache.clear(this.dataClass);
        }
    }

    public static synchronized void clearAllInternalObjectCaches() {
        synchronized (BaseDaoImpl.class) {
            if (defaultObjectCache != null) {
                defaultObjectCache.clearAll();
                defaultObjectCache = null;
            }
        }
    }

    public T mapSelectStarRow(DatabaseResults results) throws SQLException {
        return this.statementExecutor.getSelectStarRowMapper().mapRow(results);
    }

    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException {
        return this.statementExecutor.getSelectStarRowMapper();
    }

    public RawRowMapper<T> getRawRowMapper() {
        return this.statementExecutor.getRawRowMapper();
    }

    public boolean idExists(ID id) throws SQLException {
        DatabaseConnection connection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.ifExists(connection, id);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public DatabaseConnection startThreadConnection() throws SQLException {
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        this.connectionSource.saveSpecialConnection(connection);
        return connection;
    }

    public void endThreadConnection(DatabaseConnection connection) throws SQLException {
        this.connectionSource.clearSpecialConnection(connection);
        this.connectionSource.releaseConnection(connection);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            setAutoCommit(connection, autoCommit);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public void setAutoCommit(DatabaseConnection connection, boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean isAutoCommit() throws SQLException {
        DatabaseConnection connection = this.connectionSource.getReadWriteConnection();
        try {
            return isAutoCommit(connection);
        } finally {
            this.connectionSource.releaseConnection(connection);
        }
    }

    public boolean isAutoCommit(DatabaseConnection connection) throws SQLException {
        return connection.isAutoCommit();
    }

    public void commit(DatabaseConnection connection) throws SQLException {
        connection.commit((Savepoint) null);
    }

    public void rollBack(DatabaseConnection connection) throws SQLException {
        connection.rollback((Savepoint) null);
    }

    public ObjectFactory<T> getObjectFactory() {
        return this.objectFactory;
    }

    public void setObjectFactory(ObjectFactory<T> objectFactory2) {
        checkForInitialized();
        this.objectFactory = objectFactory2;
    }

    public DatabaseTableConfig<T> getTableConfig() {
        return this.tableConfig;
    }

    public TableInfo<T, ID> getTableInfo() {
        return this.tableInfo;
    }

    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    public void setConnectionSource(ConnectionSource connectionSource2) {
        this.connectionSource = connectionSource2;
    }

    public void setTableConfig(DatabaseTableConfig<T> tableConfig2) {
        this.tableConfig = tableConfig2;
    }

    static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource2, Class<T> clazz) throws SQLException {
        return new BaseDaoImpl<T, ID>(connectionSource2, clazz) {
            public /* bridge */ /* synthetic */ Iterator iterator() {
                return BaseDaoImpl.super.iterator();
            }
        };
    }

    static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource2, DatabaseTableConfig<T> tableConfig2) throws SQLException {
        return new BaseDaoImpl<T, ID>(connectionSource2, tableConfig2) {
            public /* bridge */ /* synthetic */ Iterator iterator() {
                return BaseDaoImpl.super.iterator();
            }
        };
    }

    /* access modifiers changed from: protected */
    public void checkForInitialized() {
        if (!this.initialized) {
            throw new IllegalStateException("you must call initialize() before you can use the dao");
        }
    }

    private <FT> ForeignCollection<FT> makeEmptyForeignCollection(T parent, String fieldName) throws SQLException {
        Object extractId;
        checkForInitialized();
        if (parent == null) {
            extractId = null;
        } else {
            extractId = extractId(parent);
        }
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (fieldType.getColumnName().equals(fieldName)) {
                ForeignCollection<FT> collection = fieldType.buildForeignCollection(parent, extractId);
                if (parent != null) {
                    fieldType.assignField(parent, collection, true, (ObjectCache) null);
                }
                return collection;
            }
        }
        throw new IllegalArgumentException("Could not find a field named " + fieldName);
    }

    /* access modifiers changed from: private */
    public CloseableIterator<T> createIterator(int resultFlags) {
        try {
            return this.statementExecutor.buildIterator(this, this.connectionSource, resultFlags, this.objectCache);
        } catch (Exception e) {
            throw new IllegalStateException("Could not build iterator for " + this.dataClass, e);
        }
    }

    /* access modifiers changed from: private */
    public CloseableIterator<T> createIterator(PreparedQuery<T> preparedQuery, int resultFlags) throws SQLException {
        try {
            return this.statementExecutor.buildIterator(this, this.connectionSource, preparedQuery, this.objectCache, resultFlags);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not build prepared-query iterator for " + this.dataClass, e);
        }
    }

    private List<T> queryForMatching(T matchObj, boolean useArgs) throws SQLException {
        checkForInitialized();
        QueryBuilder<T, ID> qb = queryBuilder();
        Where<T, ID> where = qb.where();
        int fieldC = 0;
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            Object fieldValue = fieldType.getFieldValueIfNotDefault(matchObj);
            if (fieldValue != null) {
                if (useArgs) {
                    fieldValue = new SelectArg(fieldValue);
                }
                where.eq(fieldType.getColumnName(), fieldValue);
                fieldC++;
            }
        }
        if (fieldC == 0) {
            return Collections.emptyList();
        }
        where.and(fieldC);
        return qb.query();
    }

    private List<T> queryForFieldValues(Map<String, Object> fieldValues, boolean useArgs) throws SQLException {
        checkForInitialized();
        QueryBuilder<T, ID> qb = queryBuilder();
        Where<T, ID> where = qb.where();
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            Object fieldValue = entry.getValue();
            if (useArgs) {
                fieldValue = new SelectArg(fieldValue);
            }
            where.eq(entry.getKey(), fieldValue);
        }
        if (fieldValues.size() == 0) {
            return Collections.emptyList();
        }
        where.and(fieldValues.size());
        return qb.query();
    }
}
