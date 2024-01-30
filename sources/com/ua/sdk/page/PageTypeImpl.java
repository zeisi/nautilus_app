package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class PageTypeImpl extends ApiTransferObject implements PageType, Parcelable {
    public static Parcelable.Creator<PageTypeImpl> CREATOR = new Parcelable.Creator<PageTypeImpl>() {
        public PageTypeImpl createFromParcel(Parcel source) {
            return new PageTypeImpl(source);
        }

        public PageTypeImpl[] newArray(int size) {
            return new PageTypeImpl[size];
        }
    };
    @SerializedName("title")
    private String mTitle;

    public PageTypeImpl() {
    }

    public EntityRef<PageType> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mTitle);
    }

    private PageTypeImpl(Parcel in) {
        super(in);
        this.mTitle = in.readString();
    }
}
