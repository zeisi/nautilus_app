package com.ua.sdk.recorder;

import com.ua.sdk.UaLog;
import com.ua.sdk.internal.Precondition;

public class RecordProcessor {
    private static final String TAG = "RecordProcessor";
    private boolean finished;
    private final MessageQueue processorMessageQueue;
    private final Thread processorThread = new Thread(new MyProcessLoopRunnable());
    private final RecorderCalculator recorderCalculator;
    private final String recorderName;

    public RecordProcessor(String recorderName2, MessageQueue processorMessageQueue2, RecorderCalculator recorderCalculator2) {
        this.recorderName = recorderName2;
        this.processorMessageQueue = processorMessageQueue2;
        this.recorderCalculator = recorderCalculator2;
    }

    public void begin() {
        Precondition.check(!this.finished, "Can not begin an already finished RecordProcessor.");
        this.finished = false;
        this.processorThread.setName("RecordProcessor-" + this.recorderName);
        this.processorThread.start();
    }

    public void finish() {
        boolean z = true;
        if (this.finished) {
            z = false;
        }
        Precondition.check(z, "Can not finish a RecordProcessor that has not called begin.");
        synchronized (this.processorMessageQueue) {
            this.finished = true;
            this.processorMessageQueue.notify();
        }
    }

    /* access modifiers changed from: protected */
    public void processLoop() {
        Message message;
        while (!this.finished) {
            try {
                synchronized (this.processorMessageQueue) {
                    message = this.processorMessageQueue.poll();
                    if (message == null) {
                        this.processorMessageQueue.wait();
                    }
                }
                if (message != null) {
                    this.recorderCalculator.onProcessMessage(message);
                }
            } catch (InterruptedException e) {
                UaLog.info("MessageProducerQueue InterruptedException, moving on.");
            }
        }
    }

    protected class MyProcessLoopRunnable implements Runnable {
        protected MyProcessLoopRunnable() {
        }

        public void run() {
            RecordProcessor.this.processLoop();
        }
    }
}
