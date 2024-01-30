package com.ua.sdk.concurrent;

import com.ua.sdk.DeleteCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;

public class DeleteRequest<T extends Reference> extends AsyncRequest<T> {
    private final DeleteCallback<T> callback;

    public DeleteRequest(DeleteCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(T ref, UaException e) {
        EntityEventHandler.callOnDeleted(ref, e, this.callback);
    }
}
