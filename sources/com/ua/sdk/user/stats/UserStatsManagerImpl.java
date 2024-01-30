package com.ua.sdk.user.stats;

import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class UserStatsManagerImpl extends AbstractManager<UserStats> implements UserStatsManager {
    public UserStatsManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<UserStats> diskCache, EntityService<UserStats> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public UserStats fetchUserStats(UserStatsRef userStatsRef) throws UaException {
        return (UserStats) fetch(userStatsRef);
    }

    public Request fetchUserStats(UserStatsRef userStatsRef, FetchCallback<UserStats> callback) {
        return fetch((Reference) userStatsRef, callback);
    }
}
