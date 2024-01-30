package com.ua.sdk.group.leaderboard;

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

public class GroupLeaderboardService extends AbstractResourceService<GroupLeaderboard> {
    public GroupLeaderboardService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<GroupLeaderboard> jsonParser, JsonWriter<GroupLeaderboard> jsonWriter, JsonParser<? extends EntityList<GroupLeaderboard>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(GroupLeaderboard resource) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<GroupLeaderboard> ref) {
        return this.urlBuilder.buildFetchGroupLeaderboardListUrl(ref);
    }
}
