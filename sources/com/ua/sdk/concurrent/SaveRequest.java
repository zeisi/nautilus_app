package com.ua.sdk.concurrent;

import com.ua.sdk.Resource;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;

public class SaveRequest<T extends Resource> extends AsyncRequest<T> {
    private final SaveCallback<T> callback;

    public SaveRequest(SaveCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(T entity, UaException e) {
        EntityEventHandler.callOnSaved(entity, e, this.callback);
    }
}
