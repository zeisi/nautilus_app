package com.ua.sdk.route.bookmark;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import com.ua.sdk.route.RouteBookmark;
import java.net.URL;

public class RouteBookmarkService extends AbstractResourceService<RouteBookmark> {
    public RouteBookmarkService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<RouteBookmark> jsonParser, JsonWriter<RouteBookmark> jsonWriter, JsonParser<? extends EntityList<RouteBookmark>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateRouteBookmarkUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildFetchRouteBookmarkUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(RouteBookmark resource) {
        throw new UnsupportedOperationException("Save RouteBookmark is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteRouteBookmarkUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch RouteBookmark is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<RouteBookmark> ref) {
        return this.urlBuilder.buildFetchRouteBookmarkListUrl(ref);
    }
}
