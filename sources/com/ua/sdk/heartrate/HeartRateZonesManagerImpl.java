package com.ua.sdk.heartrate;

import com.ua.sdk.CreateCallback;
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

public class HeartRateZonesManagerImpl extends AbstractManager<HeartRateZones> implements HeartRateZonesManager {
    private static final int NUM_HR_ZONES = 5;

    public HeartRateZonesManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<HeartRateZones> diskCache, EntityService<HeartRateZones> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<HeartRateZones> fetchHeartRateZonesList(EntityListRef<HeartRateZones> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchHeartRateZonesList(EntityListRef<HeartRateZones> ref, FetchCallback<EntityList<HeartRateZones>> callback) {
        return fetchPage(ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.Reference, com.ua.sdk.EntityRef<com.ua.sdk.heartrate.HeartRateZones>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.heartrate.HeartRateZones fetchHeartRateZones(com.ua.sdk.EntityRef<com.ua.sdk.heartrate.HeartRateZones> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.heartrate.HeartRateZones r0 = (com.ua.sdk.heartrate.HeartRateZones) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.heartrate.HeartRateZonesManagerImpl.fetchHeartRateZones(com.ua.sdk.EntityRef):com.ua.sdk.heartrate.HeartRateZones");
    }

    public Request fetchHeartRateZones(EntityRef<HeartRateZones> ref, FetchCallback<HeartRateZones> callback) {
        return fetch((Reference) ref, callback);
    }

    public HeartRateZones createHeartRateZones(HeartRateZones entity) throws UaException {
        return (HeartRateZones) create(entity);
    }

    public Request createHeartRateZones(HeartRateZones entity, CreateCallback<HeartRateZones> callback) {
        return create(entity, callback);
    }

    public HeartRateZones calculateHeartRateZonesWithAge(int age) {
        return calculateHeartRateZonesWithMaxHeartRate((int) (208.0d - (0.7d * ((double) age))));
    }

    public HeartRateZones calculateHeartRateZonesWithMaxHeartRate(int maxHR) {
        HeartRateZone[] zones = new HeartRateZone[5];
        zones[0] = new HeartRateZone("zone1", 0, (int) (((double) maxHR) * 0.6d));
        zones[1] = new HeartRateZone("zone2", zones[0].end + 1, (int) (((double) maxHR) * 0.7d));
        zones[2] = new HeartRateZone("zone3", zones[1].end + 1, (int) (((double) maxHR) * 0.8d));
        zones[3] = new HeartRateZone("zone4", zones[2].end + 1, (int) (((double) maxHR) * 0.9d));
        zones[4] = new HeartRateZone("zone5", zones[3].end + 1, maxHR);
        return new HeartRateZonesImpl(zones);
    }
}
