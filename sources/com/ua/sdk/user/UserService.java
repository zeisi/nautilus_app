package com.ua.sdk.user;

import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import com.ua.sdk.util.Streams;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class UserService extends AbstractResourceService<User> {
    public UserService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<User> userParser, JsonWriter<User> userWriter, JsonParser<EntityList<User>> userPageParser) {
        super(connFactory, authManager, urlBuilder, userParser, userWriter, userPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateUserUrl();
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getCreateAuthenticationType() {
        return AuthenticationManager.AuthenticationType.CLIENT;
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        if (ref instanceof CurrentUserRef) {
            return this.urlBuilder.buildGetCurrentUserUrl();
        }
        return this.urlBuilder.buildGetUserUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(User resource) {
        return this.urlBuilder.buildSaveUserUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("User patch is not supported.");
    }

    public void resetPassword(String email) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(email);
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildResetPasswordUrl());
            this.authManager.signAsClient(conn);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty(MIME.CONTENT_TYPE, "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            Streams.writeFully("{\"email\":\"" + email + "\"}", conn.getOutputStream());
            Precondition.isResponseSuccess(conn);
            conn.disconnect();
        } catch (InterruptedIOException e) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e2) {
            throw new UaException(UaException.Code.UNKNOWN);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<User> ref) {
        return this.urlBuilder.buildGetUserPageUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("User delete is not supported.");
    }
}
