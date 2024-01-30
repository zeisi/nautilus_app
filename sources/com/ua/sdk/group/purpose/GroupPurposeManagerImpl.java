package com.ua.sdk.group.purpose;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class GroupPurposeManagerImpl extends AbstractManager<GroupPurpose> implements GroupPurposeManager {
    public GroupPurposeManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<GroupPurpose> diskCache, EntityService<GroupPurpose> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<GroupPurpose> fetchGroupPurposeList() throws UaException {
        return fetchPage((EntityListRef) null);
    }

    public Request fetchGroupPurposeList(FetchCallback<EntityList<GroupPurpose>> callback) {
        return fetchPage((EntityListRef) null, callback);
    }
}
