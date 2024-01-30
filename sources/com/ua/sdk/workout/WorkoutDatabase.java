package com.ua.sdk.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nautilus.omni.util.Constants;
import com.ua.sdk.activitystory.SocialSettings;
import com.ua.sdk.cache.EntityDatabase;
import com.ua.sdk.cache.database.definition.BooleanColumnDefinition;
import com.ua.sdk.cache.database.definition.ColumnDefinition;
import com.ua.sdk.cache.database.definition.DateColumnDefinition;
import com.ua.sdk.cache.database.definition.DoubleColumnDefinition;
import com.ua.sdk.cache.database.definition.IntegerColumnDefinition;
import com.ua.sdk.cache.database.definition.LocalIdColumnDefinition;
import com.ua.sdk.cache.database.definition.StringColumnDefinition;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.internal.AbstractEntityList;
import com.ua.sdk.internal.Link;
import com.ua.sdk.util.FileUtil;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class WorkoutDatabase extends EntityDatabase<Workout> {
    public static final ColumnDefinition<Double> ACTIVE_TIME_TOTAL = new DoubleColumnDefinition(12, "active_time_total");
    private static final ColumnDefinition[] ALL_COLUMNS = {LOCAL_ID, REMOTE_ID, NAME, START_DATETIME, START_LOCALE_TIMEZONE, CREATED_DATETIME, UPDATED_DATETIME, REFERENCE_KEY, SOURCE, NOTES, DISTANCE_TOTAL, METABOLIC_ENERGY_TOTAL, ACTIVE_TIME_TOTAL, ELAPSED_TIME_TOTAL, STEPS_TOTAL, HEART_RATE_MIN, HEART_RATE_MAX, HEART_RATE_AVG, SPEED_MIN, SPEED_MAX, SPEED_AVG, CADENCE_MIN, CADENCE_MAX, CADENCE_AVG, POWER_MIN, POWER_MAX, POWER_AVG, TORQUE_MIN, TORQUE_MAX, TORQUE_AVG, HAS_TIME_SERIES, WILLPOWER, IS_VERIFIED, FACEBOOK, TWITTER};
    public static final ColumnDefinition<Integer> CADENCE_AVG = new IntegerColumnDefinition(23, "cadence_avg");
    public static final ColumnDefinition<Integer> CADENCE_MAX = new IntegerColumnDefinition(22, "cadence_max");
    public static final ColumnDefinition<Integer> CADENCE_MIN = new IntegerColumnDefinition(21, "cadence_min");
    public static final ColumnDefinition<Date> CREATED_DATETIME = new DateColumnDefinition(5, "created_datetime");
    public static final ColumnDefinition<Double> DISTANCE_TOTAL = new DoubleColumnDefinition(10, "distance_total");
    public static final ColumnDefinition<Double> ELAPSED_TIME_TOTAL = new DoubleColumnDefinition(13, "elapsed_time_total");
    private static final String ENTITY_NAME = "workout";
    public static final ColumnDefinition<Boolean> FACEBOOK = new BooleanColumnDefinition(33, "facebook");
    public static final ColumnDefinition<Boolean> HAS_TIME_SERIES = new BooleanColumnDefinition(30, "has_time_series");
    public static final ColumnDefinition<Integer> HEART_RATE_AVG = new IntegerColumnDefinition(17, "heartrate_avg");
    public static final ColumnDefinition<Integer> HEART_RATE_MAX = new IntegerColumnDefinition(16, "heartrate_max");
    public static final ColumnDefinition<Integer> HEART_RATE_MIN = new IntegerColumnDefinition(15, "heartrate_min");
    public static final ColumnDefinition<Boolean> IS_VERIFIED = new BooleanColumnDefinition(32, "is_verified");
    public static final ColumnDefinition<Long> LOCAL_ID = new LocalIdColumnDefinition(0, "_id");
    public static final ColumnDefinition<Double> METABOLIC_ENERGY_TOTAL = new DoubleColumnDefinition(11, "metabolic_energy_total");
    public static final ColumnDefinition<String> NAME = new StringColumnDefinition(2, "name");
    public static final ColumnDefinition<String> NOTES = new StringColumnDefinition(9, "notes");
    public static final ColumnDefinition<Double> POWER_AVG = new DoubleColumnDefinition(26, "power_avg");
    public static final ColumnDefinition<Double> POWER_MAX = new DoubleColumnDefinition(25, "power_max");
    public static final ColumnDefinition<Double> POWER_MIN = new DoubleColumnDefinition(24, "power_min");
    public static final ColumnDefinition<String> REFERENCE_KEY = new StringColumnDefinition(7, "reference_key");
    public static final ColumnDefinition<String> REMOTE_ID = new StringColumnDefinition(1, EntityDatabase.LIST.COLS.REMOTE_ID);
    public static final ColumnDefinition<String> SOURCE = new StringColumnDefinition(8, "source");
    public static final ColumnDefinition<Double> SPEED_AVG = new DoubleColumnDefinition(20, "speed_avg");
    public static final ColumnDefinition<Double> SPEED_MAX = new DoubleColumnDefinition(19, "speed_max");
    public static final ColumnDefinition<Double> SPEED_MIN = new DoubleColumnDefinition(18, "speed_min");
    public static final ColumnDefinition<Date> START_DATETIME = new DateColumnDefinition(3, "start_datetime");
    public static final ColumnDefinition<String> START_LOCALE_TIMEZONE = new StringColumnDefinition(4, "start_locale_timezone");
    public static final ColumnDefinition<Integer> STEPS_TOTAL = new IntegerColumnDefinition(14, "steps_total");
    public static final ColumnDefinition<Double> TORQUE_AVG = new DoubleColumnDefinition(29, "torque_avg");
    public static final ColumnDefinition<Double> TORQUE_MAX = new DoubleColumnDefinition(28, "torque_max");
    public static final ColumnDefinition<Double> TORQUE_MIN = new DoubleColumnDefinition(27, "torque_min");
    public static final ColumnDefinition<Boolean> TWITTER = new BooleanColumnDefinition(34, "twitter");
    public static final ColumnDefinition<Date> UPDATED_DATETIME = new DateColumnDefinition(6, "updated_datetime");
    public static final ColumnDefinition<Double> WILLPOWER = new DoubleColumnDefinition(31, BaseDataTypes.ID_WILLPOWER);
    protected static final String WORKOUT_DATABASE_NAME = "uasdk_workout";
    private static final int WORKOUT_DATABASE_VERSION = 2;
    private static final String WORKOUT_DIR_PATH = "workout";
    private static WorkoutTimeSeriesDataAdapter adapter = null;
    private static WorkoutDatabase instance = null;

    protected WorkoutDatabase(Context context) {
        super(context, "workout", WORKOUT_DATABASE_NAME, buildColumnNames(ALL_COLUMNS), REMOTE_ID.getColumnName(), 2);
    }

    public static WorkoutDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new WorkoutDatabase(context.getApplicationContext());
        }
        return instance;
    }

    /* access modifiers changed from: protected */
    public void createEntityTable(SQLiteDatabase db) {
        executeSqlScript(db, "cache/workout/1_workout_create_table.sql", String.format("Fatal error, unable to initialize entity tables for %s database.", new Object[]{"workout"}));
    }

    public void onEntityUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 0:
                db.execSQL("DROP TABLE IF EXISTS " + this.mEntityTable);
                createEntityTable(db);
                break;
            case 1:
                break;
            default:
                return;
        }
        deleteEntitiesWithNullRemoteId(db);
        deleteAllLists(db);
    }

    /* access modifiers changed from: protected */
    public AbstractEntityList<Workout> createEntityList(long localId, String remoteId, int totalCount) {
        WorkoutListImpl list = new WorkoutListImpl();
        list.setTotalCount(totalCount);
        list.setLocalId(localId);
        list.setLink("self", new Link(remoteId));
        return list;
    }

    /* access modifiers changed from: protected */
    public WorkoutImpl getEntityFromCursor(Cursor c) {
        WorkoutImpl workout = new WorkoutImpl();
        workout.setLocalId(LOCAL_ID.read(c).longValue());
        workout.startTime = START_DATETIME.read(c);
        workout.updateTime = UPDATED_DATETIME.read(c);
        workout.createdTime = CREATED_DATETIME.read(c);
        workout.name = NAME.read(c);
        workout.notes = NOTES.read(c);
        workout.timeZone = TimeZone.getTimeZone(START_LOCALE_TIMEZONE.read(c));
        workout.source = SOURCE.read(c);
        workout.referenceKey = REFERENCE_KEY.read(c);
        workout.hasTimeSeries = HAS_TIME_SERIES.read(c);
        WorkoutAggregatesImpl workoutAggregates = new WorkoutAggregatesImpl();
        workoutAggregates.heartRateMin = HEART_RATE_MIN.read(c);
        workoutAggregates.heartRateMax = HEART_RATE_MAX.read(c);
        workoutAggregates.heartRateAvg = HEART_RATE_AVG.read(c);
        workoutAggregates.speedMin = SPEED_MIN.read(c);
        workoutAggregates.speedMax = SPEED_MAX.read(c);
        workoutAggregates.speedAvg = SPEED_AVG.read(c);
        workoutAggregates.cadenceMin = CADENCE_MIN.read(c);
        workoutAggregates.cadenceMax = CADENCE_MAX.read(c);
        workoutAggregates.cadenceAvg = CADENCE_AVG.read(c);
        workoutAggregates.powerMin = POWER_MIN.read(c);
        workoutAggregates.powerMax = POWER_MAX.read(c);
        workoutAggregates.powerAvg = POWER_AVG.read(c);
        workoutAggregates.torqueMin = TORQUE_MIN.read(c);
        workoutAggregates.torqueMax = TORQUE_MAX.read(c);
        workoutAggregates.torqueAvg = TORQUE_AVG.read(c);
        workoutAggregates.willPower = WILLPOWER.read(c);
        workoutAggregates.distanceTotal = DISTANCE_TOTAL.read(c);
        workoutAggregates.metabolicEnergyTotal = METABOLIC_ENERGY_TOTAL.read(c);
        workoutAggregates.activeTimeTotal = ACTIVE_TIME_TOTAL.read(c);
        workoutAggregates.elapsedTimeTotal = ELAPSED_TIME_TOTAL.read(c);
        workoutAggregates.stepsTotal = STEPS_TOTAL.read(c);
        workout.workoutAggregates = workoutAggregates;
        if (workout.hasTimeSeries != null && workout.hasTimeSeries.booleanValue()) {
            workout.timeSeries = fetchTimeSeriesData(workout.getLocalId());
        }
        Boolean fb = FACEBOOK.read(c);
        Boolean twitter = TWITTER.read(c);
        if (!(fb == null && twitter == null)) {
            SocialSettings socialSettings = new SocialSettings();
            socialSettings.setFacebook(fb);
            socialSettings.setTwitter(twitter);
            workout.socialSettings = socialSettings;
        }
        return workout;
    }

    /* access modifiers changed from: protected */
    public ContentValues getContentValuesFromEntity(Workout workout) {
        Boolean bool;
        Boolean bool2 = null;
        ContentValues cv = new ContentValues();
        START_DATETIME.write(workout.getStartTime(), cv);
        UPDATED_DATETIME.write(workout.getUpdatedTime(), cv);
        CREATED_DATETIME.write(workout.getCreatedTime(), cv);
        NAME.write(workout.getName(), cv);
        NOTES.write(workout.getNotes(), cv);
        START_LOCALE_TIMEZONE.write(workout.getTimeZone().getID(), cv);
        SOURCE.write(workout.getSource(), cv);
        REFERENCE_KEY.write(workout.getReferenceKey(), cv);
        HAS_TIME_SERIES.write(workout.hasTimeSeries(), cv);
        WorkoutAggregates agg = workout.getAggregates();
        HEART_RATE_MAX.write(agg.getHeartRateMax(), cv);
        HEART_RATE_MIN.write(agg.getHeartRateMin(), cv);
        HEART_RATE_AVG.write(agg.getHeartRateAvg(), cv);
        SPEED_MAX.write(agg.getSpeedMax(), cv);
        SPEED_MIN.write(agg.getSpeedMin(), cv);
        SPEED_AVG.write(agg.getSpeedAvg(), cv);
        CADENCE_MAX.write(agg.getCadenceMax(), cv);
        CADENCE_MIN.write(agg.getCadenceMin(), cv);
        CADENCE_AVG.write(agg.getCadenceAvg(), cv);
        POWER_MAX.write(agg.getPowerMax(), cv);
        POWER_MIN.write(agg.getPowerMin(), cv);
        POWER_AVG.write(agg.getPowerAvg(), cv);
        TORQUE_MAX.write(agg.getTorqueMax(), cv);
        TORQUE_MIN.write(agg.getTorqueMin(), cv);
        TORQUE_AVG.write(agg.getTorqueAvg(), cv);
        WILLPOWER.write(agg.getWillPower(), cv);
        DISTANCE_TOTAL.write(agg.getDistanceTotal(), cv);
        METABOLIC_ENERGY_TOTAL.write(agg.getMetabolicEnergyTotal(), cv);
        ACTIVE_TIME_TOTAL.write(agg.getActiveTimeTotal(), cv);
        ELAPSED_TIME_TOTAL.write(agg.getElapsedTimeTotal(), cv);
        STEPS_TOTAL.write(agg.getStepsTotal(), cv);
        SocialSettings socialSettings = workout.getSocialSettings();
        ColumnDefinition<Boolean> columnDefinition = FACEBOOK;
        if (socialSettings != null) {
            bool = socialSettings.getFacebook();
        } else {
            bool = null;
        }
        columnDefinition.write(bool, cv);
        ColumnDefinition<Boolean> columnDefinition2 = TWITTER;
        if (socialSettings != null) {
            bool2 = socialSettings.getTwitter();
        }
        columnDefinition2.write(bool2, cv);
        return cv;
    }

    private List<Workout> fetchUnsyncedWorkouts(Integer state) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder sb = new StringBuilder("SELECT ");
        for (int i = 0; i < ALL_COLUMNS.length; i++) {
            sb.append("w.").append(ALL_COLUMNS[i].getColumnName());
            if (i < ALL_COLUMNS.length - 1) {
                sb.append(",");
            }
            sb.append(Constants.EMPTY_SPACE);
        }
        sb.append("FROM workout_entity w JOIN workout_meta wm ON w._id=wm.entity_id WHERE wm.pending_operation = ? AND wm.entity_list_id IS NULL;");
        Cursor c = db.rawQuery(sb.toString(), new String[]{String.valueOf(state)});
        List<Workout> result = new ArrayList<>();
        while (c.moveToNext()) {
            WorkoutImpl w = getEntityFromCursor(c);
            w.setLinkMap(getLinkMap(db, "entity_id", c.getLong(0)));
            result.add(w);
        }
        return result;
    }

    public List<Workout> fetchUnsyncedCreatedWorkouts() {
        return fetchUnsyncedWorkouts(STATE_CREATED);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x006b A[SYNTHETIC, Splitter:B:22:0x006b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void overwriteWorkoutTimeSeriesJson(long r10, com.ua.sdk.workout.Workout r12) {
        /*
            r9 = this;
            com.ua.sdk.workout.TimeSeriesData r4 = r12.getTimeSeriesData()
            if (r4 == 0) goto L_0x0049
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r4 = adapter
            if (r4 != 0) goto L_0x0011
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r4 = new com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter
            r4.<init>()
            adapter = r4
        L_0x0011:
            r2 = 0
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            android.content.Context r4 = r9.mContext     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            java.lang.String r5 = "workout"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            r6.<init>()     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            java.lang.String r7 = ".json"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            java.lang.String r6 = r6.toString()     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            java.io.FileOutputStream r4 = com.ua.sdk.util.FileUtil.openFileOutput(r4, r5, r6)     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            r3.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0051, IOException -> 0x006f }
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r5 = adapter     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            com.google.gson.stream.JsonWriter r6 = new com.google.gson.stream.JsonWriter     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            r6.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            com.ua.sdk.workout.TimeSeriesData r4 = r12.getTimeSeriesData()     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            com.ua.sdk.workout.WorkoutTimeSeriesImpl r4 = (com.ua.sdk.workout.WorkoutTimeSeriesImpl) r4     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            com.ua.sdk.workout.WorkoutTimeSeriesImpl r4 = (com.ua.sdk.workout.WorkoutTimeSeriesImpl) r4     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            r5.write((com.google.gson.stream.JsonWriter) r6, (com.ua.sdk.workout.WorkoutTimeSeriesImpl) r4)     // Catch:{ FileNotFoundException -> 0x0093, IOException -> 0x0090, all -> 0x008d }
            if (r3 == 0) goto L_0x0049
            r3.close()     // Catch:{ IOException -> 0x004a }
        L_0x0049:
            return
        L_0x004a:
            r0 = move-exception
            java.lang.String r4 = "Caught exception closing out"
            com.ua.sdk.UaLog.error((java.lang.String) r4, (java.lang.Throwable) r0)
            goto L_0x0049
        L_0x0051:
            r0 = move-exception
        L_0x0052:
            java.lang.String r4 = "Fatal error! Unable to open file to write JSON for workout with localId=%s."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0068 }
            r6 = 0
            java.lang.Long r7 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0068 }
            r5[r6] = r7     // Catch:{ all -> 0x0068 }
            java.lang.String r1 = java.lang.String.format(r4, r5)     // Catch:{ all -> 0x0068 }
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0068 }
            r4.<init>(r1)     // Catch:{ all -> 0x0068 }
            throw r4     // Catch:{ all -> 0x0068 }
        L_0x0068:
            r4 = move-exception
        L_0x0069:
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ IOException -> 0x0086 }
        L_0x006e:
            throw r4
        L_0x006f:
            r0 = move-exception
        L_0x0070:
            java.lang.String r4 = "Fatal error! Unable to write JSON for workout with localId=%s."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0068 }
            r6 = 0
            java.lang.Long r7 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0068 }
            r5[r6] = r7     // Catch:{ all -> 0x0068 }
            java.lang.String r1 = java.lang.String.format(r4, r5)     // Catch:{ all -> 0x0068 }
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0068 }
            r4.<init>(r1)     // Catch:{ all -> 0x0068 }
            throw r4     // Catch:{ all -> 0x0068 }
        L_0x0086:
            r0 = move-exception
            java.lang.String r5 = "Caught exception closing out"
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r0)
            goto L_0x006e
        L_0x008d:
            r4 = move-exception
            r2 = r3
            goto L_0x0069
        L_0x0090:
            r0 = move-exception
            r2 = r3
            goto L_0x0070
        L_0x0093:
            r0 = move-exception
            r2 = r3
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.workout.WorkoutDatabase.overwriteWorkoutTimeSeriesJson(long, com.ua.sdk.workout.Workout):void");
    }

    /* access modifiers changed from: protected */
    public void postSaveEntity(long localId, Workout workout) {
        overwriteWorkoutTimeSeriesJson(localId, workout);
    }

    /* access modifiers changed from: protected */
    public void preDeleteEntity(long localId) {
        deleteTimeSeriesData(localId);
    }

    /* access modifiers changed from: protected */
    public void preDeleteAll(SQLiteDatabase db) {
        Cursor c = db.query(this.mEntityTable, new String[]{"_id"}, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        ArrayList<Long> localIds = new ArrayList<>();
        while (c.moveToNext()) {
            localIds.add(Long.valueOf(c.getLong(0)));
        }
        c.close();
        Iterator i$ = localIds.iterator();
        while (i$.hasNext()) {
            deleteTimeSeriesData(i$.next().longValue());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x008e A[SYNTHETIC, Splitter:B:30:0x008e] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:16:0x0046=Splitter:B:16:0x0046, B:25:0x006b=Splitter:B:25:0x006b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.workout.TimeSeriesData fetchTimeSeriesData(long r10) {
        /*
            r9 = this;
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r4 = adapter
            if (r4 != 0) goto L_0x000b
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r4 = new com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter
            r4.<init>()
            adapter = r4
        L_0x000b:
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            android.content.Context r4 = r9.mContext     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            java.lang.String r5 = "workout"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            r6.<init>()     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            java.lang.String r7 = ".json"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            java.lang.String r6 = r6.toString()     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            java.io.FileInputStream r4 = com.ua.sdk.util.FileUtil.openFileInput(r4, r5, r6)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            r2.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x006a }
            com.ua.sdk.workout.WorkoutTimeSeriesDataAdapter r4 = adapter     // Catch:{ FileNotFoundException -> 0x009f, IOException -> 0x009c, all -> 0x0099 }
            com.google.gson.stream.JsonReader r5 = new com.google.gson.stream.JsonReader     // Catch:{ FileNotFoundException -> 0x009f, IOException -> 0x009c, all -> 0x0099 }
            r5.<init>(r2)     // Catch:{ FileNotFoundException -> 0x009f, IOException -> 0x009c, all -> 0x0099 }
            com.ua.sdk.workout.WorkoutTimeSeriesImpl r4 = r4.read((com.google.gson.stream.JsonReader) r5)     // Catch:{ FileNotFoundException -> 0x009f, IOException -> 0x009c, all -> 0x0099 }
            if (r2 == 0) goto L_0x003c
            r2.close()     // Catch:{ IOException -> 0x003e }
        L_0x003c:
            r1 = r2
        L_0x003d:
            return r4
        L_0x003e:
            r0 = move-exception
            java.lang.String r5 = "Caught exception closing in"
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r0)
            goto L_0x003c
        L_0x0045:
            r0 = move-exception
        L_0x0046:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r4.<init>()     // Catch:{ all -> 0x008b }
            java.lang.String r5 = "Didn't find time series for workout with localId="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x008b }
            java.lang.StringBuilder r4 = r4.append(r10)     // Catch:{ all -> 0x008b }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x008b }
            com.ua.sdk.UaLog.debug(r4)     // Catch:{ all -> 0x008b }
            r4 = 0
            if (r1 == 0) goto L_0x003d
            r1.close()     // Catch:{ IOException -> 0x0063 }
            goto L_0x003d
        L_0x0063:
            r0 = move-exception
            java.lang.String r5 = "Caught exception closing in"
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r0)
            goto L_0x003d
        L_0x006a:
            r0 = move-exception
        L_0x006b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r4.<init>()     // Catch:{ all -> 0x008b }
            java.lang.String r5 = "Fatal error! Unable to read JSON for workout with localId="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x008b }
            java.lang.StringBuilder r4 = r4.append(r10)     // Catch:{ all -> 0x008b }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x008b }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x008b }
            java.lang.String r3 = java.lang.String.format(r4, r5)     // Catch:{ all -> 0x008b }
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x008b }
            r4.<init>(r3)     // Catch:{ all -> 0x008b }
            throw r4     // Catch:{ all -> 0x008b }
        L_0x008b:
            r4 = move-exception
        L_0x008c:
            if (r1 == 0) goto L_0x0091
            r1.close()     // Catch:{ IOException -> 0x0092 }
        L_0x0091:
            throw r4
        L_0x0092:
            r0 = move-exception
            java.lang.String r5 = "Caught exception closing in"
            com.ua.sdk.UaLog.error((java.lang.String) r5, (java.lang.Throwable) r0)
            goto L_0x0091
        L_0x0099:
            r4 = move-exception
            r1 = r2
            goto L_0x008c
        L_0x009c:
            r0 = move-exception
            r1 = r2
            goto L_0x006b
        L_0x009f:
            r0 = move-exception
            r1 = r2
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.workout.WorkoutDatabase.fetchTimeSeriesData(long):com.ua.sdk.workout.TimeSeriesData");
    }

    private boolean deleteTimeSeriesData(long localId) {
        try {
            return FileUtil.getFile(this.mContext, "workout", localId + ".json").delete();
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
