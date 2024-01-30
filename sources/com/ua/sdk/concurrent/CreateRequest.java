package com.ua.sdk.concurrent;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;

public class CreateRequest<T extends Resource> extends AsyncRequest<T> {
    private final CreateCallback<T> callback;

    public CreateRequest(CreateCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(T entity, UaException e) {
        EntityEventHandler.callOnCreated(entity, e, this.callback);
    }
}
