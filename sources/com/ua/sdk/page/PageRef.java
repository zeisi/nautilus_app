package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;

public class PageRef implements EntityRef<Page> {
    public static Parcelable.Creator<PageRef> CREATOR = new Parcelable.Creator<PageRef>() {
        public PageRef createFromParcel(Parcel source) {
            return new PageRef(source);
        }

        public PageRef[] newArray(int size) {
            return new PageRef[size];
        }
    };
    private final String id;

    private PageRef(Builder init) {
        this.id = init.id;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
        }

        public Builder setUser(User user) {
            Precondition.isNotNull(user, "User");
            Precondition.isNotNull(user.getId(), "User's ID");
            this.id = "u" + user.getId();
            return this;
        }

        public Builder setUserRef(EntityRef<User> user) {
            Precondition.isNotNull(user, "User Reference");
            Precondition.isNotNull(user.getId(), "Reference's ID");
            this.id = "u" + user.getId();
            return this;
        }

        public Builder setPage(Page page) {
            Precondition.isNotNull(page, "Page");
            Precondition.isNotNull(page.getAlias(), "Page's alias");
            this.id = page.getAlias();
            return this;
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public PageRef build() {
            PageRef pageRef;
            synchronized (PageRef.class) {
                pageRef = new PageRef(this);
            }
            return pageRef;
        }
    }

    public String getId() {
        if (this.id == null || this.id.length() <= 0) {
            return null;
        }
        return this.id;
    }

    public String getHref() {
        if (this.id == null || this.id.isEmpty()) {
            return null;
        }
        return String.format(UrlBuilderImpl.GET_PAGE_URL, new Object[]{this.id});
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    private PageRef(Parcel in) {
        this.id = in.readString();
    }
}
