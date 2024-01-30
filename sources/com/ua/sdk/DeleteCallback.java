package com.ua.sdk;

import com.ua.sdk.Reference;

public interface DeleteCallback<R extends Reference> {
    void onDeleted(R r, UaException uaException);
}
