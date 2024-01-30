package com.ua.sdk.recorder.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nautilus.omni.util.Constants;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.database.definition.ColumnDefinition;
import com.ua.sdk.recorder.DataSourceConfigurationList;
import com.ua.sdk.recorder.RecorderConfiguration;
import java.util.Date;
import java.util.List;

public class RecorderConfigurationDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "record_configuration";
    public static final String DB_TABLE = "recorders";
    private static final int DB_VERSION = 1;
    private static RecorderConfigurationDatabase instance;

    public static RecorderConfigurationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new RecorderConfigurationDatabase(context);
        }
        return instance;
    }

    public RecorderConfigurationDatabase(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buildCreateStatement(DB_TABLE, RecorderConfigurationDatabaseMapper.COLUMNS));
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recorders");
        onCreate(db);
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

    private static void endTransaction(SQLiteDatabase db) {
        try {
            db.endTransaction();
        } catch (Throwable e) {
            UaLog.error("Failed to end transaction.", e);
        }
    }

    public void insert(String name, String userId, String activityTypeId, Date createDate, Date updateDate, DataSourceConfigurationList configuration) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            db.insertOrThrow(DB_TABLE, "_id", RecorderConfigurationDatabaseMapper.getContentValues(name, userId, activityTypeId, createDate, updateDate, configuration));
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
        } catch (Throwable th) {
            if (db != null) {
                endTransaction(db);
            }
            throw th;
        }
    }

    public void delete(String name) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            if (name != null) {
                if (db.delete(DB_TABLE, "name = ?", new String[]{name}) == 0) {
                    UaLog.info("Failed to delete recorder with name " + name);
                }
            }
            db.setTransactionSuccessful();
            if (db != null) {
                endTransaction(db);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                endTransaction((SQLiteDatabase) null);
            }
            throw th;
        }
    }

    public void update(String name, Date updateDate, DataSourceConfigurationList configuration) {
        SQLiteDatabase db = null;
        try {
            SQLiteDatabase db2 = getWritableDatabase();
            db2.beginTransaction();
            if (!(name == null || updateDate == null)) {
                db2.update(DB_TABLE, RecorderConfigurationDatabaseMapper.getUpdateValues(updateDate, configuration), "name = ?", new String[]{name});
            }
            db2.setTransactionSuccessful();
            if (db2 != null) {
                db2.endTransaction();
            }
        } catch (Throwable th) {
            if (db != null) {
                db.endTransaction();
            }
            throw th;
        }
    }

    public List<RecorderConfiguration> getAllEntries(String userId) {
        return RecorderConfigurationDatabaseMapper.getCachedConfigurations(getReadableDatabase().query(DB_TABLE, buildColumnNames(RecorderConfigurationDatabaseMapper.COLUMNS), "user_id = ?", new String[]{userId}, (String) null, (String) null, (String) null));
    }

    public RecorderConfiguration get(String userId, String name) {
        return RecorderConfigurationDatabaseMapper.getCachedConfigurations(getReadableDatabase().query(DB_TABLE, buildColumnNames(RecorderConfigurationDatabaseMapper.COLUMNS), "user_id = ? AND name = ?", new String[]{userId, name}, (String) null, (String) null, (String) null)).get(0);
    }
}
