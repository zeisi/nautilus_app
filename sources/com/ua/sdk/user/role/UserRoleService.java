package com.ua.sdk.user.role;

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
import javax.net.ssl.HttpsURLConnection;

public class UserRoleService implements EntityService<UserRole> {
    private final AuthenticationManager authManager;
    private final ConnectionFactory connFactory;
    private final JsonParser<UserRole> jsonParser;
    private final JsonWriter<UserRole> jsonWriter;
    private final UrlBuilder urlBuilder;

    public UserRoleService(ConnectionFactory connFactory2, AuthenticationManager authManager2, UrlBuilder urlBuilder2, JsonParser<UserRole> jsonParser2, JsonWriter<UserRole> jsonWriter2) {
        this.connFactory = connFactory2;
        this.authManager = authManager2;
        this.urlBuilder = urlBuilder2;
        this.jsonParser = jsonParser2;
        this.jsonWriter = jsonWriter2;
    }

    public UserRole create(UserRole entity) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(entity);
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildCreateUserRoleUrl());
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("POST");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            this.jsonWriter.write(entity, conn.getOutputStream());
            Precondition.isExpectedResponse(conn, 200);
            UserRole parse = this.jsonParser.parse(conn.getInputStream());
            conn.disconnect();
            return parse;
        } catch (NetworkError e) {
            if (e.getResponseCode() == 401) {
                throw new UaException(UaException.Code.NOT_AUTHENTICATED, (Throwable) e);
            }
            throw e;
        } catch (InterruptedIOException e2) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e3) {
            throw new UaException(UaException.Code.UNKNOWN);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    public UserRole fetch(Reference ref) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetUserRoleUrl(ref));
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            Precondition.isExpectedResponse(conn, 200);
            UserRole parse = this.jsonParser.parse(conn.getInputStream());
            conn.disconnect();
            return parse;
        } catch (NetworkError e) {
            if (e.getResponseCode() == 401) {
                throw new UaException(UaException.Code.NOT_AUTHENTICATED, (Throwable) e);
            }
            throw e;
        } catch (InterruptedIOException e2) {
            throw new UaException(UaException.Code.CANCELED);
        } catch (IOException e3) {
            throw new UaException(UaException.Code.UNKNOWN);
        } catch (Throwable th) {
            conn.disconnect();
            throw th;
        }
    }

    public UserRole save(UserRole entity) throws UaException {
        throw new UnsupportedOperationException();
    }

    public void delete(Reference ref) throws UaException {
        throw new UnsupportedOperationException();
    }

    public UserRole patch(UserRole entity, Reference ref) throws UaException {
        throw new UnsupportedOperationException();
    }

    public EntityList<UserRole> fetchPage(EntityListRef<UserRole> entityListRef) throws UaException {
        throw new UnsupportedOperationException();
    }
}
