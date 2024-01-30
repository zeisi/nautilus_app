package com.ua.sdk.user.role;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.AbstractGsonWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;

public class UserRoleJsonWriter extends AbstractGsonWriter<UserRole> {
    public UserRoleJsonWriter(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public void write(UserRole entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) UserRoleTO.toTransferObject(entity), (Appendable) writer);
        try {
            writer.flush();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            UaLog.error("Unable to flush UserRoleJsonWriter during write.");
            throw new UaException((Throwable) e2);
        }
    }
}
