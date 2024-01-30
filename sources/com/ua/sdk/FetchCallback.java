package com.ua.sdk;

import com.ua.sdk.Resource;

public interface FetchCallback<T extends Resource> {
    void onFetched(T t, UaException uaException);
}
