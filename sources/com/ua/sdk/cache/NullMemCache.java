package com.ua.sdk.cache;

import com.ua.sdk.Reference;
import com.ua.sdk.Resource;

public class NullMemCache implements Cache {
    public <R extends Reference> Resource<R> get(R r) {
        return null;
    }

    public void put(Resource entity) {
    }

    public boolean remove(Resource entity) {
        return false;
    }

    public boolean remove(Reference ref) {
        return false;
    }

    public long getCacheAge(Reference ref) {
        return 0;
    }
}
