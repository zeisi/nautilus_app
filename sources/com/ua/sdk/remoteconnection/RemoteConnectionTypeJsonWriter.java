package com.ua.sdk.remoteconnection;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.JsonWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RemoteConnectionTypeJsonWriter implements JsonWriter<RemoteConnectionType> {
    private Gson gson;

    public RemoteConnectionTypeJsonWriter(Gson gson2) {
        this.gson = gson2;
    }

    public void write(RemoteConnectionType rc, OutputStream out) throws UaException {
        RemoteConnectionTypeTransferObject rcto = RemoteConnectionTypeTransferObject.fromRemoteConnectionType(rc);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        this.gson.toJson((Object) rcto, (Appendable) writer);
        try {
            writer.flush();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            UaLog.error("Unable to flush RemoteConnectionTypeJsonWriter during write.");
            throw new UaException((Throwable) e2);
        }
    }
}
