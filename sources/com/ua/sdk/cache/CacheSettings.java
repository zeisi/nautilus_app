package com.ua.sdk.cache;

import com.ua.sdk.internal.Precondition;

public class CacheSettings {
    private final CachePolicy mDefaultPolicy;
    private final long mMaxCacheAge;

    public CacheSettings(CachePolicy defaultPolicy, long maxCacheAge) {
        this.mDefaultPolicy = (CachePolicy) Precondition.isNotNull(defaultPolicy);
        this.mMaxCacheAge = maxCacheAge;
    }

    public long getMaxCacheAge() {
        return this.mMaxCacheAge;
    }

    public CachePolicy getDefaultCachePolicy() {
        return this.mDefaultPolicy;
    }
}
