package com.ua.sdk.cache.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.nautilus.omni.util.Constants;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.LocalDate;
import com.ua.sdk.Reference;
import com.ua.sdk.Resource;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.cache.database.definition.ColumnDefinition;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LegacyEntityDatabase<T extends Resource> extends SQLiteOpenHelper implements DiskCache<T> {
    protected static final int ENTITY_DATABASE_VERSION = 0;
    private static final String KEY_LINK_FOREIGN_KEY = "foreign_key";
    private static final String KEY_LINK_HREF = "href";
    private static final String KEY_LINK_ID = "id";
    private static final String KEY_LINK_NAME = "name";
    private static final String KEY_LINK_RELATION = "relation";
    private static final String KEY_META_ID = "id";
    private static final String KEY_META_LAST_UPDATE_TIME_MS = "last_update_time_ms";
    private static final String KEY_META_PENDING_OPERATION = "pending_operation";
    private static final Integer STATE_CREATED = 1;
    private static final Integer STATE_DELETED = 4;
    private static final Integer STATE_MODIFIED = 2;
    private static final Integer STATE_SYNCED = 0;
    private static final String TABLE_LINKS = "links";
    private static final String TABLE_META = "meta";
    private final String[] mEntityCols;
    private final String mEntityKeyCol;
    private final String mEntityTable;

    /* access modifiers changed from: protected */
    public abstract void createEntityTable(SQLiteDatabase sQLiteDatabase);

    /* access modifiers changed from: protected */
    public abstract ContentValues getContentValuesFromEntity(T t);

    /* access modifiers changed from: protected */
    public abstract T getEntityFromCursor(Cursor cursor);

    public abstract void onEntityUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    protected LegacyEntityDatabase(Context context, String databaseName, String entityTable, String[] entityCols, String entityKeyCol, int version) {
        super(context, databaseName, (SQLiteDatabase.CursorFactory) null, getCombinedVersion(0, version));
        this.mEntityTable = entityTable;
        this.mEntityKeyCol = entityKeyCol;
        this.mEntityCols = entityCols;
        if (Arrays.binarySearch(entityCols, "_id") < 0) {
            throw new IllegalArgumentException("entityCols do not contain _id");
        }
    }

    public final void onCreate(SQLiteDatabase db) {
        createMetaTables(db);
        createEntityTable(db);
    }

    private void createMetaTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE links(foreign_key INTEGER NOT NULL,relation TEXT,href TEXT,id TEXT,name TEXT)");
        db.execSQL("CREATE TABLE meta(id INTEGER PRIMARY KEY UNIQUE NOT NULL,pending_operation NUMERIC,last_update_time_ms NUMERIC)");
    }

    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (getMyVersion(newVersion) > getMyVersion(oldVersion)) {
            db.execSQL("DROP TABLE IF EXISTS links");
            db.execSQL("DROP TABLE IF EXISTS meta");
            createMetaTables(db);
        }
        int newSubVersion = getSubVersion(newVersion);
        int oldSubVersion = getSubVersion(oldVersion);
        if (newSubVersion > oldSubVersion) {
            onEntityUpgrade(db, oldSubVersion, newSubVersion);
        }
    }

    private static int getCombinedVersion(int myVersion, int subVersion) {
        return (subVersion << 15) | myVersion;
    }

    private static int getMyVersion(int version) {
        return version & 8191;
    }

    private static int getSubVersion(int version) {
        return (536805376 & version) >> 15;
    }

    public static String buildCreateStatement(String tableName, ColumnDefinition[] columnDefinitions) {
        StringBuilder answer = new StringBuilder();
        answer.append("CREATE TABLE ");
        answer.append(tableName);
        answer.append(" (");
        for (int i = 0; i < columnDefinitions.length; i++) {
            ColumnDefinition col = columnDefinitions[i];
            answer.append(col.getColumnName());
            answer.append(Constants.EMPTY_SPACE);
            answer.append(col.getDbType());
            if (i + 1 < columnDefinitions.length) {
                answer.append(",");
            }
        }
        answer.append(")");
        return answer.toString();
    }

    public static String[] buildColumnNames(ColumnDefinition[] columnDefinitions) {
        String[] answer = new String[columnDefinitions.length];
        for (int i = 0; i < columnDefinitions.length; i++) {
            answer[i] = columnDefinitions[i].getColumnName();
        }
        return answer;
    }

    private void insertOrReplaceMetadataAfterFetch(SQLiteDatabase db, long foreignKey) {
        insertOrReplaceMetadata(db, foreignKey, System.currentTimeMillis(), DiskCache.State.SYNCED);
    }

    private void insertOrReplaceMetadata(SQLiteDatabase db, long foreignKey, DiskCache.State state) {
        ContentValues values = new ContentValues();
        values.put("pending_operation", stateToDatabaseValue(state));
        if (db.update(TABLE_META, values, "id=?", new String[]{String.valueOf(foreignKey)}) == 0) {
            values.put("id", Long.valueOf(foreignKey));
            db.insert(TABLE_META, (String) null, values);
        }
    }

    private void insertOrReplaceMetadata(SQLiteDatabase db, long foreignKey, long lastUpdateFromServerMs, DiskCache.State state) {
        ContentValues values = new ContentValues();
        values.put("id", Long.valueOf(foreignKey));
        values.put("pending_operation", stateToDatabaseValue(state));
        values.put(KEY_META_LAST_UPDATE_TIME_MS, Long.valueOf(lastUpdateFromServerMs));
        db.insertWithOnConflict(TABLE_META, (String) null, values, 5);
    }

    private Integer stateToDatabaseValue(DiskCache.State state) {
        if (state == null) {
            return null;
        }
        switch (state) {
            case CREATED:
                return STATE_CREATED;
            case MODIFIED:
                return STATE_MODIFIED;
            case SYNCED:
                return STATE_SYNCED;
            case DELETED:
                return STATE_DELETED;
            default:
                return null;
        }
    }

    private DiskCache.State stateFromDatabaseValue(int state) {
        if (state == STATE_CREATED.intValue()) {
            return DiskCache.State.CREATED;
        }
        if (state == STATE_DELETED.intValue()) {
            return DiskCache.State.DELETED;
        }
        if (state == STATE_MODIFIED.intValue()) {
            return DiskCache.State.MODIFIED;
        }
        if (state == STATE_SYNCED.intValue()) {
            return DiskCache.State.SYNCED;
        }
        return DiskCache.State.NONE;
    }

    private void bulkInsertLinks(SQLiteDatabase db, long foreignKey, ApiTransferObject entity) {
        SQLiteStatement statement = db.compileStatement("INSERT INTO links VALUES (?,?,?,?,?);");
        try {
            Map<String, ArrayList<Link>> linkMap = entity.getLinkMap();
            if (linkMap != null) {
                for (String key : linkMap.keySet()) {
                    for (Link link : linkMap.get(key)) {
                        statement.clearBindings();
                        statement.bindLong(1, foreignKey);
                        statement.bindString(2, key);
                        nullSafeBind(statement, 3, link.getHref());
                        nullSafeBind(statement, 4, link.getId());
                        nullSafeBind(statement, 5, link.getName());
                        statement.execute();
                    }
                }
            }
        } finally {
            statement.close();
        }
    }

    private void nullSafeBind(SQLiteStatement statement, int index, String value) {
        if (value != null) {
            statement.bindString(index, value);
        } else {
            statement.bindNull(index);
        }
    }

    public synchronized Map<String, ArrayList<Link>> getLinkMap(Reference ref) {
        Map<String, ArrayList<Link>> emptyMap;
        try {
            SQLiteDatabase db = getReadableDatabase();
            long localId = getLocalId(db, ref);
            if (localId < 0) {
                emptyMap = Collections.emptyMap();
                close();
            } else {
                emptyMap = getLinkMap(db, localId);
                close();
            }
        } catch (Throwable t) {
            UaLog.error("Unable to get link map.", t);
            emptyMap = Collections.emptyMap();
        }
        return emptyMap;
    }

    /* access modifiers changed from: protected */
    public Map<String, ArrayList<Link>> getLinkMap(SQLiteDatabase db, long id) {
        Cursor linksCursor = db.query(TABLE_LINKS, (String[]) null, "foreign_key=?", new String[]{String.valueOf(id)}, (String) null, (String) null, (String) null, (String) null);
        Map<String, ArrayList<Link>> linkMap = new HashMap<>();
        while (linksCursor.moveToNext()) {
            try {
                String relation = linksCursor.getString(linksCursor.getColumnIndex(KEY_LINK_RELATION));
                Link link = new Link(linksCursor.getString(linksCursor.getColumnIndex(KEY_LINK_HREF)), linksCursor.getString(linksCursor.getColumnIndex("id")), linksCursor.getString(linksCursor.getColumnIndex("name")));
                ArrayList<Link> links = linkMap.get(relation);
                if (links == null) {
                    links = new ArrayList<>();
                    linkMap.put(relation, links);
                }
                links.add(link);
            } finally {
                linksCursor.close();
            }
        }
        return linkMap;
    }

    /* access modifiers changed from: protected */
    public <T extends ApiTransferObject> void setAllLinkMaps(SQLiteDatabase db, List<T> entities) {
        Cursor linksCursor = db.query(TABLE_LINKS, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "foreign_key ASC", (String) null);
        try {
            int fkCol = linksCursor.getColumnIndex(KEY_LINK_FOREIGN_KEY);
            int relationCol = linksCursor.getColumnIndex(KEY_LINK_RELATION);
            int idCol = linksCursor.getColumnIndex("id");
            int hrefCol = linksCursor.getColumnIndex(KEY_LINK_HREF);
            int nameCol = linksCursor.getColumnIndex("name");
            long fk = -1;
            long key = -1;
            int entityIndex = 0;
            int entityCount = entities.size();
            T entity = null;
            Map<String, ArrayList<Link>> linkMap = null;
            Link link = null;
            String relation = null;
            while (entityIndex < entityCount && !linksCursor.isLast()) {
                if (entity == null) {
                    entity = (ApiTransferObject) entities.get(entityIndex);
                    linkMap = new HashMap<>();
                    key = entity.getLocalId();
                }
                if (link == null) {
                    linksCursor.moveToNext();
                    fk = linksCursor.getLong(fkCol);
                    relation = linksCursor.getString(relationCol);
                    link = new Link(linksCursor.getString(hrefCol), linksCursor.getString(idCol), linksCursor.getString(nameCol));
                }
                if (key == fk) {
                    ArrayList<Link> links = linkMap.get(relation);
                    if (links == null) {
                        links = new ArrayList<>();
                        linkMap.put(relation, links);
                    }
                    links.add(link);
                    link = null;
                } else if (key < fk) {
                    if (!linkMap.isEmpty()) {
                        entity.setLinkMap(linkMap);
                    }
                    entity = null;
                    linkMap = null;
                    entityIndex++;
                } else {
                    link = null;
                }
            }
            if (!(entity == null || linkMap == null)) {
                entity.setLinkMap(linkMap);
            }
        } finally {
            linksCursor.close();
        }
    }

    public synchronized void deleteAll() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            deleteAllEntities(db);
            deleteAllLinks(db);
            deleteAllMetadata(db);
            close();
        } catch (Throwable t) {
            UaLog.error("Unable to delete all entities.", t);
        }
        return;
    }

    /* access modifiers changed from: protected */
    public void deleteAllLinksWithId(SQLiteDatabase db, long id) {
        if (id >= 0) {
            db.delete(TABLE_LINKS, "foreign_key=?", new String[]{Long.toString(id)});
        }
    }

    /* access modifiers changed from: protected */
    public void deleteAllLinks(SQLiteDatabase db) {
        db.execSQL("DELETE FROM links");
    }

    /* access modifiers changed from: protected */
    public void deleteAllMetadata(SQLiteDatabase db) {
        db.execSQL("DELETE FROM meta");
    }

    /* access modifiers changed from: protected */
    public void deleteAllMetadataWithId(SQLiteDatabase db, long id) {
        if (id >= 0) {
            db.delete(TABLE_META, "id=?", new String[]{Long.toString(id)});
        }
    }

    /* access modifiers changed from: protected */
    public long getLocalId(SQLiteDatabase db, Reference ref) {
        String id;
        long localId = -1;
        if ((ref instanceof LinkEntityRef) && ((LinkEntityRef) ref).checkCache()) {
            long id2 = ((LinkEntityRef) ref).getLocalId();
            if (id2 >= 0) {
                localId = id2;
            }
        }
        if (localId == -1 && (id = ref.getId()) != null) {
            Cursor cursor = db.query(this.mEntityTable, new String[]{"_id"}, this.mEntityKeyCol + "= '" + id + "'", (String[]) null, (String) null, (String) null, (String) null);
            try {
                if (cursor.moveToFirst()) {
                    localId = cursor.getLong(0);
                }
            } finally {
                cursor.close();
            }
        }
        return localId;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
        r4 = getReadableDatabase();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized T get(com.ua.sdk.Reference r22) {
        /*
            r21 = this;
            monitor-enter(r21)
            java.lang.String r5 = "ref"
            r0 = r22
            com.ua.sdk.internal.Precondition.isNotNull(r0, r5)     // Catch:{ all -> 0x00c2 }
            r14 = 0
            r13 = 0
            r16 = 1
            r17 = 0
            r15 = 0
            r0 = r22
            boolean r5 = r0 instanceof com.ua.sdk.internal.LinkEntityRef     // Catch:{ Throwable -> 0x00b1 }
            if (r5 == 0) goto L_0x0022
            r0 = r22
            com.ua.sdk.internal.LinkEntityRef r0 = (com.ua.sdk.internal.LinkEntityRef) r0     // Catch:{ Throwable -> 0x00b1 }
            r5 = r0
            boolean r5 = r5.checkCache()     // Catch:{ Throwable -> 0x00b1 }
            if (r5 != 0) goto L_0x009a
            r16 = 0
        L_0x0022:
            if (r16 == 0) goto L_0x0034
            if (r17 != 0) goto L_0x0030
            r0 = r21
            java.lang.String r0 = r0.mEntityKeyCol     // Catch:{ Throwable -> 0x00b1 }
            r17 = r0
            java.lang.String r15 = r22.getId()     // Catch:{ Throwable -> 0x00b1 }
        L_0x0030:
            if (r15 != 0) goto L_0x0034
            r16 = 0
        L_0x0034:
            if (r16 == 0) goto L_0x0090
            android.database.sqlite.SQLiteDatabase r4 = r21.getReadableDatabase()     // Catch:{ Throwable -> 0x00b1 }
            r0 = r21
            java.lang.String r5 = r0.mEntityTable     // Catch:{ Throwable -> 0x00b1 }
            r0 = r21
            java.lang.String[] r6 = r0.mEntityCols     // Catch:{ Throwable -> 0x00b1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00b1 }
            r7.<init>()     // Catch:{ Throwable -> 0x00b1 }
            r0 = r17
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r8 = "=?"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00b1 }
            r8 = 1
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ Throwable -> 0x00b1 }
            r9 = 0
            r8[r9] = r15     // Catch:{ Throwable -> 0x00b1 }
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            android.database.Cursor r13 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Throwable -> 0x00b1 }
            if (r13 == 0) goto L_0x0090
            boolean r5 = r13.moveToFirst()     // Catch:{ Throwable -> 0x00b1 }
            if (r5 == 0) goto L_0x0090
            r0 = r21
            com.ua.sdk.Resource r14 = r0.getEntityFromCursor(r13)     // Catch:{ Throwable -> 0x00b1 }
            boolean r5 = r14 instanceof com.ua.sdk.internal.ApiTransferObject     // Catch:{ Throwable -> 0x00b1 }
            if (r5 == 0) goto L_0x0090
            java.lang.String r5 = "_id"
            int r5 = r13.getColumnIndex(r5)     // Catch:{ Throwable -> 0x00b1 }
            long r18 = r13.getLong(r5)     // Catch:{ Throwable -> 0x00b1 }
            r0 = r14
            com.ua.sdk.internal.ApiTransferObject r0 = (com.ua.sdk.internal.ApiTransferObject) r0     // Catch:{ Throwable -> 0x00b1 }
            r5 = r0
            r0 = r21
            r1 = r18
            java.util.Map r6 = r0.getLinkMap(r4, r1)     // Catch:{ Throwable -> 0x00b1 }
            r5.setLinkMap(r6)     // Catch:{ Throwable -> 0x00b1 }
        L_0x0090:
            if (r13 == 0) goto L_0x0095
            r13.close()     // Catch:{ all -> 0x00c2 }
        L_0x0095:
            r21.close()     // Catch:{ all -> 0x00c2 }
        L_0x0098:
            monitor-exit(r21)
            return r14
        L_0x009a:
            r0 = r22
            com.ua.sdk.internal.LinkEntityRef r0 = (com.ua.sdk.internal.LinkEntityRef) r0     // Catch:{ Throwable -> 0x00b1 }
            r5 = r0
            long r18 = r5.getLocalId()     // Catch:{ Throwable -> 0x00b1 }
            r6 = 0
            int r5 = (r18 > r6 ? 1 : (r18 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x0022
            java.lang.String r17 = "_id"
            java.lang.String r15 = java.lang.String.valueOf(r18)     // Catch:{ Throwable -> 0x00b1 }
            goto L_0x0022
        L_0x00b1:
            r20 = move-exception
            java.lang.String r5 = "Unable to get entity."
            r0 = r20
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00c5 }
            if (r13 == 0) goto L_0x00be
            r13.close()     // Catch:{ all -> 0x00c2 }
        L_0x00be:
            r21.close()     // Catch:{ all -> 0x00c2 }
            goto L_0x0098
        L_0x00c2:
            r5 = move-exception
            monitor-exit(r21)
            throw r5
        L_0x00c5:
            r5 = move-exception
            if (r13 == 0) goto L_0x00cb
            r13.close()     // Catch:{ all -> 0x00c2 }
        L_0x00cb:
            r21.close()     // Catch:{ all -> 0x00c2 }
            throw r5     // Catch:{ all -> 0x00c2 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.cache.database.LegacyEntityDatabase.get(com.ua.sdk.Reference):com.ua.sdk.Resource");
    }

    public EntityList<T> getList(Reference ref) {
        return null;
    }

    /* access modifiers changed from: protected */
    public ContentValues getContentValuesFromEntity(long localId, T entity) {
        ContentValues values = getContentValuesFromEntity(entity);
        if (localId >= 0) {
            values.put("_id", Long.valueOf(localId));
        } else if (values.containsKey("_id")) {
            values.remove("_id");
        }
        return values;
    }

    /* access modifiers changed from: protected */
    public long delete(SQLiteDatabase db, Reference ref) {
        int rowsChanged;
        long localId = getLocalId(db, ref);
        if (localId >= 0 && (rowsChanged = db.delete(this.mEntityTable, "_id=?", new String[]{Long.toString(localId)})) != 1) {
            UaLog.error("Failed to delete entity. refType=%s id=%s rowsChanged=%s", ref.getClass().getSimpleName(), Long.valueOf(localId), Integer.valueOf(rowsChanged));
        }
        return localId;
    }

    /* access modifiers changed from: protected */
    public long insert(SQLiteDatabase db, T entity) {
        long localId = db.insert(this.mEntityTable, "_id", getContentValuesFromEntity(-1, entity));
        deleteAllLinksWithId(db, localId);
        if (entity instanceof ApiTransferObject) {
            ((ApiTransferObject) entity).setLocalId(localId);
            bulkInsertLinks(db, localId, (ApiTransferObject) entity);
        }
        return localId;
    }

    /* access modifiers changed from: protected */
    public void update(SQLiteDatabase db, long localId, T entity) {
        int rowsChanged = db.update(this.mEntityTable, getContentValuesFromEntity(localId, entity), "_id=" + localId, (String[]) null);
        if (rowsChanged >= 1) {
            deleteAllLinksWithId(db, localId);
            if (entity instanceof ApiTransferObject) {
                ((ApiTransferObject) entity).setLocalId(localId);
                bulkInsertLinks(db, localId, (ApiTransferObject) entity);
                return;
            }
            return;
        }
        UaLog.error("Failed to update entity. type=%s id=%s rowsChanged=%s", entity.getClass().getSimpleName(), Long.valueOf(localId), Integer.valueOf(rowsChanged));
    }

    /* access modifiers changed from: protected */
    public long insertOrUpdate(SQLiteDatabase db, T entity) {
        long localId = db.insertWithOnConflict(this.mEntityTable, "_id", getContentValuesFromEntity(getLocalId(db, entity.getRef()), entity), 5);
        deleteAllLinksWithId(db, localId);
        if (entity instanceof ApiTransferObject) {
            ((ApiTransferObject) entity).setLocalId(localId);
            bulkInsertLinks(db, localId, (ApiTransferObject) entity);
        }
        return localId;
    }

    /* access modifiers changed from: protected */
    public void deleteAllEntities(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + this.mEntityTable);
    }

    public synchronized void updateAfterFetch(T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            insertOrReplaceMetadataAfterFetch(db, insertOrUpdate(db, entity));
            db.setTransactionSuccessful();
            if (db != null) {
                db.endTransaction();
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                db.endTransaction();
            }
            close();
            throw th;
        }
    }

    public synchronized void updateAfterFetch(EntityListRef<T> entityListRef, EntityList<T> entities, boolean partial) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            for (T entity : entities.getAll()) {
                long localId = getLocalId(db, entity.getRef());
                if (partial) {
                    if (localId >= 0) {
                        update(db, localId, entity);
                    }
                } else if (localId >= 0) {
                    update(db, localId, entity);
                    insertOrReplaceMetadataAfterFetch(db, localId);
                } else {
                    insertOrReplaceMetadataAfterFetch(db, insert(db, entity));
                }
            }
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
    }

    private static void endTransaction(SQLiteDatabase db) {
        try {
            db.endTransaction();
        } catch (Throwable e) {
            UaLog.error("Failed to end transaction.", e);
        }
    }

    public synchronized void updateAfterCreate(long localId, T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            update(db, localId, entity);
            insertOrReplaceMetadataAfterFetch(db, localId);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
    }

    public synchronized long putForCreate(T entity) {
        long localId;
        localId = -1;
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            localId = insertOrUpdate(db, entity);
            insertOrReplaceMetadata(db, localId, DiskCache.State.CREATED);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
        return localId;
    }

    public synchronized void putForSave(T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            insertOrReplaceMetadata(db, insertOrUpdate(db, entity), DiskCache.State.MODIFIED);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
    }

    public synchronized void updateAfterSave(T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            insertOrReplaceMetadataAfterFetch(db, insertOrUpdate(db, entity));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
    }

    public synchronized void markForDelete(Reference ref) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            insertOrReplaceMetadata(db, getLocalId(db, ref), DiskCache.State.DELETED);
            close();
        } catch (Throwable th) {
            close();
            throw th;
        }
        return;
    }

    public synchronized void delete(Reference ref) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            long localId = delete(db, ref);
            deleteAllMetadataWithId(db, localId);
            deleteAllLinksWithId(db, localId);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            close();
            throw th;
        }
    }

    public void deleteList(EntityListRef<T> entityListRef) {
    }

    public synchronized long getLastSynced(Reference ref) {
        long lastSynced;
        lastSynced = -1;
        if (ref != null) {
            Cursor c = null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                long localId = getLocalId(db, ref);
                if (localId >= 0) {
                    c = db.query(TABLE_META, new String[]{KEY_META_LAST_UPDATE_TIME_MS}, "id=?", new String[]{Long.toString(localId)}, (String) null, (String) null, (String) null);
                    if (c.moveToFirst() && !c.isNull(0)) {
                        lastSynced = c.getLong(0);
                    }
                }
                if (c != null) {
                    c.close();
                }
                close();
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
                close();
                throw th;
            }
        }
        return lastSynced;
    }

    public long getCacheAge(Reference ref) {
        if (ref == null) {
            return -1;
        }
        long lastSynced = getLastSynced(ref);
        if (lastSynced >= 0) {
            return System.currentTimeMillis() - lastSynced;
        }
        return -1;
    }

    public synchronized DiskCache.State getState(Reference ref) {
        DiskCache.State state;
        state = DiskCache.State.NONE;
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            long localId = getLocalId(db, ref);
            if (localId >= 0) {
                c = db.query(TABLE_META, new String[]{"pending_operation"}, "id=?", new String[]{Long.toString(localId)}, (String) null, (String) null, (String) null);
                if (c.moveToFirst() && !c.isNull(0)) {
                    state = stateFromDatabaseValue(c.getInt(0));
                }
            }
            if (c != null) {
                c.close();
            }
            close();
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            close();
            throw th;
        }
        return state;
    }

    public static String readString(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return c.getString(column);
    }

    public static Boolean readBoolean(int column, Cursor c) {
        boolean z = true;
        if (c.isNull(column)) {
            return null;
        }
        if (c.getInt(column) != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public static Integer readInteger(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return Integer.valueOf(c.getInt(column));
    }

    public static Long readLong(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return Long.valueOf(c.getLong(column));
    }

    public static Double readDouble(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return Double.valueOf(c.getDouble(column));
    }

    public static Date readDate(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return new Date(c.getLong(column));
    }

    public static LocalDate readLocalDate(int column, Cursor c) {
        if (c.isNull(column)) {
            return null;
        }
        return LocalDate.fromString(c.getString(column));
    }

    public static <T extends Enum<T>> T readEnum(int column, Cursor c, Class<T> enumClass) {
        if (c.isNull(column)) {
            return null;
        }
        return Enum.valueOf(enumClass, c.getString(column));
    }

    public static void writeString(ContentValues values, String key, String value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value);
        }
    }

    public static void writeBoolean(ContentValues values, String key, Boolean value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value);
        }
    }

    public static void writeInteger(ContentValues values, String key, Integer value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value);
        }
    }

    public static void writeLong(ContentValues values, String key, Long value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value);
        }
    }

    public static void writeDouble(ContentValues values, String key, Double value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value);
        }
    }

    public static void writeDate(ContentValues values, String key, Date date) {
        if (date == null) {
            values.putNull(key);
        } else {
            values.put(key, Long.valueOf(date.getTime()));
        }
    }

    public static void writeLocalDate(ContentValues values, String key, LocalDate date) {
        if (date == null) {
            values.putNull(key);
        } else {
            values.put(key, date.toString());
        }
    }

    public static void writeEnum(ContentValues values, String key, Enum<?> value) {
        if (value == null) {
            values.putNull(key);
        } else {
            values.put(key, value.toString());
        }
    }
}
