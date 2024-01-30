package com.ua.sdk;

import com.ua.sdk.Resource;

public interface CreateCallback<T extends Resource> {
    void onCreated(T t, UaException uaException);
}
