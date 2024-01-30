package com.ua.sdk.group.user;

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

public class GroupUserService extends AbstractResourceService<GroupUser> {
    public GroupUserService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<GroupUser> jsonParser, JsonWriter<GroupUser> jsonWriter, JsonParser<? extends EntityList<GroupUser>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateGroupUserUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(GroupUser resource) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteGroupUserUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<GroupUser> ref) {
        return this.urlBuilder.buildGetGroupUserUrl(ref);
    }
}
