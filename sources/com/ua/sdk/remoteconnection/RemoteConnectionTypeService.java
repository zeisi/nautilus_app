package com.ua.sdk.remoteconnection;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.UaException;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.LinkListRef;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class RemoteConnectionTypeService extends AbstractResourceService<RemoteConnectionType> {
    public EntityListRef<RemoteConnectionType> PAGE_REF = new LinkListRef((String) null);
    public Reference REF = new LinkEntityRef((String) null, (String) null);

    public RemoteConnectionTypeService(ConnectionFactory connFactory, UrlBuilder urlBuilder, AuthenticationManager authManager, JsonParser<RemoteConnectionType> parser, JsonParser<EntityList<RemoteConnectionType>> pageJsonParser) {
        super(connFactory, authManager, urlBuilder, parser, (JsonWriter) null, pageJsonParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create RemooteConnectionType is not supported.");
    }

    public RemoteConnectionType fetch(Reference ref) throws UaException {
        if (ref == null) {
            ref = this.REF;
        }
        return (RemoteConnectionType) super.fetch(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetRemoteConnectionTypeUrl(ref);
    }

    public EntityList<RemoteConnectionType> fetchPage(EntityListRef<RemoteConnectionType> ref) throws UaException {
        if (ref == null) {
            ref = this.PAGE_REF;
        }
        return super.fetchPage(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<RemoteConnectionType> ref) {
        return this.urlBuilder.buildGetRemoteConnectionTypeUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(RemoteConnectionType resource) {
        throw new UnsupportedOperationException("Save RemooteConnectionType is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete RemooteConnectionType is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch RemooteConnectionType is not supported.");
    }
}
