package com.ua.sdk.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class OAuth2CredentialsParser implements JsonParser<OAuth2Credentials> {
    private Gson gson;

    public OAuth2CredentialsParser(Gson gson2) {
        this.gson = gson2;
    }

    public OAuth2Credentials parse(InputStream inputStream) throws UaException {
        try {
            return OAuth2CredentialsTO.toImpl((OAuth2CredentialsTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) OAuth2CredentialsTO.class));
        } catch (JsonSyntaxException e) {
            throw new UaException("Parse error");
        } catch (JsonIOException e2) {
            throw new UaException("Parse error", (Throwable) e2);
        }
    }
}
