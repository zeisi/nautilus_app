package com.ua.sdk.sleep;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class SleepService extends AbstractResourceService<SleepMetric> {
    public SleepService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<SleepMetric> jsonParser, JsonWriter<SleepMetric> jsonWriter, JsonParser<SleepMetricList> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create Sleep is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetSleepUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(SleepMetric resource) {
        return this.urlBuilder.buildSaveSleepUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildGetSleepUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch Sleep is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<SleepMetric> ref) {
        return this.urlBuilder.buildGetSleepListUrl(ref);
    }
}
