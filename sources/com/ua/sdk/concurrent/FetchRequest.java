package com.ua.sdk.concurrent;

import com.ua.sdk.FetchCallback;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;

public class FetchRequest<T extends Resource> extends AsyncRequest<T> {
    private final FetchCallback<T> callback;

    public FetchRequest(FetchCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(T entity, UaException e) {
        EntityEventHandler.callOnFetched(entity, e, this.callback);
    }
}
