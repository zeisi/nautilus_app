package com.ua.sdk.group.invite;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class GroupInviteJsonWriter extends AbstractGsonWriter<GroupInvite> {
    public GroupInviteJsonWriter() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public void write(GroupInvite entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
