package com.ua.sdk.friendship;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FriendshipPageJsonWriter implements JsonWriter<FriendshipListImpl> {
    public void write(FriendshipListImpl friendshipPage, OutputStream out) throws UaException {
        Gson gson = GsonFactory.newInstance();
        FriendshipPageTransferObject uto = FriendshipPageTransferObject.toTransferObject(friendshipPage);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        gson.toJson((Object) uto, (Appendable) writer);
        try {
            writer.flush();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            UaLog.error("Unable to flush UserJsonWriter during write.");
            throw new UaException((Throwable) e2);
        }
    }
}
