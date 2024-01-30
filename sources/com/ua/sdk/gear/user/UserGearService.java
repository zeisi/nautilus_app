package com.ua.sdk.gear.user;

import com.android.volley.toolbox.HttpClientStack;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.NetworkError;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

public class UserGearService implements EntityService<UserGear> {
    AuthenticationManager authenticationManager;
    ConnectionFactory connectionFactory;
    JsonParser<UserGear> jsonParser;
    JsonWriter<UserGear> jsonWriter;
    JsonParser<EntityList<UserGear>> listJsonParser;
    UrlBuilder urlBuilder;

    public UserGearService(ConnectionFactory connectionFactory2, AuthenticationManager authenticationManager2, UrlBuilder urlBuilder2, JsonParser<EntityList<UserGear>> listJsonParser2, JsonParser<UserGear> jsonParser2, JsonWriter<UserGear> jsonWriter2) {
        this.connectionFactory = connectionFactory2;
        this.authenticationManager = authenticationManager2;
        this.urlBuilder = urlBuilder2;
        this.listJsonParser = listJsonParser2;
        this.jsonParser = jsonParser2;
        this.jsonWriter = jsonWriter2;
    }

    public UserGear create(UserGear userGear) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connectionFactory.getSslConnection(this.urlBuilder.buildCreateUserGearUrl());
            Precondition.isAuthenticated(this.authenticationManager);
            this.authenticationManager.signAsUser(conn);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            this.jsonWriter.write(userGear, conn.getOutputStream());
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                UserGear parse = this.jsonParser.parse(conn.getInputStream());
                conn.disconnect();
                return parse;
            }
            throw new NetworkError(responseCode, (HttpURLConnection) conn);
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (IOException e2) {
            throw new UaException(UaException.Code.UNKNOWN, (Throwable) e2);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    public void delete(Reference ref) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connectionFactory.getSslConnection(this.urlBuilder.buildDeleteUserGearUrl(ref));
            Precondition.isAuthenticated(this.authenticationManager);
            this.authenticationManager.signAsUser(conn);
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            int responseCode = conn.getResponseCode();
            if (responseCode != 204) {
                throw new NetworkError(responseCode, (HttpURLConnection) conn);
            }
            conn.disconnect();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (IOException e2) {
            throw new UaException(UaException.Code.UNKNOWN, (Throwable) e2);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    public UserGear patch(UserGear userGear, Reference ref) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connectionFactory.getSslConnection(this.urlBuilder.buildPatchUserGearUrl(ref));
            Precondition.isAuthenticated(this.authenticationManager);
            this.authenticationManager.signAsUser(conn);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.addRequestProperty("X-HTTP-Method-Override", HttpClientStack.HttpPatch.METHOD_NAME);
            this.jsonWriter.write(userGear, conn.getOutputStream());
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                UserGear parse = this.jsonParser.parse(conn.getInputStream());
                conn.disconnect();
                return parse;
            }
            throw new NetworkError(responseCode, (HttpURLConnection) conn);
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (IOException e2) {
            throw new UaException(UaException.Code.UNKNOWN, (Throwable) e2);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    public EntityList<UserGear> fetchPage(EntityListRef<UserGear> ref) throws UaException {
        HttpsURLConnection conn;
        if (ref != null) {
            try {
                Precondition.isAuthenticated(this.authenticationManager);
                conn = this.connectionFactory.getSslConnection(this.urlBuilder.buildGetUserGearUrl(ref));
                Precondition.isAuthenticated(this.authenticationManager);
                this.authenticationManager.signAsUser(conn);
                conn.setRequestMethod("GET");
                conn.setDoOutput(false);
                conn.setUseCaches(false);
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    EntityList<UserGear> parse = this.listJsonParser.parse(conn.getInputStream());
                    conn.disconnect();
                    return parse;
                }
                throw new NetworkError(responseCode, (HttpURLConnection) conn);
            } catch (InterruptedIOException e) {
                throw new UaException(UaException.Code.CANCELED, (Throwable) e);
            } catch (IOException e2) {
                throw new UaException(UaException.Code.UNKNOWN, (Throwable) e2);
            } catch (Throwable th) {
                conn.disconnect();
                throw th;
            }
        } else {
            throw new UaException("The ref must not be null!");
        }
    }

    public UserGear fetch(Reference ref) throws UaException {
        return null;
    }

    public UserGear save(UserGear entity) throws UaException {
        return null;
    }
}
