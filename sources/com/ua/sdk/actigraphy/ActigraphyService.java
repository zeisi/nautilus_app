package com.ua.sdk.actigraphy;

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

public class ActigraphyService extends AbstractResourceService<Actigraphy> {
    public ActigraphyService(ConnectionFactory connectionFactory, AuthenticationManager authenticationManager, UrlBuilder urlBuilder, JsonParser<EntityList<Actigraphy>> jsonParser) {
        super(connectionFactory, authenticationManager, urlBuilder, (JsonParser) null, (JsonWriter) null, jsonParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create Actigraphy is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException("Fetch Actigraphy is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(Actigraphy resource) {
        throw new UnsupportedOperationException("Save Actigraphy is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete Actigraphy is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<Actigraphy> ref) {
        if (ref instanceof ActigraphyListRef) {
            return this.urlBuilder.buildGetActigraphyUrl((ActigraphyListRef) ref);
        }
        throw new IllegalArgumentException("Use ActigraphyListRef.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch Actigraphy is not supported.");
    }
}
