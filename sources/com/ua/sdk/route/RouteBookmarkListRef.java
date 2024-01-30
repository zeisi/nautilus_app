package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class RouteBookmarkListRef implements EntityListRef<RouteBookmark> {
    public static Parcelable.Creator<RouteBookmarkListRef> CREATOR = new Parcelable.Creator<RouteBookmarkListRef>() {
        public RouteBookmarkListRef createFromParcel(Parcel source) {
            return new RouteBookmarkListRef(source);
        }

        public RouteBookmarkListRef[] newArray(int size) {
            return new RouteBookmarkListRef[size];
        }
    };
    private final String href;

    private RouteBookmarkListRef(Parcel in) {
        this.href = in.readString();
    }

    private RouteBookmarkListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String USER = "user";

        public Builder() {
            super(UrlBuilderImpl.ROUTE_BOOKMARK_COLLECTION_URL);
        }

        public Builder setUser(String userId) {
            Precondition.isNotNull(userId);
            setParam("user", userId);
            return this;
        }

        public RouteBookmarkListRef build() {
            Precondition.isNotNull(getParam("user"));
            return new RouteBookmarkListRef(this);
        }
    }
}
