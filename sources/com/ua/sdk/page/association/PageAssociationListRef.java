package com.ua.sdk.page.association;

import android.os.Parcel;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.page.Page;

public class PageAssociationListRef implements EntityListRef<PageAssociation> {
    private String href;

    private PageAssociationListRef(Builder builder) {
        Precondition.isNotNull(builder);
        this.href = builder.getHref();
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public String getId() {
        return null;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        private String fromPageId;
        private String toPageId;

        protected Builder() {
            super("/v7.0/page_association/");
        }

        public Builder setFromPage(EntityRef<Page> fromPageRef) {
            if (fromPageRef == null) {
                this.fromPageId = null;
            } else {
                this.fromPageId = fromPageRef.getId();
            }
            setParam("from_page_id", this.fromPageId);
            return this;
        }

        public Builder setToPage(EntityRef<Page> toPageRef) {
            if (toPageRef == null) {
                this.toPageId = null;
            } else {
                this.toPageId = toPageRef.getId();
            }
            setParam("to_page_id", this.toPageId);
            return this;
        }

        public PageAssociationListRef build() {
            return new PageAssociationListRef(this);
        }
    }
}
