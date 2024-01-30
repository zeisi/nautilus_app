package com.ua.sdk.route;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.Link;
import com.ua.sdk.route.bookmark.RouteBookmarkImpl;
import com.ua.sdk.user.User;
import com.ua.sdk.user.UserManager;
import java.util.concurrent.ExecutorService;

public class RouteManagerImpl extends AbstractManager<Route> implements RouteManager {
    private final RouteBookmarkManager routeBookmarkManager;
    private final UserManager userManager;

    public RouteManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Route> diskCache, EntityService<Route> service, ExecutorService executor, RouteBookmarkManager routeBookmarkManager2, UserManager userManager2) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.routeBookmarkManager = routeBookmarkManager2;
        this.userManager = userManager2;
    }

    public RouteBuilder getRouteBuilder() {
        return new RouteBuilderImpl();
    }

    public Route fetchRoute(RouteRef ref) throws UaException {
        return (Route) fetch(ref);
    }

    public Request fetchRoute(RouteRef ref, FetchCallback<Route> callback) {
        return fetch((Reference) ref, callback);
    }

    public void createRoute(Route route) throws UaException {
        create(route);
    }

    public Request createRoute(Route route, CreateCallback<Route> callback) {
        return create(route, callback);
    }

    public EntityList<Route> fetchRouteList(EntityListRef<Route> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchRouteList(EntityListRef<Route> ref, FetchCallback<EntityList<Route>> callback) {
        return fetchPage(ref, callback);
    }

    public void updateRoute(Route route) throws UaException {
        update(route);
    }

    public Request updateRoute(Route route, SaveCallback<Route> callback) {
        return update(route, callback);
    }

    public void deleteRoute(RouteRef ref) throws UaException {
        delete(ref);
    }

    public Request deleteRoute(RouteRef ref, DeleteCallback<RouteRef> callback) {
        return delete(ref, callback);
    }

    public EntityList<RouteBookmark> fetchRouteBookmarkList(EntityListRef<RouteBookmark> ref) throws UaException {
        return this.routeBookmarkManager.fetchRouteBookmarkList(ref);
    }

    public Request fetchRouteBookmarkList(EntityListRef<RouteBookmark> ref, FetchCallback<EntityList<RouteBookmark>> callback) {
        return this.routeBookmarkManager.fetchRouteBookmarkList(ref, callback);
    }

    public EntityList<Route> fetchBookmarkedRoutes(EntityListRef<RouteBookmark> ref) throws UaException {
        EntityList<RouteBookmark> bookmarks = this.routeBookmarkManager.fetchRouteBookmarkList(ref);
        RouteList routes = new RouteList();
        for (RouteBookmark bookmark : bookmarks.getAll()) {
            routes.add(fetchRoute(RouteRef.getBuilder().setId(bookmark.getRoute().getId()).build()));
        }
        return routes;
    }

    public Request fetchBookmarkedRoutes(final EntityListRef<RouteBookmark> ref, FetchCallback<EntityList<Route>> callback) {
        final FetchRequest<EntityList<Route>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                UaException error = null;
                EntityList<Route> result = null;
                try {
                    result = RouteManagerImpl.this.fetchBookmarkedRoutes(ref);
                } catch (UaException e) {
                    UaLog.error("Failed to fetch routes");
                    error = e;
                } catch (Throwable e2) {
                    UaLog.error("Failed to fetch routes");
                    error = new UaException(e2);
                }
                request.done(result, error);
            }
        }));
        return request;
    }

    public RouteBookmark createRouteBookmark(Route route) throws UaException {
        return this.routeBookmarkManager.createRouteBookmark(buildRouteBookmark(route));
    }

    public Request createRouteBookmark(Route route, CreateCallback<RouteBookmark> callback) {
        return this.routeBookmarkManager.createRouteBookmark(buildRouteBookmark(route), callback);
    }

    public void deleteRouteBookmark(EntityRef<RouteBookmark> ref) throws UaException {
        this.routeBookmarkManager.deleteRouteBookmark(ref);
    }

    public Request deleteRouteBookmark(EntityRef<RouteBookmark> ref, DeleteCallback<EntityRef<RouteBookmark>> callback) {
        return this.routeBookmarkManager.deleteRouteBookmark(ref, callback);
    }

    private RouteBookmark buildRouteBookmark(Route route) {
        RouteBookmarkImpl bookmark = new RouteBookmarkImpl();
        EntityRef<User> userRef = this.userManager.getCurrentUserRef();
        EntityRef<Route> routeRef = RouteRef.getBuilder().setId(route.getRef().getId()).build();
        bookmark.setRoute(new Link(routeRef.getHref(), routeRef.getId()));
        bookmark.setUser(new Link(userRef.getHref(), userRef.getId()));
        return bookmark;
    }
}
