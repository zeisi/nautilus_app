package com.ua.sdk.activitystory;

import android.net.Uri;
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
import com.ua.sdk.UploadCallback;
import com.ua.sdk.activitystory.ActivityStoryActor;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryRpcPostObject;
import com.ua.sdk.activitystory.Attachment;
import com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl;
import com.ua.sdk.activitystory.object.ActivityStoryCommentObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryLikeObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryPrivacyObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryRepostObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryRouteObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryStatusObjectImpl;
import com.ua.sdk.activitystory.object.ActivityStoryWorkoutObjectImpl;
import com.ua.sdk.cache.Cache;
import com.ua.sdk.cache.CacheSettings;
import com.ua.sdk.cache.DiskCache;
import com.ua.sdk.concurrent.CreateRequest;
import com.ua.sdk.concurrent.EntityEventHandler;
import com.ua.sdk.concurrent.SaveRequest;
import com.ua.sdk.concurrent.SynchronousRequest;
import com.ua.sdk.concurrent.UploadRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.EntityService;
import com.ua.sdk.internal.MediaService;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.privacy.PrivacyHelper;
import com.ua.sdk.user.User;
import com.ua.sdk.user.UserManager;
import com.ua.sdk.user.profilephoto.UserProfilePhotoImpl;
import com.ua.sdk.user.profilephoto.UserProfilePhotoManager;
import com.ua.sdk.user.profilephoto.UserProfilePhotoRef;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class ActivityStoryManagerImpl extends AbstractManager<ActivityStory> implements ActivityStoryManager {
    /* access modifiers changed from: private */
    public final UserManager mUserManager;
    private final UserProfilePhotoManager mUserProfilePhotoManager;
    /* access modifiers changed from: private */
    public final MediaService<ActivityStory> mediaService;

    public ActivityStoryManagerImpl(UserManager userManager, UserProfilePhotoManager userProfilePhotoManager, MediaService mediaService2, CacheSettings cacheSettings, Cache memCache, DiskCache<ActivityStory> diskCache, EntityService<ActivityStory> service, ExecutorService executor) {
        super(cacheSettings, memCache, diskCache, service, executor);
        this.mUserManager = (UserManager) Precondition.isNotNull(userManager, "userManager");
        this.mUserProfilePhotoManager = (UserProfilePhotoManager) Precondition.isNotNull(userProfilePhotoManager, "userProfilePhotoManager");
        this.mediaService = (MediaService) Precondition.isNotNull(mediaService2, "mediaService");
    }

    public Request fetchActivityStoryList(EntityListRef<ActivityStory> ref, FetchCallback<EntityList<ActivityStory>> callback) {
        return fetchPage(ref, callback);
    }

    public EntityList<ActivityStory> fetchActivityStoryList(EntityListRef<ActivityStory> ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchActivityStory(EntityRef<ActivityStory> ref, FetchCallback<ActivityStory> callback) {
        return fetch((Reference) ref, callback);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.ua.sdk.Reference, com.ua.sdk.EntityRef<com.ua.sdk.activitystory.ActivityStory>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.activitystory.ActivityStory fetchActivityStory(com.ua.sdk.EntityRef<com.ua.sdk.activitystory.ActivityStory> r2) throws com.ua.sdk.UaException {
        /*
            r1 = this;
            com.ua.sdk.Resource r0 = r1.fetch(r2)
            com.ua.sdk.activitystory.ActivityStory r0 = (com.ua.sdk.activitystory.ActivityStory) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.activitystory.ActivityStoryManagerImpl.fetchActivityStory(com.ua.sdk.EntityRef):com.ua.sdk.activitystory.ActivityStory");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0047, code lost:
        throw new java.lang.IllegalArgumentException("story object needs to be like or comment.");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.ua.sdk.activitystory.ActivityStoryImpl newPost(com.ua.sdk.activitystory.ActivityStoryObject r5, com.ua.sdk.EntityRef<com.ua.sdk.user.User> r6, com.ua.sdk.EntityRef<com.ua.sdk.activitystory.ActivityStory> r7, com.ua.sdk.activitystory.ActivityStoryActor r8) {
        /*
            r4 = this;
            java.lang.String r2 = "object"
            com.ua.sdk.internal.Precondition.isNotNull(r5, r2)
            java.lang.String r2 = "currentUserRef"
            com.ua.sdk.internal.Precondition.isNotNull(r6, r2)
            com.ua.sdk.activitystory.ActivityStoryImpl r0 = new com.ua.sdk.activitystory.ActivityStoryImpl
            r0.<init>()
            if (r8 != 0) goto L_0x0048
            com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl r2 = new com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl
            r2.<init>()
            r0.mActor = r2
            com.ua.sdk.activitystory.ActivityStoryActor r2 = r0.mActor
            com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl r2 = (com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl) r2
            r2.setUser(r6)
        L_0x001f:
            r0.mObject = r5
            if (r7 == 0) goto L_0x0031
            com.ua.sdk.activitystory.target.ActivityStoryUnknownTarget r1 = new com.ua.sdk.activitystory.target.ActivityStoryUnknownTarget
            r1.<init>()
            java.lang.String r2 = r7.getId()
            r1.setId(r2)
            r0.mTarget = r1
        L_0x0031:
            int[] r2 = com.ua.sdk.activitystory.ActivityStoryManagerImpl.AnonymousClass11.$SwitchMap$com$ua$sdk$activitystory$ActivityStoryObject$Type
            com.ua.sdk.activitystory.ActivityStoryObject$Type r3 = r5.getType()
            int r3 = r3.ordinal()
            r2 = r2[r3]
            switch(r2) {
                case 1: goto L_0x004b;
                case 2: goto L_0x0050;
                case 3: goto L_0x0055;
                default: goto L_0x0040;
            }
        L_0x0040:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "story object needs to be like or comment."
            r2.<init>(r3)
            throw r2
        L_0x0048:
            r0.mActor = r8
            goto L_0x001f
        L_0x004b:
            com.ua.sdk.activitystory.ActivityStoryVerb r2 = com.ua.sdk.activitystory.ActivityStoryVerb.LIKE
            r0.mVerb = r2
        L_0x004f:
            return r0
        L_0x0050:
            com.ua.sdk.activitystory.ActivityStoryVerb r2 = com.ua.sdk.activitystory.ActivityStoryVerb.COMMENT
            r0.mVerb = r2
            goto L_0x004f
        L_0x0055:
            com.ua.sdk.activitystory.ActivityStoryVerb r2 = com.ua.sdk.activitystory.ActivityStoryVerb.POST
            r0.mVerb = r2
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.activitystory.ActivityStoryManagerImpl.newPost(com.ua.sdk.activitystory.ActivityStoryObject, com.ua.sdk.EntityRef, com.ua.sdk.EntityRef, com.ua.sdk.activitystory.ActivityStoryActor):com.ua.sdk.activitystory.ActivityStoryImpl");
    }

    public ActivityStory createLike(EntityRef<ActivityStory> targetStory, ActivityStoryActor actor) throws UaException {
        return (ActivityStory) create(newPost(new ActivityStoryLikeObjectImpl(), this.mUserManager.getCurrentUser().getRef(), targetStory, actor));
    }

    public Request createLike(final EntityRef<ActivityStory> targetStory, final ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.createLike(targetStory, actor), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public ActivityStory createComment(String text, EntityRef<ActivityStory> targetStory, ActivityStoryActor actor) throws UaException {
        return (ActivityStory) create(newPost(new ActivityStoryCommentObjectImpl(text), this.mUserManager.getCurrentUser().getRef(), targetStory, actor));
    }

    public Request createComment(String text, EntityRef<ActivityStory> targetStory, ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        final String str = text;
        final EntityRef<ActivityStory> entityRef = targetStory;
        final ActivityStoryActor activityStoryActor = actor;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.createComment(str, entityRef, activityStoryActor), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public ActivityStory createStatus(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor) throws UaException {
        return createStatus(text, privacy, socialSettings, actor, (ActivityStoryTarget) null);
    }

    public ActivityStory createStatus(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target) throws UaException {
        ActivityStoryImpl status = initStatus(text, privacy, socialSettings, actor);
        if (target != null) {
            status.mTarget = target;
        }
        return (ActivityStory) create(status);
    }

    public ActivityStory createStatusWithImage(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor) throws UaException {
        return createStatusWithImage(text, privacy, socialSettings, actor, (ActivityStoryTarget) null);
    }

    public ActivityStory createStatusWithImage(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target) throws UaException {
        ActivityStoryImpl status = initStatus(text, privacy, socialSettings, actor);
        status.mAttachments = new Attachments();
        status.mAttachments.addAttachment(Attachment.Type.PHOTO);
        if (target != null) {
            status.mTarget = target;
        }
        return (ActivityStory) create(status);
    }

    public ActivityStory createStatusWithVideo(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor) throws UaException {
        return createStatusWithVideo(text, privacy, socialSettings, actor, (ActivityStoryTarget) null);
    }

    public ActivityStory createStatusWithVideo(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target) throws UaException {
        ActivityStoryImpl status = initStatus(text, privacy, socialSettings, actor);
        status.mAttachments = new Attachments();
        status.mAttachments.addAttachment(Attachment.Type.VIDEO);
        if (target != null) {
            status.mTarget = target;
        }
        return (ActivityStory) create(status);
    }

    public void cancelUpload() throws IOException {
        this.mediaService.close();
    }

    public Request createStatus(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        return createStatus(text, privacy, socialSettings, actor, (ActivityStoryTarget) null, callback);
    }

    public Request createStatus(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        final String str = text;
        final Privacy.Level level = privacy;
        final SocialSettings socialSettings2 = socialSettings;
        final ActivityStoryActor activityStoryActor = actor;
        final ActivityStoryTarget activityStoryTarget = target;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.createStatus(str, level, socialSettings2, activityStoryActor, activityStoryTarget), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public ActivityStory repostStatus(ActivityStory activityStory, String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor) throws UaException {
        ActivityStoryImpl status = initStatus(text, privacy, socialSettings, actor);
        status.mObject = new ActivityStoryRepostObjectImpl(activityStory.getId(), text, PrivacyHelper.getPrivacy(privacy));
        status.mVerb = ActivityStoryVerb.REPOST;
        ActivityStory story = (ActivityStory) create(status);
        if (story != null) {
            ((ActivityStoryRepostObjectImpl) story.getObject()).setOriginalStory(activityStory);
        }
        return story;
    }

    public Request repostStatus(ActivityStory activityStory, String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        final ActivityStory activityStory2 = activityStory;
        final String str = text;
        final Privacy.Level level = privacy;
        final SocialSettings socialSettings2 = socialSettings;
        final ActivityStoryActor activityStoryActor = actor;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.repostStatus(activityStory2, str, level, socialSettings2, activityStoryActor), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public Request createStatusWithImage(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        return createStatusWithImage(text, privacy, socialSettings, actor, (ActivityStoryTarget) null, callback);
    }

    public Request createStatusWithImage(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        final String str = text;
        final Privacy.Level level = privacy;
        final SocialSettings socialSettings2 = socialSettings;
        final ActivityStoryActor activityStoryActor = actor;
        final ActivityStoryTarget activityStoryTarget = target;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.createStatusWithImage(str, level, socialSettings2, activityStoryActor, activityStoryTarget), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public Request createStatusWithVideo(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, CreateCallback<ActivityStory> callback) {
        return createStatusWithVideo(text, privacy, socialSettings, actor, (ActivityStoryTarget) null, callback);
    }

    public Request createStatusWithVideo(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor, ActivityStoryTarget target, CreateCallback<ActivityStory> callback) {
        final CreateRequest<ActivityStory> request = new CreateRequest<>(callback);
        final String str = text;
        final Privacy.Level level = privacy;
        final SocialSettings socialSettings2 = socialSettings;
        final ActivityStoryActor activityStoryActor = actor;
        final ActivityStoryTarget activityStoryTarget = target;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.createStatusWithVideo(str, level, socialSettings2, activityStoryActor, activityStoryTarget), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public Request attachImageWithStatus(ActivityStory story, Uri image, UploadCallback callback) {
        final UploadRequest request = new UploadRequest(callback);
        final ActivityStory activityStory = story;
        final Uri uri = image;
        final UploadCallback uploadCallback = callback;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    ActivityStoryManagerImpl.this.mediaService.uploadImage(uri, new AttachmentDest(activityStory.getRef().getHref(), "attachments", 0), uploadCallback);
                    request.done(activityStory, (UaException) null);
                } catch (UaException error) {
                    request.done(activityStory, error);
                }
            }
        }));
        return request;
    }

    public Request attachVideoWithStatus(ActivityStory story, Uri video, UploadCallback callback) {
        final UploadRequest request = new UploadRequest(callback);
        final ActivityStory activityStory = story;
        final Uri uri = video;
        final UploadCallback uploadCallback = callback;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    ActivityStoryManagerImpl.this.mediaService.uploadVideo(uri, new AttachmentDest(activityStory.getRef().getHref(), "attachments", 0, ActivityStoryManagerImpl.this.mUserManager.getCurrentUserRef().getId()), uploadCallback);
                    request.done(activityStory, (UaException) null);
                } catch (UaException error) {
                    request.done(activityStory, error);
                }
            }
        }));
        return request;
    }

    private ActivityStoryImpl initStatus(String text, Privacy.Level privacy, SocialSettings socialSettings, ActivityStoryActor actor) throws UaException {
        EntityRef<User> currentUserRef = this.mUserManager.getCurrentUser().getRef();
        ActivityStoryImpl status = new ActivityStoryImpl();
        if (actor == null) {
            actor = new ActivityStoryUserActorImpl(currentUserRef.getId());
        }
        status.mActor = actor;
        status.mObject = new ActivityStoryStatusObjectImpl(text, PrivacyHelper.getPrivacy(privacy));
        status.mVerb = ActivityStoryVerb.POST;
        if (socialSettings != null) {
            status.mSharingSettngs = socialSettings;
        }
        return status;
    }

    public EntityRef<ActivityStory> deleteStory(EntityRef<ActivityStory> storyRef) throws UaException {
        return (EntityRef) delete(storyRef);
    }

    public Request deleteStory(EntityRef<ActivityStory> storyRef, DeleteCallback<EntityRef<ActivityStory>> callback) {
        return delete(storyRef, callback);
    }

    public Request deleteLike(ActivityStory targetStory, DeleteCallback<EntityRef<ActivityStory>> callback) {
        if (targetStory.isLikedByCurrentUser()) {
            return deleteStory(targetStory.getLikesSummary().getReplyRef(), callback);
        }
        EntityEventHandler.callOnDeleted(null, new UaException("Story is not liked by the current user."), callback);
        return SynchronousRequest.INSTANCE;
    }

    public ActivityStory updateStoryPrivacy(ActivityStory targetStory, Privacy.Level newPrivacyLevel) throws UaException {
        ActivityStoryObject.Type type = targetStory.getObject().getType();
        switch (type) {
            case STATUS:
            case REPOST:
            case WORKOUT:
                ActivityStoryImpl impl = new ActivityStoryImpl();
                impl.mObject = new ActivityStoryPrivacyObjectImpl.Builder(type, newPrivacyLevel).build();
                return (ActivityStory) patch(impl, targetStory.getRef());
            case ROUTE:
                return (ActivityStory) update(new ActivityStoryRpcPostObject.Builder(PrivacyHelper.getPrivacy(newPrivacyLevel)).setLink(String.format(UrlBuilderImpl.PATCH_ROUTE, new Object[]{((ActivityStoryRouteObjectImpl) targetStory.getObject()).getRouteId()})).build());
            default:
                throw new UaException("Attempted to update privacy on a privacy story type that is incompatible");
        }
    }

    public Request updateStoryPrivacy(final ActivityStory targetStory, final Privacy.Level newPrivacyLevel, SaveCallback<ActivityStory> callback) {
        final SaveRequest<ActivityStory> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.updateStoryPrivacy(targetStory, newPrivacyLevel), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    public ActivityStory updateStoryStatus(ActivityStory targetStory, String status) throws UaException {
        ActivityStoryObject.Type type = targetStory.getObject().getType();
        ActivityStoryImpl impl = new ActivityStoryImpl();
        switch (type) {
            case STATUS:
                impl.mObject = new ActivityStoryStatusObjectImpl(status, (Privacy) null);
                break;
            case REPOST:
                impl.mObject = new ActivityStoryRepostObjectImpl((String) null, status, (Privacy) null);
                break;
            case WORKOUT:
                impl.mObject = new ActivityStoryWorkoutObjectImpl(status);
                break;
            default:
                throw new UaException("Attempted to update status on a story type that is not compatible");
        }
        return (ActivityStory) patch(impl, targetStory.getRef());
    }

    public Request updateStoryStatus(final ActivityStory targetStory, final String status, SaveCallback<ActivityStory> callback) {
        final SaveRequest<ActivityStory> request = new SaveRequest<>(callback);
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    request.done(ActivityStoryManagerImpl.this.updateStoryStatus(targetStory, status), (UaException) null);
                } catch (UaException e) {
                    request.done(null, e);
                }
            }
        }));
        return request;
    }

    /* access modifiers changed from: protected */
    public ActivityStory onPostServiceFetch(Reference ref, ActivityStory entity) throws UaException {
        if (entity == null) {
            return null;
        }
        ActivityStoryImpl storyImpl = (ActivityStoryImpl) entity;
        if (storyImpl.getActor() == null || storyImpl.getActor().getType() != ActivityStoryActor.Type.USER) {
            return storyImpl;
        }
        ((ActivityStoryUserActorImpl) storyImpl.getActor()).setUserProfilePicture(((UserProfilePhotoImpl) this.mUserProfilePhotoManager.fetchCurrentProfilePhoto(UserProfilePhotoRef.getBuilder().setId(((ActivityStoryUserActorImpl) storyImpl.getActor()).getId()).build())).toImageUrl());
        return storyImpl;
    }

    /* access modifiers changed from: protected */
    public EntityList<ActivityStory> onPostServiceFetchPage(Reference ref, EntityList<ActivityStory> list) throws UaException {
        if (list == null) {
            return null;
        }
        for (ActivityStory story : list.getAll()) {
            onPostServiceFetch(ref, story);
        }
        return list;
    }
}
