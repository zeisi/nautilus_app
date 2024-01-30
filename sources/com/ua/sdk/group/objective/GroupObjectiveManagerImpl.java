package com.ua.sdk.group.objective;

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
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import java.util.concurrent.ExecutorService;

public class GroupObjectiveManagerImpl extends AbstractManager<GroupObjective> implements GroupObjectiveManager {
    public GroupObjectiveManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<GroupObjective> diskCache, EntityService<GroupObjective> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<GroupObjective> fetchGroupObjectiveList(EntityListRef<GroupObjective> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchGroupObjectiveList(EntityListRef<GroupObjective> ref, FetchCallback<EntityList<GroupObjective>> callback) {
        return fetchPage(ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.group.objective.GroupObjective>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.group.objective.GroupObjective fetchGroupObjective(com.ua.sdk.EntityRef<com.ua.sdk.group.objective.GroupObjective> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.group.objective.GroupObjective r0 = (com.ua.sdk.group.objective.GroupObjective) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.group.objective.GroupObjectiveManagerImpl.fetchGroupObjective(com.ua.sdk.EntityRef):com.ua.sdk.group.objective.GroupObjective");
    }

    public Request fetchGroupObjective(EntityRef<GroupObjective> ref, FetchCallback<GroupObjective> callback) {
        return fetch((Reference) ref, callback);
    }

    public GroupObjective createGroupObjective(GroupObjective entity) throws UaException {
        return (GroupObjective) create(entity);
    }

    public Request createGroupObjective(GroupObjective entity, CreateCallback<GroupObjective> callback) {
        return create(entity, callback);
    }

    public GroupObjective updateGroupObjective(GroupObjective entity) throws UaException {
        return (GroupObjective) update(entity);
    }

    public Request updateGroupObjective(GroupObjective entity, SaveCallback<GroupObjective> callback) {
        return update(entity, callback);
    }

    public EntityRef<GroupObjective> deleteGroupObjective(EntityRef<GroupObjective> ref) throws UaException {
        return (EntityRef) delete(ref);
    }

    public Request deleteGroupObjective(EntityRef<GroupObjective> ref, DeleteCallback<EntityRef<GroupObjective>> callback) {
        return delete(ref, callback);
    }
}
