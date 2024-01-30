package com.ua.sdk.moderation;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.JsonWriter;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class ModerationActionService extends AbstractResourceService<ModerationAction> {
    public ModerationActionService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<ModerationAction> jsonParser, JsonWriter<ModerationAction> jsonWriter) {
        super(connFactory, authManager, urlBuilder, jsonParser, jsonWriter, (JsonParser) null);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreateModerationActionUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        throw new UnsupportedOperationException("Fetch ModerationAction is not supported");
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(ModerationAction resource) {
        throw new UnsupportedOperationException("Save ModerationAction is not supported");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete ModerationAction is not supported");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch ModerationAction is not supported");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<ModerationAction> entityListRef) {
        throw new UnsupportedOperationException("Fetch ModerationActions is not supported");
    }
}
