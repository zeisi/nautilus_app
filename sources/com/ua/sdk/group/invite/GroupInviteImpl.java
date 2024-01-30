package com.ua.sdk.group.invite;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;

public class GroupInviteImpl extends ApiTransferObject implements GroupInvite {
    public static Parcelable.Creator<GroupInviteImpl> CREATOR = new Parcelable.Creator<GroupInviteImpl>() {
        public GroupInviteImpl createFromParcel(Parcel source) {
            return new GroupInviteImpl(source);
        }

        public GroupInviteImpl[] newArray(int size) {
            return new GroupInviteImpl[size];
        }
    };
    private static final String GROUP_KEY = "group";
    private static final String USER_KEY = "user";
    @SerializedName("email")
    String email;
    transient EntityRef<Group> groupRef;
    transient EntityRef<User> userRef;

    public GroupInviteImpl() {
    }

    private GroupInviteImpl(Parcel in) {
        super(in);
        this.email = in.readString();
    }

    public String getEmail() {
        return this.email;
    }

    public EntityRef<User> getUserRef() {
        Link user;
        if (this.userRef == null && (user = getLink("user")) != null) {
            this.userRef = new LinkEntityRef(user.getId(), user.getHref());
        }
        return this.userRef;
    }

    public EntityRef<Group> getGroupRef() {
        Link group;
        if (this.groupRef == null && (group = getLink(GROUP_KEY)) != null) {
            this.groupRef = new LinkEntityRef(group.getId(), group.getHref());
        }
        return this.groupRef;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public void setGroupRef(EntityRef<Group> groupRef2) {
        if (groupRef2 != null && groupRef2.getHref() != null) {
            this.groupRef = groupRef2;
            addLink(GROUP_KEY, new Link(groupRef2.getHref()));
        }
    }

    public void setUserRef(EntityRef<User> userRef2) {
        if (userRef2 != null && userRef2.getHref() != null) {
            this.userRef = userRef2;
            addLink("user", new Link(userRef2.getHref()));
        }
    }

    public EntityRef<GroupInvite> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.email);
    }
}
