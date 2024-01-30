package com.ua.sdk.recorder;

import com.ua.sdk.recorder.Message;
import java.io.InputStream;
import java.io.OutputStream;

public interface MessagePersistence<T extends Message> {
    T deserialize(InputStream inputStream, int i);

    void serialize(T t, OutputStream outputStream);
}
