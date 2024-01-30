package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import java.util.Date;
import java.util.List;

public class ActivityTypeImpl extends ApiTransferObject implements ActivityType, Parcelable {
    public static Parcelable.Creator<ActivityTypeImpl> CREATOR = new Parcelable.Creator<ActivityTypeImpl>() {
        public ActivityTypeImpl createFromParcel(Parcel source) {
            return new ActivityTypeImpl(source);
        }

        public ActivityTypeImpl[] newArray(int size) {
            return new ActivityTypeImpl[size];
        }
    };
    protected static final String REF_ICON_URL = "icon_url";
    protected static final String REF_ROOT = "root";
    private Date mAccessed;
    private String mActivityId;
    private Boolean mHasChildren;
    private Double mMets;
    private String mMetsSpeed;
    private String mName;
    private String mParentActivityId;
    private String mShortName;
    private transient List<WorkoutMetsSpeed> mSpeedList;

    protected ActivityTypeImpl() {
    }

    protected ActivityTypeImpl(String activityId, String parentId, String name, String shortName, Double mets, String metsSpeed, Boolean hasChildren, Date accessed) {
        Double d;
        Date date = null;
        this.mActivityId = activityId;
        this.mParentActivityId = parentId;
        this.mName = name;
        this.mShortName = shortName;
        if (mets != null) {
            d = Double.valueOf(mets.doubleValue());
        } else {
            d = null;
        }
        this.mMets = d;
        this.mMetsSpeed = metsSpeed;
        this.mHasChildren = hasChildren;
        this.mAccessed = accessed != null ? new Date(accessed.getTime()) : date;
    }

    public ActivityTypeRef getRef() {
        String id = null;
        Link self = getLink("self");
        if (self != null) {
            id = self.getId();
        }
        if (id == null) {
            return null;
        }
        return ActivityTypeRef.getBuilder().setActivityTypeId(id).setLocalId(this.mLocalId).build();
    }

    public String getActivityId() {
        return this.mActivityId;
    }

    public String getParentActivityId() {
        return this.mParentActivityId;
    }

    public String getRootActivityId() {
        Link link = getLink(REF_ROOT);
        if (link == null) {
            return null;
        }
        return link.getId();
    }

    public String getName() {
        return this.mName;
    }

    public String getShortName() {
        return this.mShortName;
    }

    public Boolean getHasChildren() {
        return this.mHasChildren;
    }

    public boolean hasChildren() {
        if (this.mHasChildren == null) {
            return false;
        }
        return this.mHasChildren.booleanValue();
    }

    public Date getAccessedDate() {
        if (this.mAccessed != null) {
            return new Date(this.mAccessed.getTime());
        }
        return null;
    }

    public Boolean canCalculateCalories() {
        if (this.mMets != null && this.mMets.doubleValue() > 0.0d) {
            return true;
        }
        if (getSpeedList().size() > 0) {
            return true;
        }
        return false;
    }

    public List<WorkoutMetsSpeed> getSpeedList() {
        if (this.mSpeedList == null) {
            this.mSpeedList = WorkoutMetsSpeed.parseSpeedList(this.mMetsSpeed);
        }
        return this.mSpeedList;
    }

    public Double getMetsValue() {
        if (this.mMets != null) {
            return Double.valueOf(this.mMets.doubleValue());
        }
        return null;
    }

    public String getMetsSpeed() {
        return this.mMetsSpeed;
    }

    public String getIconUrl() {
        return getLinkHelper(REF_ICON_URL);
    }

    public String getParentUrl() {
        return getLinkHelper("parent");
    }

    public String getRootUrl() {
        return getLinkHelper(REF_ROOT);
    }

    private String getLinkHelper(String relation) {
        Link url = getLink(relation);
        if (url == null) {
            return null;
        }
        return url.getHref();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mActivityId);
        dest.writeString(this.mParentActivityId);
        dest.writeLong(this.mAccessed != null ? this.mAccessed.getTime() : -1);
        dest.writeString(this.mName);
        dest.writeString(this.mShortName);
        dest.writeValue(this.mMets);
        dest.writeString(this.mMetsSpeed);
        dest.writeValue(this.mHasChildren);
    }

    private ActivityTypeImpl(Parcel in) {
        super(in);
        this.mActivityId = in.readString();
        this.mParentActivityId = in.readString();
        long tmpMAccessed = in.readLong();
        this.mAccessed = tmpMAccessed == -1 ? null : new Date(tmpMAccessed);
        this.mName = in.readString();
        this.mShortName = in.readString();
        this.mMets = (Double) in.readValue(Double.class.getClassLoader());
        this.mMetsSpeed = in.readString();
        this.mHasChildren = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }
}
