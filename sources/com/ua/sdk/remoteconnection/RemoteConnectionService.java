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
import com.ua.sdk.internal.LinkListRef;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class RemoteConnectionService extends AbstractResourceService<RemoteConnection> {
    public EntityListRef<RemoteConnection> PAGE_REF = new LinkListRef((String) null);

    public RemoteConnectionService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<RemoteConnection> remoteConnectionParser, JsonParser<EntityList<RemoteConnection>> remoteConnectionPageParser) {
        super(connFactory, authManager, urlBuilder, remoteConnectionParser, (JsonWriter) null, remoteConnectionPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create RemoteConnection is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(RemoteConnection resource) {
        throw new UnsupportedOperationException("Save RemoteConnection is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteRemoteConnectionUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetRemoteConnectionUrl(ref);
    }

    public EntityList<RemoteConnection> fetchPage(EntityListRef<RemoteConnection> ref) throws UaException {
        if (ref == null) {
            ref = this.PAGE_REF;
        }
        return super.fetchPage(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<RemoteConnection> ref) {
        return this.urlBuilder.buildGetRemoteConnectionUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch RemoteConnection is unsupported.");
    }
}
