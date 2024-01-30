package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.ActivityStoryActor;
import com.ua.sdk.activitystory.ActivityStoryPageActor;
import com.ua.sdk.internal.ImageUrlImpl;
import com.ua.sdk.page.Page;
import com.ua.sdk.page.PageRef;

public class ActivityStoryPageActorImpl implements ActivityStoryPageActor {
    public static Parcelable.Creator<ActivityStoryPageActorImpl> CREATOR = new Parcelable.Creator<ActivityStoryPageActorImpl>() {
        public ActivityStoryPageActorImpl createFromParcel(Parcel source) {
            return new ActivityStoryPageActorImpl(source);
        }

        public ActivityStoryPageActorImpl[] newArray(int size) {
            return new ActivityStoryPageActorImpl[size];
        }
    };
    @SerializedName("alias")
    String mAlias;
    @SerializedName("cover_photo")
    Photo mCoverPhoto;
    transient ImageUrl mCoverPhotoUrl;
    @SerializedName("id")
    String mId;
    @SerializedName("profile_photo")
    Photo mProfilePhoto;
    transient ImageUrl mProfilePhotoUrl;
    @SerializedName("title")
    String mTitle;

    public ActivityStoryPageActorImpl(String id) {
        this.mId = id;
    }

    public EntityRef<Page> getPageRef() {
        if (this.mId == null) {
            return null;
        }
        return PageRef.getBuilder().setId(this.mId).build();
    }

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.PAGE;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public ImageUrl getProfilePhoto() {
        if (this.mProfilePhotoUrl == null && this.mProfilePhoto != null) {
            this.mProfilePhotoUrl = ImageUrlImpl.getBuilder().setLarge(this.mProfilePhoto.uri).setTemplate(this.mProfilePhoto.template).build();
        }
        return this.mProfilePhotoUrl;
    }

    public ImageUrl getCoverPhoto() {
        if (this.mCoverPhotoUrl == null && this.mCoverPhoto != null) {
            this.mCoverPhotoUrl = ImageUrlImpl.getBuilder().setLarge(this.mCoverPhoto.uri).setTemplate(this.mCoverPhoto.template).build();
        }
        return this.mCoverPhotoUrl;
    }

    public String getId() {
        return this.mId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mAlias);
        dest.writeParcelable(this.mProfilePhoto, flags);
        dest.writeParcelable(this.mCoverPhoto, flags);
    }

    public ActivityStoryPageActorImpl() {
    }

    private ActivityStoryPageActorImpl(Parcel in) {
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mAlias = in.readString();
        this.mProfilePhoto = (Photo) in.readParcelable(ImageUrl.class.getClassLoader());
        this.mCoverPhoto = (Photo) in.readParcelable(ImageUrl.class.getClassLoader());
    }

    public static class Photo implements Parcelable {
        public static Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
            public Photo createFromParcel(Parcel source) {
                return new Photo(source);
            }

            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };
        @SerializedName("template")
        String template;
        @SerializedName("uri")
        String uri;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.uri);
            dest.writeString(this.template);
        }

        public Photo() {
        }

        private Photo(Parcel in) {
            this.uri = in.readString();
            this.template = in.readString();
        }
    }
}
