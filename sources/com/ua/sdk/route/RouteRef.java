package com.ua.sdk.route;

import android.os.Parcel;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.LinkEntityRef;

public class RouteRef extends LinkEntityRef<Route> {
    public RouteRef(String href) {
        super(href);
    }

    public RouteRef(String id, String href) {
        super(id, href);
    }

    public RouteRef(String id, long localId, String href) {
        super(id, localId, href);
    }

    public RouteRef(Parcel in) {
        super(in);
    }

    private RouteRef(Builder init) {
        super(init.id, init.getHref());
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        private String fieldSet;
        /* access modifiers changed from: private */
        public String id;

        protected Builder() {
            super("/v7.0/route/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public Builder setFieldSet(String fieldSet2) {
            this.fieldSet = fieldSet2;
            setParam("field_set", String.valueOf(fieldSet2));
            return this;
        }

        public RouteRef build() {
            return new RouteRef(getHref());
        }
    }
}
