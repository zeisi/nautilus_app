package com.ua.sdk.bodymass;

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

public class BodyMassService extends AbstractResourceService<BodyMass> {
    public BodyMassService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<BodyMass> jsonParser, JsonWriter<BodyMass> jsonWriter, JsonParser<? extends EntityList<BodyMass>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create BodyMass is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildFetchBodyMassUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete BodyMass is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(BodyMass resource) {
        return this.urlBuilder.buildSaveBodyMassUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch BodyMass is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<BodyMass> ref) {
        return this.urlBuilder.buildFetchBodyMassUrl(ref);
    }
}
