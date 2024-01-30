package com.ua.sdk.page;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.page.association.PageAssociation;
import com.ua.sdk.page.association.PageAssociationRef;
import com.ua.sdk.page.follow.PageFollow;
import com.ua.sdk.page.follow.PageFollowManager;
import com.ua.sdk.user.User;
import java.util.List;

public class PageSuperManager implements PageManager {
    private final PageFollowManager pageFollowManager;
    private final PageManagerImpl pageManager;

    public PageSuperManager(PageManagerImpl pageManager2, PageFollowManager pageFollowManager2) {
        this.pageManager = pageManager2;
        this.pageFollowManager = pageFollowManager2;
    }

    public Page fetchPage(EntityRef<Page> ref) throws UaException {
        return this.pageManager.fetchPage(ref);
    }

    public Request fetchPage(EntityRef<Page> ref, FetchCallback<Page> callback) {
        return this.pageManager.fetchPage(ref, callback);
    }

    public EntityList<PageType> fetchPageTypeList() throws UaException {
        return null;
    }

    public Request fetchPageTypeList(FetchCallback<EntityList<PageType>> fetchCallback) {
        return null;
    }

    public PageType fetchPageType(EntityRef<PageType> entityRef) throws UaException {
        return null;
    }

    public Request fetchPageType(EntityRef<PageType> entityRef, FetchCallback<PageType> fetchCallback) {
        return null;
    }

    public EntityList<PageAssociation> fetchPageAssociation(PageAssociationRef ref) throws UaException {
        return null;
    }

    public Request fetchPageAssociation(PageAssociationRef ref, FetchCallback<EntityList<PageAssociation>> fetchCallback) {
        return null;
    }

    public EntityList<PageFollow> fetchPageFollowList(EntityListRef<PageFollow> ref) throws UaException {
        return this.pageFollowManager.fetchPageFollowList(ref);
    }

    public Request fetchPageFollowList(EntityListRef<PageFollow> ref, FetchCallback<EntityList<PageFollow>> callback) {
        return this.pageFollowManager.fetchPageFollowList(ref, callback);
    }

    public EntityList<PageFollow> fetchIsFollowingPage(EntityRef<User> user, EntityRef<Page> page) throws UaException {
        return this.pageFollowManager.fetchIsFollowingPage(user, page);
    }

    public Request fetchIsFollowingPage(EntityRef<User> user, EntityRef<Page> page, FetchCallback<EntityList<PageFollow>> callback) {
        return this.pageFollowManager.fetchIsFollowingPage(user, page, callback);
    }

    public void deletePageFollow(EntityRef<PageFollow> pageFollowRef) throws UaException {
        this.pageFollowManager.deletePageFollow(pageFollowRef);
    }

    public Request deletePageFollow(EntityRef<PageFollow> pageFollowRef, DeleteCallback<Reference> callback) {
        return this.pageFollowManager.deletePageFollow(pageFollowRef, callback);
    }

    public PageFollow createPageFollow(EntityRef<Page> pageRef, EntityRef<User> userRef) throws UaException {
        return this.pageFollowManager.createPageFollow(pageRef, userRef);
    }

    public Request createPageFollow(EntityRef<Page> pageRef, EntityRef<User> userRef, CreateCallback<PageFollow> callback) {
        return this.pageFollowManager.createPageFollow(pageRef, userRef, callback);
    }

    public EntityList<PageFollow> createPageFollows(List<EntityRef<Page>> pageRefs, EntityRef<User> userRef) throws UaException {
        return this.pageFollowManager.createPageFollows(pageRefs, userRef);
    }

    public Request createPageFollows(List<EntityRef<Page>> pageRefs, EntityRef<User> userRef, CreateCallback<EntityList<PageFollow>> callback) {
        return this.pageFollowManager.createPageFollows(pageRefs, userRef, callback);
    }

    public EntityList<Page> fetchPageList(EntityListRef<Page> ref) throws UaException {
        return this.pageManager.fetchPageList(ref);
    }

    public Request fetchPageList(EntityListRef<Page> ref, FetchCallback<EntityList<Page>> callback) {
        return this.pageManager.fetchPageList(ref, callback);
    }
}
