package com.ua.sdk.group.purpose;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class GroupPurposeImpl extends ApiTransferObject implements GroupPurpose {
    public static Parcelable.Creator<GroupPurposeImpl> CREATOR = new Parcelable.Creator<GroupPurposeImpl>() {
        public GroupPurposeImpl createFromParcel(Parcel source) {
            return new GroupPurposeImpl(source);
        }

        public GroupPurposeImpl[] newArray(int size) {
            return new GroupPurposeImpl[size];
        }
    };
    @SerializedName("restrict_membership")
    Boolean isRestricted;
    @SerializedName("purpose")
    String purpose;

    public GroupPurposeImpl() {
    }

    private GroupPurposeImpl(Parcel in) {
        super(in);
        this.purpose = in.readString();
        this.isRestricted = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public String getPurpose() {
        return this.purpose;
    }

    public Boolean isRestricted() {
        return this.isRestricted;
    }

    public EntityRef<GroupPurpose> getRef() {
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
        dest.writeString(this.purpose);
        dest.writeValue(this.isRestricted);
    }
}
