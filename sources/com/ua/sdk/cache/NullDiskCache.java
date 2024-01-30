package com.ua.sdk.cache;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.Resource;
import com.ua.sdk.cache.DiskCache;

public class NullDiskCache<T extends Resource> implements DiskCache<T> {
    public T get(Reference ref) {
        return null;
    }

    public void updateAfterFetch(T t) {
    }

    public void updateAfterFetch(EntityListRef<T> entityListRef, EntityList<T> entityList, boolean partial) {
    }

    public EntityList<T> getList(Reference ref) {
        return null;
    }

    public long putForCreate(T t) {
        return 0;
    }

    public void updateAfterCreate(long localId, T t) {
    }

    public void putForSave(T t) {
    }

    public void updateAfterSave(T t) {
    }

    public void markForDelete(Reference ref) {
    }

    public void delete(Reference ref) {
    }

    public void deleteList(EntityListRef<T> entityListRef) {
    }

    public long getCacheAge(Reference ref) {
        return 0;
    }

    public long getLastSynced(Reference ref) {
        return 0;
    }

    public DiskCache.State getState(Reference ref) {
        return null;
    }
}
