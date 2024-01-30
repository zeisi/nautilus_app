package com.ua.sdk.recorder.message;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifierImpl;
import com.ua.sdk.recorder.Message;
import com.ua.sdk.recorder.MessagePersistence;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.util.Pools;

public class RecorderContextMessage extends Message {
    private RecorderContext recorderContext;

    public RecorderContextMessage(DataSourceIdentifierImpl dataSourceIdentifier, MessagePersistence messagePersistence, Pools.Pool pool) {
        super(dataSourceIdentifier, messagePersistence, pool);
    }

    public void setRecorderContext(RecorderContext recorderContext2) {
        this.recorderContext = recorderContext2;
    }

    public void processMessage(RecorderCalculator recorderCalculator) {
        recorderCalculator.updateRecorderContext(this.recorderContext);
    }

    public void reset() {
        this.recorderContext = null;
    }
}
