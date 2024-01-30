package com.ua.sdk.activitytype;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ActivityTypeManagerImpl extends AbstractManager<ActivityType> implements ActivityTypeManager {
    private final ActivityTypeDatabase activityTypeCache;

    public ActivityTypeManagerImpl(CacheSettings cacheSettings, Cache memCache, ActivityTypeDatabase diskCache, EntityService<ActivityType> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.activityTypeCache = diskCache;
    }

    public EntityList<ActivityType> fetchActivityTypeList() throws UaException {
        List result = new ArrayList();
        if (this.activityTypeCache.isAllActivityTypeCacheSet() && this.activityTypeCache.getCacheAge() < this.cacheSettings.getMaxCacheAge()) {
            return new AllActivityTypesList((List) this.activityTypeCache.getAllActivityTypes());
        }
        EntityListRef<ActivityType> ref = getRef();
        EntityList<ActivityType> list = fetchPage(ref);
        for (ActivityType at : list.getAll()) {
            result.add(at);
        }
        this.diskCache.updateAfterFetch(ref, list, false);
        while (list.hasNext()) {
            EntityListRef<ActivityType> ref2 = list.getNextPage();
            list = fetchPage(ref2);
            for (ActivityType at2 : list.getAll()) {
                result.add(at2);
            }
            this.diskCache.updateAfterFetch(ref2, list, false);
        }
        this.activityTypeCache.setAllActivityTypesCached(true);
        return new AllActivityTypesList(result);
    }

    public Request fetchActivityTypeList(FetchCallback<EntityList<ActivityType>> callback) {
        final FetchRequest<EntityList<ActivityType>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityTypeManagerImpl.this.fetchActivityTypeList(), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    private ActivityTypeListRef getRef() {
        return ActivityTypeListRef.getBuilder().build();
    }

    public ActivityType fetchActivityType(ActivityTypeRef ref) throws UaException {
        return (ActivityType) fetch(ref);
    }

    public Request fetchActivityType(ActivityTypeRef ref, FetchCallback<ActivityType> callback) {
        return fetch((Reference) ref, callback);
    }
}
