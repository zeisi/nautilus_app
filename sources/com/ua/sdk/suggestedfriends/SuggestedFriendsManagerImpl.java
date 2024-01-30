package com.ua.sdk.suggestedfriends;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
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
import com.ua.sdk.user.profilephoto.UserProfilePhotoImpl;
import com.ua.sdk.user.profilephoto.UserProfilePhotoManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SuggestedFriendsManagerImpl extends AbstractManager<SuggestedFriends> implements SuggestedFriendsManager {
    private UserProfilePhotoManager userProfilePhotoManager;

    public SuggestedFriendsManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<SuggestedFriends> diskCache, EntityService<SuggestedFriends> service, ExecutorService executor, UserProfilePhotoManager userProfilePhotoManager2) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.userProfilePhotoManager = userProfilePhotoManager2;
    }

    private void fetchUserProfilePhoto(SuggestedFriendsImpl suggestedFriends) throws UaException {
        List<Link> profilePictureLink = suggestedFriends.getLinks(SuggestedFriendsImpl.REF_PROFILE_PIC);
        if (profilePictureLink != null) {
            suggestedFriends.setSuggestedFriendsProfilePicture(((UserProfilePhotoImpl) this.userProfilePhotoManager.fetchCurrentProfilePhoto(new LinkEntityRef<>(profilePictureLink.get(0).getId(), profilePictureLink.get(0).getHref()))).toImageUrl());
        }
    }

    public EntityList<SuggestedFriends> fetchSuggestedFriendList(EntityListRef<SuggestedFriends> listRef) throws UaException {
        return fetchPage(listRef);
    }

    public Request fetchSuggestedFriendList(EntityListRef<SuggestedFriends> listRef, FetchCallback<EntityList<SuggestedFriends>> callback) {
        return fetchPage(listRef, callback);
    }

    /* access modifiers changed from: protected */
    public EntityList<SuggestedFriends> onPostServiceFetchPage(Reference ref, EntityList<SuggestedFriends> list) throws UaException {
        Iterator i$ = ((SuggestedFriendsListImpl) list).getElements().iterator();
        while (i$.hasNext()) {
            fetchUserProfilePhoto((SuggestedFriendsImpl) ((SuggestedFriends) i$.next()));
        }
        return list;
    }
}
