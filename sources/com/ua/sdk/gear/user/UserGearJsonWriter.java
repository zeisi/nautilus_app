package com.ua.sdk.gear.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class UserGearJsonWriter extends AbstractGsonWriter<UserGear> {
    public UserGearJsonWriter() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public void write(UserGear entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, new TypeToken<UserGear>() {
        }.getType(), (Appendable) writer);
    }
}
