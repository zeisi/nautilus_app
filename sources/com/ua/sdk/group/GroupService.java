package com.ua.sdk.group;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class GroupService extends AbstractResourceService<Group> {
    public GroupService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<Group> jsonParser, JsonWriter<Group> jsonWriter, JsonParser<? extends EntityList<Group>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateGroupUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildFetchGroupUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(Group resource) {
        Precondition.isNotNull(resource);
        return this.urlBuilder.buildSaveGroupUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteGroupUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        return this.urlBuilder.buildPatchGroupUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<Group> ref) {
        return this.urlBuilder.buildFetchGroupListUrl(ref);
    }
}
