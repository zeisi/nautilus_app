package com.ua.sdk.authentication;

import com.ua.sdk.UaException;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import javax.net.ssl.HttpsURLConnection;

public class InternalTokenCredentialService {
    private AuthenticationManager authManager;
    private ConnectionFactory connFactory;
    private JsonWriter<InternalTokenCredential> internalTokenCredentialWriter;
    private JsonParser<OAuth2Credentials> oauth2Parser;
    private UrlBuilder urlBuilder;

    public void init(ConnectionFactory connFactory2, UrlBuilder urlBuilder2, AuthenticationManager authManager2, JsonParser<OAuth2Credentials> oauth2Parser2, JsonWriter<InternalTokenCredential> internalTokenCredentialWriter2) {
        this.connFactory = connFactory2;
        this.urlBuilder = urlBuilder2;
        this.authManager = authManager2;
        this.oauth2Parser = oauth2Parser2;
        this.internalTokenCredentialWriter = internalTokenCredentialWriter2;
    }

    public OAuth2Credentials save(InternalTokenCredential internalTokenCredential, boolean authAsUser) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(internalTokenCredential, "internalTokenCredential");
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildCreateInternalTokenCredentialUrl());
            if (authAsUser) {
                this.authManager.signAsUser(conn);
            } else {
                this.authManager.signAsClient(conn);
            }
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            this.internalTokenCredentialWriter.write(internalTokenCredential, conn.getOutputStream());
            Precondition.isResponseSuccess(conn);
            OAuth2Credentials parse = this.oauth2Parser.parse(conn.getInputStream());
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
    }
}
