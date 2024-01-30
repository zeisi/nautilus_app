package com.ua.sdk.recorder.producer;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.MessagePersistence;
import com.ua.sdk.recorder.MessageQueue;
import com.ua.sdk.recorder.RecorderClock;
import com.ua.sdk.recorder.message.TimeMessage;
import com.ua.sdk.util.Pools;
import java.util.Timer;
import java.util.TimerTask;

public class TimeProducer extends MessageProducer {
    private static final long INTERVAL = 1000;
    private Pools.Pool<TimeMessage> timeMessagePool = new Pools.SynchronizedPool(512);
    private Timer timer;

    public TimeProducer(RecorderClock recorderClock, MessageQueue messageQueue) {
        super(recorderClock, messageQueue);
    }

    public void beginRecorder() {
        this.timer = new Timer("RecordTimeProducer");
        this.timer.scheduleAtFixedRate(new MyTimerTask(), 0, 1000);
    }

    public void finishRecorder() {
        this.timer.cancel();
        this.timer = null;
    }

    /* access modifiers changed from: private */
    public TimeMessage acquireTimeMessage() {
        TimeMessage timeMessage = this.timeMessagePool.acquire();
        if (timeMessage == null) {
            return new TimeMessage((DataSourceIdentifier) null, (MessagePersistence) null, this.timeMessagePool);
        }
        return timeMessage;
    }

    protected class MyTimerTask extends TimerTask {
        protected MyTimerTask() {
        }

        public void run() {
            TimeProducer.this.offer(TimeProducer.this.acquireTimeMessage());
        }
    }
}
