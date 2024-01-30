package com.ua.sdk.bodymass;

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

public class BodyMassManagerImpl extends AbstractManager<BodyMass> implements BodyMassManager {
    public BodyMassManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<BodyMass> diskCache, EntityService<BodyMass> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<BodyMass> fetchBodyMassList(EntityListRef<BodyMass> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchBodyMassList(EntityListRef<BodyMass> ref, FetchCallback<EntityList<BodyMass>> callback) {
        return fetchPage(ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.bodymass.BodyMass>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.bodymass.BodyMass fetchBodyMass(com.ua.sdk.EntityRef<com.ua.sdk.bodymass.BodyMass> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.bodymass.BodyMass r0 = (com.ua.sdk.bodymass.BodyMass) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.bodymass.BodyMassManagerImpl.fetchBodyMass(com.ua.sdk.EntityRef):com.ua.sdk.bodymass.BodyMass");
    }

    public Request fetchBodyMass(EntityRef<BodyMass> ref, FetchCallback<BodyMass> callback) {
        return fetch((Reference) ref, callback);
    }

    public BodyMass updateBodyMass(BodyMass entity) throws UaException {
        return (BodyMass) update(entity);
    }

    public Request updateBodyMass(BodyMass entity, SaveCallback<BodyMass> callback) {
        return update(entity, callback);
    }
}
