package com.ua.sdk.authentication;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.JsonWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class InternalTokenCredentialWriter implements JsonWriter<InternalTokenCredential> {
    private Gson gson;

    public InternalTokenCredentialWriter(Gson gson2) {
        this.gson = gson2;
    }

    public void write(InternalTokenCredential entity, OutputStream out) throws UaException {
        InternalTokenCredentialTO transfer = InternalTokenCredentialTO.toTransferObject(entity);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        this.gson.toJson((Object) transfer, (Appendable) writer);
        try {
            writer.flush();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            UaLog.error("Unable to flush InternalTokenCredentialWriter during write.");
            throw new UaException((Throwable) e2);
        }
    }
}
