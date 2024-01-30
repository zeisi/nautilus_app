package com.ua.sdk.moderation;

import com.nautilus.underarmourconnection.services.workouts.ActivityType;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityRef;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import java.util.concurrent.ExecutorService;

public class ModerationActionManager extends AbstractManager<ModerationAction> {
    public ModerationActionManager(CacheSettings cacheSettings, Cache memCache, DiskCache<ModerationAction> diskCache, EntityService<ModerationAction> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public ModerationAction flagEntity(EntityRef ref) throws UaException {
        ModerationActionImpl action = new ModerationActionImpl();
        action.setAction(String.format(UrlBuilderImpl.GET_MODERATION_ACTION_TYPE, new Object[]{ActivityType.GENERIC}));
        action.setResource(ref.getHref());
        return (ModerationAction) create(action);
    }

    public Request flagEntity(EntityRef ref, CreateCallback<ModerationAction> callback) {
        ModerationActionImpl action = new ModerationActionImpl();
        action.setAction(String.format(UrlBuilderImpl.GET_MODERATION_ACTION_TYPE, new Object[]{ActivityType.GENERIC}));
        action.setResource(ref.getHref());
        return create(action, callback);
    }

    public ModerationAction unflagEntity(EntityRef ref) throws UaException {
        ModerationActionImpl action = new ModerationActionImpl();
        action.setAction(String.format(UrlBuilderImpl.GET_MODERATION_ACTION_TYPE, new Object[]{"2"}));
        action.setResource(ref.getHref());
        return (ModerationAction) create(action);
    }

    public Request unflagEntity(EntityRef ref, CreateCallback<ModerationAction> callback) {
        ModerationActionImpl action = new ModerationActionImpl();
        action.setAction(String.format(UrlBuilderImpl.GET_MODERATION_ACTION_TYPE, new Object[]{"2"}));
        action.setResource(ref.getHref());
        return create(action, callback);
    }
}
