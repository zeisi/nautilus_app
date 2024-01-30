package com.ua.sdk.recorder.producer;

import com.ua.sdk.recorder.Message;
import com.ua.sdk.recorder.MessageQueue;
import com.ua.sdk.recorder.RecorderClock;

public abstract class MessageProducer {
    private MessageQueue messageQueue;
    private RecorderClock recorderClock;

    public abstract void beginRecorder();

    public abstract void finishRecorder();

    protected MessageProducer(RecorderClock recorderClock2, MessageQueue messageQueue2) {
        this.recorderClock = recorderClock2;
        this.messageQueue = messageQueue2;
    }

    /* access modifiers changed from: protected */
    public void offer(Message message) {
        message.setTimestamp(this.recorderClock.getTimestamp());
        this.messageQueue.offer(message);
    }
}
