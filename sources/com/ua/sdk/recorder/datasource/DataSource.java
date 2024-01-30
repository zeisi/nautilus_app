package com.ua.sdk.recorder.datasource;

import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.RecorderClock;
import com.ua.sdk.recorder.RecorderContext;
import java.util.List;

public abstract class DataSource {
    protected RecorderClock clock;
    protected DataSourceIdentifier dataSourceIdentifier;
    protected List<DataTypeRef> dataTypeRefs;
    protected RecorderContext recorderContext;

    public abstract void connectDataSource();

    public abstract void disconnectDataSource();

    public abstract void startSegment();

    public abstract void stopSegment();

    public DataSource(DataSourceIdentifier dataSourceIdentifier2, List<DataTypeRef> dataTypeRefs2) {
        this.dataSourceIdentifier = dataSourceIdentifier2;
        this.dataTypeRefs = dataTypeRefs2;
    }

    public void configure(RecorderContext recorderContext2) {
        this.recorderContext = recorderContext2;
        this.clock = recorderContext2.getClock();
    }

    public List<DataTypeRef> getDataTypeRefs() {
        return this.dataTypeRefs;
    }

    public DataSourceIdentifier getDataSourceIdentifier() {
        return this.dataSourceIdentifier;
    }
}
