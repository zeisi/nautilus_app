package com.ua.sdk.authentication;

import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.InterruptedIOException;
import javax.net.ssl.HttpsURLConnection;

public class FilemobileCredentialService {
    private AuthenticationManager authManager;
    private ConnectionFactory connFactory;
    private JsonParser<FilemobileCredential> jsonParser;
    private UrlBuilder urlBuilder;

    public void init(ConnectionFactory connFactory2, UrlBuilder urlBuilder2, JsonParser<FilemobileCredential> jsonParser2, AuthenticationManager authManager2) {
        this.connFactory = connFactory2;
        this.urlBuilder = urlBuilder2;
        this.jsonParser = jsonParser2;
        this.authManager = authManager2;
    }

    public FilemobileCredential fetchCredentials() throws UaException {
        return fetchCredentials(0);
    }

    public FilemobileCredential fetchCredentials(int attempts) throws UaException {
        HttpsURLConnection conn;
        FilemobileCredential parse;
        if (attempts > 2) {
            UaLog.debug("Tried fetching Filemobile credentials three(3) times.  Was not able to get a token.");
            throw new UaException("Unable to fetch Filemobile credentials.");
        }
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildCreateFilemobileTokenCredentialUrl());
            this.authManager.signAsUser(conn);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            Precondition.isResponseSuccess(conn);
            if (conn.getResponseCode() == 202) {
                parse = retryFetchCredentials(attempts);
                conn.disconnect();
            } else {
                parse = this.jsonParser.parse(conn.getInputStream());
                conn.disconnect();
            }
            return parse;
        } catch (InterruptedIOException e) {
            UaLog.debug("Filemobile fetch credentials cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to fetch Filemobile credentials.", t);
            throw new UaException("Unable to fetch Filemobile credentials.", t);
        }
    }

    private FilemobileCredential retryFetchCredentials(int attempts) throws InterruptedException, UaException {
        UaLog.debug("Retrying in three(3) seconds.");
        Thread.sleep(3000);
        UaLog.debug("Fetching Filemobile credentials attempt: " + (attempts + 1));
        return fetchCredentials(attempts + 1);
    }
}
