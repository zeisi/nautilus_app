package com.ua.sdk.actigraphysettings;

import com.ua.sdk.EntityListRef;
import com.ua.sdk.Reference;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.internal.AbstractResourceService;
import com.ua.sdk.internal.ConnectionFactory;
import com.ua.sdk.internal.JsonParser;
import com.ua.sdk.internal.net.UrlBuilder;
import java.net.URL;

public class ActigraphySettingsService extends AbstractResourceService<ActigraphySettings> {
    public ActigraphySettingsService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, ActigraphySettingsJsonParser actigraphySettingsParser, ActigraphySettingsRequestWriter actigraphySettingsRequestWriter) {
        super(connFactory, authManager, urlBuilder, actigraphySettingsParser, actigraphySettingsRequestWriter, (JsonParser) null);
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetActigraphySettingsUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildGetActigraphyRecorderPriorityUrl();
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(ActigraphySettings resource) {
        throw new UnsupportedOperationException("Save ActigraphySettings is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        throw new UnsupportedOperationException("Delete ActigraphySettings is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<ActigraphySettings> entityListRef) {
        throw new UnsupportedOperationException("Fetch ActigraphySettings page is unsupported.");
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Patch ActigraphySettings is unsupported.");
    }
}
