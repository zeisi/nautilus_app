package com.ua.sdk;

import com.ua.sdk.Resource;

public interface SaveCallback<T extends Resource> {
    void onSaved(T t, UaException uaException);
}
