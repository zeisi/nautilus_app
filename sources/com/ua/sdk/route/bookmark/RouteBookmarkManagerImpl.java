package com.ua.sdk.route.bookmark;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.route.RouteBookmark;
import com.ua.sdk.route.RouteBookmarkManager;
import java.util.concurrent.ExecutorService;

public class RouteBookmarkManagerImpl extends AbstractManager<RouteBookmark> implements RouteBookmarkManager {
    public RouteBookmarkManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<RouteBookmark> diskCache, EntityService<RouteBookmark> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<RouteBookmark> fetchRouteBookmarkList(EntityListRef<RouteBookmark> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchRouteBookmarkList(EntityListRef<RouteBookmark> ref, FetchCallback<EntityList<RouteBookmark>> callback) {
        return fetchPage(ref, callback);
    }

    public RouteBookmark createRouteBookmark(RouteBookmark entity) throws UaException {
        return (RouteBookmark) create(entity);
    }

    public Request createRouteBookmark(RouteBookmark entity, CreateCallback<RouteBookmark> callback) {
        return create(entity, callback);
    }

    public void deleteRouteBookmark(EntityRef<RouteBookmark> ref) throws UaException {
        delete(ref);
    }

    public Request deleteRouteBookmark(EntityRef<RouteBookmark> ref, DeleteCallback<EntityRef<RouteBookmark>> callback) {
        return delete(ref, callback);
    }
}
