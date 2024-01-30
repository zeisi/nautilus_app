package com.ua.sdk.friendship;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.CreateRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.suggestedfriends.SuggestedFriends;
import com.ua.sdk.suggestedfriends.SuggestedFriendsManager;
import com.ua.sdk.user.User;
import com.ua.sdk.user.UserManager;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class FriendshipManagerImpl extends AbstractManager<Friendship> implements FriendshipManager {
    private SuggestedFriendsManager suggestedFriendsManager;
    private UserManager userManager;

    public FriendshipManagerImpl(UserManager userManager2, SuggestedFriendsManager suggestedFriendsManager2, CacheSettings cacheSettings, Cache memCache, DiskCache<Friendship> diskCache, EntityService<Friendship> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.userManager = (UserManager) Precondition.isNotNull(userManager2, "userManager");
        this.suggestedFriendsManager = (SuggestedFriendsManager) Precondition.isNotNull(suggestedFriendsManager2, "suggestedFriendsManager");
    }

    public EntityList<Friendship> fetchFriendList(EntityListRef<Friendship> listRef) throws UaException {
        return fetchPage(listRef);
    }

    public Request fetchFriendList(EntityListRef<Friendship> listRef, FetchCallback<EntityList<Friendship>> callback) {
        return fetchPage(listRef, callback);
    }

    public Friendship approvePendingFriendRequest(Friendship friendship) throws UaException {
        ((FriendshipImpl) friendship).setFriendshipStatus(FriendshipStatus.ACTIVE);
        return (Friendship) update(friendship);
    }

    public Request approvePendingFriendRequest(Friendship friendship, SaveCallback<Friendship> callback) {
        ((FriendshipImpl) friendship).setFriendshipStatus(FriendshipStatus.ACTIVE);
        return update(friendship, callback);
    }

    public Friendship approvePendingFriendRequest(FriendshipRef friendshipRef) throws UaException {
        FriendshipImpl friendship = new FriendshipImpl(friendshipRef);
        friendship.setFriendshipStatus(FriendshipStatus.ACTIVE);
        return (Friendship) update(friendship);
    }

    public Request approvePendingFriendRequest(FriendshipRef friendshipRef, SaveCallback<Friendship> callback) {
        FriendshipImpl friendship = new FriendshipImpl(friendshipRef);
        friendship.setFriendshipStatus(FriendshipStatus.ACTIVE);
        return update(friendship, callback);
    }

    public void deleteOrDenyFriendship(EntityRef<Friendship> friendship) throws UaException {
        delete(friendship);
    }

    public Request deleteOrDenyFriendship(EntityRef<Friendship> friendship, DeleteCallback<Reference> callback) {
        return delete(friendship, callback);
    }

    public Friendship createNewFriendRequest(EntityRef<User> toUser, String message) throws UaException {
        return (Friendship) create(getFriendshipBody(toUser, message));
    }

    public Request createNewFriendRequest(EntityRef<User> toUser, String message, CreateCallback<Friendship> callback) {
        return create(getFriendshipBody(toUser, message), callback);
    }

    public EntityList<Friendship> createNewFriendRequests(List<EntityRef<User>> toUsers, String message) throws UaException {
        return ((FriendshipService) this.service).patch(getFriendships(toUsers, message));
    }

    public Request createNewFriendRequests(final List<EntityRef<User>> toUsers, final String message, CreateCallback<EntityList<Friendship>> callback) {
        final CreateRequest<EntityList<Friendship>> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(FriendshipManagerImpl.this.createNewFriendRequests(toUsers, message), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    private FriendshipImpl getFriendships(List<EntityRef<User>> toUsers, String message) {
        FriendshipImpl impl = new FriendshipImpl();
        for (EntityRef<User> user : toUsers) {
            impl.addFriendship(getFriendshipBody(user, message));
        }
        return impl;
    }

    public EntityList<SuggestedFriends> fetchSuggestedFriendList(EntityListRef<SuggestedFriends> listRef) throws UaException {
        return this.suggestedFriendsManager.fetchSuggestedFriendList(listRef);
    }

    public Request fetchSuggestedFriendList(EntityListRef<SuggestedFriends> listRef, FetchCallback<EntityList<SuggestedFriends>> callback) {
        return this.suggestedFriendsManager.fetchSuggestedFriendList(listRef, callback);
    }

    public EntityList<User> fetchMutualFriendList(EntityListRef<User> listRef) throws UaException {
        return this.userManager.fetchUserList(listRef);
    }

    public Request fetchMutualFriendList(EntityListRef<User> listRef, FetchCallback<EntityList<User>> callback) {
        return this.userManager.fetchUserList(listRef, callback);
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.user.User>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.ua.sdk.friendship.FriendshipImpl getFriendshipBody(com.ua.sdk.EntityRef<com.ua.sdk.user.User> r8, java.lang.String r9) {
        /*
            r7 = this;
            com.ua.sdk.friendship.FriendshipImpl r0 = new com.ua.sdk.friendship.FriendshipImpl
            r0.<init>()
            com.ua.sdk.user.UserManager r4 = r7.userManager
            com.ua.sdk.EntityRef r1 = r4.getCurrentUserRef()
            java.lang.Object r1 = com.ua.sdk.internal.Precondition.isNotNull(r1)
            com.ua.sdk.EntityRef r1 = (com.ua.sdk.EntityRef) r1
            java.lang.Object r8 = com.ua.sdk.internal.Precondition.isNotNull(r8)
            com.ua.sdk.EntityRef r8 = (com.ua.sdk.EntityRef) r8
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            com.ua.sdk.internal.Link r4 = new com.ua.sdk.internal.Link
            java.lang.String r5 = r8.getHref()
            java.lang.String r6 = r8.getId()
            r4.<init>((java.lang.String) r5, (java.lang.String) r6)
            r3.add(r4)
            java.lang.String r4 = "to_user"
            r0.setLinksForRelation(r4, r3)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.ua.sdk.internal.Link r4 = new com.ua.sdk.internal.Link
            java.lang.String r5 = r1.getHref()
            java.lang.String r6 = r1.getId()
            r4.<init>((java.lang.String) r5, (java.lang.String) r6)
            r2.add(r4)
            java.lang.String r4 = "from_user"
            r0.setLinksForRelation(r4, r2)
            r0.setMessage(r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.friendship.FriendshipManagerImpl.getFriendshipBody(com.ua.sdk.EntityRef, java.lang.String):com.ua.sdk.friendship.FriendshipImpl");
    }
}
