package com.ua.sdk.cache.memory;

import com.ua.sdk.Reference;
import com.ua.sdk.Resource;
import com.ua.sdk.cache.Cache;
import java.util.WeakHashMap;

public class MemoryCache implements Cache {
    WeakHashMap<Reference, Resource> map = new WeakHashMap<>();

    public <R extends Reference> Resource<R> get(R ref) {
        return this.map.get(ref);
    }

    public void put(Resource entity) {
        if (entity != null) {
            this.map.put(entity.getRef(), entity);
        }
    }

    public boolean remove(Resource entity) {
        if (entity == null || this.map.remove(entity.getRef()) == null) {
            return false;
        }
        return true;
    }

    public boolean remove(Reference ref) {
        return this.map.remove(ref) != null;
    }

    public long getCacheAge(Reference ref) {
        return 0;
    }
}
