package com.ua.sdk.authentication;

import android.content.SharedPreferences;
import android.os.SystemClock;
import com.ua.sdk.NetworkError;
import com.ua.sdk.Request;
import com.ua.sdk.Ua;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.concurrent.LoginRequest;
import com.ua.sdk.internal.Precondition;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;

public class AuthenticationManagerImpl implements AuthenticationManager {
    protected static final String PREF_ACCESS_TOKEN = "mmdk_oauth2_access_token";
    protected static final String PREF_PREFIX = "mmdk_";
    protected static final String PREF_REFRESH_TIME = "mmdk_oauth2_refresh_time";
    protected static final String PREF_REFRESH_TOKEN = "mmdk_oauth2_refresh_token";
    protected static final int TIMEOUT_DELTA = 1200000;
    protected AuthenticationService authService;
    protected ExecutorService executorService;
    protected OAuth2Credentials oAuth2Credentials;
    private long refreshNanos = Long.MIN_VALUE;
    protected SharedPreferences sharedPrefs;

    public void init(AuthenticationService authService2, ExecutorService executorService2, SharedPreferences sharedPrefs2) {
        this.authService = (AuthenticationService) Precondition.isNotNull(authService2);
        this.executorService = (ExecutorService) Precondition.isNotNull(executorService2);
        this.sharedPrefs = (SharedPreferences) Precondition.isNotNull(sharedPrefs2);
        String token = sharedPrefs2.getString(PREF_ACCESS_TOKEN, (String) null);
        if (token != null) {
            String refreshToken = sharedPrefs2.getString(PREF_REFRESH_TOKEN, (String) null);
            long refreshTime = sharedPrefs2.getLong(PREF_REFRESH_TIME, 0);
            this.oAuth2Credentials = new OAuth2CredentialsImpl();
            this.oAuth2Credentials.setAccessToken(token);
            this.oAuth2Credentials.setRefreshToken(refreshToken);
            this.oAuth2Credentials.setExpiresAt(Long.valueOf(refreshTime));
        }
    }

    public synchronized void refreshToken(long requestNanos) throws UaException {
        if (!isAuthenticated()) {
            UaLog.error("Can't refresh Oauth2Credentials, not authenticated.");
            throw new UaException(UaException.Code.NOT_AUTHENTICATED);
        } else if (requestNanos > this.refreshNanos) {
            try {
                OAuth2Credentials token = this.authService.refreshAuthentication(this.oAuth2Credentials);
                this.refreshNanos = SystemClock.elapsedRealtime();
                setOAuth2Credentials(token);
                UaLog.debug("Oauth2Credentials have been refreshed");
            } catch (NetworkError e) {
                if (e.getResponseCode() == 401) {
                    UaLog.error("Failed to refresh Oauth2Credentials.", (Throwable) e);
                }
                onLogout();
                throw e;
            } catch (UaException e2) {
                UaLog.error("Failed to refresh Oauth2Credentials.", (Throwable) e2);
                throw e2;
            }
        } else {
            UaLog.debug("Oauth2Credentials were already refreshed. Not refreshing again.");
        }
    }

    public void sign(URLConnection conn, AuthenticationManager.AuthenticationType type) throws UaException {
        if (type != null) {
            switch (type) {
                case USER:
                    signAsUser(conn);
                    return;
                case CLIENT:
                    signAsClient(conn);
                    return;
                default:
                    return;
            }
        }
    }

    public synchronized void signAsUser(URLConnection conn) throws UaException {
        Precondition.isAuthenticated(this);
        if (this.oAuth2Credentials != null) {
            refreshUserTokenIfNeeded();
            signConnection(this.oAuth2Credentials, conn);
        }
    }

    public synchronized void signAsClient(URLConnection conn) throws UaException {
        signConnection(this.authService.authenticateClient(), conn);
    }

    private synchronized void signConnection(OAuth2Credentials token, URLConnection conn) {
        conn.setRequestProperty("Authorization", "Bearer " + token.getAccessToken());
    }

    private synchronized void refreshUserTokenIfNeeded() throws UaException {
        if (this.oAuth2Credentials != null && this.oAuth2Credentials.getExpiresAt().longValue() - 1200000 <= System.currentTimeMillis()) {
            refreshToken(System.nanoTime());
        }
    }

    public synchronized void login(String authorizationCode) throws UaException {
        UaLog.debug("Attempting login with authorization code.");
        setOAuth2Credentials(this.authService.authenticateUser(authorizationCode));
        UaLog.debug("Successfully logged in using authorization code.");
    }

    public Request login(final String authorizationCode, Ua.LoginCallback callback) {
        final LoginRequest request = new LoginRequest(callback);
        request.setFuture(this.executorService.submit(new Runnable() {
            public void run() {
                try {
                    AuthenticationManagerImpl.this.login(authorizationCode);
                    request.done(null, (UaException) null);
                } catch (UaException e) {
                    AuthenticationManagerImpl.this.onLogout();
                    UaLog.error("Failed to log in with authorization code.", (Throwable) e);
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public boolean isAuthenticated() {
        return this.oAuth2Credentials != null;
    }

    public synchronized void onLogout() {
        this.oAuth2Credentials = null;
        this.sharedPrefs.edit().remove(PREF_ACCESS_TOKEN).remove(PREF_REFRESH_TOKEN).remove(PREF_REFRESH_TIME).commit();
    }

    public synchronized void setOAuth2Credentials(OAuth2Credentials oAuth2Credentials2) {
        Precondition.isNotNull(oAuth2Credentials2, "oAuth2Credentials");
        this.oAuth2Credentials = oAuth2Credentials2;
        this.sharedPrefs.edit().putString(PREF_ACCESS_TOKEN, oAuth2Credentials2.getAccessToken()).putString(PREF_REFRESH_TOKEN, oAuth2Credentials2.getRefreshToken()).putLong(PREF_REFRESH_TIME, oAuth2Credentials2.getExpiresAt().longValue()).commit();
    }

    public OAuth2Credentials getOAuth2Credentials() {
        return this.oAuth2Credentials;
    }

    public synchronized void requestUserAuthorization(String redirectUri) {
        this.authService.requestUserAuthorization(redirectUri);
    }

    public synchronized String getUserAuthorizationUrl(String redirectUri) {
        return this.authService.getUserAuthorizationUrl(redirectUri);
    }
}
