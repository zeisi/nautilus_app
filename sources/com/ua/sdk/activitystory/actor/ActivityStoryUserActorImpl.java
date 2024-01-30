package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.nautilus.omni.util.Constants;
import com.ua.sdk.EntityRef;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.ActivityStoryActor;
import com.ua.sdk.activitystory.ActivityStoryUserActor;
import com.ua.sdk.friendship.FriendshipStatus;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.Gender;
import com.ua.sdk.user.User;

public class ActivityStoryUserActorImpl extends ApiTransferObject implements ActivityStoryUserActor {
    public static Parcelable.Creator<ActivityStoryUserActorImpl> CREATOR = new Parcelable.Creator<ActivityStoryUserActorImpl>() {
        public ActivityStoryUserActorImpl createFromParcel(Parcel source) {
            return new ActivityStoryUserActorImpl(source);
        }

        public ActivityStoryUserActorImpl[] newArray(int size) {
            return new ActivityStoryUserActorImpl[size];
        }
    };
    @SerializedName("first_name")
    String mFirstName;
    @SerializedName("friendship")
    Friendship mFriendship;
    @SerializedName("gender")
    Gender mGender;
    @SerializedName("id")
    String mId;
    @SerializedName("is_mvp")
    Boolean mIsMvp;
    @SerializedName("last_name")
    String mLastName;
    transient ImageUrl mProfilePicture;
    @SerializedName("title")
    String mTitle;

    static class Friendship {
        @SerializedName("from_user")
        String fromUser;
        @SerializedName("status")
        FriendshipStatus status;
        @SerializedName("to_user")
        String toUser;

        Friendship() {
        }
    }

    public String getFirstName() {
        return this.mFirstName;
    }

    public String getLastName() {
        return this.mLastName;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public Gender getGender() {
        return this.mGender;
    }

    public Boolean isMvp() {
        return this.mIsMvp;
    }

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.USER;
    }

    public ActivityStoryUserActorImpl(String id) {
        this.mId = id;
    }

    public String getId() {
        return this.mId;
    }

    public ImageUrl getProfilePhoto() {
        return this.mProfilePicture;
    }

    public FriendshipStatus getFriendshipStatus() {
        if (this.mFriendship == null) {
            return FriendshipStatus.NONE;
        }
        return this.mFriendship.status;
    }

    public void setUserProfilePicture(ImageUrl mProfilePicture2) {
        this.mProfilePicture = mProfilePicture2;
    }

    /* access modifiers changed from: protected */
    public void createFriendship() {
        if (this.mFriendship == null) {
            this.mFriendship = new Friendship();
        }
    }

    public EntityRef<User> getUserRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void setUser(EntityRef<User> ref) {
        if (ref == null) {
            this.mId = null;
        } else {
            this.mId = ref.getId();
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = -1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
        dest.writeString(this.mTitle);
        dest.writeInt(this.mGender == null ? -1 : this.mGender.ordinal());
        dest.writeValue(this.mIsMvp);
        dest.writeParcelable(this.mProfilePicture, flags);
        dest.writeString((this.mFriendship == null || this.mFriendship.fromUser == null) ? "" : this.mFriendship.fromUser);
        if (!(this.mFriendship == null || this.mFriendship.status == null)) {
            i = this.mFriendship.status.ordinal();
        }
        dest.writeInt(i);
        dest.writeString((this.mFriendship == null || this.mFriendship.toUser == null) ? "" : this.mFriendship.toUser);
    }

    public ActivityStoryUserActorImpl() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private ActivityStoryUserActorImpl(Parcel in) {
        super(in);
        String str = null;
        this.mId = in.readString();
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
        this.mTitle = in.readString();
        int tmpMGender = in.readInt();
        this.mGender = tmpMGender == -1 ? null : Gender.values()[tmpMGender];
        this.mIsMvp = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mProfilePicture = (ImageUrl) in.readParcelable(ImageUrl.class.getClassLoader());
        String tmpFromUser = in.readString();
        int tmpStatus = in.readInt();
        String tmpToUser = in.readString();
        if (!tmpFromUser.equals("") || tmpStatus != -1 || !tmpToUser.equals("")) {
            createFriendship();
            this.mFriendship.fromUser = tmpFromUser.equals("") ? null : tmpFromUser;
            this.mFriendship.status = tmpStatus == -1 ? null : FriendshipStatus.values()[tmpStatus];
            this.mFriendship.toUser = !tmpToUser.equals("") ? tmpToUser : str;
        }
    }

    public String toString() {
        return getFirstName() + Constants.EMPTY_SPACE + getLastName();
    }
}
