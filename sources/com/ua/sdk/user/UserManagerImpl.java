package com.ua.sdk.user;

import android.content.SharedPreferences;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.NetworkError;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CachePolicy;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.profilephoto.UserProfilePhotoImpl;
import com.ua.sdk.user.profilephoto.UserProfilePhotoManager;
import com.ua.sdk.user.profilephoto.UserProfilePhotoRef;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class UserManagerImpl extends AbstractManager<User> implements UserManager {
    protected static final String PREF_CURRENT_USER_HREF = "mmdk_user_href";
    protected static final String PREF_CURRENT_USER_ID = "mmdk_user_id";
    protected static final long THREAD_WAIT_DELAY = 1000;
    protected AuthenticationManager mAuthManager;
    protected User mCurrentUser;
    protected EntityRef<User> mCurrentUserRef;
    protected final SharedPreferences mSharedPrefs;
    protected UserProfilePhotoManager mUserProfilePhotoManager;
    protected UserService mUserService;

    public UserManagerImpl(CacheSettings cacheSettings, Cache memCache, DiskCache<User> diskCache, EntityService<User> service, ExecutorService executor, AuthenticationManager authManager, UserProfilePhotoManager userProfilePhotoManager, SharedPreferences sharedPreferences) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.mSharedPrefs = (SharedPreferences) Precondition.isNotNull(sharedPreferences);
        this.mAuthManager = (AuthenticationManager) Precondition.isNotNull(authManager);
        this.mUserProfilePhotoManager = (UserProfilePhotoManager) Precondition.isNotNull(userProfilePhotoManager);
        this.mUserService = (UserService) service;
        if (getCurrentUserRef() != null) {
            try {
                this.mCurrentUser = (User) fetch(getCurrentUserRef(), CachePolicy.CACHE_ONLY_IGNORE_MAX_AGE);
            } catch (UaException e) {
                UaLog.error("Failed to get current user from cache.", (Throwable) e);
            }
        }
    }

    public EntityRef<User> getCurrentUserRef() {
        if (this.mCurrentUserRef != null) {
            return this.mCurrentUserRef;
        }
        String currentUserId = this.mSharedPrefs.getString(PREF_CURRENT_USER_ID, (String) null);
        String currentUserHref = this.mSharedPrefs.getString(PREF_CURRENT_USER_HREF, (String) null);
        if (currentUserId != null) {
            this.mCurrentUserRef = new LinkEntityRef(currentUserId, currentUserHref);
        }
        return this.mCurrentUserRef;
    }

    private void setCurrentUser(User user) {
        if (user != null) {
            this.mCurrentUser = user;
            this.mCurrentUserRef = user.getRef();
            String id = this.mCurrentUserRef.getId();
            String href = this.mCurrentUserRef.getHref();
            if (id == null || href == null) {
                onLogout();
            } else {
                this.mSharedPrefs.edit().putString(PREF_CURRENT_USER_ID, id).putString(PREF_CURRENT_USER_HREF, href).commit();
            }
        } else {
            onLogout();
        }
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }

    public User updateUser(User user) throws UaException {
        return (User) update(user);
    }

    public Request updateUser(User user, SaveCallback<User> callback) {
        return update(user, callback);
    }

    /* access modifiers changed from: protected */
    public User onPostServiceSave(User entity) throws UaException {
        UserImpl user = (UserImpl) entity;
        fetchUserProfilePhoto(user);
        if (isCurrentUserRef(entity.getRef())) {
            setCurrentUser(user);
        }
        return user;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.EntityRef<com.ua.sdk.user.User>, com.ua.sdk.Reference] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.user.User fetchUser(com.ua.sdk.EntityRef<com.ua.sdk.user.User> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.user.User r0 = (com.ua.sdk.user.User) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.user.UserManagerImpl.fetchUser(com.ua.sdk.EntityRef):com.ua.sdk.user.User");
    }

    public Request fetchUser(EntityRef<User> ref, FetchCallback<User> callback) {
        return fetch((Reference) ref, callback);
    }

    public Request fetchUser(EntityRef<User> ref, CachePolicy cachePolicy, FetchCallback<User> callback) {
        return fetch((Reference) ref, cachePolicy, callback);
    }

    private boolean isCurrentUserRef(Reference ref) {
        if (ref == null || ref.getId() == null) {
            return false;
        }
        if ((ref instanceof CurrentUserRef) || ref.getId().equalsIgnoreCase("self")) {
            return true;
        }
        Reference currentUserRef = getCurrentUserRef();
        if (currentUserRef == null || !ref.getId().equals(currentUserRef.getId())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public User onPostServiceFetch(Reference ref, User user) throws UaException {
        if (isCurrentUserRef(ref)) {
            setCurrentUser(user);
        }
        fetchUserProfilePhoto((UserImpl) user);
        return user;
    }

    private void fetchUserProfilePhoto(UserImpl userImpl) throws UaException {
        if (userImpl.getLinks("image") != null) {
            userImpl.setUserProfilePhoto(((UserProfilePhotoImpl) this.mUserProfilePhotoManager.fetchCurrentProfilePhoto(UserProfilePhotoRef.getBuilder().setId(userImpl.getId()).build())).toImageUrl());
        }
    }

    /* access modifiers changed from: protected */
    public User onPostServiceCreate(User createdEntity) throws UaException {
        UserImpl userImpl = (UserImpl) createdEntity;
        this.mAuthManager.setOAuth2Credentials(userImpl.getOauth2Credentials());
        setCurrentUser(userImpl);
        fetchUserProfilePhoto(userImpl);
        return userImpl;
    }

    public EntityList<User> fetchUserList(EntityListRef<User> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchUserList(EntityListRef<User> ref, FetchCallback<EntityList<User>> callback) {
        return fetchPage(ref, callback);
    }

    public Request fetchUserList(EntityListRef<User> ref, CachePolicy cachePolicy, FetchCallback<EntityList<User>> callback) {
        return fetchPage(ref, cachePolicy, callback);
    }

    /* access modifiers changed from: protected */
    public EntityList<User> onPostServiceFetchPage(Reference ref, EntityList<User> list) throws UaException {
        Iterator i$ = ((UserListImpl) list).getElements().iterator();
        while (i$.hasNext()) {
            fetchUserProfilePhoto((UserImpl) ((User) i$.next()));
        }
        return list;
    }

    public User newUser() {
        return new UserImpl();
    }

    public void onLogout() {
        this.mCurrentUser = null;
        this.mCurrentUserRef = null;
        this.mSharedPrefs.edit().remove(PREF_CURRENT_USER_ID).remove(PREF_CURRENT_USER_HREF).commit();
    }

    /* access modifiers changed from: protected */
    public EntityList<User> fetchPage(EntityListRef<User> ref, CachePolicy cachePolicy, boolean includeMemCache) throws UaException {
        return fetchPage(ref, cachePolicy, includeMemCache, 0);
    }

    /* access modifiers changed from: protected */
    public EntityList<User> fetchPage(EntityListRef<User> ref, CachePolicy cachePolicy, boolean includeMemCache, int recursiveCounter) throws UaException {
        try {
            return super.fetchPage(ref, cachePolicy, includeMemCache);
        } catch (NetworkError e) {
            if (e.getResponseCode() == 202 && recursiveCounter < 5) {
                try {
                    Thread.sleep(1000);
                    return fetchPage(ref, cachePolicy, false, recursiveCounter + 1);
                } catch (InterruptedException e1) {
                    throw new UaException((Throwable) e1);
                }
            } else if (cachePolicy.checkCache()) {
                return fetchPage(ref, CachePolicy.CACHE_ONLY, includeMemCache);
            } else {
                throw e;
            }
        }
    }
}
