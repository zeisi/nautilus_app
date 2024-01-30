package com.ua.sdk.group;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class GroupJsonWriter extends AbstractGsonWriter<Group> {
    public GroupJsonWriter() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public void write(Group entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, new TypeToken<Group>() {
        }.getType(), (Appendable) writer);
    }
}
