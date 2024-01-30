package com.ua.sdk.actigraphysettings;

import com.google.android.gms.fitness.FitnessActivities;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.CreateRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.remoteconnection.RemoteConnectionType;
import com.ua.sdk.remoteconnection.RemoteConnectionTypeManager;
import com.ua.sdk.remoteconnection.RemoteConnectionTypeRef;
import com.ua.sdk.user.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ActigraphySettingsManagerImpl extends AbstractManager<ActigraphySettings> implements ActigraphySettingsManager {
    private RemoteConnectionTypeManager remoteConnectionTypeManager;
    private UserManager userManager;

    public ActigraphySettingsManagerImpl(UserManager userManager2, CacheSettings cacheSettings, Cache memCache, DiskCache<ActigraphySettings> diskCache, EntityService<ActigraphySettings> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.userManager = userManager2;
    }

    public void setRemoteConnectionTypeManager(RemoteConnectionTypeManager remoteConnectionTypeManager2) {
        this.remoteConnectionTypeManager = remoteConnectionTypeManager2;
    }

    public ActigraphySettings fetchActigraphySettings() throws UaException {
        return (ActigraphySettings) fetch(this.userManager.getCurrentUserRef());
    }

    public Request fetchActigraphySettings(FetchCallback<ActigraphySettings> callback) {
        return fetch((Reference) this.userManager.getCurrentUserRef(), callback);
    }

    public ActigraphySettings setSleepRecorderPriority(EntityRef<RemoteConnectionType> ref) throws UaException {
        if (!(ref instanceof RemoteConnectionTypeRef) || ((RemoteConnectionTypeRef) ref).getRecorderTypeKey() == null || ((RemoteConnectionTypeRef) ref).getRecorderTypeKey().equals("")) {
            return (ActigraphySettings) create(buildPrioritySetting(FitnessActivities.SLEEP, this.remoteConnectionTypeManager.fetchRemoteConnectionType(ref)));
        }
        return (ActigraphySettings) create(buildPrioritySetting(FitnessActivities.SLEEP, (RemoteConnectionTypeRef) ref));
    }

    public Request setSleepRecorderPriority(final EntityRef<RemoteConnectionType> ref, CreateCallback<ActigraphySettings> callback) {
        final CreateRequest<ActigraphySettings> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActigraphySettingsManagerImpl.this.setSleepRecorderPriority(ref), (UaException) null);
                } catch (UaException e) {
                    UaLog.error("Failed to set sleep priority");
                }
            }
        }));
        return request;
    }

    public ActigraphySettings setActivityRecorderPriority(EntityRef<RemoteConnectionType> ref) throws UaException {
        if (!(ref instanceof RemoteConnectionTypeRef) || ((RemoteConnectionTypeRef) ref).getRecorderTypeKey() == null || ((RemoteConnectionTypeRef) ref).getRecorderTypeKey().equals("")) {
            return (ActigraphySettings) create(buildPrioritySetting("activity", this.remoteConnectionTypeManager.fetchRemoteConnectionType(ref)));
        }
        return (ActigraphySettings) create(buildPrioritySetting("activity", (RemoteConnectionTypeRef) ref));
    }

    public Request setActivityRecorderPriority(final EntityRef<RemoteConnectionType> ref, CreateCallback<ActigraphySettings> callback) {
        final CreateRequest<ActigraphySettings> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActigraphySettingsManagerImpl.this.setActivityRecorderPriority(ref), (UaException) null);
                } catch (UaException e) {
                    UaLog.error("Failed to set activity priority");
                }
            }
        }));
        return request;
    }

    private ActigraphySettings buildPrioritySetting(String type, RemoteConnectionType connectionType) {
        ActigraphySettingsImpl settings = new ActigraphySettingsImpl();
        List<String> priorities = new ArrayList<>();
        if (type.equals(FitnessActivities.SLEEP)) {
            priorities.add(connectionType.getRecorderTypeKey());
            settings.setSleepPriority(priorities);
        } else {
            priorities.add(connectionType.getRecorderTypeKey());
            settings.setActivityPriority(priorities);
        }
        return settings;
    }

    private ActigraphySettings buildPrioritySetting(String type, RemoteConnectionTypeRef ref) {
        ActigraphySettingsImpl settings = new ActigraphySettingsImpl();
        List<String> priorities = new ArrayList<>();
        if (type.equals(FitnessActivities.SLEEP)) {
            priorities.add(ref.getRecorderTypeKey());
            settings.setSleepPriority(priorities);
        } else {
            priorities.add(ref.getRecorderTypeKey());
            settings.setActivityPriority(priorities);
        }
        return settings;
    }
}
