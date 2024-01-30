package com.ua.sdk.friendship;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendshipImpl extends ApiTransferObject implements Friendship {
    public static final String ARG_FROM_USER = "from_user";
    public static final String ARG_SELF = "self";
    public static final String ARG_TO_USER = "to_user";
    public static Parcelable.Creator<FriendshipImpl> CREATOR = new Parcelable.Creator<FriendshipImpl>() {
        public FriendshipImpl createFromParcel(Parcel source) {
            return new FriendshipImpl(source);
        }

        public FriendshipImpl[] newArray(int size) {
            return new FriendshipImpl[size];
        }
    };
    private transient List<Friendship> friendships;
    private Date mCreatedDateTime;
    private FriendshipStatus mFriendshipStatus;
    private String mMessage;

    protected FriendshipImpl() {
        this.mFriendshipStatus = FriendshipStatus.NONE;
    }

    protected FriendshipImpl(FriendshipStatus friendshipStatus, Date createdDateTime, String message) {
        this.mFriendshipStatus = FriendshipStatus.NONE;
        this.mFriendshipStatus = friendshipStatus;
        this.mCreatedDateTime = createdDateTime;
        this.mMessage = message;
    }

    protected FriendshipImpl(FriendshipRef ref) {
        this.mFriendshipStatus = FriendshipStatus.NONE;
        ArrayList<Link> links = new ArrayList<>(1);
        links.add(new Link(ref.getHref(), ref.getId()));
        setLinksForRelation("self", links);
        ArrayList<Link> toLinks = new ArrayList<>(1);
        toLinks.add(new Link("/v7.0/user/" + ref.getToUserId() + "/", ref.getToUserId()));
        setLinksForRelation(ARG_TO_USER, toLinks);
        ArrayList<Link> fromLinks = new ArrayList<>(1);
        fromLinks.add(new Link("/v7.0/user/" + ref.getFromUserId() + "/", ref.getFromUserId()));
        setLinksForRelation(ARG_FROM_USER, fromLinks);
    }

    public void addFriendship(Friendship friendship) {
        if (this.friendships == null) {
            this.friendships = new ArrayList();
        }
        this.friendships.add(friendship);
    }

    public List<Friendship> getFriendships() {
        if (this.friendships == null) {
            this.friendships = new ArrayList();
        }
        return this.friendships;
    }

    public FriendshipStatus getFriendshipStatus() {
        return this.mFriendshipStatus;
    }

    public Date getCreatedDateTime() {
        return this.mCreatedDateTime;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public EntityRef<User> getToUserEntityRef() {
        List<Link> selfLinks = getLinks(ARG_TO_USER);
        if (selfLinks != null || !selfLinks.isEmpty()) {
            return new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
        }
        return null;
    }

    public EntityRef<User> getFromUserEntityRef() {
        List<Link> selfLinks = getLinks(ARG_FROM_USER);
        if (selfLinks != null || !selfLinks.isEmpty()) {
            return new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
        }
        return null;
    }

    public EntityRef<Friendship> getRef() {
        List<Link> selfLinks = getLinks("self");
        if (selfLinks != null || !selfLinks.isEmpty()) {
            return new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
        }
        return null;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.mFriendshipStatus = friendshipStatus;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.mCreatedDateTime = createdDateTime;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mFriendshipStatus == null ? -1 : this.mFriendshipStatus.ordinal());
        dest.writeLong(this.mCreatedDateTime != null ? this.mCreatedDateTime.getTime() : -1);
        dest.writeString(this.mMessage);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private FriendshipImpl(Parcel in) {
        super(in);
        Date date = null;
        this.mFriendshipStatus = FriendshipStatus.NONE;
        int tmpMFriendshipStatus = in.readInt();
        this.mFriendshipStatus = tmpMFriendshipStatus == -1 ? null : FriendshipStatus.values()[tmpMFriendshipStatus];
        long tmpMCreatedDateTime = in.readLong();
        this.mCreatedDateTime = tmpMCreatedDateTime != -1 ? new Date(tmpMCreatedDateTime) : date;
        this.mMessage = in.readString();
    }
}
