package com.ua.sdk.concurrent;

import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import java.util.concurrent.Future;

public abstract class AsyncRequest<T> implements Request {
    boolean canceled = false;
    boolean done = false;
    Future<?> future = null;

    /* access modifiers changed from: package-private */
    public abstract void onDone(T t, UaException uaException);

    public synchronized boolean cancel() {
        boolean z = true;
        synchronized (this) {
            if (!this.done) {
                if (this.future != null) {
                    this.future.cancel(true);
                }
                this.canceled = true;
                onDone((Object) null, new UaException(UaException.Code.CANCELED));
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean isAsynchronous() {
        return false;
    }

    public synchronized void setFuture(Future<?> future2) {
        this.future = future2;
        if (this.canceled) {
            future2.cancel(true);
        }
    }

    public synchronized void done(T result, UaException e) {
        if (!this.canceled) {
            onDone(result, e);
            this.done = true;
        }
    }
}
