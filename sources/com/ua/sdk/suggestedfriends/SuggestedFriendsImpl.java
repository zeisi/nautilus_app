package com.ua.sdk.suggestedfriends;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.ImageUrlImpl;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.LinkListRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.List;

public class SuggestedFriendsImpl extends ApiTransferObject implements SuggestedFriends {
    public static Parcelable.Creator<SuggestedFriendsImpl> CREATOR = new Parcelable.Creator<SuggestedFriendsImpl>() {
        public SuggestedFriendsImpl createFromParcel(Parcel source) {
            return new SuggestedFriendsImpl(source);
        }

        public SuggestedFriendsImpl[] newArray(int size) {
            return new SuggestedFriendsImpl[size];
        }
    };
    public static final String REF_MUTUAL_FRIENDS = "mutual_friends";
    public static final String REF_PROFILE_PIC = "profile_picture";
    public static final String REF_USER = "user";
    Integer mutualFriendCount;
    @SerializedName("reasons")
    List<SuggestedFriendsReasonImpl> reasons;
    ImageUrlImpl suggestedFriendsProfilePicture;

    public SuggestedFriendsImpl() {
        this.reasons = new ArrayList();
        this.suggestedFriendsProfilePicture = new ImageUrlImpl();
    }

    public List<SuggestedFriendsReason> getReasons() {
        List<SuggestedFriendsReason> suggestedFriendsReasons = new ArrayList<>();
        suggestedFriendsReasons.addAll(this.reasons);
        return suggestedFriendsReasons;
    }

    public ImageUrlImpl getSuggestedFriendsProfilePicture() {
        return this.suggestedFriendsProfilePicture;
    }

    public EntityListRef<User> getMutualFriendsRef() {
        Link mutualFriendsLink = getLink(REF_MUTUAL_FRIENDS);
        if (mutualFriendsLink == null) {
            return null;
        }
        return new LinkListRef(mutualFriendsLink.getHref());
    }

    public Integer getMutualFriendCount() {
        Link mutualFriendsLink = getLink(REF_MUTUAL_FRIENDS);
        if (mutualFriendsLink == null) {
            return null;
        }
        return mutualFriendsLink.getCount();
    }

    public EntityRef<User> getSuggestedFriendRef() {
        Link userLink = getLink("user");
        if (userLink == null) {
            return null;
        }
        return new LinkEntityRef(userLink.getId(), userLink.getHref());
    }

    public String getSuggestFriendDisplayName() {
        Link userLink = getLink("user");
        if (userLink == null) {
            return null;
        }
        return userLink.getDisplayName();
    }

    public EntityRef getRef() {
        return null;
    }

    public void setReasons(List<SuggestedFriendsReasonImpl> reasons2) {
        this.reasons = reasons2;
    }

    public void setMutualFriendCount(Integer mutualFriendCount2) {
        this.mutualFriendCount = mutualFriendCount2;
    }

    public void setSuggestedFriendsProfilePicture(ImageUrlImpl suggestedFriendProfilePicture) {
        this.suggestedFriendsProfilePicture = suggestedFriendProfilePicture;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.reasons);
        dest.writeValue(this.mutualFriendCount);
        dest.writeParcelable(this.suggestedFriendsProfilePicture, flags);
    }

    private SuggestedFriendsImpl(Parcel in) {
        super(in);
        this.reasons = new ArrayList();
        this.suggestedFriendsProfilePicture = new ImageUrlImpl();
        in.readTypedList(this.reasons, SuggestedFriendsReasonImpl.CREATOR);
        this.mutualFriendCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.suggestedFriendsProfilePicture = (ImageUrlImpl) in.readParcelable(ImageUrlImpl.class.getClassLoader());
    }
}
