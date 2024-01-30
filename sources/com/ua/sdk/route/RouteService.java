package com.ua.sdk.route;

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

public class RouteService extends AbstractResourceService<Route> {
    public RouteService(ConnectionFactory connFactory, UrlBuilder urlBuilder, AuthenticationManager authManager, JsonParser<Route> jsonParser, JsonWriter<Route> jsonWriter, JsonParser<EntityList<Route>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateRouteUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetRouteUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(Route resource) {
        return this.urlBuilder.buildUpdateRouteUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteRouteUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch Route is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<Route> ref) {
        return this.urlBuilder.buildGetRouteUrl(ref);
    }
}
