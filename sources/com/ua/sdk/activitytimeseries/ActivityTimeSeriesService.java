package com.ua.sdk.activitytimeseries;

import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;
import java.util.concurrent.Callable;
import javax.net.ssl.HttpsURLConnection;

public class ActivityTimeSeriesService extends AbstractResourceService<ActivityTimeSeries> {
    public ActivityTimeSeriesService(ConnectionFactory connectionFactory, AuthenticationManager authenticationManager, UrlBuilder urlBuilder, JsonWriter<ActivityTimeSeries> activityTimeSeriesJsonWriter, JsonParser<ActivityTimeSeries> activityTimeSeriesJsonParser) {
        super(connectionFactory, authenticationManager, urlBuilder, activityTimeSeriesJsonParser, activityTimeSeriesJsonWriter, (JsonParser) null);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateActivityTimeSeriesUrl();
    }

    /* access modifiers changed from: protected */
    public Callable<ActivityTimeSeries> getCreateCallable(final ActivityTimeSeries resource) throws UaException {
        return new Callable<ActivityTimeSeries>() {
            public ActivityTimeSeries call() throws Exception {
                HttpsURLConnection conn = ActivityTimeSeriesService.this.connFactory.getSslConnection(ActivityTimeSeriesService.this.getCreateUrl());
                Precondition.isAuthenticated(ActivityTimeSeriesService.this.authManager);
                try {
                    ActivityTimeSeriesService.this.authManager.sign(conn, ActivityTimeSeriesService.this.getCreateAuthenticationType());
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    ActivityTimeSeriesService.this.jsonWriter.write(resource, conn.getOutputStream());
                    Precondition.isExpectedResponse(conn, OmniDictionaryKeys.ProductFWVersion);
                    return resource;
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException("Fetch ActivityTimeSeries is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(ActivityTimeSeries resource) {
        throw new UnsupportedOperationException("Save ActivityTimeSeries is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete ActivityTimeSeries is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch ActivityTimeSeries is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<ActivityTimeSeries> entityListRef) {
        throw new UnsupportedOperationException("Fetch ActivityTimeSeries Page is unsupported.");
    }
}
