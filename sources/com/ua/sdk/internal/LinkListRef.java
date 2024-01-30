package com.ua.sdk.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;

public class LinkListRef<E> implements EntityListRef<E> {
    public static final Parcelable.Creator<LinkListRef> CREATOR = new Parcelable.Creator<LinkListRef>() {
        public LinkListRef createFromParcel(Parcel in) {
            return new LinkListRef(in);
        }

        public LinkListRef[] newArray(int size) {
            return new LinkListRef[size];
        }
    };
    private final String href;
    private final long localId;

    public LinkListRef(String href2) {
        this.href = href2;
        this.localId = -1;
    }

    public LinkListRef(long localId2, String href2) {
        this.href = href2;
        this.localId = localId2;
    }

    public String getHref() {
        return this.href;
    }

    public long getLocalId() {
        return this.localId;
    }

    public boolean checkCache() {
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.localId);
        dest.writeString(this.href);
    }

    protected LinkListRef(Parcel in) {
        this.localId = in.readLong();
        this.href = in.readString();
    }

    public String getId() {
        return getHref();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkListRef that = (LinkListRef) o;
        if (this.localId != that.localId) {
            return false;
        }
        if (this.href != null) {
            if (this.href.equals(that.href)) {
                return true;
            }
        } else if (that.href == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.href != null ? this.href.hashCode() : 0) * 31) + ((int) (this.localId ^ (this.localId >>> 32)));
    }
}
