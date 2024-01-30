package com.ua.sdk.internal;

import android.net.Uri;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;

public interface MediaEntity<T extends Resource> {
    T upload(Uri uri, T t) throws UaException;
}
