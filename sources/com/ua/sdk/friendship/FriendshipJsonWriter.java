package com.ua.sdk.friendship;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.JsonWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FriendshipJsonWriter implements JsonWriter<Friendship> {
    private Gson gson;

    public FriendshipJsonWriter(Gson gson2) {
        this.gson = gson2;
    }

    public void write(Friendship friendship, OutputStream out) throws UaException {
        FriendshipTransferObject uto = FriendshipTransferObject.fromFriendship(friendship);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        this.gson.toJson((Object) uto, (Appendable) writer);
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
