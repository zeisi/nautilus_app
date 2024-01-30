package com.ua.sdk.internal;

import com.ua.sdk.UaException;
import java.io.InputStream;

public interface JsonParser<T> {
    T parse(InputStream inputStream) throws UaException;
}
