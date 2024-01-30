package com.ua.sdk.group.purpose;

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

public class GroupPurposeService extends AbstractResourceService<GroupPurpose> {
    public GroupPurposeService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<GroupPurpose> jsonParser, JsonWriter<GroupPurpose> jsonWriter, JsonParser<? extends EntityList<GroupPurpose>> jsonPageParser) {
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
    public URL getSaveUrl(GroupPurpose resource) {
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
    public URL getFetchPageUrl(EntityListRef<GroupPurpose> ref) {
        return this.urlBuilder.buildGetGroupPurposesUrl(ref);
    }
}
