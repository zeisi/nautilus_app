package com.ua.sdk.authentication;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class FilemobileCredentialJsonParser extends AbstractGsonParser<FilemobileCredential> {
    public FilemobileCredentialJsonParser() {
        super(GsonFactory.newFilemobileCredentialInstance());
    }

    /* access modifiers changed from: protected */
    public FilemobileCredential read(Gson gson, JsonReader reader) throws UaException {
        return (FilemobileCredential) gson.fromJson(reader, (Type) FilemobileCredential.class);
    }
}
