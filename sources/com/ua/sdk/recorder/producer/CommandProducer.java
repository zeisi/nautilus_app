package com.ua.sdk.recorder.producer;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifierImpl;
import com.ua.sdk.recorder.MessagePersistence;
import com.ua.sdk.recorder.MessageQueue;
import com.ua.sdk.recorder.RecorderClock;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.recorder.message.RecorderContextMessage;
import com.ua.sdk.recorder.message.StartMessage;
import com.ua.sdk.recorder.message.StopMessage;
import com.ua.sdk.util.Pools;

public class CommandProducer extends MessageProducer {
    public CommandProducer(RecorderClock clock, MessageQueue messageQueue) {
        super(clock, messageQueue);
    }

    public void beginRecorder() {
    }

    public void finishRecorder() {
    }

    public void produceStartSegment() {
        offer(new StartMessage((DataSourceIdentifier) null, (MessagePersistence) null, (Pools.Pool) null));
    }

    public void produceStopSegment() {
        offer(new StopMessage((DataSourceIdentifier) null, (MessagePersistence) null, (Pools.Pool) null));
    }

    public void produceRecorderContext(RecorderContext recorderContext) {
        RecorderContextMessage recorderContextMessage = new RecorderContextMessage((DataSourceIdentifierImpl) null, (MessagePersistence) null, (Pools.Pool) null);
        recorderContextMessage.setRecorderContext(recorderContext);
        offer(recorderContextMessage);
    }
}
