package com.ua.sdk.gear.brand;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
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

public class GearBrandService implements EntityService<GearBrand> {
    private final AuthenticationManager authenticationManager;
    private final ConnectionFactory connectionFactory;
    private final JsonParser<EntityList<GearBrand>> listJsonParser;
    private final UrlBuilder urlBuilder;

    public GearBrandService(ConnectionFactory connectionFactory2, AuthenticationManager authenticationManager2, UrlBuilder urlBuilder2, JsonParser<EntityList<GearBrand>> listJsonParser2) {
        this.connectionFactory = connectionFactory2;
        this.authenticationManager = authenticationManager2;
        this.urlBuilder = urlBuilder2;
        this.listJsonParser = listJsonParser2;
    }

    public GearBrand create(GearBrand entity) throws UaException {
        return null;
    }

    public GearBrand fetch(Reference ref) throws UaException {
        return null;
    }

    public GearBrand save(GearBrand entity) throws UaException {
        return null;
    }

    public void delete(Reference ref) throws UaException {
    }

    public GearBrand patch(GearBrand entity, Reference ref) throws UaException {
        return null;
    }

    public EntityList<GearBrand> fetchPage(EntityListRef<GearBrand> ref) throws UaException {
        HttpsURLConnection conn;
        if (ref != null) {
            try {
                Precondition.isAuthenticated(this.authenticationManager);
                conn = this.connectionFactory.getSslConnection(this.urlBuilder.buildGetGearBrandUrl(ref));
                this.authenticationManager.signAsUser(conn);
                conn.setRequestMethod("GET");
                conn.setDoOutput(false);
                conn.setUseCaches(false);
                Precondition.isResponseHttpOk(conn);
                EntityList<GearBrand> parse = this.listJsonParser.parse(conn.getInputStream());
                conn.disconnect();
                return parse;
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
}
