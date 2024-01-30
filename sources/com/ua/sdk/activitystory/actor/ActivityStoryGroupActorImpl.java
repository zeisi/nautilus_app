package com.ua.sdk.activitystory.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitystory.ActivityStoryActor;
import com.ua.sdk.activitystory.ActivityStoryGroupActor;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataField;
import com.ua.sdk.datapoint.DataType;
import com.ua.sdk.group.Group;
import com.ua.sdk.group.invite.GroupInvite;
import com.ua.sdk.group.objective.Criteria;
import com.ua.sdk.group.user.GroupUser;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Period;
import com.ua.sdk.user.User;
import java.util.Date;

public class ActivityStoryGroupActorImpl extends ApiTransferObject implements ActivityStoryGroupActor {
    public static final Parcelable.Creator<ActivityStoryGroupActorImpl> CREATOR = new Parcelable.Creator<ActivityStoryGroupActorImpl>() {
        public ActivityStoryGroupActorImpl createFromParcel(Parcel source) {
            return new ActivityStoryGroupActorImpl(source);
        }

        public ActivityStoryGroupActorImpl[] newArray(int size) {
            return new ActivityStoryGroupActorImpl[size];
        }
    };
    private static final String GROUP_INVITE_LINK = "group_invites";
    private static final String GROUP_OWNER_LINK = "group_owner";
    private static final String GROUP_USER_LINK = "group_users";
    @SerializedName("criteria")
    private Criteria criteria;
    private transient DataField dataField;
    @SerializedName("data_type_field")
    private String dataFieldStr;
    private transient DataType dataType;
    private transient EntityRef<DataType> dataTypeRef;
    @SerializedName("data_type")
    private String dataTypeStr;
    @SerializedName("end_time")
    private Date endTime;
    private transient Integer groupInviteCount;
    private transient EntityRef<GroupInvite> groupInviteRef;
    private transient EntityRef<User> groupOwnerRef;
    private transient Integer groupUserCount;
    private transient EntityRef<GroupUser> groupUserRef;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("period")
    private Period period;
    private transient EntityRef<Group> selfRef;
    @SerializedName("start_time")
    private Date startTime;

    public ActivityStoryGroupActorImpl() {
    }

    private ActivityStoryGroupActorImpl(Parcel in) {
        super(in);
        this.startTime = (Date) in.readValue(Date.class.getClassLoader());
        this.endTime = (Date) in.readValue(Date.class.getClassLoader());
        this.name = in.readString();
        this.dataTypeStr = in.readString();
        this.id = in.readString();
        this.period = (Period) in.readParcelable(Period.class.getClassLoader());
        this.dataFieldStr = in.readString();
        this.criteria = (Criteria) in.readValue(Criteria.class.getClassLoader());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime2) {
        this.startTime = startTime2;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime2) {
        this.endTime = endTime2;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(Period period2) {
        this.period = period2;
    }

    public DataType getDataType() {
        if (this.dataType == null && this.dataTypeStr != null) {
            this.dataType = BaseDataTypes.ALL_BASE_TYPE_MAP.get(this.dataTypeStr);
        }
        return this.dataType;
    }

    public void setDataType(DataType dataType2) {
        if (dataType2 != null) {
            this.dataType = BaseDataTypes.ALL_BASE_TYPE_MAP.get(dataType2.getId());
            if (this.dataType != null) {
                this.dataTypeStr = this.dataType.getId();
            }
        }
    }

    public DataField getDataField() {
        if (this.dataFieldStr != null && this.dataField == null) {
            this.dataField = BaseDataTypes.findDataField(this.dataFieldStr, getDataType());
        }
        return this.dataField;
    }

    public void setDataField(DataField dataField2) {
        if (dataField2 != null) {
            this.dataField = BaseDataTypes.findDataField(dataField2.getId(), getDataType());
            if (this.dataField != null) {
                this.dataFieldStr = this.dataField.getId();
            }
        }
    }

    public int getGroupInviteCount() {
        int i = 0;
        if (this.groupInviteCount == null) {
            Link link = getLink(GROUP_INVITE_LINK);
            if (link == null) {
                this.groupInviteCount = 0;
            } else {
                if (link.getCount() != null) {
                    i = link.getCount().intValue();
                }
                this.groupInviteCount = Integer.valueOf(i);
            }
        }
        return this.groupInviteCount.intValue();
    }

    public ActivityStoryActor.Type getType() {
        return ActivityStoryActor.Type.GROUP;
    }

    public int getGroupUserCount() {
        int i = 0;
        if (this.groupUserCount == null) {
            Link link = getLink(GROUP_USER_LINK);
            if (link == null) {
                this.groupUserCount = 0;
            } else {
                if (link.getCount() != null) {
                    i = link.getCount().intValue();
                }
                this.groupUserCount = Integer.valueOf(i);
            }
        }
        return this.groupUserCount.intValue();
    }

    public EntityRef<Group> getRef() {
        Link ref;
        if (this.selfRef == null && (ref = getLink("self")) != null) {
            this.selfRef = new LinkEntityRef(ref.getId(), ref.getHref());
        }
        return this.selfRef;
    }

    public Criteria getCriteria() {
        return this.criteria;
    }

    public EntityRef<User> getGroupOwnerRef() {
        Link user;
        if (this.groupOwnerRef == null && (user = getLink(GROUP_OWNER_LINK)) != null) {
            this.groupOwnerRef = new LinkEntityRef(user.getId(), user.getHref());
        }
        return this.groupOwnerRef;
    }

    public void setCriteria(Criteria criteria2) {
        this.criteria = criteria2;
    }

    public EntityRef<GroupInvite> getGroupInviteRef() {
        Link link;
        if (this.groupInviteRef == null && (link = getLink(GROUP_INVITE_LINK)) != null) {
            this.groupInviteRef = new LinkEntityRef(link.getId(), link.getHref());
        }
        return this.groupInviteRef;
    }

    public EntityRef<DataType> getDataTypeRef() {
        Link link;
        if (this.dataTypeRef == null && (link = getLink("data_type")) != null) {
            this.dataTypeRef = new LinkEntityRef(link.getId(), link.getHref());
        }
        return this.dataTypeRef;
    }

    public EntityRef<GroupUser> getGroupUserRef() {
        Link link;
        if (this.groupUserRef == null && (link = getLink(GROUP_USER_LINK)) != null) {
            this.groupUserRef = new LinkEntityRef(link.getId(), link.getHref());
        }
        return this.groupUserRef;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.startTime);
        dest.writeValue(this.endTime);
        dest.writeString(this.name);
        dest.writeString(this.dataTypeStr);
        dest.writeString(this.id);
        dest.writeParcelable(this.period, flags);
        dest.writeString(this.dataFieldStr);
        dest.writeValue(this.criteria);
    }
}
