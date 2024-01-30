package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.Date;

public class ActigraphyLinkEntityRef extends LinkEntityRef {
    public static Parcelable.Creator<ActigraphyLinkEntityRef> CREATOR = new Parcelable.Creator<ActigraphyLinkEntityRef>() {
        public ActigraphyLinkEntityRef createFromParcel(Parcel source) {
            return new ActigraphyLinkEntityRef(source);
        }

        public ActigraphyLinkEntityRef[] newArray(int size) {
            return new ActigraphyLinkEntityRef[size];
        }
    };
    private Date mDate;
    private String mUserId;

    protected ActigraphyLinkEntityRef(String id, String href, Date date, String userId) {
        super(id, href);
        this.mDate = date;
        this.mUserId = userId;
    }

    public Date getDate() {
        return this.mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getUserId() {
        return this.mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public String getId() {
        return super.getId();
    }

    public String getHref() {
        return super.getHref();
    }

    public int describeContents() {
        return super.describeContents();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.mDate == null ? -1 : this.mDate.getTime());
        dest.writeString(this.mUserId);
    }

    private ActigraphyLinkEntityRef(Parcel in) {
        super(in);
        Long tmpDate = Long.valueOf(in.readLong());
        this.mDate = tmpDate.longValue() == -1 ? null : new Date(tmpDate.longValue());
        this.mUserId = in.readString();
    }
}
