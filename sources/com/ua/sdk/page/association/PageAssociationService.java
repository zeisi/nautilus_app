package com.ua.sdk.page.association;

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

public class PageAssociationService extends AbstractResourceService<PageAssociation> {
    public PageAssociationService(ConnectionFactory connFactory, AuthenticationManager authManager, UrlBuilder urlBuilder, JsonParser<PageAssociation> pageAssociationParser, JsonParser<? extends EntityList<PageAssociation>> pageAssociationListParser, JsonWriter<PageAssociation> pageAssociationRequestWriter) {
        super(connFactory, authManager, urlBuilder, pageAssociationParser, pageAssociationRequestWriter, pageAssociationListParser);
    }

    /* access modifiers changed from: protected */
    public URL getCreateUrl() {
        return this.urlBuilder.buildCreatePageAssociationUrl();
    }

    /* access modifiers changed from: protected */
    public URL getFetchUrl(Reference ref) {
        return this.urlBuilder.buildGetPageAssociationUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getSaveUrl(PageAssociation resource) {
        throw new UnsupportedOperationException("Save PageAssociation is not supported.");
    }

    /* access modifiers changed from: protected */
    public URL getDeleteUrl(Reference ref) {
        return this.urlBuilder.buildDeletePageAssociationUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getFetchPageUrl(EntityListRef<PageAssociation> ref) {
        return this.urlBuilder.buildGetPageAssociationsUrl(ref);
    }

    /* access modifiers changed from: protected */
    public URL getPatchUrl(Reference ref) {
        throw new UnsupportedOperationException("Save PageAssociation is not supported.");
    }
}
