package com.ua.sdk.user.profilephoto;

import android.content.SharedPreferences;
import android.net.Uri;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.concurrent.SaveRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.MediaService;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import java.util.Locale;

public class UserProfilePhotoManagerImpl extends AbstractManager<UserProfilePhoto> implements UserProfilePhotoManager {
    protected static final String PREF_CURRENT_PICTURE_PROFILE_LAST_SAVED = "mmdk_user_last_saved";
    private final MediaService<UserProfilePhoto> mediaService;
    private final SharedPreferences sharedPreferences;

    /* JADX WARNING: type inference failed for: r7v0, types: [java.lang.Object, com.ua.sdk.internal.MediaService<com.ua.sdk.user.profilephoto.UserProfilePhoto>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public UserProfilePhotoManagerImpl(com.ua.sdk.internal.MediaService<com.ua.sdk.user.profilephoto.UserProfilePhoto> r7, com.ua.sdk.cache.CacheSettings r8, com.ua.sdk.cache.Cache r9, com.ua.sdk.cache.DiskCache<com.ua.sdk.user.profilephoto.UserProfilePhoto> r10, com.ua.sdk.internal.EntityService<com.ua.sdk.user.profilephoto.UserProfilePhoto> r11, java.util.concurrent.ExecutorService r12, android.content.SharedPreferences r13) {
        /*
            r6 = this;
            r0 = r6
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5)
            java.lang.String r0 = "mediaService"
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r7, r0)
            com.ua.sdk.internal.MediaService r0 = (com.ua.sdk.internal.MediaService) r0
            r6.mediaService = r0
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r13)
            android.content.SharedPreferences r0 = (android.content.SharedPreferences) r0
            r6.sharedPreferences = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.user.profilephoto.UserProfilePhotoManagerImpl.<init>(com.ua.sdk.internal.MediaService, com.ua.sdk.cache.CacheSettings, com.ua.sdk.cache.Cache, com.ua.sdk.cache.DiskCache, com.ua.sdk.internal.EntityService, java.util.concurrent.ExecutorService, android.content.SharedPreferences):void");
    }

    public UserProfilePhoto fetchCurrentProfilePhoto(EntityRef<UserProfilePhoto> ref) throws UaException {
        if (ref == null) {
            throw new UaException("ref can't be null");
        }
        String timestamp = this.sharedPreferences.getString(PREF_CURRENT_PICTURE_PROFILE_LAST_SAVED, "");
        UserProfilePhotoImpl userProfilePhoto = new UserProfilePhotoImpl();
        userProfilePhoto.setRef(ref);
        userProfilePhoto.setLargeImageURL(String.format(Locale.US, UrlBuilderImpl.GET_USER_PROFILE_PICTURE_DIRECT_URL, new Object[]{ref.getId(), "Large", timestamp}));
        userProfilePhoto.setMediumImageURL(String.format(Locale.US, UrlBuilderImpl.GET_USER_PROFILE_PICTURE_DIRECT_URL, new Object[]{ref.getId(), "Medium", timestamp}));
        userProfilePhoto.setSmallImageURL(String.format(Locale.US, UrlBuilderImpl.GET_USER_PROFILE_PICTURE_DIRECT_URL, new Object[]{ref.getId(), "Small", timestamp}));
        return userProfilePhoto;
    }

    public Request fetchCurrentProfilePhoto(EntityRef<UserProfilePhoto> ref, FetchCallback<UserProfilePhoto> callback) {
        return fetch((Reference) ref, callback);
    }

    public UserProfilePhoto updateUserProfilePhoto(Uri image, UserProfilePhoto entity) throws UaException {
        if (image == null) {
            throw new UaException("Uri image cannot be NULL!");
        } else if (entity != null) {
            return this.mediaService.uploadUserProfileImage(image, entity);
        } else {
            throw new UaException("User profile picture entity cannot be NULL!");
        }
    }

    public Request updateUserProfilePhoto(final Uri image, final UserProfilePhoto entity, SaveCallback<UserProfilePhoto> callback) {
        final SaveRequest<UserProfilePhoto> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                UaException error = null;
                UserProfilePhoto savedUserProfilePhoto = null;
                try {
                    savedUserProfilePhoto = UserProfilePhotoManagerImpl.this.updateUserProfilePhoto(image, entity);
                } catch (UaException e) {
                    UaLog.error("Failed to save user profile picture!", (Throwable) e);
                    error = e;
                }
                request.done(savedUserProfilePhoto, error);
            }
        }));
        return request;
    }

    /* access modifiers changed from: protected */
    public UserProfilePhoto onPostServiceSave(UserProfilePhoto entity) throws UaException {
        this.sharedPreferences.edit().putString(PREF_CURRENT_PICTURE_PROFILE_LAST_SAVED, String.valueOf(System.currentTimeMillis())).apply();
        return (UserProfilePhoto) super.onPostServiceSave(entity);
    }
}
