package com.ua.sdk.concurrent;

import com.ua.sdk.MultipleCreateCallback;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;
import java.util.List;

public class MultipleCreateRequest<T extends Resource> extends AsyncRequest<List<T>> {
    private final MultipleCreateCallback<T> callback;

    public MultipleCreateRequest(MultipleCreateCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(List<T> result, UaException e) {
        EntityEventHandler.callOnMultipleCreated(result, e, this.callback);
    }
}
