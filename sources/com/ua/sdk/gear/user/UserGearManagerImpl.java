package com.ua.sdk.gear.user;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class UserGearManagerImpl extends AbstractManager<UserGear> implements UserGearManager {
    public UserGearManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<UserGear> diskCache, EntityService<UserGear> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public UserGear createEmptyUserGearObject() {
        return new UserGearImpl();
    }

    public EntityList<UserGear> fetchUserGear(EntityListRef<UserGear> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchUserGear(EntityListRef<UserGear> ref, FetchCallback<EntityList<UserGear>> callback) {
        return fetchPage(ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.Reference, com.ua.sdk.EntityRef<com.ua.sdk.gear.user.UserGear>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.gear.user.UserGear updateUserGear(com.ua.sdk.EntityRef<com.ua.sdk.gear.user.UserGear> r2, com.ua.sdk.gear.user.UserGear r3) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.patch(r3, r2)
            com.ua.sdk.gear.user.UserGear r0 = (com.ua.sdk.gear.user.UserGear) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.gear.user.UserGearManagerImpl.updateUserGear(com.ua.sdk.EntityRef, com.ua.sdk.gear.user.UserGear):com.ua.sdk.gear.user.UserGear");
    }

    public Request updateUserGear(EntityRef<UserGear> ref, UserGear userGear, CreateCallback<UserGear> callback) {
        return patch(userGear, ref, callback);
    }

    private UserGear setDefaultActivities(UserGear userGear) {
        List<EntityRef<ActivityType>> activities = new ArrayList<>();
        activities.add(new LinkEntityRef("9", "/v7.0/activity_type/9/"));
        activities.add(new LinkEntityRef("16", "/v7.0/activity_type/16/"));
        activities.add(new LinkEntityRef("22", "/v7.0/activity_type/22/"));
        activities.add(new LinkEntityRef("24", "/v7.0/activity_type/24/"));
        activities.add(new LinkEntityRef("25", "/v7.0/activity_type/25/"));
        userGear.setDefaultActivities(activities);
        return userGear;
    }

    public UserGear createUserGear(UserGear userGear) throws UaException {
        return (UserGear) create(setDefaultActivities(userGear));
    }

    public Request createUserGear(UserGear userGear, CreateCallback<UserGear> callback) {
        return create(setDefaultActivities(userGear), callback);
    }

    public void deleteUserGear(EntityRef<UserGear> ref) throws Exception {
        delete(ref);
    }

    public Request deleteUserGear(EntityRef<UserGear> ref, DeleteCallback callback) {
        return delete(ref, callback);
    }
}
