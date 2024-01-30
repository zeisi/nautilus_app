package com.ua.sdk.page.association;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.page.Page;
import java.util.concurrent.ExecutorService;

public class PageAssociationManagerImpl extends AbstractManager<PageAssociation> implements PageAssociationManager {
    public PageAssociationManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<PageAssociation> diskCache, EntityService<PageAssociation> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
    }

    public EntityList<PageAssociation> fetchPageAssociationList(EntityListRef<PageAssociation> pageAssociationRef) throws UaException {
        return fetchPage(pageAssociationRef);
    }

    public Request fetchPageAssociationList(EntityListRef<PageAssociation> pageAssociationRef, FetchCallback<EntityList<PageAssociation>> callback) {
        return fetchPage(pageAssociationRef, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.page.association.PageAssociation>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.page.association.PageAssociation fetchPageAssociation(com.ua.sdk.EntityRef<com.ua.sdk.page.association.PageAssociation> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.page.association.PageAssociation r0 = (com.ua.sdk.page.association.PageAssociation) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.page.association.PageAssociationManagerImpl.fetchPageAssociation(com.ua.sdk.EntityRef):com.ua.sdk.page.association.PageAssociation");
    }

    public Request fetchPageAssociation(EntityRef<PageAssociation> ref, FetchCallback<PageAssociation> callback) {
        return fetch((Reference) ref, callback);
    }

    public PageAssociation createPageAssociation(EntityRef<Page> fromPage, EntityRef<Page> toPage) throws UaException {
        return (PageAssociation) this.service.create(buildPageAssociation(fromPage, toPage));
    }

    public Request createPageAssociation(EntityRef<Page> entityRef, EntityRef<Page> entityRef2, CreateCallback<Page> createCallback) {
        return null;
    }

    private PageAssociation buildPageAssociation(EntityRef<Page> fromPage, EntityRef<Page> toPage) {
        PageAssociationImpl pageAssociation = new PageAssociationImpl();
        pageAssociation.setLink("from_page", new Link(((LinkEntityRef) fromPage).getHref(), fromPage.getId()));
        pageAssociation.setLink("to_page", new Link(((LinkEntityRef) toPage).getHref(), toPage.getId()));
        return pageAssociation;
    }
}
