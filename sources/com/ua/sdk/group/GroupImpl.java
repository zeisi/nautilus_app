package com.ua.sdk.group;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitystory.ActivityStory;
import com.ua.sdk.group.invite.GroupInvite;
import com.ua.sdk.group.objective.GroupObjective;
import com.ua.sdk.group.purpose.GroupPurpose;
import com.ua.sdk.group.user.GroupUser;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.List;

public class GroupImpl extends ApiTransferObject implements Group, Parcelable {
    public static final Parcelable.Creator<GroupImpl> CREATOR = new Parcelable.Creator<GroupImpl>() {
        public GroupImpl createFromParcel(Parcel source) {
            return new GroupImpl(source);
        }

        public GroupImpl[] newArray(int size) {
            return new GroupImpl[size];
        }
    };
    private static final String REF_SELF = "self";
    private transient EntityRef<ActivityStory> activityFeedRef;
    @SerializedName("description")
    private String description;
    private transient EntityRef<GroupInvite> groupInviteRef;
    @SerializedName("group_objective")
    private GroupObjective groupObjective;
    private transient EntityRef<User> groupOwnerRef;
    private transient EntityRef<GroupPurpose> groupPurposeRef;
    private transient EntityRef<GroupUser> groupUserRef;
    @SerializedName("invitation_code")
    private String invitationCode;
    @SerializedName("invitation_required")
    private Boolean invitationRequired;
    @SerializedName("max_users")
    private Integer maxUsers;
    private transient int memberCount;
    @SerializedName("name")
    private String name;
    @SerializedName("is_public")
    private Boolean publicGroup;

    public GroupImpl() {
    }

    public Boolean getPublicGroup() {
        return this.publicGroup;
    }

    public void setPublicGroup(Boolean publicGroup2) {
        this.publicGroup = publicGroup2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public Boolean getInvitationRequired() {
        return this.invitationRequired;
    }

    public void setInvitationRequired(Boolean invitationRequired2) {
        this.invitationRequired = invitationRequired2;
    }

    public String getInvitationCode() {
        return this.invitationCode;
    }

    public void setInvitationCode(String invitationCode2) {
        this.invitationCode = invitationCode2;
    }

    public int getMemberCount() {
        Link userRef;
        if (this.memberCount == 0 && (userRef = getLink("users")) != null) {
            this.memberCount = userRef.getCount().intValue();
        }
        return this.memberCount;
    }

    public int getMaxUsers() {
        if (this.maxUsers == null) {
            return 0;
        }
        return this.maxUsers.intValue();
    }

    public void setMaxUsers(int count) {
        this.maxUsers = Integer.valueOf(count);
    }

    public GroupObjective getGroupObjective() {
        return this.groupObjective;
    }

    public void setGroupObjective(GroupObjective groupObjective2) {
        this.groupObjective = groupObjective2;
    }

    public EntityRef<GroupUser> getGroupUserRef() {
        List<Link> links;
        if (this.groupUserRef == null && (links = getLinks("group_user")) != null) {
            this.groupUserRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.groupUserRef;
    }

    public EntityRef<ActivityStory> getActivityFeedRef() {
        List<Link> links;
        if (this.activityFeedRef == null && (links = getLinks("activity_feed")) != null) {
            this.activityFeedRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.activityFeedRef;
    }

    public EntityRef<GroupPurpose> getGroupPurposeRef() {
        List<Link> links;
        if (this.groupPurposeRef == null && (links = getLinks("group_purpose")) != null) {
            this.groupPurposeRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.groupPurposeRef;
    }

    public void setGroupPurposeRef(EntityRef<GroupPurpose> ref) {
        this.groupPurposeRef = ref;
        addLink("group_purpose", new Link(ref.getHref(), ref.getId()));
    }

    public EntityRef<GroupInvite> getGroupInviteRef() {
        List<Link> links;
        if (this.groupInviteRef == null && (links = getLinks("group_invite")) != null) {
            this.groupInviteRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.groupInviteRef;
    }

    public EntityRef<User> getGroupOwnerRef() {
        Link owner;
        if (this.groupOwnerRef == null && (owner = getLink("group_owner")) != null) {
            this.groupOwnerRef = new LinkEntityRef(owner.getId(), owner.getHref());
        }
        return this.groupOwnerRef;
    }

    public EntityRef<GroupImpl> getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.publicGroup);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.invitationRequired);
        dest.writeString(this.invitationCode);
        dest.writeValue(this.groupObjective);
        dest.writeValue(this.maxUsers);
    }

    private GroupImpl(Parcel in) {
        super(in);
        this.publicGroup = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.invitationRequired = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.invitationCode = in.readString();
        this.groupObjective = (GroupObjective) in.readValue(GroupObjective.class.getClassLoader());
        this.maxUsers = (Integer) in.readValue(Integer.class.getClassLoader());
    }
}
