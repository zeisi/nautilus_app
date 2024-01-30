package com.ua.sdk.page.follow;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.JsonWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PageFollowJsonWriter implements JsonWriter<PageFollow> {
    private Gson gson;

    public PageFollowJsonWriter(Gson gson2) {
        this.gson = gson2;
    }

    public void write(PageFollow pageFollow, OutputStream out) throws UaException {
        PageFollowTransferObject pfto = PageFollowTransferObject.fromPageFollow(pageFollow);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        this.gson.toJson((Object) pfto, (Appendable) writer);
        try {
            writer.flush();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            UaLog.error("Unable to flush PageFollowJsonWriter during write.");
            throw new UaException((Throwable) e2);
        }
    }
}
