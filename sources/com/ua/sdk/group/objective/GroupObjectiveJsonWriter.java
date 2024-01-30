package com.ua.sdk.group.objective;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class GroupObjectiveJsonWriter extends AbstractGsonWriter<GroupObjective> {
    public GroupObjectiveJsonWriter() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public void write(GroupObjective entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
