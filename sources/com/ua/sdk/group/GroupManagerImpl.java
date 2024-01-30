package com.ua.sdk.group;

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
import com.ua.sdk.concurrent.SaveRequest;
import com.ua.sdk.group.objective.GroupObjectiveImpl;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.Precondition;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class GroupManagerImpl extends AbstractManager<Group> implements GroupManager {
    public GroupManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Group> diskCache, EntityService<Group> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<Group> fetchGroupList(EntityListRef<Group> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchGroupList(EntityListRef<Group> ref, FetchCallback<EntityList<Group>> callback) {
        return fetchPage(ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.group.Group>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.group.Group fetchGroup(com.ua.sdk.EntityRef<com.ua.sdk.group.Group> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.group.Group r0 = (com.ua.sdk.group.Group) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.group.GroupManagerImpl.fetchGroup(com.ua.sdk.EntityRef):com.ua.sdk.group.Group");
    }

    public Request fetchGroup(EntityRef<Group> ref, FetchCallback<Group> callback) {
        return fetch((Reference) ref, callback);
    }

    public Group createGroup(Group group) throws UaException {
        return (Group) create(group);
    }

    public Request createGroup(Group group, CreateCallback<Group> callback) {
        return create(group, callback);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.group.Group>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.group.Group updateGroup(com.ua.sdk.group.Group r2, com.ua.sdk.EntityRef<com.ua.sdk.group.Group> r3) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.patch(r2, r3)
            com.ua.sdk.group.Group r0 = (com.ua.sdk.group.Group) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.group.GroupManagerImpl.updateGroup(com.ua.sdk.group.Group, com.ua.sdk.EntityRef):com.ua.sdk.group.Group");
    }

    public Request updateGroup(Group group, EntityRef<Group> ref, CreateCallback<Group> callback) {
        return patch(group, ref, callback);
    }

    public Group endGroupChallenge(Group group) throws UaException {
        Precondition.isNotNull(group);
        Precondition.isNotNull((GroupObjectiveImpl) group.getGroupObjective());
        ((GroupObjectiveImpl) group.getGroupObjective()).setEndDate(new Date());
        return (Group) update(group);
    }

    public Request endGroupChallenge(final Group group, SaveCallback<Group> callback) {
        final SaveRequest<Group> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(GroupManagerImpl.this.endGroupChallenge(group), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public Group endGroupChallenge(EntityRef<Group> ref) throws UaException {
        Precondition.isNotNull(ref);
        return endGroupChallenge(fetchGroup(ref));
    }

    public Request endGroupChallenge(final EntityRef<Group> ref, SaveCallback<Group> callback) {
        final SaveRequest<Group> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(GroupManagerImpl.this.endGroupChallenge((EntityRef<Group>) ref), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public void deleteGroup(EntityRef<Group> ref) throws UaException {
        delete(ref);
    }

    public Request deleteGroup(EntityRef<Group> ref, DeleteCallback<EntityRef<Group>> callback) {
        return delete(ref, callback);
    }
}
