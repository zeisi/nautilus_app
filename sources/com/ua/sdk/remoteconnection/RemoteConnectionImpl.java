package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Precondition;
import java.util.Date;
import java.util.List;

public class RemoteConnectionImpl extends ApiTransferObject implements RemoteConnection, Parcelable {
    public static Parcelable.Creator<RemoteConnectionImpl> CREATOR = new Parcelable.Creator<RemoteConnectionImpl>() {
        public RemoteConnectionImpl createFromParcel(Parcel source) {
            return new RemoteConnectionImpl(source);
        }

        public RemoteConnectionImpl[] newArray(int size) {
            return new RemoteConnectionImpl[size];
        }
    };
    private Date mCreatedDateTime;
    private Date mLastSyncTime;
    private String mType;

    protected RemoteConnectionImpl() {
    }

    protected RemoteConnectionImpl(RemoteConnection remoteConnection) {
        Precondition.isNotNull(remoteConnection, "remoteConnection");
        this.mCreatedDateTime = remoteConnection.getCreatedDateTime();
        this.mLastSyncTime = remoteConnection.getLastSyncTime();
        this.mType = remoteConnection.getType();
        if (remoteConnection instanceof RemoteConnectionImpl) {
            copyLinkMap(((RemoteConnectionImpl) remoteConnection).getLinkMap());
        }
    }

    public EntityRef<RemoteConnection> getRef() {
        List<Link> selfLinks = getLinks("self");
        if (selfLinks == null || selfLinks.isEmpty()) {
            return null;
        }
        return new LinkEntityRef(selfLinks.get(0).getId(), selfLinks.get(0).getHref());
    }

    public int describeContents() {
        return 0;
    }

    public Date getCreatedDateTime() {
        return this.mCreatedDateTime;
    }

    public Date getLastSyncTime() {
        return this.mLastSyncTime;
    }

    public String getType() {
        return this.mType;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.mCreatedDateTime = createdDateTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.mLastSyncTime = lastSyncTime;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.mCreatedDateTime.getTime());
        dest.writeLong(this.mLastSyncTime.getTime());
        dest.writeString(this.mType);
    }

    private RemoteConnectionImpl(Parcel in) {
        super(in);
        this.mCreatedDateTime = new Date(in.readLong());
        this.mLastSyncTime = new Date(in.readLong());
        this.mType = in.readString();
    }
}
