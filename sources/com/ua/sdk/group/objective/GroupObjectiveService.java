package com.ua.sdk.group.objective;

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

public class GroupObjectiveService extends AbstractResourceService<GroupObjective> {
    public GroupObjectiveService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<GroupObjective> jsonParser, JsonWriter<GroupObjective> jsonWriter, JsonParser<? extends EntityList<GroupObjective>> jsonPageParser) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, jsonPageParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateGroupObjectiveUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetGroupObjectiveUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(GroupObjective resource) {
        return this.urlBuilder.buildSaveGroupObjectiveUrl(resource.getRef());
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeleteGroupObjectiveUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<GroupObjective> ref) {
        return this.urlBuilder.buildGetGroupObjectiveListUrl(ref);
    }
}
