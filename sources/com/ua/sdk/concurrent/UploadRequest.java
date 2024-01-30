package com.ua.sdk.concurrent;

import com.ua.sdk.Resource;
import com.ua.sdk.UaException;
import com.ua.sdk.UploadCallback;

public class UploadRequest<T extends Resource> extends AsyncRequest<T> {
    private final UploadCallback callback;

    public UploadRequest(UploadCallback callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(T result, UaException e) {
        EntityEventHandler.callOnUploadUploaded(result, e, this.callback);
    }
}
