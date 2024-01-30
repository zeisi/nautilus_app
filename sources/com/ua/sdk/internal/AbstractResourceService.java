package com.ua.sdk.internal;

import android.os.Build;
import android.os.NetworkOnMainThreadException;
import android.os.SystemClock;
import com.android.volley.toolbox.HttpClientStack;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.NetworkError;
import com.ua.sdk.Reference;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.net.UrlBuilder;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.concurrent.Callable;
import javax.net.ssl.HttpsURLConnection;

public abstract class AbstractResourceService<T extends Resource> implements EntityService<T> {
    /* access modifiers changed from: protected */
    public final AuthenticationManager authManager;
    /* access modifiers changed from: protected */
    public final ConnectionFactory connFactory;
    protected final JsonParser<? extends EntityList<T>> jsonPageParser;
    protected final JsonParser<T> jsonParser;
    /* access modifiers changed from: protected */
    public final JsonWriter<T> jsonWriter;
    protected final UrlBuilder urlBuilder;

    /* access modifiers changed from: protected */
    public abstract URL getCreateUrl();

    /* access modifiers changed from: protected */
    public abstract URL getDeleteUrl(Reference reference);

    /* access modifiers changed from: protected */
    public abstract URL getFetchPageUrl(EntityListRef<T> entityListRef);

    /* access modifiers changed from: protected */
    public abstract URL getFetchUrl(Reference reference);

    /* access modifiers changed from: protected */
    public abstract URL getPatchUrl(Reference reference);

    /* access modifiers changed from: protected */
    public abstract URL getSaveUrl(T t);

    public AbstractResourceService(ConnectionFactory connFactory2, AuthenticationManager authManager2, UrlBuilder urlBuilder2, JsonParser<T> jsonParser2, JsonWriter<T> jsonWriter2, JsonParser<? extends EntityList<T>> jsonPageParser2) {
        this.connFactory = (ConnectionFactory) Precondition.isNotNull(connFactory2);
        this.urlBuilder = (UrlBuilder) Precondition.isNotNull(urlBuilder2);
        this.jsonParser = jsonParser2;
        this.jsonWriter = jsonWriter2;
        this.authManager = authManager2;
        this.jsonPageParser = jsonPageParser2;
    }

    /* access modifiers changed from: protected */
    public final <R> R call(Callable<R> networkCall) throws UaException {
        return call(networkCall, true);
    }

    private <R> R call(Callable<R> networkCall, boolean refreshTokenIfUnauthorized) throws UaException {
        long requestNanos = SystemClock.elapsedRealtime();
        try {
            return networkCall.call();
        } catch (NetworkError e) {
            if (e.getResponseCode() != 401) {
                throw e;
            } else if (refreshTokenIfUnauthorized) {
                this.authManager.refreshToken(requestNanos);
                return call(networkCall, false);
            } else {
                throw new UaException(UaException.Code.NOT_AUTHENTICATED, (Throwable) e);
            }
        } catch (UaException e2) {
            throw e2;
        } catch (InterruptedIOException e3) {
            throw new UaException(UaException.Code.CANCELED, (Throwable) e3);
        } catch (IOException e4) {
            throw new UaException(UaException.Code.UNKNOWN, (Throwable) e4);
        } catch (Exception e5) {
            if (Build.VERSION.SDK_INT < 11 || !(e5 instanceof NetworkOnMainThreadException)) {
                throw new UaException(UaException.Code.UNKNOWN, (Throwable) e5);
            }
            throw new UaException(UaException.Code.NETWORK_ON_MAIN_THREAD, (Throwable) e5);
        }
    }

    /* access modifiers changed from: protected */
    public final void checkAuthentication(AuthenticationManager.AuthenticationType type) throws UaException {
        if (type == AuthenticationManager.AuthenticationType.USER) {
            Precondition.isAuthenticated(this.authManager);
        }
    }

    public T create(T resource) throws UaException {
        try {
            checkAuthentication(getCreateAuthenticationType());
            return (Resource) call(getCreateCallable(resource));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public Callable<T> getCreateCallable(final T resource) throws UaException {
        Precondition.isNotNull(this.jsonWriter, "jsonWriter");
        Precondition.isNotNull(this.jsonParser, "jsonParser");
        return new Callable<T>() {
            public T call() throws Exception {
                Precondition.isNotNull(resource, "resource");
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getCreateUrl());
                AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getCreateAuthenticationType());
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                AbstractResourceService.this.jsonWriter.write(resource, conn.getOutputStream());
                Precondition.isExpectedResponse(conn, OmniDictionaryKeys.ProductHWVarient);
                return (Resource) AbstractResourceService.this.jsonParser.parse(conn.getInputStream());
            }
        };
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getCreateAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }

