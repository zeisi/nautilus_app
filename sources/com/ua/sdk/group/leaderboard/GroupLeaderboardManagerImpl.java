package com.ua.sdk.group.leaderboard;

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

public class GroupLeaderboardManagerImpl extends AbstractManager<GroupLeaderboard> implements GroupLeaderboardManager {
    public GroupLeaderboardManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<GroupLeaderboard> diskCache, EntityService<GroupLeaderboard> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<GroupLeaderboard> fetchGroupLeaderboardList(EntityListRef<GroupLeaderboard> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchGroupLeaderboardList(EntityListRef<GroupLeaderboard> ref, FetchCallback<EntityList<GroupLeaderboard>> callback) {
        return fetchPage(ref, callback);
    }
}
