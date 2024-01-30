package com.ua.sdk.actigraphy;

import com.ua.sdk.EntityList;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.FetchRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.Precondition;
import java.util.concurrent.ExecutorService;

public class ActigraphyManagerImpl extends AbstractManager<Actigraphy> implements ActigraphyManager {
    private ActigraphyService actigraphyService;

    public ActigraphyManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<Actigraphy> diskCache, ActigraphyService service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.actigraphyService = (ActigraphyService) Precondition.isNotNull(service);
    }

    public EntityList<Actigraphy> fetchActigraphy(ActigraphyListRef ref) throws UaException {
        return this.actigraphyService.fetchPage(ref);
    }

    public Request fetchActigraphy(final ActigraphyListRef ref, FetchCallback<EntityList<Actigraphy>> callback) {
        final FetchRequest<EntityList<Actigraphy>> request = new FetchRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                UaException error = null;
                EntityList<Actigraphy> result = null;
                try {
                    result = ActigraphyManagerImpl.this.fetchActigraphy(ref);
                } catch (UaException e) {
                    UaLog.error("Failed to fetch actigraphy");
                    error = e;
                } catch (Throwable e2) {
                    UaLog.error("Failed to fetch actigraphy");
                    error = new UaException(e2);
                }
                request.done(result, error);
            }
        }));
        return request;
    }
}