    public T fetch(Reference ref) throws UaException {
        try {
            checkAuthentication(getFetchAuthenticationType());
            return (Resource) call(getFetchCallable(ref));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public Callable<T> getFetchCallable(final Reference ref) throws UaException {
        Precondition.isNotNull(this.jsonParser, "jsonParser");
        return new Callable<T>() {
            public T call() throws Exception {
                Precondition.isNotNull(ref, "ref");
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getFetchUrl(ref));
                try {
                    AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getFetchAuthenticationType());
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(false);
                    conn.setUseCaches(false);
                    Precondition.isExpectedResponse(conn, 200);
                    return (Resource) AbstractResourceService.this.jsonParser.parse(conn.getInputStream());
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getFetchAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }

    public T save(T resource) throws UaException {
        try {
            checkAuthentication(getSaveAuthenticationType());
            return (Resource) call(getSaveCallable(resource));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public Callable<T> getSaveCallable(final T resource) throws UaException {
        Precondition.isNotNull(this.jsonWriter, "jsonWriter");
        Precondition.isNotNull(this.jsonParser, "jsonParser");
        return new Callable<T>() {
            public T call() throws Exception {
                Precondition.isNotNull(resource, "resource");
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getSaveUrl(resource));
                try {
                    AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getSaveAuthenticationType());
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    AbstractResourceService.this.jsonWriter.write(resource, conn.getOutputStream());
                    Precondition.isExpectedResponse(conn, 200);
                    return (Resource) AbstractResourceService.this.jsonParser.parse(conn.getInputStream());
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getSaveAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }

    public void delete(Reference ref) throws UaException {
        try {
            checkAuthentication(getDeleteAuthenticationType());
            call(getDeleteCallable(ref));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getDeleteAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }

    private Callable<Void> getDeleteCallable(final Reference ref) throws UaException {
        Precondition.isNotNull(ref, "ref");
        return new Callable<Void>() {
            /* JADX INFO: finally extract failed */
            public Void call() throws Exception {
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getDeleteUrl(ref));
                try {
                    AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getDeleteAuthenticationType());
                    conn.setRequestMethod("DELETE");
                    conn.setDoOutput(false);
                    conn.setUseCaches(false);
                    Precondition.isExpectedResponse(conn, OmniDictionaryKeys.ProductFWVersion);
                    conn.disconnect();
                    return null;
                } catch (Throwable th) {
                    conn.disconnect();
                    throw th;
                }
            }
        };
    }

    public T patch(T resource, Reference ref) throws UaException {
        try {
            checkAuthentication(getPatchAuthenticationType());
            return (Resource) call(getPatchCallable(resource, ref));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public Callable<T> getPatchCallable(final T resource, final Reference ref) throws UaException {
        Precondition.isNotNull(this.jsonWriter, "jsonWriter");
        Precondition.isNotNull(this.jsonParser, "jsonParser");
        Precondition.isNotNull(resource, "resource");
        return new Callable<T>() {
            public T call() throws Exception {
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getPatchUrl(ref));
                try {
                    AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getPatchAuthenticationType());
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.addRequestProperty("X-HTTP-Method-Override", HttpClientStack.HttpPatch.METHOD_NAME);
                    AbstractResourceService.this.jsonWriter.write(resource, conn.getOutputStream());
                    Precondition.isResponseSuccess(conn);
                    return (Resource) AbstractResourceService.this.jsonParser.parse(conn.getInputStream());
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getPatchAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }

    public EntityList<T> fetchPage(EntityListRef<T> ref) throws UaException {
        try {
            checkAuthentication(getFetchPageAuthenticationType());
            return (EntityList) call(getFetchPageCallable(ref));
        } catch (UaException e) {
            throw e;
        } catch (Exception e2) {
            throw new UaException((Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public Callable<EntityList<T>> getFetchPageCallable(final EntityListRef<T> ref) throws UaException {
        Precondition.isNotNull(this.jsonPageParser, "jsonPageParser");
        Precondition.isNotNull(ref, "ref");
        return new Callable<EntityList<T>>() {
            public EntityList<T> call() throws Exception {
                HttpsURLConnection conn = AbstractResourceService.this.connFactory.getSslConnection(AbstractResourceService.this.getFetchPageUrl(ref));
                try {
                    AbstractResourceService.this.authManager.sign(conn, AbstractResourceService.this.getFetchPageAuthenticationType());
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(false);
                    conn.setUseCaches(false);
                    Precondition.isExpectedResponse(conn, 200);
                    return (EntityList) AbstractResourceService.this.jsonPageParser.parse(conn.getInputStream());
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public AuthenticationManager.AuthenticationType getFetchPageAuthenticationType() {
        return AuthenticationManager.AuthenticationType.USER;
    }
}
