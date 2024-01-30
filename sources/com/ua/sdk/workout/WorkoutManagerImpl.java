package com.ua.sdk.workout;

import android.net.Uri;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.DeleteCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.MultipleCreateCallback;
import com.ua.sdk.Reference;
import com.ua.sdk.Request;
import com.ua.sdk.SaveCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.UploadCallback;
import com.ua.sdk.activitystory.AttachmentDest;
import com.ua.sdk.concurrent.MultipleCreateRequest;
import com.ua.sdk.concurrent.UploadRequest;
import com.ua.sdk.internal.AbstractManager;
import com.ua.sdk.internal.MediaService;
import com.ua.sdk.user.UserManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutManagerImpl extends AbstractManager<Workout> implements WorkoutManager {
    /* access modifiers changed from: private */
    public final MediaService<Workout> mediaService;
    /* access modifiers changed from: private */
    public final UserManager userManager;
    private WorkoutDatabase workoutDatabase;

    /* JADX WARNING: type inference failed for: r13v0, types: [com.ua.sdk.internal.MediaService<com.ua.sdk.workout.Workout>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WorkoutManagerImpl(com.ua.sdk.user.UserManager r7, com.ua.sdk.cache.CacheSettings r8, com.ua.sdk.cache.Cache r9, com.ua.sdk.workout.WorkoutDatabase r10, com.ua.sdk.internal.EntityService<com.ua.sdk.workout.Workout> r11, java.util.concurrent.ExecutorService r12, com.ua.sdk.internal.MediaService<com.ua.sdk.workout.Workout> r13) {
        /*
            r6 = this;
            r0 = r6
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5)
            java.lang.String r0 = "userManager"
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r7, r0)
            com.ua.sdk.user.UserManager r0 = (com.ua.sdk.user.UserManager) r0
            r6.userManager = r0
            java.lang.String r0 = "imageService"
            java.lang.Object r0 = com.ua.sdk.internal.Precondition.isNotNull(r13, r0)
            com.ua.sdk.internal.MediaService r0 = (com.ua.sdk.internal.MediaService) r0
            r6.mediaService = r0
            r6.workoutDatabase = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.workout.WorkoutManagerImpl.<init>(com.ua.sdk.user.UserManager, com.ua.sdk.cache.CacheSettings, com.ua.sdk.cache.Cache, com.ua.sdk.workout.WorkoutDatabase, com.ua.sdk.internal.EntityService, java.util.concurrent.ExecutorService, com.ua.sdk.internal.MediaService):void");
    }

    public WorkoutBuilder getWorkoutBuilderCreate() {
        return new WorkoutBuilderImpl();
    }

    public WorkoutBuilder getWorkoutBuilderUpdate(Workout workout, boolean invalidateTimeSeries) {
        return new WorkoutBuilderImpl(workout, invalidateTimeSeries);
    }

    public WorkoutNameGenerator getWorkoutNameGenerator() {
        return new WorkoutNameGeneratorImpl();
    }

    public Workout createWorkout(Workout workout) throws UaException {
        return (Workout) create(workout);
    }

    public Request createWorkout(Workout workout, CreateCallback<Workout> callback) {
        return create(workout, callback);
    }

    public Request uploadImage(Workout workout, Uri image, UploadCallback<Workout> callback) {
        final UploadRequest<Workout> request = new UploadRequest<>(callback);
        final Workout workout2 = workout;
        final Uri uri = image;
        final UploadCallback<Workout> uploadCallback = callback;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    WorkoutManagerImpl.this.mediaService.uploadImage(uri, new AttachmentDest(workout2.getRef().getHref(), "attachments", 0), uploadCallback);
                    request.done(workout2, (UaException) null);
                } catch (UaException error) {
                    request.done(workout2, error);
                }
            }
        }));
        return request;
    }

    public Request uploadVideo(Workout workout, Uri video, UploadCallback<Workout> callback) {
        final UploadRequest<Workout> request = new UploadRequest<>(callback);
        final Workout workout2 = workout;
        final Uri uri = video;
        final UploadCallback<Workout> uploadCallback = callback;
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    WorkoutManagerImpl.this.mediaService.uploadVideo(uri, new AttachmentDest(workout2.getRef().getHref(), "attachments", 0, WorkoutManagerImpl.this.userManager.getCurrentUserRef().getId()), uploadCallback);
                    request.done(workout2, (UaException) null);
                } catch (UaException error) {
                    request.done(workout2, error);
                }
            }
        }));
        return request;
    }

    public Request deleteWorkout(WorkoutRef ref, DeleteCallback<WorkoutRef> callback) {
        return delete(ref, callback);
    }

    public void deleteWorkout(WorkoutRef ref) throws UaException {
        delete(ref);
    }

    public Workout fetchWorkout(WorkoutRef ref, boolean setTimeSeriesField) throws UaException {
        if (setTimeSeriesField) {
            return (Workout) fetch(WorkoutRef.getFieldBuilder(ref).setTimeSeriesField(true).build());
        }
        return (Workout) fetch(ref);
    }

    public Request fetchWorkout(WorkoutRef ref, boolean setTimeSeriesField, FetchCallback<Workout> callback) {
        if (setTimeSeriesField) {
            return fetch((Reference) WorkoutRef.getFieldBuilder(ref).setTimeSeriesField(true).build(), callback);
        }
        return fetch((Reference) ref, callback);
    }

    public EntityList<Workout> fetchWorkoutList(WorkoutListRef ref) throws UaException {
        return fetchPage(ref);
    }

    public Request fetchWorkoutList(WorkoutListRef ref, FetchCallback<EntityList<Workout>> callback) {
        return fetchPage(ref, callback);
    }

    public Workout updateWorkout(Workout workout) throws UaException {
        return (Workout) update(workout);
    }

    public Request updateWorkout(Workout workout, SaveCallback<Workout> callback) {
        return update(workout, callback);
    }

    public void cancelUpload() throws IOException {
        this.mediaService.close();
    }

    public List<Workout> fetchUnsyncedCreatedWorkouts() {
        return this.workoutDatabase.fetchUnsyncedCreatedWorkouts();
    }

    public Request syncAllUnsyncedWorkouts(MultipleCreateCallback<Workout> callback) throws UaException {
        final MultipleCreateRequest<Workout> request = new MultipleCreateRequest<>(callback);
        final List<Workout> syncedWorkouts = new ArrayList<>();
        request.setFuture(this.executor.submit(new Runnable() {
            public void run() {
                try {
                    for (Workout w : WorkoutManagerImpl.this.fetchUnsyncedCreatedWorkouts()) {
                        WorkoutManagerImpl.this.createWorkout(w);
                        syncedWorkouts.add(w);
                    }
                    request.done(syncedWorkouts, (UaException) null);
                } catch (UaException e) {
                    request.done(syncedWorkouts, e);
                }
            }
        }));
        return request;
    }
}
