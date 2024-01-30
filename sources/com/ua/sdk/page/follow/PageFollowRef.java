package com.ua.sdk.page.follow;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class PageFollowRef implements EntityListRef<PageFollow> {
    public static Parcelable.Creator<PageFollowRef> CREATOR = new Parcelable.Creator<PageFollowRef>() {
        public PageFollowRef createFromParcel(Parcel source) {
            return new PageFollowRef(source);
        }

        public PageFollowRef[] newArray(int size) {
            return new PageFollowRef[size];
        }
    };
    private String href;

    private PageFollowRef(Builder init) {
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        private Builder() {
            super("/v7.0/page_follow/");
        }

        public Builder setUserId(String userId) {
            setParam(MyFitnessPalConstants.MFP_USER_ID_KEY, userId);
            return this;
        }

        public Builder setPageId(String pageId) {
            setParam("page_id", pageId);
            return this;
        }

        public PageFollowRef build() {
            PageFollowRef pageFollowRef;
            synchronized (PageFollowRef.class) {
                pageFollowRef = new PageFollowRef(this);
            }
            return pageFollowRef;
        }
    }

    public String getId() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public String getHref() {
        return this.href;
    }

    private PageFollowRef(Parcel in) {
        this.href = in.readString();
    }
}
