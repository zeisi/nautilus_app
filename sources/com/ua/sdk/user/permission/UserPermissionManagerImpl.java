package com.ua.sdk.user.permission;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class UserPermissionManagerImpl extends AbstractManager<UserPermission> implements UserPermissionManager {
    private final UserPermissionService service;

    public UserPermissionManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<UserPermission> diskCache, UserPermissionService service2, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, (EntityService) null, executor);
        this.service = service2;
    }

    public EntityList<UserPermission> fetchUserPermission(EntityRef ref) throws UaException {
        return this.service.fetchUserPermission(ref);
    }

    public Request fetchUserPermission(final EntityRef ref, FetchCallback<EntityList<UserPermission>> callback) {
        final FetchRequest<EntityList<UserPermission>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(UserPermissionManagerImpl.this.fetchUserPermission(ref), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public EntityList<UserPermission> fetchUserPermissionList() throws UaException {
        return this.service.fetchUserPermissions();
    }

    public Request fetchUserPermissionList(FetchCallback<EntityList<UserPermission>> callback) {
        final FetchRequest<EntityList<UserPermission>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(UserPermissionManagerImpl.this.fetchUserPermissionList(), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }
}
