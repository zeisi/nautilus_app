package com.ua.sdk.recorder;

import android.content.Context;
import com.ua.sdk.IntensityCalculator;
import com.ua.sdk.MetabolicEnergyCalculator;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.activitytype.ActivityTypeManager;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifierBuilder;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifierBuilderImpl;
import com.ua.sdk.device.DeviceBuilder;
import com.ua.sdk.device.DeviceBuilderImpl;
import com.ua.sdk.heartrate.HeartRateZonesManager;
import com.ua.sdk.recorder.RecorderImpl;
import com.ua.sdk.recorder.RecorderManager;
import com.ua.sdk.recorder.datasource.AbstractDataSourceConfiguration;
import com.ua.sdk.recorder.datasource.derived.DerivedDataSourceConfigurationImpl;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothSensorDataSourceConfigurationImpl;
import com.ua.sdk.recorder.datasource.sensor.location.LocationSensorSensorDataSourceConfigurationImpl;
import com.ua.sdk.recorder.persistence.RecorderConfigurationDatabase;
import com.ua.sdk.user.UserManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class RecorderManagerImpl implements RecorderManager {
    private ActivityTypeManager activityTypeManager;
    private Context appContext;
    private ExecutorService executorService;
    private HeartRateZonesManager heartRateZonesManager;
    private IntensityCalculator intensityCalculator;
    private MetabolicEnergyCalculator metabolicEnergyCalculator;
    private RecorderConfigurationImpl recorderConfiguration;
    private RecorderConfigurationDatabase recorderConfigurationCache;
    /* access modifiers changed from: private */
    public List<RecorderManagerObserver> recorderManagerObservers = new ArrayList();
    /* access modifiers changed from: private */
    public Map<String, Recorder> recorders = new HashMap();
    private UserManager userManager;

    public RecorderManagerImpl(Context appContext2, ExecutorService executorService2, UserManager userManager2, ActivityTypeManager activityTypeManager2, HeartRateZonesManager heartRateZonesManager2, MetabolicEnergyCalculator metabolicEnergyCalculator2, IntensityCalculator intensityCalculator2, RecorderConfigurationDatabase recorderConfigurationCache2) {
        this.appContext = appContext2;
        this.executorService = executorService2;
        this.userManager = userManager2;
        this.activityTypeManager = activityTypeManager2;
        this.heartRateZonesManager = heartRateZonesManager2;
        this.metabolicEnergyCalculator = metabolicEnergyCalculator2;
        this.intensityCalculator = intensityCalculator2;
        this.recorderConfigurationCache = recorderConfigurationCache2;
        recoverCachedRecorders();
    }

    public void addRecorderManagerObserver(RecorderManagerObserver recorderManagerObserver) {
        this.recorderManagerObservers.add(recorderManagerObserver);
    }

    public void removeRecorderManagerObserver(RecorderManagerObserver recorderManagerObserver) {
        this.recorderManagerObservers.add(recorderManagerObserver);
    }

    public RecorderConfiguration createRecorderConfiguration() {
        return new RecorderConfigurationImpl();
    }

    public BluetoothSensorDataSourceConfiguration createBluetoothDataSourceConfiguration() {
        return new BluetoothSensorDataSourceConfigurationImpl();
    }

    public LocationSensorDataSourceConfiguration createLocationDataSourceConfiguration() {
        return new LocationSensorSensorDataSourceConfigurationImpl();
    }

    public DerivedDataSourceConfiguration createDerivedDataSourceConfiguration() {
        return new DerivedDataSourceConfigurationImpl();
    }

    public DataSourceIdentifierBuilder getDataSourceIdentifierBuilder() {
        return new DataSourceIdentifierBuilderImpl();
    }

    public DeviceBuilder getDeviceBuilder() {
        return new DeviceBuilderImpl();
    }

    public void createRecorder(RecorderConfiguration recorderConfiguration2, RecorderManager.CreateCallback createCallback) {
        createRecorder(recorderConfiguration2, false, createCallback);
    }

    private void createRecorder(final RecorderConfiguration recorderConfiguration2, boolean isRecovery, final RecorderManager.CreateCallback createCallback) {
        final RecorderImpl recorder = new RecorderImpl(recorderConfiguration2, this.appContext, this.userManager, this.activityTypeManager, this.heartRateZonesManager, this);
        this.recorderConfiguration = (RecorderConfigurationImpl) recorderConfiguration2;
        recorder.configure(new RecorderImpl.RecorderConfigureCallback() {
            public void onConfigureSuccess() {
                String name = ((RecorderConfigurationImpl) recorderConfiguration2).getName();
                RecorderManagerImpl.this.recorders.put(name, recorder);
                createCallback.onCreated(recorder, (UaException) null);
                for (RecorderManagerObserver recorderManagerObserver : RecorderManagerImpl.this.recorderManagerObservers) {
                    recorderManagerObserver.onRecorderCreated(name);
                }
            }

            public void onConfigureFailed(UaException e) {
                createCallback.onCreated((Recorder) null, e);
            }
        });
        if (!isRecovery) {
            insertRecorderCache();
        }
    }

    public Recorder getRecorder(String recorderName) {
        return this.recorders.get(recorderName);
    }

    /* access modifiers changed from: protected */
    public void destroyRecorder(String name) {
        this.recorders.remove(name);
        this.recorderConfigurationCache.delete(name);
        for (RecorderManagerObserver recorderManagerObserver : this.recorderManagerObservers) {
            recorderManagerObserver.onRecorderDestroyed(name);
        }
    }

    private void recoverCachedRecorders() {
        if (this.userManager.getCurrentUserRef() != null) {
            List<RecorderConfiguration> cachedConfigs = this.recorderConfigurationCache.getAllEntries(this.userManager.getCurrentUserRef().getId());
            if (cachedConfigs == null) {
                UaLog.info("No recorders to recover");
                return;
            }
            for (RecorderConfiguration configuration : cachedConfigs) {
                final String name = ((RecorderConfigurationImpl) configuration).getName();
                UaLog.warn("Recovering Recorder... name=" + name);
                createRecorder(configuration, true, new RecorderManager.CreateCallback() {
                    public void onCreated(Recorder recorder, UaException ex) {
                        UaLog.warn("Recorder Successfully Recovered. name=" + name);
                        for (RecorderManagerObserver recorderManagerObserver : RecorderManagerImpl.this.recorderManagerObservers) {
                            recorderManagerObserver.onRecorderRecovered(name);
                        }
                    }
                });
            }
        }
    }

    private void insertRecorderCache() {
        DataSourceConfigurationList dataSourceConfigurationList = convertToSerializableList(this.recorderConfiguration.getDataSourceConfigurations());
        Date date = new Date();
        this.recorderConfigurationCache.insert(this.recorderConfiguration.getName(), this.recorderConfiguration.getUserRef().getId(), this.recorderConfiguration.getActivityTypeRef().getId(), date, date, dataSourceConfigurationList);
    }

    /* access modifiers changed from: protected */
    public void addDataSourceRecorderCache(DataSourceConfiguration newDataSourceConfiguration) {
        this.recorderConfiguration.addDataSource(newDataSourceConfiguration);
        this.recorderConfigurationCache.update(this.recorderConfiguration.getName(), new Date(), convertToSerializableList(this.recorderConfiguration.getDataSourceConfigurations()));
    }

    /* access modifiers changed from: protected */
    public void removeDataSourceRecorderCache(DataSourceIdentifier dataSourceIdentifier) {
        List<DataSourceConfiguration> dataSourceConfigurations = this.recorderConfiguration.getDataSourceConfigurations();
        Iterator i$ = dataSourceConfigurations.iterator();
        while (true) {
            if (!i$.hasNext()) {
                break;
            }
            DataSourceConfiguration dataSourceConfiguration = i$.next();
            if (dataSourceIdentifier.equals(((AbstractDataSourceConfiguration) dataSourceConfiguration).dataSourceIdentifier)) {
                dataSourceConfigurations.remove(dataSourceConfiguration);
                break;
            }
        }
        this.recorderConfigurationCache.update(this.recorderConfiguration.getName(), new Date(), convertToSerializableList(dataSourceConfigurations));
    }

    private DataSourceConfigurationList convertToSerializableList(List<DataSourceConfiguration> dataSourceConfigurations) {
        DataSourceConfigurationList dataSourceConfigurationList = new DataSourceConfigurationList();
        for (DataSourceConfiguration dataSourceConfiguration : dataSourceConfigurations) {
            if (dataSourceConfiguration instanceof BluetoothSensorDataSourceConfiguration) {
                dataSourceConfigurationList.addBluetoothDataSourceConfiguration((BluetoothSensorDataSourceConfiguration) dataSourceConfiguration);
            } else if (dataSourceConfiguration instanceof LocationSensorDataSourceConfiguration) {
                dataSourceConfigurationList.addLocationDataSourceConfiguration((LocationSensorDataSourceConfiguration) dataSourceConfiguration);
            } else if (dataSourceConfiguration instanceof DerivedDataSourceConfiguration) {
                dataSourceConfigurationList.addDerivedDataSourceConfiguration((DerivedDataSourceConfiguration) dataSourceConfiguration);
            }
        }
        return dataSourceConfigurationList;
    }
}
