package com.ua.sdk.gear;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.gear.brand.GearBrand;
import com.ua.sdk.gear.brand.GearBrandManager;
import com.ua.sdk.gear.user.UserGear;
import com.ua.sdk.gear.user.UserGearManager;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.Precondition;
import java.util.concurrent.ExecutorService;

public class GearManagerImpl extends AbstractManager<Gear> implements GearManager {
    private final GearBrandManager gearBrandManager;
    private final UserGearManager userGearManager;

    public GearManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Gear> diskCache, EntityService<Gear> service, ExecutorService executor, GearBrandManager gearBrandManager2, UserGearManager userGearManager2) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.gearBrandManager = (GearBrandManager) Precondition.isNotNull(gearBrandManager2);
        this.userGearManager = (UserGearManager) Precondition.isNotNull(userGearManager2);
    }

    public EntityList<Gear> fetchGearFromBrand(EntityListRef<Gear> gearFromBrandListRef) throws UaException {
        return fetchPage(gearFromBrandListRef);
    }

    public Request fetchGearFromBrand(EntityListRef<Gear> gearFromBrandListRef, FetchCallback<EntityList<Gear>> callback) {
        return fetchPage(gearFromBrandListRef, callback);
    }

    public EntityList<GearBrand> fetchGearBrandList(EntityListRef<GearBrand> ref) throws UaException {
        return this.gearBrandManager.fetchGearBrandList(ref);
    }

    public Request fetchGearBrandList(EntityListRef<GearBrand> ref, FetchCallback<EntityList<GearBrand>> callback) {
        return this.gearBrandManager.fetchGearBrand(ref, callback);
    }

    public UserGear createEmptyUserGearObject() {
        return this.userGearManager.createEmptyUserGearObject();
    }

    public EntityList<UserGear> fetchUserGear(EntityListRef<UserGear> ref) throws UaException {
        return this.userGearManager.fetchUserGear(ref);
    }

    public Request fetchUserGear(EntityListRef<UserGear> ref, FetchCallback<EntityList<UserGear>> callback) {
        return this.userGearManager.fetchUserGear(ref, callback);
    }

    public UserGear updateUserGear(EntityRef<UserGear> ref, UserGear userGear) throws UaException {
        return this.userGearManager.updateUserGear(ref, userGear);
    }

    public Request updateUserGear(EntityRef<UserGear> ref, UserGear userGear, CreateCallback<UserGear> callback) {
        return this.userGearManager.updateUserGear(ref, userGear, callback);
    }

    public UserGear createUserGear(UserGear userGear) throws UaException {
        return this.userGearManager.createUserGear(userGear);
    }

    public Request createUserGear(UserGear userGear, CreateCallback<UserGear> callback) {
        return this.userGearManager.createUserGear(userGear, callback);
    }

    public void deleteUserGear(EntityRef<UserGear> ref) throws Exception {
        this.userGearManager.deleteUserGear(ref);
    }

    public Request deleteUserGear(EntityRef<UserGear> ref, DeleteCallback callback) {
        return this.userGearManager.deleteUserGear(ref, callback);
    }
}
