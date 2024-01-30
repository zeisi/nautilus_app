package com.ua.sdk.cache;

import com.ua.sdk.Reference;
import com.ua.sdk.Resource;

public interface Cache {
    <R extends Reference> Resource<R> get(R r);

    long getCacheAge(Reference reference);

    void put(Resource resource);

    boolean remove(Reference reference);

    boolean remove(Resource resource);
}
