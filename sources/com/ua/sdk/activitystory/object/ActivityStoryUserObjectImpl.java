package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryUserObject;
import com.ua.sdk.friendship.FriendshipStatus;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.location.Location;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.user.Gender;
import com.ua.sdk.user.User;
import java.util.Date;

public class ActivityStoryUserObjectImpl extends ApiTransferObject implements ActivityStoryUserObject {
    public static Parcelable.Creator<ActivityStoryUserObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryUserObjectImpl>() {
        public ActivityStoryUserObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryUserObjectImpl(source);
        }

        public ActivityStoryUserObjectImpl[] newArray(int size) {
            return new ActivityStoryUserObjectImpl[size];
        }
    };
    @SerializedName("date_joined")
    Date dateJoined;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("friendship")
    Friendship friendship;
    @SerializedName("gender")
    Gender gender;
    @SerializedName("id")
    String id;
    @SerializedName("is_mvp")
    Boolean isMvp;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("location")
    Location location;
    @SerializedName("privacy")
    Privacy privacy;
    @SerializedName("title")
    String title;

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

    public ActivityStoryUserObjectImpl() {
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.USER;
    }

    public Privacy getPrivacy() {
        return this.privacy;
    }

    public Location getLocation() {
        return this.location;
    }

    public Date getJoinedDate() {
        return this.dateJoined;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getTitle() {
        return this.title;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Boolean isMvp() {
        return this.isMvp;
    }

    public EntityRef<User> getUserRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public String getId() {
        return this.id;
    }

    public FriendshipStatus getFriendshipStatus() {
        if (this.friendship == null) {
            return FriendshipStatus.NONE;
        }
        return this.friendship.status;
    }

    /* access modifiers changed from: protected */
    public void createFriendship() {
        if (this.friendship == null) {
            this.friendship = new Friendship();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = -1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.title);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeValue(this.isMvp);
        dest.writeParcelable(this.privacy, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeLong(this.dateJoined == null ? -1 : this.dateJoined.getTime());
        dest.writeString((this.friendship == null || this.friendship.fromUser == null) ? "" : this.friendship.fromUser);
        if (!(this.friendship == null || this.friendship.status == null)) {
            i = this.friendship.status.ordinal();
        }
        dest.writeInt(i);
        dest.writeString((this.friendship == null || this.friendship.toUser == null) ? "" : this.friendship.toUser);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private ActivityStoryUserObjectImpl(Parcel in) {
        super(in);
        String str = null;
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.title = in.readString();
        int tmpMGender = in.readInt();
        this.gender = tmpMGender == -1 ? null : Gender.values()[tmpMGender];
        this.isMvp = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.privacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.location = (Location) in.readParcelable(Location.class.getClassLoader());
        Long tmpDate = Long.valueOf(in.readLong());
        this.dateJoined = tmpDate.longValue() == -1 ? null : new Date(tmpDate.longValue());
        String tmpFromUser = in.readString();
        int tmpStatus = in.readInt();
        String tmpToUser = in.readString();
        if (!tmpFromUser.equals("") || tmpStatus != -1 || !tmpToUser.equals("")) {
            createFriendship();
            this.friendship.fromUser = tmpFromUser.equals("") ? null : tmpFromUser;
            this.friendship.status = tmpStatus == -1 ? null : FriendshipStatus.values()[tmpStatus];
            this.friendship.toUser = !tmpToUser.equals("") ? tmpToUser : str;
        }
    }
}
