package org.acra.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import ch.acra.acra.BuildConfig;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.config.ACRAConfiguration;
import org.acra.security.KeyStoreHelper;
import org.acra.sender.HttpSender;
import org.acra.util.IOUtils;

public abstract class BaseHttpRequest<T> implements HttpRequest<T> {
    @NonNull
    private final ACRAConfiguration config;
    private final int connectionTimeOut;
    @NonNull
    private final Context context;
    private final Map<String, String> headers;
    private final String login;
    @NonNull
    private final HttpSender.Method method;
    private final String password;
    private final int socketTimeOut;

    /* access modifiers changed from: protected */
    public abstract byte[] asBytes(T t) throws IOException;

    /* access modifiers changed from: protected */
    public abstract String getContentType(@NonNull Context context2, @NonNull T t);

    public BaseHttpRequest(@NonNull ACRAConfiguration config2, @NonNull Context context2, @NonNull HttpSender.Method method2, @Nullable String login2, @Nullable String password2, int connectionTimeOut2, int socketTimeOut2, @Nullable Map<String, String> headers2) {
        this.config = config2;
        this.context = context2;
        this.method = method2;
        this.login = login2;
        this.password = password2;
        this.connectionTimeOut = connectionTimeOut2;
        this.socketTimeOut = socketTimeOut2;
        this.headers = headers2;
    }

    public void send(@NonNull URL url, @NonNull T content) throws IOException {
        HttpURLConnection urlConnection = createConnection(url);
        if (urlConnection instanceof HttpsURLConnection) {
            try {
                configureHttps((HttpsURLConnection) urlConnection);
            } catch (GeneralSecurityException e) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not configure SSL for ACRA request to " + url, e);
            }
        }
        configureTimeouts(urlConnection, this.connectionTimeOut, this.socketTimeOut);
        configureHeaders(urlConnection, this.login, this.password, this.headers, content);
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Sending request to " + url);
            ACRA.log.d(ACRA.LOG_TAG, "Http " + this.method.name() + " content : ");
            ACRA.log.d(ACRA.LOG_TAG, content.toString());
        }
        writeContent(urlConnection, this.method, content);
        handleResponse(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        urlConnection.disconnect();
    }

    /* access modifiers changed from: protected */
    @NonNull
    public HttpURLConnection createConnection(@NonNull URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    /* access modifiers changed from: protected */
    public void configureHttps(@NonNull HttpsURLConnection connection) throws GeneralSecurityException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(KeyStoreHelper.getKeyStore(this.context, this.config));
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init((KeyManager[]) null, tmf.getTrustManagers(), (SecureRandom) null);
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
    }

    /* access modifiers changed from: protected */
    public void configureTimeouts(@NonNull HttpURLConnection connection, int connectionTimeOut2, int socketTimeOut2) {
        connection.setConnectTimeout(connectionTimeOut2);
        connection.setReadTimeout(socketTimeOut2);
    }

    /* access modifiers changed from: protected */
    public void configureHeaders(@NonNull HttpURLConnection connection, @Nullable String login2, @Nullable String password2, @Nullable Map<String, String> customHeaders, @NonNull T t) throws IOException {
        connection.setRequestProperty("User-Agent", String.format("Android ACRA %1$s", new Object[]{BuildConfig.VERSION_NAME}));
        connection.setRequestProperty("Accept", "text/html,application/xml,application/json,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        connection.setRequestProperty(MIME.CONTENT_TYPE, getContentType(this.context, t));
        if (!(login2 == null || password2 == null)) {
            connection.setRequestProperty("Authorization", "Basic " + new String(Base64.encode((login2 + ':' + password2).getBytes(ACRAConstants.UTF8), 2), ACRAConstants.UTF8));
        }
        if (customHeaders != null) {
            for (Map.Entry<String, String> header : customHeaders.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeContent(@NonNull HttpURLConnection connection, @NonNull HttpSender.Method method2, @NonNull T content) throws IOException {
        byte[] contentAsBytes = asBytes(content);
        connection.setRequestMethod(method2.name());
        connection.setDoOutput(true);
        connection.setFixedLengthStreamingMode(contentAsBytes.length);
        System.setProperty("http.keepAlive", "false");
        connection.connect();
        OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
        try {
            outputStream.write(contentAsBytes);
            outputStream.flush();
        } finally {
            IOUtils.safeClose(outputStream);
        }
    }

    /* access modifiers changed from: protected */
    public void handleResponse(int responseCode, String responseMessage) throws IOException {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Request response : " + responseCode + " : " + responseMessage);
        }
        if (responseCode >= 200 && responseCode < 300) {
            ACRA.log.i(ACRA.LOG_TAG, "Request received by server");
        } else if (responseCode == 408 || responseCode >= 500) {
            ACRA.log.w(ACRA.LOG_TAG, "Could not send ACRA Post responseCode=" + responseCode + " message=" + responseMessage);
            throw new IOException("Host returned error code " + responseCode);
        } else if (responseCode < 400 || responseCode >= 500) {
            ACRA.log.w(ACRA.LOG_TAG, "Could not send ACRA Post - request will be discarded. responseCode=" + responseCode + " message=" + responseMessage);
        } else {
            ACRA.log.w(ACRA.LOG_TAG, responseCode + ": Client error - request will be discarded");
        }
    }
}
