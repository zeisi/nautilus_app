package com.ua.sdk.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.BaseColumns;
import com.nautilus.omni.util.Constants;
import com.ua.oss.de.greenrobot.dao.DbUtils;
import com.ua.sdk.Entity;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.LocalDate;
import com.ua.sdk.Reference;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.cache.database.definition.ColumnDefinition;
import com.ua.sdk.internal.AbstractEntityList;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.LinkListRef;
import com.ua.sdk.internal.Precondition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EntityDatabase<T extends Entity> extends SQLiteOpenHelper implements DiskCache<T> {
    protected static final int ENTITY_DATABASE_VERSION = 0;
    protected static final Integer STATE_CREATED = 1;
    protected static final Integer STATE_DELETED = 4;
    protected static final Integer STATE_MODIFIED = 2;
    protected static final Integer STATE_SYNCED = 0;
    protected final Context mContext;
    private final String[] mEntityCols;
    private final String mEntityKeyCol;
    private final String mEntityName;
    protected final String mEntityTable;
    private final String mLinksTable;
    private final String mListJoinTable;
    private final String mListTable;
    protected final String mMetaTable;

    public static final class LINKS {
        public static final String TABLE_SUFFIX = "_links";

        public static final class COLS implements BaseColumns {
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY_LIST_ID = "entity_list_id";
            public static final String HREF = "link_href";
            public static final String ID = "link_id";
            public static final String KEY = "link_key";
            public static final String NAME = "link_name";
            public static final String _ID = "_id";
        }
    }

    public static final class LIST {
        public static final String TABLE_SUFFIX = "_list";

        public static final class COLS {
            public static final String REMOTE_ID = "remote_id";
            public static final String TOTAL_COUNT = "total_count";
            public static final String _ID = "_id";
        }
    }

    public static final class LIST_JOIN {
        public static final String TABLE_SUFFIX = "_list_join";

        public static final class COLS {
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY_LIST_ID = "entity_list_id";
            public static final String _ID = "_id";
        }
    }

    public static final class META {
        public static final String TABLE_SUFFIX = "_meta";

        public static final class COLS {
            public static final String ENTITY_ID = "entity_id";
            public static final String ENTITY_LIST_ID = "entity_list_id";
            public static final String OPTIONS = "options";
            public static final String PENDING_OPERATION = "pending_operation";
            public static final String UPDATE_TIME = "update_time";
            public static final String _ID = "_id";
        }
    }

    /* access modifiers changed from: protected */
    public abstract AbstractEntityList<T> createEntityList(long j, String str, int i);

    /* access modifiers changed from: protected */
    public abstract void createEntityTable(SQLiteDatabase sQLiteDatabase);

    /* access modifiers changed from: protected */
    public abstract ContentValues getContentValuesFromEntity(T t);

    /* access modifiers changed from: protected */
    public abstract T getEntityFromCursor(Cursor cursor);

    public abstract void onEntityUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    protected EntityDatabase(Context context, String entityName, String databaseName, String[] entityCols, String entityKeyCol, int version) {
        super(context, databaseName, (SQLiteDatabase.CursorFactory) null, getCombinedVersion(0, version));
        this.mEntityName = entityName;
        this.mEntityTable = entityName + "_entity";
        this.mEntityKeyCol = entityKeyCol;
        this.mEntityCols = entityCols;
        this.mContext = context;
        this.mMetaTable = entityName + META.TABLE_SUFFIX;
        this.mListTable = entityName + LIST.TABLE_SUFFIX;
        this.mListJoinTable = entityName + LIST_JOIN.TABLE_SUFFIX;
        this.mLinksTable = entityName + LINKS.TABLE_SUFFIX;
        if (Arrays.binarySearch(entityCols, "_id") < 0) {
            throw new IllegalArgumentException("entityCols do not contain _id");
        }
    }

    /* access modifiers changed from: protected */
    public void executeSqlScript(SQLiteDatabase db, String scriptLocation, String errorMsg) {
        try {
            UaLog.debug("Ran %d statements.", (Object) Integer.valueOf(DbUtils.executeSqlScript(this.mContext, db, this.mEntityName, scriptLocation)));
        } catch (IOException e) {
            UaLog.error(errorMsg, (Throwable) e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    public void onConfigure(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT >= 16) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }

    public final void onCreate(SQLiteDatabase db) {
        createEntityTable(db);
        createMetaTables(db);
    }

    private void createMetaTables(SQLiteDatabase db) {
        executeSqlScript(db, "cache/entity_common/1_entity_common_create_table.sql", String.format("Fatal error, unable to initialize meta tables for %s database.", new Object[]{this.mEntityName}));
    }

    /* access modifiers changed from: protected */
    public void deleteEntitiesWithNullRemoteId(SQLiteDatabase db) {
        if (this.mEntityKeyCol != null) {
            db.execSQL("DELETE FROM " + this.mEntityTable + " WHERE " + this.mEntityKeyCol + " IS NULL" + " AND " + "_id" + " IN (" + " SELECT " + "entity_id" + " FROM " + this.mMetaTable + " WHERE " + META.COLS.PENDING_OPERATION + " = " + STATE_SYNCED + ")");
        }
    }

    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (getMyVersion(newVersion) > getMyVersion(oldVersion)) {
            db.execSQL("DROP TABLE IF EXISTS " + this.mEntityName + LINKS.TABLE_SUFFIX);
            db.execSQL("DROP TABLE IF EXISTS " + this.mEntityName + META.TABLE_SUFFIX);
            db.execSQL("DROP TABLE IF EXISTS " + this.mEntityName + LIST.TABLE_SUFFIX);
            db.execSQL("DROP TABLE IF EXISTS " + this.mEntityName + LIST_JOIN.TABLE_SUFFIX);
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
        return version & 32767;
    }

    private static int getSubVersion(int version) {
        return (536838144 & version) >> 15;
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

    private void insertOrReplaceMetadataAfterFetch(SQLiteDatabase db, String foreignKeyCol, long foreignKey, int options) {
        insertOrReplaceMetadata(db, foreignKeyCol, foreignKey, DiskCache.State.SYNCED, options, true);
    }

    private void insertOrUpdateMetadataState(SQLiteDatabase db, String foreignKeyCol, long foreignKey, DiskCache.State state, int options) {
        String whereClause;
        ContentValues values = new ContentValues();
        values.put(META.COLS.PENDING_OPERATION, stateToDatabaseValue(state));
        values.put(META.COLS.OPTIONS, Integer.valueOf(options));
        if (foreignKeyCol.equals("entity_id")) {
            whereClause = "options=? AND entity_id=? AND entity_list_id IS NULL";
        } else {
            whereClause = "options=? AND entity_list_id=? AND entity_id IS NULL";
        }
        if (db.update(this.mMetaTable, values, whereClause, new String[]{String.valueOf(options), String.valueOf(foreignKey)}) == 0) {
            values.put(foreignKeyCol, Long.valueOf(foreignKey));
            db.insert(this.mMetaTable, (String) null, values);
        }
    }

    private void insertOrReplaceMetadata(SQLiteDatabase db, String foreignKeyCol, long foreignKey, DiskCache.State state, int options, boolean updateLastUpdatedTime) {
        String whereClause;
        ContentValues values = new ContentValues();
        values.put(META.COLS.PENDING_OPERATION, stateToDatabaseValue(state));
        values.put(META.COLS.OPTIONS, Integer.valueOf(options));
        values.put(foreignKeyCol, Long.valueOf(foreignKey));
        if (updateLastUpdatedTime) {
            values.put(META.COLS.UPDATE_TIME, Long.valueOf(System.currentTimeMillis()));
        }
        if (foreignKeyCol.equals("entity_id")) {
            whereClause = "options=? AND entity_id=? AND entity_list_id IS NULL";
        } else {
            whereClause = "options=? AND entity_list_id=? AND entity_id IS NULL";
        }
        SQLiteDatabase sQLiteDatabase = db;
        Cursor c = sQLiteDatabase.query(this.mMetaTable, (String[]) null, whereClause, new String[]{String.valueOf(options), String.valueOf(foreignKey)}, (String) null, (String) null, (String) null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                long rowId = c.getLong(0);
                long result = (long) db.update(this.mMetaTable, values, "_id=?", new String[]{String.valueOf(rowId)});
                UaLog.debug("Updated %s metadata rows", (Object) Long.valueOf(result));
                if (result > 1) {
                    UaLog.warn("Updated more than 1 entity meta row! Updated: " + result);
                }
            } else {
                UaLog.debug("Inserting metadata values: " + values);
                long result2 = db.insert(this.mMetaTable, (String) null, values);
                if (result2 == -1) {
                    throw new RuntimeException("Fatal error! Could not store metadata for entity.");
                }
                UaLog.debug("Inserted or overwrote metadata row: " + result2);
            }
        } finally {
            c.close();
        }
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

    private void bulkInsertLinks(SQLiteDatabase db, String foreignKeyCol, long foreignKey, ApiTransferObject entity) {
        SQLiteStatement statement = db.compileStatement("INSERT INTO " + this.mLinksTable + "(" + foreignKeyCol + ", " + LINKS.COLS.KEY + ", " + LINKS.COLS.HREF + ", " + LINKS.COLS.ID + ", " + LINKS.COLS.NAME + ") VALUES (?,?,?,?,?);");
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
            long localId = -1;
            if (ref instanceof EntityRef) {
                localId = getEntityLocalId(db, (EntityRef) ref);
            } else if (ref instanceof EntityListRef) {
                localId = getListLocalId(db, (EntityListRef) ref);
            }
            if (localId < 0) {
                emptyMap = Collections.emptyMap();
                close();
            } else {
                emptyMap = getLinkMap(db, getForeignKeyCol(ref), localId);
                close();
            }
        } catch (Exception e) {
            UaLog.error("Unable to get link map.", (Throwable) e);
            emptyMap = Collections.emptyMap();
        } catch (Throwable th) {
            close();
            throw th;
        }
        return emptyMap;
    }

    /* access modifiers changed from: protected */
    public Map<String, ArrayList<Link>> getLinkMap(SQLiteDatabase db, String foreignKeyCol, long id) {
        Cursor linksCursor = db.query(this.mLinksTable, (String[]) null, foreignKeyCol + "=?", new String[]{String.valueOf(id)}, (String) null, (String) null, (String) null, (String) null);
        Map<String, ArrayList<Link>> linkMap = new HashMap<>();
        while (linksCursor.moveToNext()) {
            try {
                String relation = linksCursor.getString(linksCursor.getColumnIndex(LINKS.COLS.KEY));
                Link link = new Link(linksCursor.getString(linksCursor.getColumnIndex(LINKS.COLS.HREF)), linksCursor.getString(linksCursor.getColumnIndex(LINKS.COLS.ID)), linksCursor.getString(linksCursor.getColumnIndex(LINKS.COLS.NAME)));
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
        Cursor linksCursor = db.query(this.mLinksTable, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "entity_id ASC", (String) null);
        try {
            int fkCol = linksCursor.getColumnIndex("entity_id");
            int relationCol = linksCursor.getColumnIndex(LINKS.COLS.KEY);
            int idCol = linksCursor.getColumnIndex(LINKS.COLS.ID);
            int hrefCol = linksCursor.getColumnIndex(LINKS.COLS.HREF);
            int nameCol = linksCursor.getColumnIndex(LINKS.COLS.NAME);
            long fk = -1;
            long key = -1;
            int entityIndex = 0;
            int entityCount = entities.size();
            T entity = null;
            Map<String, ArrayList<Link>> linkMap = null;
            Link link = null;
            String relation = null;
            while (entityIndex < entityCount && (link != null || !linksCursor.isLast())) {
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
            preDeleteAll(db);
            deleteAllEntities(db);
            deleteAllLinks(db);
            deleteAllMetadata(db);
            deleteAllLists(db);
            close();
        } catch (Exception e) {
            UaLog.error("Unable to delete all entities.", (Throwable) e);
        } catch (Throwable th) {
            close();
            throw th;
        }
        return;
    }

    /* access modifiers changed from: protected */
    public void deleteAllLinksWithId(SQLiteDatabase db, String foreignKeyCol, long id) {
        if (id >= 0) {
            db.delete(this.mLinksTable, foreignKeyCol + "=?", new String[]{Long.toString(id)});
        }
    }

    /* access modifiers changed from: protected */
    public void deleteAllLinks(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + this.mLinksTable);
    }

    /* access modifiers changed from: protected */
    public void deleteAllLists(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + this.mListTable);
        db.execSQL("DELETE FROM " + this.mListJoinTable);
    }

    /* access modifiers changed from: protected */
    public void deleteAllMetadata(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + this.mMetaTable);
    }

    /* access modifiers changed from: protected */
    public void deleteAllMetadataWithId(SQLiteDatabase db, String foreignKeyCol, long id) {
        if (id >= 0) {
            db.delete(this.mMetaTable, foreignKeyCol + "=?", new String[]{Long.toString(id)});
        }
    }

    /* access modifiers changed from: protected */
    public long getEntityLocalId(SQLiteDatabase db, EntityRef ref) {
        String id;
        long localId = -1;
        if ((ref instanceof LinkEntityRef) && ((LinkEntityRef) ref).checkCache()) {
            long id2 = ((LinkEntityRef) ref).getLocalId();
            if (id2 >= 0) {
                localId = id2;
            }
        }
        if (!(localId != -1 || ref == null || (id = ref.getId()) == null)) {
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

    private long getListLocalId(SQLiteDatabase db, EntityListRef ref) {
        String id;
        long localId = -1;
        if ((ref instanceof LinkListRef) && ((LinkListRef) ref).checkCache()) {
            long id2 = ((LinkListRef) ref).getLocalId();
            if (id2 >= 0) {
                localId = id2;
            }
        }
        if (localId == -1 && (id = ref.getHref()) != null) {
            Cursor cursor = db.query(this.mListTable, new String[]{"_id"}, "remote_id= '" + id + "'", (String[]) null, (String) null, (String) null, (String) null);
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

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:42:0x00b2=Splitter:B:42:0x00b2, B:38:0x00aa=Splitter:B:38:0x00aa} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized T get(com.ua.sdk.Reference r21) {
        /*
            r20 = this;
            monitor-enter(r20)
            java.lang.String r5 = "ref"
            r0 = r21
            com.ua.sdk.internal.Precondition.isNotNull(r0, r5)     // Catch:{ all -> 0x004b }
            r17 = 0
            r16 = 0
            r0 = r21
            boolean r5 = r0 instanceof com.ua.sdk.internal.LinkEntityRef     // Catch:{ Exception -> 0x00c0 }
            if (r5 == 0) goto L_0x0038
            r0 = r21
            com.ua.sdk.internal.LinkEntityRef r0 = (com.ua.sdk.internal.LinkEntityRef) r0     // Catch:{ Exception -> 0x00c0 }
            r5 = r0
            boolean r5 = r5.checkCache()     // Catch:{ Exception -> 0x00c0 }
            if (r5 != 0) goto L_0x0023
            r15 = 0
            r20.close()     // Catch:{ all -> 0x004b }
        L_0x0021:
            monitor-exit(r20)
            return r15
        L_0x0023:
            r0 = r21
            com.ua.sdk.internal.LinkEntityRef r0 = (com.ua.sdk.internal.LinkEntityRef) r0     // Catch:{ Exception -> 0x00c0 }
            r5 = r0
            long r18 = r5.getLocalId()     // Catch:{ Exception -> 0x00c0 }
            r6 = 0
            int r5 = (r18 > r6 ? 1 : (r18 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x0038
            java.lang.String r17 = "_id"
            java.lang.String r16 = java.lang.String.valueOf(r18)     // Catch:{ Exception -> 0x00c0 }
        L_0x0038:
            if (r17 != 0) goto L_0x0044
            r0 = r20
            java.lang.String r0 = r0.mEntityKeyCol     // Catch:{ Exception -> 0x00c0 }
            r17 = r0
            java.lang.String r16 = r21.getId()     // Catch:{ Exception -> 0x00c0 }
        L_0x0044:
            if (r16 != 0) goto L_0x004e
            r15 = 0
            r20.close()     // Catch:{ all -> 0x004b }
            goto L_0x0021
        L_0x004b:
            r5 = move-exception
            monitor-exit(r20)
            throw r5
        L_0x004e:
            android.database.sqlite.SQLiteDatabase r4 = r20.getReadableDatabase()     // Catch:{ Exception -> 0x00c0 }
            r0 = r20
            java.lang.String r5 = r0.mEntityTable     // Catch:{ Exception -> 0x00c0 }
            r0 = r20
            java.lang.String[] r6 = r0.mEntityCols     // Catch:{ Exception -> 0x00c0 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c0 }
            r7.<init>()     // Catch:{ Exception -> 0x00c0 }
            r0 = r17
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r8 = "=?"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x00c0 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00c0 }
            r8 = 1
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ Exception -> 0x00c0 }
            r9 = 0
            r8[r9] = r16     // Catch:{ Exception -> 0x00c0 }
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            android.database.Cursor r13 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x00c0 }
            if (r13 == 0) goto L_0x00b2
            boolean r5 = r13.moveToFirst()     // Catch:{ all -> 0x00bb }
            if (r5 == 0) goto L_0x00b2
            r0 = r20
            com.ua.sdk.Entity r15 = r0.getEntityFromCursor(r13)     // Catch:{ all -> 0x00bb }
            boolean r5 = r15 instanceof com.ua.sdk.internal.ApiTransferObject     // Catch:{ all -> 0x00bb }
            if (r5 == 0) goto L_0x00aa
            java.lang.String r5 = "_id"
            int r5 = r13.getColumnIndex(r5)     // Catch:{ all -> 0x00bb }
            long r18 = r13.getLong(r5)     // Catch:{ all -> 0x00bb }
            r0 = r15
            com.ua.sdk.internal.ApiTransferObject r0 = (com.ua.sdk.internal.ApiTransferObject) r0     // Catch:{ all -> 0x00bb }
            r5 = r0
            java.lang.String r6 = "entity_id"
            r0 = r20
            r1 = r18
            java.util.Map r6 = r0.getLinkMap(r4, r6, r1)     // Catch:{ all -> 0x00bb }
            r5.setLinkMap(r6)     // Catch:{ all -> 0x00bb }
        L_0x00aa:
            r13.close()     // Catch:{ Exception -> 0x00c0 }
            r20.close()     // Catch:{ all -> 0x004b }
            goto L_0x0021
        L_0x00b2:
            r13.close()     // Catch:{ Exception -> 0x00c0 }
            r20.close()     // Catch:{ all -> 0x004b }
        L_0x00b8:
            r15 = 0
            goto L_0x0021
        L_0x00bb:
            r5 = move-exception
            r13.close()     // Catch:{ Exception -> 0x00c0 }
            throw r5     // Catch:{ Exception -> 0x00c0 }
        L_0x00c0:
            r14 = move-exception
            java.lang.String r5 = "Unable to get entity."
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r14)     // Catch:{ all -> 0x00ca }
            r20.close()     // Catch:{ all -> 0x004b }
            goto L_0x00b8
        L_0x00ca:
            r5 = move-exception
            r20.close()     // Catch:{ all -> 0x004b }
            throw r5     // Catch:{ all -> 0x004b }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.cache.EntityDatabase.get(com.ua.sdk.Reference):com.ua.sdk.Entity");
    }

    public synchronized EntityList<T> getList(Reference ref) {
        AbstractEntityList<T> entityList;
        Precondition.isNotNull(ref, "ref");
        String id = ref.getHref();
        if (id == null) {
            entityList = null;
        } else {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(this.mListTable, new String[]{"_id", LIST.COLS.REMOTE_ID, LIST.COLS.TOTAL_COUNT}, LIST.COLS.REMOTE_ID + "=?", new String[]{id}, (String) null, (String) null, (String) null, (String) null);
            entityList = null;
            long localId = -1;
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        localId = cursor.getLong(0);
                        entityList = createEntityList(localId, cursor.getString(1), cursor.getInt(2));
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (entityList == null) {
                entityList = null;
            } else {
                entityList.setLinkMap(getLinkMap(db, "entity_list_id", localId));
                getEntitiesForList(db, entityList, localId);
            }
        }
        return entityList;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void getEntitiesForList(SQLiteDatabase db, AbstractEntityList<T> list, long listLocalId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        for (int i = 0; i < this.mEntityCols.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("e.");
            sb.append(this.mEntityCols[i]);
        }
        sb.append(" FROM ");
        sb.append(this.mListJoinTable);
        sb.append(" AS j INNER JOIN ");
        sb.append(this.mEntityTable);
        sb.append(" AS e ON j.");
        sb.append("entity_id");
        sb.append(" = e._id WHERE j.");
        sb.append("entity_list_id");
        sb.append(" = ");
        sb.append(listLocalId);
        sb.append(" ORDER BY j._id ASC;");
        Cursor cursor = db.rawQuery(sb.toString(), (String[]) null);
        ArrayList<ApiTransferObject> entities = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                T entity = getEntityFromCursor(cursor);
                list.add(entity);
                entities.add((ApiTransferObject) entity);
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        cursor.close();
        Collections.sort(entities, new Comparator<ApiTransferObject>() {
            public int compare(ApiTransferObject lhs, ApiTransferObject rhs) {
                return (int) (lhs.getLocalId() - rhs.getLocalId());
            }
        });
        setAllLinkMaps(db, entities);
    }

    /* access modifiers changed from: protected */
    public ContentValues getContentValuesFromEntity(long localId, T entity) {
        Reference ref;
        String id;
        ContentValues values = getContentValuesFromEntity(entity);
        if (localId >= 0) {
            values.put("_id", Long.valueOf(localId));
        } else {
            values.remove("_id");
        }
        if (!(this.mEntityKeyCol == null || values.get(this.mEntityKeyCol) != null || (ref = entity.getRef()) == null || (id = ref.getId()) == null)) {
            values.put(this.mEntityKeyCol, id);
        }
        return values;
    }

    /* access modifiers changed from: protected */
    public void postSaveEntity(long localId, T t) {
    }

    /* access modifiers changed from: protected */
    public void preDeleteEntity(long localId) {
    }

    /* access modifiers changed from: protected */
    public void preDeleteAll(SQLiteDatabase db) {
    }

    /* access modifiers changed from: protected */
    public long deleteEntity(SQLiteDatabase db, EntityRef<T> ref) {
        int rowsChanged;
        long localId = getEntityLocalId(db, ref);
        preDeleteEntity(localId);
        if (localId >= 0 && (rowsChanged = db.delete(this.mEntityTable, "_id=?", new String[]{Long.toString(localId)})) != 1) {
            UaLog.error("Failed to delete entity. refType=%s id=%s rowsChanged=%s", ref.getClass().getSimpleName(), Long.valueOf(localId), Integer.valueOf(rowsChanged));
        }
        return localId;
    }

    public long deleteList(SQLiteDatabase db, EntityListRef<T> ref) {
        int rowsChanged;
        long localId = getListLocalId(db, ref);
        if (localId >= 0 && (rowsChanged = db.delete(this.mListTable, "_id=?", new String[]{Long.toString(localId)})) != 1) {
            UaLog.error("Failed to delete entity list. refType=%s id=%s rowsChanged=%s", ref.getClass().getSimpleName(), Long.valueOf(localId), Integer.valueOf(rowsChanged));
        }
        return localId;
    }

    /* access modifiers changed from: protected */
    public long insert(SQLiteDatabase db, T entity) {
        long localId = db.insert(this.mEntityTable, (String) null, getContentValuesFromEntity(-1, entity));
        deleteAllLinksWithId(db, "entity_id", localId);
        if (entity instanceof ApiTransferObject) {
            ((ApiTransferObject) entity).setLocalId(localId);
            bulkInsertLinks(db, "entity_id", localId, (ApiTransferObject) entity);
        }
        postSaveEntity(localId, entity);
        return localId;
    }

    /* access modifiers changed from: protected */
    public void update(SQLiteDatabase db, long localId, T entity) {
        int rowsChanged = db.update(this.mEntityTable, getContentValuesFromEntity(localId, entity), "_id=" + localId, (String[]) null);
        if (rowsChanged >= 1) {
            deleteAllLinksWithId(db, "entity_id", localId);
            if (entity instanceof ApiTransferObject) {
                ((ApiTransferObject) entity).setLocalId(localId);
                bulkInsertLinks(db, "entity_id", localId, (ApiTransferObject) entity);
            }
        } else {
            UaLog.error("Failed to update entity. type=%s id=%s rowsChanged=%s", entity.getClass().getSimpleName(), Long.valueOf(localId), Integer.valueOf(rowsChanged));
        }
        postSaveEntity(localId, entity);
    }

    /* access modifiers changed from: protected */
    public long insertOrUpdate(SQLiteDatabase db, T entity) {
        long localId = getEntityLocalId(db, entity.getRef());
        ContentValues values = getContentValuesFromEntity(localId, entity);
        if (localId > 0) {
            UaLog.debug("Updating existing entity with localId=%s", (Object) Long.valueOf(localId));
            db.update(this.mEntityTable, values, "_id=?", new String[]{String.valueOf(localId)});
        } else {
            UaLog.debug("Adding a new entity using values: %s", (Object) values);
            localId = db.insert(this.mEntityTable, (String) null, values);
        }
        deleteAllLinksWithId(db, "entity_id", localId);
        if (entity instanceof ApiTransferObject) {
            ((ApiTransferObject) entity).setLocalId(localId);
            bulkInsertLinks(db, "entity_id", localId, (ApiTransferObject) entity);
        }
        postSaveEntity(localId, entity);
        return localId;
    }

    /* JADX INFO: finally extract failed */
    private long insertOrUpdateList(SQLiteDatabase db, EntityListRef<T> requestRef, EntityList<T> entityList) {
        Precondition.isNotNull(requestRef, "entityList.getRef()");
        String cachedHref = requestRef.getHref();
        Precondition.isNotNull(cachedHref, "entityList.getRef().getHref()");
        SQLiteStatement statement = db.compileStatement("INSERT OR REPLACE INTO " + this.mListTable + "(" + LIST.COLS.REMOTE_ID + ", " + LIST.COLS.TOTAL_COUNT + ") VALUES (?,?);");
        try {
            nullSafeBind(statement, 1, cachedHref);
            nullSafeBind(statement, 2, String.valueOf(entityList.getTotalCount()));
            long localId = statement.executeInsert();
            statement.close();
            bulkInsertLinks(db, "entity_list_id", localId, (ApiTransferObject) entityList);
            return localId;
        } catch (Throwable th) {
            statement.close();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void deleteAllEntities(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + this.mEntityTable);
    }

    /* access modifiers changed from: protected */
    public int getEntityOptions(Reference ref) {
        if (ref instanceof LinkEntityRef) {
            return ((LinkEntityRef) ref).getOptions();
        }
        UaLog.warn("Entity's ref not an instance of LinkEntityRef, so options aren't available (using default of 0).");
        return 0;
    }

    public synchronized void updateAfterFetch(T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            insertOrReplaceMetadataAfterFetch(db, "entity_id", insertOrUpdate(db, entity), getEntityOptions(entity.getRef()));
            db.setTransactionSuccessful();
            if (db != null) {
                db.endTransaction();
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after fetch.", (Throwable) e);
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

    public synchronized void updateAfterFetch(EntityListRef<T> requestRef, EntityList<T> entities, boolean partial) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            if (!partial) {
                long listLocalId = insertOrUpdateList(db, requestRef, entities);
                insertOrReplaceMetadataAfterFetch(db, "entity_list_id", listLocalId, 0);
                int size = entities.getSize();
                long[] entityLocalIds = new long[size];
                for (int i = 0; i < size; i++) {
                    T entity = (Entity) entities.get(i);
                    long entityLocalId = getEntityLocalId(db, entity.getRef());
                    if (entityLocalId >= 0) {
                        update(db, entityLocalId, entity);
                    } else {
                        entityLocalId = insert(db, entity);
                    }
                    entityLocalIds[i] = entityLocalId;
                    SQLiteDatabase sQLiteDatabase = db;
                    insertOrReplaceMetadataAfterFetch(sQLiteDatabase, "entity_id", entityLocalId, getEntityOptions(entity.getRef()));
                }
                deleteListJoins(db, listLocalId);
                bulkInsertListJoin(db, listLocalId, entityLocalIds);
            } else {
                for (T entity2 : entities.getAll()) {
                    long localId = getEntityLocalId(db, entity2.getRef());
                    if (localId >= 0) {
                        update(db, localId, entity2);
                    }
                }
            }
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after fetch.", (Throwable) e);
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

    private void deleteListJoins(SQLiteDatabase db, long listLocalId) {
        db.execSQL("DELETE FROM " + this.mListJoinTable + " WHERE " + "entity_list_id" + " = " + listLocalId);
    }

    private void bulkInsertListJoin(SQLiteDatabase db, long listLocalId, long[] entityLocalIds) {
        if (entityLocalIds != null && entityLocalIds.length != 0) {
            SQLiteStatement statement = db.compileStatement("INSERT OR IGNORE INTO " + this.mListJoinTable + " (" + "entity_list_id" + ", " + "entity_id" + ") VALUES (?,?);");
            int i = 0;
            while (i < entityLocalIds.length) {
                try {
                    long entityLocalId = entityLocalIds[i];
                    statement.clearBindings();
                    statement.bindLong(1, listLocalId);
                    statement.bindLong(2, entityLocalId);
                    statement.execute();
                    i++;
                } finally {
                    statement.close();
                }
            }
        }
    }

    private static void endTransaction(SQLiteDatabase db) {
        try {
            db.endTransaction();
        } catch (Exception e) {
            UaLog.error("Failed to end transaction.", (Throwable) e);
        }
    }

    public synchronized void updateAfterCreate(long localId, T entity) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            update(db, localId, entity);
            insertOrReplaceMetadataAfterFetch(db, "entity_id", localId, getEntityOptions(entity.getRef()));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after create", (Throwable) e);
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
            insertOrUpdateMetadataState(db, "entity_id", localId, DiskCache.State.CREATED, getEntityOptions(entity.getRef()));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to put in cache for create.", (Throwable) e);
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
            insertOrUpdateMetadataState(db, "entity_id", insertOrUpdate(db, entity), DiskCache.State.MODIFIED, getEntityOptions(entity.getRef()));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to put in cache for save.", (Throwable) e);
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
            insertOrReplaceMetadataAfterFetch(db, "entity_id", insertOrUpdate(db, entity), getEntityOptions(entity.getRef()));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after save", (Throwable) e);
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
            insertOrUpdateMetadataState(db, "entity_id", getEntityLocalId(db, (EntityRef) ref), DiskCache.State.DELETED, getEntityOptions(ref));
            close();
        } catch (Exception e) {
            UaLog.error("Failed to mark the cache for delete.", (Throwable) e);
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
            long localId = deleteEntity(db, (EntityRef) ref);
            deleteAllMetadataWithId(db, "entity_id", localId);
            deleteAllLinksWithId(db, "entity_id", localId);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after delete", (Throwable) e);
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

    public synchronized void deleteList(EntityListRef<T> ref) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            long localId = deleteList(db, ref);
            deleteAllMetadataWithId(db, "entity_list_id", localId);
            deleteAllLinksWithId(db, "entity_list_id", localId);
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
            close();
        } catch (Exception e) {
            UaLog.error("Failed to update the cache after delete", (Throwable) e);
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

    private static String getForeignKeyCol(Reference ref) {
        if (ref instanceof EntityListRef) {
            return "entity_list_id";
        }
        return "entity_id";
    }

    public synchronized long getLastSynced(Reference ref) {
        long j = -1;
        synchronized (this) {
            if (ref != null) {
                if (ref instanceof EntityRef) {
                    j = getLastEntitySynced((EntityRef) ref);
                } else if (ref instanceof EntityListRef) {
                    j = getLastEntityListSynced((EntityListRef) ref);
                } else {
                    UaLog.error("Unknown reference type: " + ref.getClass().getCanonicalName());
                }
            }
        }
        return j;
    }

    private synchronized long getLastEntityListSynced(EntityListRef ref) {
        long lastSynced;
        lastSynced = -1;
        if (ref != null) {
            Cursor c = null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                long localId = getListLocalId(db, ref);
                if (localId >= 0) {
                    c = db.query(this.mMetaTable, new String[]{META.COLS.UPDATE_TIME}, getForeignKeyCol(ref) + "=?", new String[]{Long.toString(localId)}, (String) null, (String) null, (String) null);
                    if (c.moveToFirst() && !c.isNull(0)) {
                        lastSynced = c.getLong(0);
                    }
                }
                if (c != null) {
                    c.close();
                }
                close();
            } catch (Exception e) {
                UaLog.error("Unable to get last synced time.", (Throwable) e);
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

    private synchronized long getLastEntitySynced(EntityRef ref) {
        long lastSynced;
        lastSynced = -1;
        if (ref != null) {
            Cursor c = null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                long localId = getEntityLocalId(db, ref);
                if (localId >= 0) {
                    c = db.query(this.mMetaTable, new String[]{META.COLS.UPDATE_TIME}, getForeignKeyCol(ref) + "=?", new String[]{Long.toString(localId)}, (String) null, (String) null, (String) null);
                    if (c.moveToFirst() && !c.isNull(0)) {
                        lastSynced = c.getLong(0);
                    }
                }
                if (c != null) {
                    c.close();
                }
                close();
            } catch (Exception e) {
                UaLog.error("Unable to get last synced time.", (Throwable) e);
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
            long localId = getEntityLocalId(db, (EntityRef) ref);
            if (localId >= 0) {
                c = db.query(this.mMetaTable, new String[]{META.COLS.PENDING_OPERATION}, getForeignKeyCol(ref) + "=?", new String[]{Long.toString(localId)}, (String) null, (String) null, (String) null);
                if (c.moveToFirst() && !c.isNull(0)) {
                    state = stateFromDatabaseValue(c.getInt(0));
                }
            }
            if (c != null) {
                c.close();
            }
            close();
        } catch (Exception e) {
            UaLog.error("Unable to get cache state.", (Throwable) e);
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
