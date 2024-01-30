package com.ua.sdk.remoteconnection;

import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
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

public class RemoteConnectionManagerImpl extends AbstractManager<RemoteConnection> implements RemoteConnectionManager {
    public RemoteConnectionManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<RemoteConnection> diskCache, EntityService<RemoteConnection> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<RemoteConnection> fetchRemoteConnectionList() throws UaException {
        return fetchPage((EntityListRef) null);
    }

    public Request fetchRemoteConnectionList(FetchCallback<EntityList<RemoteConnection>> callback) {
        return fetchPage((EntityListRef) null, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.remoteconnection.RemoteConnection>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.remoteconnection.RemoteConnection fetchRemoteConnection(com.ua.sdk.EntityRef<com.ua.sdk.remoteconnection.RemoteConnection> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.remoteconnection.RemoteConnection r0 = (com.ua.sdk.remoteconnection.RemoteConnection) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.remoteconnection.RemoteConnectionManagerImpl.fetchRemoteConnection(com.ua.sdk.EntityRef):com.ua.sdk.remoteconnection.RemoteConnection");
    }

    public Request fetchRemoteConnection(EntityRef<RemoteConnection> ref, FetchCallback<RemoteConnection> callback) {
        return fetch((Reference) ref, callback);
    }

    public void deleteRemoteConnection(EntityRef<RemoteConnection> ref) throws UaException {
        delete(ref);
    }

    public Request deleteRemoteConnection(EntityRef<RemoteConnection> ref, DeleteCallback<RemoteConnectionRef> callback) {
        return delete(ref, callback);
    }
}
