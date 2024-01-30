package com.ua.sdk.user.stats;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class UserStatsService extends AbstractResourceService<UserStats> {
    public UserStatsService(ConnectionFactory connectionFactory, AuthenticationManager authenticationManager, UrlBuilder urlBuilder, JsonParser<UserStats> userStatsJsonParser) {
        super(connectionFactory, authenticationManager, urlBuilder, userStatsJsonParser, (JsonWriter) null, (JsonParser) null);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create UserStats not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetUserStatsUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(UserStats resource) {
        throw new UnsupportedOperationException("Save UserStats not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete UserStats not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch UserStats not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<UserStats> entityListRef) {
        throw new UnsupportedOperationException("Fetch UserStats page not supported.");
    }
}
