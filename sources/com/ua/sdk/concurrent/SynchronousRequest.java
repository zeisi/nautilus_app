package com.ua.sdk.concurrent;

import com.ua.sdk.Request;

public class SynchronousRequest implements Request {
    public static final SynchronousRequest INSTANCE = new SynchronousRequest();

    private SynchronousRequest() {
    }

    public boolean cancel() {
        return false;
    }

    public boolean isCanceled() {
        return false;
    }

    public boolean isAsynchronous() {
        return false;
    }
}
