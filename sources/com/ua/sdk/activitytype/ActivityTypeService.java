package com.ua.sdk.activitytype;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class ActivityTypeService extends AbstractResourceService<ActivityType> {
    public ActivityTypeService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<ActivityType> jsonParser, JsonWriter<ActivityType> jsonWriter, JsonParser<ActivityTypeList> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Activity types may not be created.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetActivityTypeUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(ActivityType resource) {
        throw new UnsupportedOperationException("Activity types may not be saved.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Activity types may not be deleted.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Activity types may not be patched.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<ActivityType> ref) {
        return this.urlBuilder.buildGetActivityTypeListUrl(ref);
    }
}
