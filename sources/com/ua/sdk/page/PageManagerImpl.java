package com.ua.sdk.page;

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

public class PageManagerImpl extends AbstractManager<Page> {
    public PageManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Page> diskCache, EntityService<Page> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.Reference, com.ua.sdk.EntityRef<com.ua.sdk.page.Page>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.page.Page fetchPage(com.ua.sdk.EntityRef<com.ua.sdk.page.Page> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.page.Page r0 = (com.ua.sdk.page.Page) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.page.PageManagerImpl.fetchPage(com.ua.sdk.EntityRef):com.ua.sdk.page.Page");
    }

    public Request fetchPage(EntityRef<Page> ref, FetchCallback<Page> callback) {
        return fetch((Reference) ref, callback);
    }

    public EntityList<Page> fetchPageList(EntityListRef<Page> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchPageList(EntityListRef<Page> ref, FetchCallback<EntityList<Page>> callback) {
        return fetchPage(ref, callback);
    }
}
