package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.Source;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class SourceImpl extends ApiTransferObject implements Source {
    public static Parcelable.Creator<SourceImpl> CREATOR = new Parcelable.Creator<SourceImpl>() {
        public SourceImpl createFromParcel(Parcel source) {
            return new SourceImpl(source);
        }

        public SourceImpl[] newArray(int size) {
            return new SourceImpl[size];
        }
    };
    @SerializedName("id")
    String id;
    @SerializedName("site_name")
    String siteName;
    @SerializedName("site_url")
    String url;

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName2) {
        this.siteName = siteName2;
    }

    public EntityRef<Source> getRef() {
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
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.siteName);
    }

    public SourceImpl() {
    }

    private SourceImpl(Parcel in) {
        super(in);
        this.id = in.readString();
        this.url = in.readString();
        this.siteName = in.readString();
    }
}
