package com.ua.sdk.recorder.message;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.Message;
import com.ua.sdk.recorder.MessagePersistence;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.util.Pools;

public class StartMessage extends Message {
    public StartMessage(DataSourceIdentifier dataSourceIdentifier, MessagePersistence messagePersistence, Pools.Pool pool) {
        super(dataSourceIdentifier, messagePersistence, pool);
    }

    public void processMessage(RecorderCalculator recorderCalculator) {
        recorderCalculator.startSegment(getDataSourceIdentifier(), getTimestamp());
    }

    public void reset() {
    }
}
