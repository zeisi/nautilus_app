package com.ua.sdk.internal;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractGsonWriter<T> implements JsonWriter<T> {
    private Gson mGson;

    /* access modifiers changed from: protected */
    public abstract void write(T t, Gson gson, OutputStreamWriter outputStreamWriter) throws UaException;

    public AbstractGsonWriter(Gson gson) {
        Precondition.isNotNull(gson);
        this.mGson = gson;
    }

    public final void write(T entity, OutputStream out) throws UaException {
        OutputStreamWriter writer = new OutputStreamWriter(out);
        try {
            write(entity, this.mGson, writer);
            try {
                writer.flush();
            } catch (InterruptedIOException e) {
                throw new UaException(UaException.Code.CANCELED);
            } catch (IOException e2) {
                UaLog.error("Unable to flush json writer during write.", (Throwable) e2);
                throw new UaException((Throwable) e2);
            }
        } catch (JsonIOException e3) {
            UaLog.error("Unable to write json.", (Throwable) e3);
            throw new UaException((Throwable) e3);
        }
    }
}
