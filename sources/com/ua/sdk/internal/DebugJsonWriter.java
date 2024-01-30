package com.ua.sdk.internal;

import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.util.Streams;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.acra.ACRAConstants;

public class DebugJsonWriter<T> implements JsonWriter<T> {
    private final JsonWriter<T> writer;

    public DebugJsonWriter(JsonWriter<T> writer2) {
        Precondition.isNotNull(writer2);
        this.writer = writer2;
    }

    public void write(T entity, OutputStream out) throws UaException {
        String json = null;
        try {
            ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
            this.writer.write(entity, tempOut);
            String json2 = new String(tempOut.toByteArray(), ACRAConstants.UTF8);
            try {
                Streams.writeFully(json2, out);
                UaLog.debug("request=%s", (Object) json2);
            } catch (IOException e) {
                e = e;
                json = json2;
                try {
                    throw new UaException((Throwable) e);
                } catch (Throwable th) {
                    th = th;
                    UaLog.debug("request=%s", (Object) json);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                json = json2;
                UaLog.debug("request=%s", (Object) json);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            throw new UaException((Throwable) e);
        }
    }
}
