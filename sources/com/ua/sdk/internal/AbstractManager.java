package com.ua.sdk.internal;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.Resource;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CachePolicy;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.cache.NullDiskCache;
import com.ua.sdk.cache.NullMemCache;
import com.ua.sdk.concurrent.CreateRequest;
import com.ua.sdk.concurrent.DeleteRequest;
import com.ua.sdk.concurrent.EntityEventHandler;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.concurrent.SaveRequest;
import com.ua.sdk.concurrent.SynchronousRequest;
import java.util.concurrent.ExecutorService;

public abstract class AbstractManager<T extends Resource> {
    protected final CacheSettings cacheSettings;
    protected final DiskCache<T> diskCache;
    protected final ExecutorService executor;
    protected final Cache memCache;
    protected final EntityService<T> service;

    protected AbstractManager(CacheSettings cacheSettings2, Cache memCache2, DiskCache<T> diskCache2, EntityService<T> service2, ExecutorService executor2) {
        this.cacheSettings = (CacheSettings) Precondition.isNotNull(cacheSettings2);
        if (diskCache2 == null) {
            this.diskCache = new NullDiskCache();
        } else {
            this.diskCache = diskCache2;
        }
        if (memCache2 == null) {
            this.memCache = new NullMemCache();
        } else {
            this.memCache = memCache2;
        }
        this.executor = executor2;
        this.service = service2;
    }

    public T create(T entity) throws UaException {
        Precondition.isNotNull(entity);
        long localId = this.diskCache.putForCreate(entity);
        T createdEntity = onPostServiceCreate(this.service.create(entity));
        this.diskCache.updateAfterCreate(localId, createdEntity);
        this.memCache.put(createdEntity);
        return createdEntity;
    }

