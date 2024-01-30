package com.ua.sdk.role;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.NetworkError;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import javax.net.ssl.HttpsURLConnection;

public class RoleService implements EntityService<Role> {
    private final AuthenticationManager authManager;
    private final ConnectionFactory connFactory;
    private final JsonParser<? extends EntityList<Role>> jsonPageParser;
    private final JsonParser<Role> jsonParser;
    private final UrlBuilder urlBuilder;

    public RoleService(ConnectionFactory connFactory2, AuthenticationManager authManager2, UrlBuilder urlBuilder2, JsonParser<Role> jsonParser2, JsonParser<? extends EntityList<Role>> jsonPageParser2) {
        this.connFactory = connFactory2;
        this.authManager = authManager2;
        this.urlBuilder = urlBuilder2;
        this.jsonParser = jsonParser2;
        this.jsonPageParser = jsonPageParser2;
    }

    public Role create(Role entity) throws UaException {
        throw new UnsupportedOperationException();
    }

    public Role fetch(Reference ref) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetRoleUrl(ref));
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            Precondition.isExpectedResponse(conn, 200);
            Role parse = this.jsonParser.parse(conn.getInputStream());
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

    public Role save(Role entity) throws UaException {
        throw new UnsupportedOperationException();
    }

    public void delete(Reference ref) throws UaException {
        throw new UnsupportedOperationException();
    }

    public Role patch(Role entity, Reference ref) throws UaException {
        throw new UnsupportedOperationException();
    }

    public EntityList<Role> fetchPage(EntityListRef<Role> entityListRef) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetRolesUrl((EntityListRef) null));
            this.authManager.sign(conn, AuthenticationManager.AuthenticationType.USER);
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            Precondition.isExpectedResponse(conn, 200);
            EntityList<Role> entityList = (EntityList) this.jsonPageParser.parse(conn.getInputStream());
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
