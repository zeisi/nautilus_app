package com.ua.sdk.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;

public class LinkEntityRef<T> implements EntityRef<T> {
    public static final Parcelable.Creator<LinkEntityRef> CREATOR = new Parcelable.Creator<LinkEntityRef>() {
        public LinkEntityRef createFromParcel(Parcel in) {
            return new LinkEntityRef(in);
        }

        public LinkEntityRef[] newArray(int size) {
            return new LinkEntityRef[size];
        }
    };
    protected final String href;
    protected final String id;
    protected final long localId;
    protected final int options;

    public LinkEntityRef(String href2) {
        this((String) null, href2);
    }

    public LinkEntityRef(String id2, String href2) {
        this.id = id2;
        this.href = href2;
        this.localId = -1;
        this.options = 0;
    }

    public LinkEntityRef(String id2, long localId2, String href2) {
        this.id = id2;
        this.href = href2;
        this.localId = localId2;
        this.options = 0;
    }

    public LinkEntityRef(String id2, long localId2, String href2, int options2) {
        this.id = id2;
        this.href = href2;
        this.localId = localId2;
        this.options = options2;
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public long getLocalId() {
        return this.localId;
    }

    public int getOptions() {
        return this.options;
    }

    public boolean checkCache() {
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.localId);
        dest.writeString(this.href);
        dest.writeInt(this.options);
    }

    protected LinkEntityRef(Parcel in) {
        this.id = in.readString();
        this.localId = in.readLong();
        this.href = in.readString();
        this.options = in.readInt();
    }
}
