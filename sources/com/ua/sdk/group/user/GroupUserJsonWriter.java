package com.ua.sdk.group.user;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class GroupUserJsonWriter extends AbstractGsonWriter<GroupUser> {
    public GroupUserJsonWriter() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public void write(GroupUser entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
