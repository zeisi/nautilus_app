package com.ua.sdk.internal;

import com.ua.sdk.UaException;
import java.io.OutputStream;

public interface JsonWriter<T> {
    void write(T t, OutputStream outputStream) throws UaException;
}
