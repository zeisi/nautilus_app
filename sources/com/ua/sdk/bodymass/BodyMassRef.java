package com.ua.sdk.bodymass;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class BodyMassRef implements EntityRef<BodyMass> {
    public static Parcelable.Creator<BodyMassRef> CREATOR = new Parcelable.Creator<BodyMassRef>() {
        public BodyMassRef createFromParcel(Parcel source) {
            return new BodyMassRef(source);
        }

        public BodyMassRef[] newArray(int size) {
            return new BodyMassRef[size];
        }
    };
    private final String id;

    private BodyMassRef(Builder init) {
        this.id = init.id;
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
        return String.format(UrlBuilderImpl.BODYMASS_URL, new Object[]{this.id});
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super(UrlBuilderImpl.BODYMASS_URL);
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public BodyMassRef build() {
            Precondition.isNotNull(this.id, "BodyMass Id");
            return new BodyMassRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    private BodyMassRef(Parcel in) {
        this.id = in.readString();
    }
}
