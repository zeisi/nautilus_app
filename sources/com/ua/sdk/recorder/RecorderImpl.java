package com.ua.sdk.recorder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.ua.sdk.EntityList;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.LocalDate;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.activitytype.ActivityTypeManager;
import com.ua.sdk.activitytype.ActivityTypeRef;
import com.ua.sdk.datapoint.DataFrame;
import com.ua.sdk.datapoint.DataFrameImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.heartrate.HeartRateZones;
import com.ua.sdk.heartrate.HeartRateZonesListRef;
import com.ua.sdk.heartrate.HeartRateZonesManager;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.recorder.data.DataFrameObserver;
import com.ua.sdk.recorder.datasource.AbstractDataSourceConfiguration;
import com.ua.sdk.recorder.datasource.DataSource;
import com.ua.sdk.recorder.datasource.sensor.SensorDataSource;
import com.ua.sdk.recorder.producer.CommandProducer;
import com.ua.sdk.recorder.producer.MessageProducer;
import com.ua.sdk.recorder.producer.SensorMessageProducer;
import com.ua.sdk.recorder.producer.TimeProducer;
import com.ua.sdk.recorder.save.RecorderWorkoutConverterImpl;
import com.ua.sdk.user.User;
import com.ua.sdk.user.UserManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecorderImpl implements Recorder {
    private static final int HANDLER_DISPATCH_DATA_TYPE_OBSERVERS = 1;
    private static final int HANDLER_DISPATCH_SEGMENT_STATE_OBSERVERS = 3;
    private static final int HANDLER_DISPATCH_SENSOR_STATE_OBSERVERS = 2;
    private static final int HANDLER_DISPATCH_TIME_OBSERVERS = 4;
    /* access modifiers changed from: private */
    public ActivityTypeManager activityTypeManager;
    /* access modifiers changed from: private */
    public CommandProducer commandProducer;
    /* access modifiers changed from: private */
    public RecorderConfigurationImpl configuration;
    private Context context;
    /* access modifiers changed from: private */
    public Map<DataTypeRef, List<DataFrameObserver>> dataFrameObservers = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    @Deprecated
    public List<DataFrame> dataFrames = new ArrayList();
    private MyRecordSessionHandler handler = new MyRecordSessionHandler();
    /* access modifiers changed from: private */
    public HeartRateZonesManager heartRateZonesManager;
    private boolean isConfigured;
    private boolean isConfiguring;
    private boolean isDestroying;
    private List<MessageProducer> messageProducers = new ArrayList();
    private MessageQueue processorMessageQueue;
    private RecorderManagerImpl recordManager;
    private RecordProcessor recordProcessor;
    private RecorderCalculator recorderCalculator;
    /* access modifiers changed from: private */
    public RecorderContext recorderContext;
    /* access modifiers changed from: private */
    public List<RecorderObserver> recorderObservers = new ArrayList();
    /* access modifiers changed from: private */
    public List<SensorDataSourceObserver> sensorDataSourceObservers = new ArrayList();
    private SensorMessageProducer sensorMessageProducer;
    private TimeProducer timeProducer;
    private UserManager userManager;

    protected interface RecorderConfigureCallback {
        void onConfigureFailed(UaException uaException);

        void onConfigureSuccess();
    }

    public RecorderImpl(RecorderConfiguration recorderConfiguration, Context context2, UserManager userManager2, ActivityTypeManager activityTypeManager2, HeartRateZonesManager heartRateZonesManager2, RecorderManagerImpl recordManager2) {
        this.configuration = (RecorderConfigurationImpl) Precondition.isNotNull(recorderConfiguration);
        this.context = (Context) Precondition.isNotNull(context2);
        this.userManager = (UserManager) Precondition.isNotNull(userManager2);
        this.activityTypeManager = (ActivityTypeManager) Precondition.isNotNull(activityTypeManager2);
        this.heartRateZonesManager = (HeartRateZonesManager) Precondition.isNotNull(heartRateZonesManager2);
        this.recordManager = (RecorderManagerImpl) Precondition.isNotNull(recordManager2);
        Precondition.isNotNull(this.configuration.getName(), "recorder name");
        Precondition.isNotNull(this.configuration.getActivityTypeRef(), "activity type");
        Precondition.isNotNull(this.configuration.getUserRef(), "user ref");
    }

    /* access modifiers changed from: package-private */
    public void configure(RecorderConfigureCallback callback) {
        this.isConfiguring = true;
        this.isConfigured = false;
        this.recorderContext = new RecorderContext();
        this.recorderContext.setApplicationContext(this.context);
        this.recorderContext.setName(this.configuration.getName());
        RecorderClock recorderClock = new RecorderClock();
        recorderClock.init();
        this.recorderContext.setClock(recorderClock);
        this.processorMessageQueue = new MessageQueue();
        this.commandProducer = new CommandProducer(recorderClock, this.processorMessageQueue);
        this.messageProducers.add(this.commandProducer);
        this.timeProducer = new TimeProducer(recorderClock, this.processorMessageQueue);
        this.messageProducers.add(this.timeProducer);
        this.sensorMessageProducer = new SensorMessageProducer(recorderClock, this.processorMessageQueue);
        this.messageProducers.add(this.sensorMessageProducer);
        this.userManager.fetchUser(this.configuration.getUserRef(), new MyFetchUserCallback(callback));
    }

    /* access modifiers changed from: package-private */
    public void onPostConfigureLoad(RecorderConfigureCallback callback) {
        this.recorderCalculator = new RecorderCalculator(this.recorderContext, new MyRecordCalculatorListener());
        this.recordProcessor = new RecordProcessor(this.configuration.getName(), this.processorMessageQueue, this.recorderCalculator);
        this.isConfigured = true;
        for (MessageProducer messageProducer : this.messageProducers) {
            messageProducer.beginRecorder();
        }
        for (DataSourceConfiguration dataSourceConfiguration : this.configuration.getDataSourceConfigurations()) {
            addDataSource(dataSourceConfiguration, false);
        }
        this.dataFrames.add(this.recorderCalculator.createDataFrame());
        this.recordProcessor.begin();
        this.isConfiguring = false;
        callback.onConfigureSuccess();
        if (this.isDestroying) {
            destroyRecorder();
        }
    }

    public void startSegment() {
        checkConfigured();
        this.commandProducer.produceStartSegment();
    }

    public void stopSegment() {
        checkConfigured();
        this.commandProducer.produceStopSegment();
    }

    public void destroy() {
        checkConfigured();
        this.isConfigured = false;
        this.isDestroying = true;
        if (!this.isConfiguring) {
            destroyRecorder();
        }
    }

    private void destroyRecorder() {
        this.recordProcessor.finish();
        for (MessageProducer messageProducer : this.messageProducers) {
            messageProducer.finishRecorder();
        }
        for (DataSource dataSource : this.recorderCalculator.getDataSources()) {
            dataSource.disconnectDataSource();
        }
        this.recordManager.destroyRecorder(this.configuration.getName());
    }

    public DataFrame getDataFrame() {
        checkConfigured();
        if (!this.dataFrames.isEmpty()) {
            return this.dataFrames.get(this.dataFrames.size() - 1);
        }
        return null;
    }

    public List<DataFrame> getAllDataFrames() {
        checkConfigured();
        return new ArrayList(this.dataFrames);
    }

    public RecorderWorkoutConverter getRecorderWorkoutConverter() {
        checkConfigured();
        return new RecorderWorkoutConverterImpl(getAllDataFrames(), this.recorderContext);
    }

    public void addDataSource(DataSourceConfiguration dataSourceConfiguration) {
        addDataSource(dataSourceConfiguration, true);
    }

    /* access modifiers changed from: protected */
    public void addDataSource(DataSourceConfiguration dataSourceConfiguration, boolean isUpdate) {
        checkConfigured();
        DataSource dataSource = ((AbstractDataSourceConfiguration) dataSourceConfiguration).build(this.sensorMessageProducer, new MySensorDataSourceListener());
        dataSource.configure(this.recorderContext);
        dataSource.connectDataSource();
        this.recorderCalculator.addDataSource(dataSource);
        if (isUpdate) {
            this.recordManager.addDataSourceRecorderCache(dataSourceConfiguration);
        }
    }

    public boolean removeDataSource(DataSourceIdentifier dataSourceIdentifier) {
        checkConfigured();
        for (DataSource dataSource : this.recorderCalculator.getDataSources()) {
            if (dataSource.getDataSourceIdentifier().equals(dataSourceIdentifier)) {
                dataSource.disconnectDataSource();
                this.recorderCalculator.removeDataSource(dataSource);
                this.recordManager.removeDataSourceRecorderCache(dataSourceIdentifier);
                return true;
            }
        }
        return false;
    }

    public void updateRecorderActivityType(ActivityTypeRef activityTypeRef) {
        checkConfigured();
        this.activityTypeManager.fetchActivityType(activityTypeRef, new MyUpdateActivityTypeFetchCallback());
    }

    public List<DataSourceIdentifier> getDataSources() {
        checkConfigured();
        List<DataSourceIdentifier> dataSourceIdentifiers = new ArrayList<>();
        for (DataSource dataSource : this.recorderCalculator.getDataSources()) {
            dataSourceIdentifiers.add(dataSource.getDataSourceIdentifier());
        }
        return dataSourceIdentifiers;
    }

    public void addDataFrameObserver(DataFrameObserver dataFrameObserver, DataTypeRef... dataTypesRefs) {
        DataFrameImpl dataFrame = (DataFrameImpl) getDataFrame();
        for (DataTypeRef dataTypeRef : dataTypesRefs) {
            List<DataFrameObserver> typeObservers = this.dataFrameObservers.get(dataTypeRef);
            if (typeObservers == null) {
                typeObservers = new ArrayList<>();
                this.dataFrameObservers.put(dataTypeRef, typeObservers);
            }
            typeObservers.add(dataFrameObserver);
            if (!(dataFrame == null || dataFrame.getDataPoint(dataTypeRef) == null || !dataFrame.getPrimaryDataSources().containsKey(dataTypeRef))) {
                dispatchDataFrame(dataFrame.getPrimaryDataSources().get(dataTypeRef), dataTypeRef, dataFrame);
            }
        }
    }

    public void removeDataFrameObserver(DataFrameObserver dataFrameObserver) {
        Iterator<Map.Entry<DataTypeRef, List<DataFrameObserver>>> itr = this.dataFrameObservers.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<DataTypeRef, List<DataFrameObserver>> entry = itr.next();
            entry.getValue().remove(dataFrameObserver);
            if (entry.getValue().isEmpty()) {
                itr.remove();
            }
        }
    }

    public void addSensorDataSourceObserver(SensorDataSourceObserver sensorDataSourceObserver) {
        checkConfigured();
        this.sensorDataSourceObservers.add(sensorDataSourceObserver);
        for (DataSource source : this.recorderCalculator.getDataSources()) {
            if (source instanceof SensorDataSource) {
                sensorDataSourceObserver.onSensorDataSourceStatus(source.getDataSourceIdentifier(), ((SensorDataSource) source).getSensorStatus(), ((SensorDataSource) source).getSensorHealth());
            }
        }
    }

    public void removeSensorDataSourceObserver(SensorDataSourceObserver sensorDataSourceObserver) {
        this.sensorDataSourceObservers.remove(sensorDataSourceObserver);
    }

    public void addRecorderObserver(RecorderObserver recorderObserver) {
        checkConfigured();
        this.recorderObservers.add(recorderObserver);
    }

    public void removeRecorderObserver(RecorderObserver recorderObserver) {
        this.recorderObservers.remove(recorderObserver);
    }

    /* access modifiers changed from: protected */
    public void dispatchDataFrame(DataSourceIdentifier dataSourceIdentifier, DataTypeRef dataTypeRef, DataFrame dataFrame) {
        this.handler.sendMessage(this.handler.obtainMessage(1, new DataFrameObserverData(dataSourceIdentifier, dataTypeRef, dataFrame)));
    }

    /* access modifiers changed from: protected */
    public void dispatchTime(DataFrameImpl dataFrame) {
        this.handler.sendMessage(this.handler.obtainMessage(4, dataFrame));
    }

    /* access modifiers changed from: protected */
    public void dispatchSegmentState(DataFrameImpl dataFrame) {
        this.handler.sendMessage(this.handler.obtainMessage(3, dataFrame));
    }

    /* access modifiers changed from: protected */
    public void dispatchSensorStateChanged(DataSourceIdentifier dataSourceIdentifier, SensorStatus status, SensorHealth health) {
        this.handler.sendMessage(this.handler.obtainMessage(2, new SensorDataSourceObserverData(dataSourceIdentifier, status, health)));
    }

    private void checkConfigured() {
        if (!this.isConfigured) {
            throw new IllegalStateException("Cannot call method before configure callback is called");
        } else if (this.isDestroying) {
            throw new IllegalStateException("Cannot call method after destroy is called");
        }
    }

    /* access modifiers changed from: private */
    public int getAge(User user) {
        LocalDate birthDate = user.getBirthdate();
        Calendar today = Calendar.getInstance();
        if (birthDate == null) {
            return 35;
        }
        int age = today.get(1) - birthDate.getYear();
        if (today.get(2) < birthDate.getMonth()) {
            return age - 1;
        }
        if (today.get(2) != birthDate.getMonth() || today.get(5) >= birthDate.getDayOfMonth()) {
            return age;
        }
        return age - 1;
    }

    protected class MyRecordCalculatorListener implements RecorderCalculator.RecorderCalculatorListener {
        protected MyRecordCalculatorListener() {
        }

        public void storeDataFrame(DataFrame dataFrame) {
            RecorderImpl.this.dataFrames.add(dataFrame);
        }

        public boolean isDataTypeObserved(DataTypeRef dataTypeRef) {
            return RecorderImpl.this.dataFrameObservers.containsKey(dataTypeRef);
        }

        public void onDataTypeUpdated(DataFrameImpl dataFrame) {
            int index = 0;
            List<DataSourceIdentifier> dataSourceIdentifiers = dataFrame.getDataSourceIdentifiersChanged();
            for (DataTypeRef dataTypeRef : dataFrame.getDataTypesChanged()) {
                RecorderImpl.this.dispatchDataFrame(dataSourceIdentifiers.get(index), dataTypeRef, dataFrame);
                index++;
            }
        }

        public boolean isTimeObserved() {
            return !RecorderImpl.this.recorderObservers.isEmpty();
        }

        public void onTimeUpdated(DataFrameImpl dataFrame) {
            RecorderImpl.this.dispatchTime(dataFrame);
        }

        public boolean isSegmentStateObserved() {
            return !RecorderImpl.this.recorderObservers.isEmpty();
        }

        public void onSegmentStateUpdated(DataFrameImpl dataFrame) {
            RecorderImpl.this.dispatchSegmentState(dataFrame);
        }
    }

    protected class MySensorDataSourceListener implements SensorDataSource.SensorDataSourceListener {
        protected MySensorDataSourceListener() {
        }

        public void onSensorStateChanged(DataSourceIdentifier dataSourceIdentifier, SensorStatus status, SensorHealth health) {
            if (RecorderImpl.this.sensorDataSourceObservers != null && !RecorderImpl.this.sensorDataSourceObservers.isEmpty()) {
                RecorderImpl.this.dispatchSensorStateChanged(dataSourceIdentifier, status, health);
            }
        }
    }

    protected class MyRecordSessionHandler extends Handler {
        protected MyRecordSessionHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    DataFrameObserverData observerData = (DataFrameObserverData) msg.obj;
                    List<DataFrameObserver> typeObservers = (List) RecorderImpl.this.dataFrameObservers.get(observerData.dataTypeRef);
                    if (typeObservers != null) {
                        for (DataFrameObserver observer : typeObservers) {
                            observer.onDataFrameUpdated(observerData.dataSourceIdentifier, observerData.dataTypeRef, observerData.dataFrame);
                        }
                        return;
                    }
                    return;
                case 2:
                    SensorDataSourceObserverData observerData2 = (SensorDataSourceObserverData) msg.obj;
                    for (SensorDataSourceObserver observer2 : RecorderImpl.this.sensorDataSourceObservers) {
                        observer2.onSensorDataSourceStatus(observerData2.dataSourceIdentifier, observerData2.status, observerData2.health);
                    }
                    return;
                case 3:
                    for (RecorderObserver recorderObserver : RecorderImpl.this.recorderObservers) {
                        recorderObserver.onSegmentStateUpdated((DataFrameImpl) msg.obj);
                    }
                    return;
                case 4:
                    DataFrameImpl dataFrame = (DataFrameImpl) msg.obj;
                    double activeTime = dataFrame.getActiveTime().doubleValue();
                    double elapsedTime = dataFrame.getElapsedTime().doubleValue();
                    for (RecorderObserver recorderObserver2 : RecorderImpl.this.recorderObservers) {
                        recorderObserver2.onTimeUpdated(activeTime, elapsedTime);
                    }
                    return;
                default:
                    UaLog.error("RecordSessionImpl unknown handler what");
                    return;
            }
        }
    }

    private class MyFetchUserCallback implements FetchCallback<User> {
        private RecorderConfigureCallback callback;

        private MyFetchUserCallback(RecorderConfigureCallback callback2) {
            this.callback = callback2;
        }

        public void onFetched(User userEntity, UaException e) {
            if (userEntity != null) {
                RecorderImpl.this.recorderContext.setUser(userEntity);
                RecorderImpl.this.activityTypeManager.fetchActivityType(RecorderImpl.this.configuration.getActivityTypeRef(), new MyFetchActivityTypeCallback(this.callback));
                return;
            }
            this.callback.onConfigureFailed(e);
        }
    }

    private class MyFetchActivityTypeCallback implements FetchCallback<ActivityType> {
        private RecorderConfigureCallback callback;

        private MyFetchActivityTypeCallback(RecorderConfigureCallback callback2) {
            this.callback = callback2;
        }

        public void onFetched(ActivityType activityTypeEntity, UaException e) {
            if (activityTypeEntity != null) {
                RecorderImpl.this.recorderContext.setActivityType(activityTypeEntity);
                RecorderImpl.this.heartRateZonesManager.fetchHeartRateZonesList(HeartRateZonesListRef.getBuilder().setUser(RecorderImpl.this.recorderContext.getUser().getRef()).build(), new MyFetchHeartRateZonesCallback(this.callback));
                return;
            }
            this.callback.onConfigureFailed(e);
        }
    }

    private class MyFetchHeartRateZonesCallback implements FetchCallback<EntityList<HeartRateZones>> {
        private RecorderConfigureCallback callback;

        private MyFetchHeartRateZonesCallback(RecorderConfigureCallback callback2) {
            this.callback = callback2;
        }

        public void onFetched(EntityList<HeartRateZones> heartRateZonesEntityList, UaException e) {
            if (e != null) {
                this.callback.onConfigureFailed(e);
            }
            if (heartRateZonesEntityList == null || heartRateZonesEntityList.isEmpty()) {
                RecorderImpl.this.recorderContext.setHeartRateZones(RecorderImpl.this.heartRateZonesManager.calculateHeartRateZonesWithAge(RecorderImpl.this.getAge(RecorderImpl.this.recorderContext.getUser())));
            } else {
                RecorderImpl.this.recorderContext.setHeartRateZones(heartRateZonesEntityList.get(0));
            }
            RecorderImpl.this.onPostConfigureLoad(this.callback);
        }
    }

    private class MyUpdateActivityTypeFetchCallback implements FetchCallback<ActivityType> {
        private MyUpdateActivityTypeFetchCallback() {
        }

        public void onFetched(ActivityType activityType, UaException e) {
            if (e == null) {
                RecorderImpl.this.recorderContext.setActivityType(activityType);
                RecorderImpl.this.commandProducer.produceRecorderContext(RecorderImpl.this.recorderContext);
            }
        }
    }

    private static class DataFrameObserverData {
        DataFrame dataFrame;
        DataSourceIdentifier dataSourceIdentifier;
        DataTypeRef dataTypeRef;

        DataFrameObserverData(DataSourceIdentifier dataSourceIdentifier2, DataTypeRef dataTypeRef2, DataFrame dataFrame2) {
            this.dataSourceIdentifier = dataSourceIdentifier2;
            this.dataTypeRef = dataTypeRef2;
            this.dataFrame = dataFrame2;
        }
    }

    private static class SensorDataSourceObserverData {
        DataSourceIdentifier dataSourceIdentifier;
        SensorHealth health;
        SensorStatus status;

        SensorDataSourceObserverData(DataSourceIdentifier dataSourceIdentifier2, SensorStatus status2, SensorHealth health2) {
            this.dataSourceIdentifier = dataSourceIdentifier2;
            this.status = status2;
            this.health = health2;
        }
    }
}
