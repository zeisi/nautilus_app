package com.ua.sdk.user.permission;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.NetworkError;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import javax.net.ssl.HttpsURLConnection;

public class UserPermissionService {
    private final AuthenticationManager authManager;
    private final ConnectionFactory connFactory;
    private final JsonParser<? extends EntityList<UserPermission>> jsonPageParser;
    private final UrlBuilder urlBuilder;

    public UserPermissionService(ConnectionFactory connFactory2, AuthenticationManager authManager2, UrlBuilder urlBuilder2, JsonParser<? extends EntityList<UserPermission>> jsonPageParser2) {
        this.connFactory = connFactory2;
        this.authManager = authManager2;
        this.urlBuilder = urlBuilder2;
        this.jsonPageParser = jsonPageParser2;
    }

    public EntityList<UserPermission> fetchUserPermission(EntityRef ref) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetUserPermissionUrl(ref));
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            Precondition.isExpectedResponse(conn, 200);
            EntityList<UserPermission> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
            conn.disconnect();
            return entityList;
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

    public EntityList<UserPermission> fetchUserPermissions() throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetUserPermissionsUrl((EntityListRef) null));
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            Precondition.isExpectedResponse(conn, 200);
            EntityList<UserPermission> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
            conn.disconnect();
            return entityList;
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
}
