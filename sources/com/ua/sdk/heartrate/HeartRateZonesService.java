package com.ua.sdk.heartrate;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class HeartRateZonesService extends AbstractResourceService<HeartRateZones> {
    public HeartRateZonesService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<HeartRateZones> jsonParser, JsonWriter<HeartRateZones> jsonWriter, JsonParser<? extends EntityList<HeartRateZones>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateHeartRateZonesUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildFetchHeartRateZonesUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(HeartRateZones resource) {
        throw new UnsupportedOperationException("Heart Rate Zones cannot be saved");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Heart Rate Zones cannot be deleted");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Heart Rate Zones cannot be patched");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<HeartRateZones> ref) {
        return this.urlBuilder.buildFetchHeartRateZonesListUrl(ref);
    }
}
