package com.j256.ormlite.dao;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {
    private static Map<ClassConnectionSource, Dao<?, ?>> classMap = null;
    private static Map<Class<?>, DatabaseTableConfig<?>> configMap = null;
    private static Logger logger = LoggerFactory.getLogger((Class<?>) DaoManager.class);
    private static Map<TableConfigConnectionSource, Dao<?, ?>> tableConfigMap = null;

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0085, code lost:
        r2 = new java.lang.Object[]{r17};
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized <D extends com.j256.ormlite.dao.Dao<T, ?>, T> D createDao(com.j256.ormlite.support.ConnectionSource r17, java.lang.Class<T> r18) throws java.sql.SQLException {
        /*
            java.lang.Class<com.j256.ormlite.dao.DaoManager> r14 = com.j256.ormlite.dao.DaoManager.class
            monitor-enter(r14)
            if (r17 != 0) goto L_0x0010
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x000d }
            java.lang.String r15 = "connectionSource argument cannot be null"
            r13.<init>(r15)     // Catch:{ all -> 0x000d }
            throw r13     // Catch:{ all -> 0x000d }
        L_0x000d:
            r13 = move-exception
            monitor-exit(r14)
            throw r13
        L_0x0010:
            com.j256.ormlite.dao.DaoManager$ClassConnectionSource r12 = new com.j256.ormlite.dao.DaoManager$ClassConnectionSource     // Catch:{ all -> 0x000d }
            r0 = r17
            r1 = r18
            r12.<init>(r0, r1)     // Catch:{ all -> 0x000d }
            com.j256.ormlite.dao.Dao r5 = lookupDao((com.j256.ormlite.dao.DaoManager.ClassConnectionSource) r12)     // Catch:{ all -> 0x000d }
            if (r5 == 0) goto L_0x0022
            r3 = r5
        L_0x0020:
            monitor-exit(r14)
            return r3
        L_0x0022:
            java.lang.Object r5 = createDaoFromConfig(r17, r18)     // Catch:{ all -> 0x000d }
            com.j256.ormlite.dao.Dao r5 = (com.j256.ormlite.dao.Dao) r5     // Catch:{ all -> 0x000d }
            if (r5 == 0) goto L_0x002c
            r3 = r5
            goto L_0x0020
        L_0x002c:
            java.lang.Class<com.j256.ormlite.table.DatabaseTable> r13 = com.j256.ormlite.table.DatabaseTable.class
            r0 = r18
            java.lang.annotation.Annotation r9 = r0.getAnnotation(r13)     // Catch:{ all -> 0x000d }
            com.j256.ormlite.table.DatabaseTable r9 = (com.j256.ormlite.table.DatabaseTable) r9     // Catch:{ all -> 0x000d }
            if (r9 == 0) goto L_0x0048
            java.lang.Class r13 = r9.daoClass()     // Catch:{ all -> 0x000d }
            java.lang.Class<java.lang.Void> r15 = java.lang.Void.class
            if (r13 == r15) goto L_0x0048
            java.lang.Class r13 = r9.daoClass()     // Catch:{ all -> 0x000d }
            java.lang.Class<com.j256.ormlite.dao.BaseDaoImpl> r15 = com.j256.ormlite.dao.BaseDaoImpl.class
            if (r13 != r15) goto L_0x0072
        L_0x0048:
            com.j256.ormlite.db.DatabaseType r10 = r17.getDatabaseType()     // Catch:{ all -> 0x000d }
            r0 = r17
            r1 = r18
            com.j256.ormlite.table.DatabaseTableConfig r4 = r10.extractDatabaseTableConfig(r0, r1)     // Catch:{ all -> 0x000d }
            if (r4 != 0) goto L_0x006b
            com.j256.ormlite.dao.Dao r8 = com.j256.ormlite.dao.BaseDaoImpl.createDao((com.j256.ormlite.support.ConnectionSource) r17, r18)     // Catch:{ all -> 0x000d }
        L_0x005a:
            r5 = r8
            com.j256.ormlite.logger.Logger r13 = logger     // Catch:{ all -> 0x000d }
            java.lang.String r15 = "created dao for class {} with reflection"
            r0 = r18
            r13.debug((java.lang.String) r15, (java.lang.Object) r0)     // Catch:{ all -> 0x000d }
        L_0x0064:
            r0 = r17
            registerDao(r0, r5)     // Catch:{ all -> 0x000d }
            r3 = r5
            goto L_0x0020
        L_0x006b:
            r0 = r17
            com.j256.ormlite.dao.Dao r8 = com.j256.ormlite.dao.BaseDaoImpl.createDao((com.j256.ormlite.support.ConnectionSource) r0, r4)     // Catch:{ all -> 0x000d }
            goto L_0x005a
        L_0x0072:
            java.lang.Class r6 = r9.daoClass()     // Catch:{ all -> 0x000d }
            r13 = 2
            java.lang.Object[] r2 = new java.lang.Object[r13]     // Catch:{ all -> 0x000d }
            r13 = 0
            r2[r13] = r17     // Catch:{ all -> 0x000d }
            r13 = 1
            r2[r13] = r18     // Catch:{ all -> 0x000d }
            java.lang.reflect.Constructor r7 = findConstructor(r6, r2)     // Catch:{ all -> 0x000d }
            if (r7 != 0) goto L_0x00b0
            r13 = 1
            java.lang.Object[] r2 = new java.lang.Object[r13]     // Catch:{ all -> 0x000d }
            r13 = 0
            r2[r13] = r17     // Catch:{ all -> 0x000d }
            java.lang.reflect.Constructor r7 = findConstructor(r6, r2)     // Catch:{ all -> 0x000d }
            if (r7 != 0) goto L_0x00b0
            java.sql.SQLException r13 = new java.sql.SQLException     // Catch:{ all -> 0x000d }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x000d }
            r15.<init>()     // Catch:{ all -> 0x000d }
            java.lang.String r16 = "Could not find public constructor with ConnectionSource and optional Class parameters "
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ all -> 0x000d }
            java.lang.StringBuilder r15 = r15.append(r6)     // Catch:{ all -> 0x000d }
            java.lang.String r16 = ".  Missing static on class?"
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ all -> 0x000d }
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x000d }
            r13.<init>(r15)     // Catch:{ all -> 0x000d }
            throw r13     // Catch:{ all -> 0x000d }
        L_0x00b0:
            java.lang.Object r13 = r7.newInstance(r2)     // Catch:{ Exception -> 0x00c2 }
            r0 = r13
            com.j256.ormlite.dao.Dao r0 = (com.j256.ormlite.dao.Dao) r0     // Catch:{ Exception -> 0x00c2 }
            r5 = r0
            com.j256.ormlite.logger.Logger r13 = logger     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r15 = "created dao for class {} from constructor"
            r0 = r18
            r13.debug((java.lang.String) r15, (java.lang.Object) r0)     // Catch:{ Exception -> 0x00c2 }
            goto L_0x0064
        L_0x00c2:
            r11 = move-exception
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x000d }
            r13.<init>()     // Catch:{ all -> 0x000d }
            java.lang.String r15 = "Could not call the constructor in class "
            java.lang.StringBuilder r13 = r13.append(r15)     // Catch:{ all -> 0x000d }
            java.lang.StringBuilder r13 = r13.append(r6)     // Catch:{ all -> 0x000d }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x000d }
            java.sql.SQLException r13 = com.j256.ormlite.misc.SqlExceptionUtil.create(r13, r11)     // Catch:{ all -> 0x000d }
            throw r13     // Catch:{ all -> 0x000d }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.dao.DaoManager.createDao(com.j256.ormlite.support.ConnectionSource, java.lang.Class):com.j256.ormlite.dao.Dao");
    }

    public static synchronized <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, Class<T> clazz) {
        D castDao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            castDao = lookupDao(new ClassConnectionSource(connectionSource, clazz));
        }
        return castDao;
    }

    public static synchronized <D extends Dao<T, ?>, T> D createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        D doCreateDao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            doCreateDao = doCreateDao(connectionSource, tableConfig);
        }
        return doCreateDao;
    }

    public static synchronized <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) {
        Dao<?, ?> dao;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            Dao<?, ?> dao2 = lookupDao(new TableConfigConnectionSource(connectionSource, tableConfig));
            if (dao2 == null) {
                dao = null;
            } else {
                dao = dao2;
            }
        }
        return dao;
    }

    public static synchronized void registerDao(ConnectionSource connectionSource, Dao<?, ?> dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            addDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
        }
    }

    public static synchronized void unregisterDao(ConnectionSource connectionSource, Dao<?, ?> dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            removeDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
        }
    }

    public static synchronized void registerDaoWithTableConfig(ConnectionSource connectionSource, Dao<?, ?> dao) {
        DatabaseTableConfig<?> tableConfig;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            } else if (!(dao instanceof BaseDaoImpl) || (tableConfig = ((BaseDaoImpl) dao).getTableConfig()) == null) {
                addDaoToClassMap(new ClassConnectionSource(connectionSource, dao.getDataClass()), dao);
            } else {
                addDaoToTableMap(new TableConfigConnectionSource(connectionSource, tableConfig), dao);
            }
        }
    }

    public static synchronized void clearCache() {
        synchronized (DaoManager.class) {
            if (configMap != null) {
                configMap.clear();
                configMap = null;
            }
            clearDaoCache();
        }
    }

    public static synchronized void clearDaoCache() {
        synchronized (DaoManager.class) {
            if (classMap != null) {
                classMap.clear();
                classMap = null;
            }
            if (tableConfigMap != null) {
                tableConfigMap.clear();
                tableConfigMap = null;
            }
        }
    }

    public static synchronized void addCachedDatabaseConfigs(Collection<DatabaseTableConfig<?>> configs) {
        Map<Class<?>, DatabaseTableConfig<?>> newMap;
        synchronized (DaoManager.class) {
            if (configMap == null) {
                newMap = new HashMap<>();
            } else {
                newMap = new HashMap<>(configMap);
            }
            for (DatabaseTableConfig<?> config : configs) {
                newMap.put(config.getDataClass(), config);
                logger.info("Loaded configuration for {}", (Object) config.getDataClass());
            }
            configMap = newMap;
        }
    }

    private static void addDaoToClassMap(ClassConnectionSource key, Dao<?, ?> dao) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        classMap.put(key, dao);
    }

    private static void removeDaoToClassMap(ClassConnectionSource key, Dao<?, ?> dao) {
        if (classMap != null) {
            classMap.remove(key);
        }
    }

    private static void addDaoToTableMap(TableConfigConnectionSource key, Dao<?, ?> dao) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        tableConfigMap.put(key, dao);
    }

    private static <T> Dao<?, ?> lookupDao(ClassConnectionSource key) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        Dao<?, ?> dao = classMap.get(key);
        if (dao == null) {
            return null;
        }
        return dao;
    }

    private static <T> Dao<?, ?> lookupDao(TableConfigConnectionSource key) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        Dao<?, ?> dao = tableConfigMap.get(key);
        if (dao == null) {
            return null;
        }
        return dao;
    }

    private static Constructor<?> findConstructor(Class<?> daoClass, Object[] params) {
        for (Constructor<?> constructor : daoClass.getConstructors()) {
            Class<?>[] paramsTypes = constructor.getParameterTypes();
            if (paramsTypes.length == params.length) {
                boolean match = true;
                int i = 0;
                while (true) {
                    if (i >= paramsTypes.length) {
                        break;
                    } else if (!paramsTypes[i].isAssignableFrom(params[i].getClass())) {
                        match = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (match) {
                    return constructor;
                }
            }
        }
        return null;
    }

    private static <D, T> D createDaoFromConfig(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        DatabaseTableConfig<T> config;
        if (configMap == null || (config = configMap.get(clazz)) == null) {
            return null;
        }
        return doCreateDao(connectionSource, config);
    }

    private static <D extends Dao<T, ?>, T> D doCreateDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
        Dao<?, ?> dao;
        TableConfigConnectionSource tableKey = new TableConfigConnectionSource(connectionSource, tableConfig);
        D dao2 = lookupDao(tableKey);
        if (dao2 != null) {
            return dao2;
        }
        Class<T> dataClass = tableConfig.getDataClass();
        ClassConnectionSource classKey = new ClassConnectionSource(connectionSource, dataClass);
        D dao3 = lookupDao(classKey);
        if (dao3 != null) {
            addDaoToTableMap(tableKey, dao3);
            return dao3;
        }
        DatabaseTable databaseTable = (DatabaseTable) tableConfig.getDataClass().getAnnotation(DatabaseTable.class);
        if (databaseTable == null || databaseTable.daoClass() == Void.class || databaseTable.daoClass() == BaseDaoImpl.class) {
            dao = BaseDaoImpl.createDao(connectionSource, tableConfig);
        } else {
            Class<?> daoClass = databaseTable.daoClass();
            Object[] arguments = {connectionSource, tableConfig};
            Constructor<?> constructor = findConstructor(daoClass, arguments);
            if (constructor == null) {
                throw new SQLException("Could not find public constructor with ConnectionSource, DatabaseTableConfig parameters in class " + daoClass);
            }
            try {
                dao = (Dao) constructor.newInstance(arguments);
            } catch (Exception e) {
                throw SqlExceptionUtil.create("Could not call the constructor in class " + daoClass, e);
            }
        }
        addDaoToTableMap(tableKey, dao);
        logger.debug("created dao for class {} from table config", (Object) dataClass);
        if (lookupDao(classKey) == null) {
            addDaoToClassMap(classKey, dao);
        }
        return dao;
    }

    private static class ClassConnectionSource {
        Class<?> clazz;
        ConnectionSource connectionSource;

        public ClassConnectionSource(ConnectionSource connectionSource2, Class<?> clazz2) {
            this.connectionSource = connectionSource2;
            this.clazz = clazz2;
        }

        public int hashCode() {
            return ((this.clazz.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ClassConnectionSource other = (ClassConnectionSource) obj;
            if (!this.clazz.equals(other.clazz) || !this.connectionSource.equals(other.connectionSource)) {
                return false;
            }
            return true;
        }
    }

    private static class TableConfigConnectionSource {
        ConnectionSource connectionSource;
        DatabaseTableConfig<?> tableConfig;

        public TableConfigConnectionSource(ConnectionSource connectionSource2, DatabaseTableConfig<?> tableConfig2) {
            this.connectionSource = connectionSource2;
            this.tableConfig = tableConfig2;
        }

        public int hashCode() {
            return ((this.tableConfig.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TableConfigConnectionSource other = (TableConfigConnectionSource) obj;
            if (!this.tableConfig.equals(other.tableConfig) || !this.connectionSource.equals(other.connectionSource)) {
                return false;
            }
            return true;
        }
    }
}
