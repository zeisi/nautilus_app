package com.ua.sdk.role;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.role.Role;
import java.util.concurrent.ExecutorService;

public class RoleManagerImpl extends AbstractManager<Role> implements RoleManager {
    public RoleManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Role> diskCache, EntityService<Role> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public Role fetchRole(Role.Type name) {
        return RoleHelper.getRole(name);
    }

    public EntityList<Role> fetchRoleList() throws UaException {
        return this.service.fetchPage((EntityListRef) null);
    }

    public Request fetchRoleList(FetchCallback<EntityList<Role>> callback) {
        final FetchRequest<EntityList<Role>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(RoleManagerImpl.this.fetchRoleList(), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }
}
