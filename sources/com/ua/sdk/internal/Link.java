package com.ua.sdk.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Link implements Parcelable {
    public static Parcelable.Creator<Link> CREATOR = new Parcelable.Creator<Link>() {
        public Link createFromParcel(Parcel source) {
            return new Link(source);
        }

        public Link[] newArray(int size) {
            return new Link[size];
        }
    };
    @SerializedName("count")
    Integer mCount;
    @SerializedName("display_name")
    String mDisplayName;
    @SerializedName("href")
    String mHref;
    @SerializedName("id")
    String mId;
    @SerializedName("iteration")
    Integer mIteration;
    @SerializedName("name")
    String mName;

    public Link() {
    }

    public Link(String href) {
        this(href, (String) null, (String) null, (String) null, (Integer) null, (Integer) null);
    }

    public Link(String href, String id) {
        this(href, id, (String) null, (String) null, (Integer) null, (Integer) null);
    }

    public Link(String href, String id, String name) {
        this(href, id, name, (String) null, (Integer) null, (Integer) null);
    }

    public Link(String href, String id, String name, String displayName) {
        this(href, id, name, displayName, (Integer) null, (Integer) null);
    }

    public Link(String href, String id, String name, String displayName, Integer count) {
        this(href, id, name, displayName, count, (Integer) null);
    }

    public Link(String href, String id, String name, String displayName, Integer count, Integer iteration) {
        this.mHref = href;
        this.mId = id;
        this.mName = name;
        this.mCount = count;
        this.mDisplayName = displayName;
        this.mIteration = iteration;
    }

    public String getHref() {
        return this.mHref;
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public Integer getCount() {
        return this.mCount;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public Integer getIteration() {
        return this.mIteration;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHref);
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeValue(this.mCount);
        dest.writeValue(this.mIteration);
    }

    private Link(Parcel in) {
        this.mHref = in.readString();
        this.mId = in.readString();
        this.mName = in.readString();
        this.mCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mIteration = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        if (this.mCount == null ? link.mCount != null : !this.mCount.equals(link.mCount)) {
            return false;
        }
        if (this.mDisplayName == null ? link.mDisplayName != null : !this.mDisplayName.equals(link.mDisplayName)) {
            return false;
        }
        if (this.mHref == null) {
            return false;
        }
        if (!this.mHref.equals(link.mHref)) {
            return false;
        }
        if (this.mId == null ? link.mId != null : !this.mId.equals(link.mId)) {
            return false;
        }
        if (this.mName == null ? link.mName != null : !this.mName.equals(link.mName)) {
            return false;
        }
        if (this.mIteration != null) {
            if (this.mIteration.equals(link.mIteration)) {
                return true;
            }
        } else if (link.mIteration == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        if (this.mHref != null) {
            result = this.mHref.hashCode();
        } else {
            result = 0;
        }
        int i6 = result * 31;
        if (this.mId != null) {
            i = this.mId.hashCode();
        } else {
            i = 0;
        }
        int i7 = (i6 + i) * 31;
        if (this.mName != null) {
            i2 = this.mName.hashCode();
        } else {
            i2 = 0;
        }
        int i8 = (i7 + i2) * 31;
        if (this.mCount != null) {
            i3 = this.mCount.hashCode();
        } else {
            i3 = 0;
        }
        int i9 = (i8 + i3) * 31;
        if (this.mDisplayName != null) {
            i4 = this.mDisplayName.hashCode();
        } else {
            i4 = 0;
        }
        int i10 = (i9 + i4) * 31;
        if (this.mIteration != null) {
            i5 = this.mIteration.hashCode();
        }
        return i10 + i5;
    }
}
