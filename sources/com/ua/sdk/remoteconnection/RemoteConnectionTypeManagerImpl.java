package com.ua.sdk.remoteconnection;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.actigraphysettings.ActigraphySettings;
import com.ua.sdk.actigraphysettings.ActigraphySettingsManager;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class RemoteConnectionTypeManagerImpl extends AbstractManager<RemoteConnectionType> implements RemoteConnectionTypeManager {
    private ActigraphySettingsManager actigraphySettingsManager;
    private RemoteConnectionTypePageImpl mRemoteConnectionTypePage;

    public RemoteConnectionTypeManagerImpl(ActigraphySettingsManager actigraphySettingsManager2, CacheSettings cacheSettings, Cache memCache, DiskCache<RemoteConnectionType> diskCache, EntityService<RemoteConnectionType> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.actigraphySettingsManager = actigraphySettingsManager2;
    }

    public EntityList<RemoteConnectionType> fetchRemoteConnectionTypeList() throws UaException {
        return fetchPage((EntityListRef) null);
    }

    public Request fetchRemoteConnectionTypeList(FetchCallback<EntityList<RemoteConnectionType>> callback) {
        return fetchPage((EntityListRef) null, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.Reference, com.ua.sdk.EntityRef<com.ua.sdk.remoteconnection.RemoteConnectionType>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.remoteconnection.RemoteConnectionType fetchRemoteConnectionType(com.ua.sdk.EntityRef<com.ua.sdk.remoteconnection.RemoteConnectionType> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.remoteconnection.RemoteConnectionType r0 = (com.ua.sdk.remoteconnection.RemoteConnectionType) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.remoteconnection.RemoteConnectionTypeManagerImpl.fetchRemoteConnectionType(com.ua.sdk.EntityRef):com.ua.sdk.remoteconnection.RemoteConnectionType");
    }

    public Request fetchRemoteConnectionType(EntityRef<RemoteConnectionType> ref, FetchCallback<RemoteConnectionType> callback) {
        return fetch((Reference) ref, callback);
    }

    public ActigraphySettings fetchRemoteConnectionPriorities() throws UaException {
        return this.actigraphySettingsManager.fetchActigraphySettings();
    }

    public Request fetchRemoteConnectionPriorities(FetchCallback<ActigraphySettings> callback) {
        return this.actigraphySettingsManager.fetchActigraphySettings(callback);
    }

    public void updateSleepConnectionPriorities(EntityRef<RemoteConnectionType> ref) throws UaException {
        this.actigraphySettingsManager.setSleepRecorderPriority(ref);
    }

    public Request updateSleepConnectionPriorities(EntityRef<RemoteConnectionType> ref, CreateCallback<ActigraphySettings> callback) {
        return this.actigraphySettingsManager.setSleepRecorderPriority(ref, callback);
    }

    public void updateActivityConnectionPriorities(EntityRef<RemoteConnectionType> ref) throws UaException {
        this.actigraphySettingsManager.setActivityRecorderPriority(ref);
    }

    public Request updateActivityConnectionPriorities(EntityRef<RemoteConnectionType> ref, CreateCallback<ActigraphySettings> callback) {
        return this.actigraphySettingsManager.setActivityRecorderPriority(ref, callback);
    }
}
