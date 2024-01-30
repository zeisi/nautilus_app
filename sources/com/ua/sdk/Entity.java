package com.ua.sdk;

import com.ua.sdk.EntityRef;

public interface Entity<R extends EntityRef> extends Resource<R> {
    R getRef();
}
