package com.ua.sdk.suggestedfriends;

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

public class SuggestedFriendsService extends AbstractResourceService<SuggestedFriends> {
    public SuggestedFriendsService(ConnectionFactory connectionFactory, AuthenticationManager authenticationManager, UrlBuilder urlBuilder, JsonParser<? extends EntityList<SuggestedFriends>> suggestedFriendsPageParser, JsonParser<SuggestedFriends> suggestedFriendsParser) {
        super(connectionFactory, authenticationManager, urlBuilder, suggestedFriendsParser, (JsonWriter) null, suggestedFriendsPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException("Create SuggestedFriends is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException("Fetch SuggestedFriends is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(SuggestedFriends resource) {
        throw new UnsupportedOperationException("Save SuggestedFriends is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Save SuggestedFriends is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<SuggestedFriends> ref) {
        return this.urlBuilder.buildGetSuggestedFriendsUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch SuggestedFriends is unsupported.");
    }
}