    public Request create(final T entity, CreateCallback<T> callback) {
        final CreateRequest<T> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(AbstractManager.this.create(entity), (UaException) null);
                } catch (UaException e) {
                    request.done(entity, e);
                }
            }
        }));
        return request;
    }

    /* access modifiers changed from: protected */
    public T onPostServiceCreate(T createdEntity) throws UaException {
        return createdEntity;
    }

    public T fetch(Reference ref) throws UaException {
        return fetch(ref, this.cacheSettings.getDefaultCachePolicy());
    }

    /* access modifiers changed from: protected */
    public T fetch(Reference ref, CachePolicy cachePolicy) throws UaException {
        return fetch(ref, cachePolicy, true);
    }

    /* access modifiers changed from: protected */
    public T fetch(Reference ref, CachePolicy cachePolicy, boolean includeMemCache) throws UaException {
        if (ref == null) {
            throw new UaException("ref can't be null");
        }
        if (cachePolicy == null) {
            cachePolicy = this.cacheSettings.getDefaultCachePolicy();
        }
        T entity = null;
        if (cachePolicy.checkCacheFirst()) {
            if (includeMemCache) {
                long ageInCache = this.memCache.getCacheAge(ref);
                if ((cachePolicy.ignoreAge() || (ageInCache >= 0 && ageInCache <= this.cacheSettings.getMaxCacheAge())) && (entity = this.memCache.get(ref)) != null) {
                    return entity;
                }
            }
            long ageInCache2 = this.diskCache.getCacheAge(ref);
            if ((cachePolicy.ignoreAge() || (ageInCache2 >= 0 && ageInCache2 <= this.cacheSettings.getMaxCacheAge())) && (entity = this.diskCache.get(ref)) != null) {
                this.memCache.put(entity);
                return entity;
            }
        }
        if (cachePolicy.checkNetwork()) {
            try {
                entity = onPostServiceFetch(ref, this.service.fetch(ref));
                if (entity != null) {
                    this.diskCache.updateAfterFetch(entity);
                    this.memCache.put(entity);
                }
            } catch (UaException e) {
                if (cachePolicy.checkNetworkFirst() && cachePolicy.checkCache()) {
                    return fetch(ref, CachePolicy.CACHE_ONLY, includeMemCache);
                }
                throw e;
            }
        }
        return entity;
    }

    /* access modifiers changed from: protected */
    public Request fetch(Reference ref, FetchCallback<T> callback) {
        return fetch(ref, this.cacheSettings.getDefaultCachePolicy(), callback);
    }

    /* access modifiers changed from: protected */
    public Request fetch(final Reference ref, CachePolicy cachePolicy, FetchCallback<T> callback) {
        final CachePolicy policy;
        T entity;
        if (cachePolicy != null) {
            policy = cachePolicy;
        } else {
            policy = this.cacheSettings.getDefaultCachePolicy();
        }
        if (policy.checkCacheFirst()) {
            long ageInCache = this.memCache.getCacheAge(ref);
            if ((policy.ignoreAge() || (ageInCache >= 0 && ageInCache <= this.cacheSettings.getMaxCacheAge())) && (entity = this.memCache.get(ref)) != null) {
                EntityEventHandler.callOnFetched(entity, (UaException) null, callback);
                return SynchronousRequest.INSTANCE;
            }
        }
        final FetchRequest<T> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(AbstractManager.this.fetch(ref, policy, !policy.checkCacheFirst()), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    /* access modifiers changed from: protected */
    public T onPostServiceFetch(Reference ref, T entity) throws UaException {
        return entity;
    }

    /* access modifiers changed from: protected */
    public EntityList<T> onPostServiceFetchPage(Reference ref, EntityList<T> list) throws UaException {
        return list;
    }

    public T update(T entity) throws UaException {
        T savedEntity = null;
        T fallbackEntity = this.diskCache.get(entity.getRef());
        try {
            this.memCache.put(entity);
            this.diskCache.putForSave(entity);
            savedEntity = onPostServiceSave(this.service.save(entity));
            this.diskCache.updateAfterSave(savedEntity);
            this.memCache.remove((Resource) entity);
            this.memCache.put(savedEntity);
            return savedEntity;
        } catch (UaException e) {
            switch (e.getCode()) {
                case NOT_CONNECTED:
                    break;
                default:
                    if (savedEntity == null) {
                        this.diskCache.updateAfterSave(fallbackEntity);
                        this.memCache.remove((Resource) entity);
                        this.memCache.put(fallbackEntity);
                        break;
                    }
                    break;
            }
            throw e;
        }
    }

    /* access modifiers changed from: protected */
    public T onPostServiceSave(T entity) throws UaException {
        return entity;
    }

    public Request update(final T entity, SaveCallback<T> callback) {
        final SaveRequest<T> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(AbstractManager.this.update(entity), (UaException) null);
                } catch (UaException e) {
                    request.done(entity, e);
                }
            }
        }));
        return request;
    }

    public <R extends Reference> R delete(R ref) throws UaException {
        this.memCache.remove((Reference) ref);
        this.diskCache.markForDelete(ref);
        this.service.delete(ref);
        this.diskCache.delete(ref);
        return ref;
    }

    public Request delete(final Reference ref, DeleteCallback callback) {
        final DeleteRequest request = new DeleteRequest(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    AbstractManager.this.delete(ref);
                    request.done(ref, (UaException) null);
                } catch (UaException e) {
                    request.done(ref, e);
                }
            }
        }));
        return request;
    }

    public EntityList<T> fetchPage(EntityListRef<T> ref) throws UaException {
        return fetchPage(ref, this.cacheSettings.getDefaultCachePolicy());
    }

    /* access modifiers changed from: protected */
    public EntityList<T> fetchPage(EntityListRef<T> ref, CachePolicy cachePolicy) throws UaException {
        return fetchPage(ref, cachePolicy, true);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ac, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b2, code lost:
        throw new com.ua.sdk.UaException(r7);
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ac A[ExcHandler: Throwable (r7v0 't' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:4:0x000b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.EntityList<T> fetchPage(com.ua.sdk.EntityListRef<T> r13, com.ua.sdk.cache.CachePolicy r14, boolean r15) throws com.ua.sdk.UaException {
        /*
            r12 = this;
            r10 = 0
            if (r14 != 0) goto L_0x000a
            com.ua.sdk.cache.CacheSettings r8 = r12.cacheSettings
            com.ua.sdk.cache.CachePolicy r14 = r8.getDefaultCachePolicy()
        L_0x000a:
            r5 = 0
            boolean r8 = r14.checkCacheFirst()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 == 0) goto L_0x0064
            if (r15 == 0) goto L_0x003b
            com.ua.sdk.cache.Cache r8 = r12.memCache     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            long r2 = r8.getCacheAge(r13)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            boolean r8 = r14.ignoreAge()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 != 0) goto L_0x002d
            int r8 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r8 < 0) goto L_0x003b
            com.ua.sdk.cache.CacheSettings r8 = r12.cacheSettings     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            long r8 = r8.getMaxCacheAge()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 > 0) goto L_0x003b
        L_0x002d:
            com.ua.sdk.cache.Cache r8 = r12.memCache     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            com.ua.sdk.Resource r8 = r8.get(r13)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            r0 = r8
            com.ua.sdk.EntityList r0 = (com.ua.sdk.EntityList) r0     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            r5 = r0
            if (r5 == 0) goto L_0x003b
            r8 = r5
        L_0x003a:
            return r8
        L_0x003b:
            com.ua.sdk.cache.DiskCache<T> r8 = r12.diskCache     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            long r2 = r8.getCacheAge(r13)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            boolean r8 = r14.ignoreAge()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 != 0) goto L_0x0055
            int r8 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r8 < 0) goto L_0x0064
            com.ua.sdk.cache.CacheSettings r8 = r12.cacheSettings     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            long r8 = r8.getMaxCacheAge()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 > 0) goto L_0x0064
        L_0x0055:
            com.ua.sdk.cache.DiskCache<T> r8 = r12.diskCache     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            com.ua.sdk.EntityList r5 = r8.getList(r13)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r5 == 0) goto L_0x0064
            com.ua.sdk.cache.Cache r8 = r12.memCache     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            r8.put(r5)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            r8 = r5
            goto L_0x003a
        L_0x0064:
            boolean r8 = r14.checkNetwork()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 == 0) goto L_0x008c
            com.ua.sdk.internal.EntityService<T> r8 = r12.service     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            com.ua.sdk.EntityList r5 = r8.fetchPage(r13)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            com.ua.sdk.EntityList r5 = r12.onPostServiceFetchPage(r13, r5)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            if (r5 == 0) goto L_0x008c
            boolean r8 = r5 instanceof com.ua.sdk.internal.AbstractEntityList     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            if (r8 == 0) goto L_0x008e
            r0 = r5
            com.ua.sdk.internal.AbstractEntityList r0 = (com.ua.sdk.internal.AbstractEntityList) r0     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            r8 = r0
            boolean r6 = r8.preparePartials(r13)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            com.ua.sdk.cache.DiskCache<T> r8 = r12.diskCache     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            r8.updateAfterFetch(r13, r5, r6)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
        L_0x0087:
            com.ua.sdk.cache.Cache r8 = r12.memCache     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            r8.put(r5)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
        L_0x008c:
            r8 = r5
            goto L_0x003a
        L_0x008e:
            com.ua.sdk.cache.DiskCache<T> r8 = r12.diskCache     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            r9 = 0
            r8.updateAfterFetch(r13, r5, r9)     // Catch:{ UaException -> 0x0095, Throwable -> 0x00ac }
            goto L_0x0087
        L_0x0095:
            r4 = move-exception
            boolean r8 = r14.checkNetworkFirst()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 == 0) goto L_0x00a9
            boolean r8 = r14.checkCache()     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            if (r8 == 0) goto L_0x00a9
            com.ua.sdk.cache.CachePolicy r8 = com.ua.sdk.cache.CachePolicy.CACHE_ONLY     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            com.ua.sdk.EntityList r8 = r12.fetchPage(r13, (com.ua.sdk.cache.CachePolicy) r8, (boolean) r15)     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
            goto L_0x003a
        L_0x00a9:
            throw r4     // Catch:{ UaException -> 0x00aa, Throwable -> 0x00ac }
        L_0x00aa:
            r4 = move-exception
            throw r4
        L_0x00ac:
            r7 = move-exception
            com.ua.sdk.UaException r8 = new com.ua.sdk.UaException
            r8.<init>((java.lang.Throwable) r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.internal.AbstractManager.fetchPage(com.ua.sdk.EntityListRef, com.ua.sdk.cache.CachePolicy, boolean):com.ua.sdk.EntityList");
    }

    /* access modifiers changed from: protected */
    public Request fetchPage(EntityListRef<T> ref, FetchCallback<EntityList<T>> callback) {
        return fetchPage(ref, this.cacheSettings.getDefaultCachePolicy(), callback);
    }

    /* access modifiers changed from: protected */
    public Request fetchPage(final EntityListRef<T> ref, CachePolicy cachePolicy, FetchCallback<EntityList<T>> callback) {
        final CachePolicy policy;
        EntityList<T> list;
        if (cachePolicy != null) {
            policy = cachePolicy;
        } else {
            policy = this.cacheSettings.getDefaultCachePolicy();
        }
        if (policy.checkCacheFirst()) {
            long ageInCache = this.memCache.getCacheAge(ref);
            if ((policy.ignoreAge() || (ageInCache >= 0 && ageInCache <= this.cacheSettings.getMaxCacheAge())) && (list = (EntityList) this.memCache.get(ref)) != null) {
                EntityEventHandler.callOnFetched(list, (UaException) null, callback);
                return SynchronousRequest.INSTANCE;
            }
        }
        final FetchRequest<EntityList<T>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(AbstractManager.this.fetchPage(ref, policy, !policy.checkCacheFirst()), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public T patch(T entity, Reference ref) throws UaException {
        this.memCache.put(entity);
        this.diskCache.putForSave(entity);
        T savedEntity = onPostServicePatch(this.service.patch(entity, ref));
        this.diskCache.updateAfterSave(savedEntity);
        this.memCache.remove((Resource) entity);
        this.memCache.put(savedEntity);
        return savedEntity;
    }

    /* access modifiers changed from: protected */
    public T onPostServicePatch(T entity) throws UaException {
        return entity;
    }

    public Request patch(final T entity, final Reference ref, CreateCallback<T> callback) {
        final CreateRequest<T> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(AbstractManager.this.patch(entity, ref), (UaException) null);
                } catch (UaException e) {
                    request.done(entity, e);
                }
            }
        }));
        return request;
    }
}
