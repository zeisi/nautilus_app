package com.ua.sdk.user.role;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.CreateRequest;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class UserRoleManagerImpl extends AbstractManager<UserRole> implements UserRoleManager {
    public UserRoleManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<UserRole> diskCache, EntityService<UserRole> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public UserRole fetchUserRole(EntityRef ref) throws UaException {
        return (UserRole) fetch(ref);
    }

    public Request fetchUserRole(final EntityRef ref, FetchCallback<UserRole> callback) {
        final FetchRequest<UserRole> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(UserRoleManagerImpl.this.fetchUserRole(ref), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public UserRole createUserRole(UserRole entity) throws UaException {
        return (UserRole) create(entity);
    }

    public Request createUserRole(final UserRole entity, CreateCallback<UserRole> callback) {
        final CreateRequest<UserRole> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(UserRoleManagerImpl.this.createUserRole(entity), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }
}
