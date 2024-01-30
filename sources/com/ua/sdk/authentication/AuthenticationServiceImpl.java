package com.ua.sdk.authentication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import com.ua.sdk.util.Streams;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.acra.ACRAConstants;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final String clientId;
    private final String clientSecret;
    private final ConnectionFactory connFactory;
    private final Context context;
    private final JsonParser<OAuth2Credentials> oauth2Parser;
    private final UrlBuilder urlBuilder;

    /* JADX WARNING: type inference failed for: r6v0, types: [com.ua.sdk.internal.JsonParser<com.ua.sdk.authentication.OAuth2Credentials>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AuthenticationServiceImpl(java.lang.String r2, java.lang.String r3, com.ua.sdk.internal.ConnectionFactory r4, com.ua.sdk.internal.net.UrlBuilder r5, com.ua.sdk.internal.JsonParser<com.ua.sdk.authentication.OAuth2Credentials> r6, android.content.Context r7) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r4)
            com.ua.sdk.internal.ConnectionFactory r0 = (com.ua.sdk.internal.ConnectionFactory) r0
            r1.connFactory = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r5)
            com.ua.sdk.internal.net.UrlBuilder r0 = (com.ua.sdk.internal.net.UrlBuilder) r0
            r1.urlBuilder = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r6)
            com.ua.sdk.internal.JsonParser r0 = (com.ua.sdk.internal.JsonParser) r0
            r1.oauth2Parser = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r2)
            java.lang.String r0 = (java.lang.String) r0
            r1.clientId = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r3)
            java.lang.String r0 = (java.lang.String) r0
            r1.clientSecret = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r7)
            android.content.Context r0 = (android.content.Context) r0
            r1.context = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.authentication.AuthenticationServiceImpl.<init>(java.lang.String, java.lang.String, com.ua.sdk.internal.ConnectionFactory, com.ua.sdk.internal.net.UrlBuilder, com.ua.sdk.internal.JsonParser, android.content.Context):void");
    }

    public void requestUserAuthorization(String redirectUri) {
        Intent i = new Intent("android.intent.action.VIEW", Uri.parse(this.urlBuilder.buildOAuth2AuthorizationUrl(this.clientId, redirectUri).toString()));
        i.setFlags(268435456);
        this.context.startActivity(i);
    }

    public String getUserAuthorizationUrl(String redirectUri) {
        return this.urlBuilder.buildOAuth2AuthorizationUrl(this.clientId, redirectUri).toString();
    }

    public OAuth2Credentials authenticateUser(CharSequence username, CharSequence password) throws UaException {
        try {
            StringBuilder formParams = new StringBuilder();
            formParams.append("grant_type=password");
            formParams.append("&client_id=").append(this.clientId);
            formParams.append("&client_secret=").append(this.clientSecret);
            formParams.append("&username=").append(URLEncoder.encode(username.toString(), ACRAConstants.UTF8));
            formParams.append("&password=").append(URLEncoder.encode(password.toString(), ACRAConstants.UTF8));
            return requestToken(formParams);
        } catch (UnsupportedEncodingException e) {
            throw new UaException();
        }
    }

    public OAuth2Credentials authenticateUser(String authorizationCode) throws UaException {
        StringBuilder formParams = new StringBuilder();
        formParams.append("grant_type=authorization_code");
        formParams.append("&client_id=").append(this.clientId);
        formParams.append("&client_secret=").append(this.clientSecret);
        formParams.append("&code=").append(authorizationCode);
        return requestToken(formParams);
    }

    public OAuth2Credentials authenticateClient() throws UaException {
        StringBuilder formParams = new StringBuilder();
        formParams.append("grant_type=client_credentials");
        formParams.append("&client_id=").append(this.clientId);
        formParams.append("&client_secret=").append(this.clientSecret);
        return requestToken(formParams);
    }

    public OAuth2Credentials refreshAuthentication(OAuth2Credentials token) throws UaException {
        StringBuilder formParams = new StringBuilder();
        formParams.append("grant_type=refresh_token");
        formParams.append("&client_id=").append(this.clientId);
        formParams.append("&client_secret=").append(this.clientSecret);
        formParams.append("&refresh_token=").append(token.getRefreshToken());
        return requestToken(formParams);
    }

    private OAuth2Credentials requestToken(CharSequence formData) throws UaException {
        HttpsURLConnection conn;
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetAuthenticationToken());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty(MIME.CONTENT_TYPE, "application/x-www-form-urlencoded");
            Streams.writeFully(formData, conn.getOutputStream());
            Precondition.isResponseHttpOk(conn);
            OAuth2Credentials token = this.oauth2Parser.parse(conn.getInputStream());
            conn.disconnect();
            return token;
        } catch (UaException e) {
            throw e;
        } catch (InterruptedIOException e2) {
            throw new UaException(UaException.Code.CANCELED, (Throwable) e2);
        } catch (Throwable e3) {
            throw new UaException(e3);
        }
    }
}
