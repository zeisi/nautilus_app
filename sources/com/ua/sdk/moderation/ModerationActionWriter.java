package com.ua.sdk.moderation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class ModerationActionWriter extends AbstractGsonWriter<ModerationAction> {
    public ModerationActionWriter() {
        super(GsonFactory.newModerationInstance());
    }

    /* access modifiers changed from: protected */
    public void write(ModerationAction entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, new TypeToken<ModerationAction>() {
        }.getType(), (Appendable) writer);
    }
}
