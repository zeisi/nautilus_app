package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class RemoteConnectionRef implements EntityRef<RemoteConnection> {
    public static final Parcelable.Creator<RemoteConnectionRef> CREATOR = new Parcelable.Creator<RemoteConnectionRef>() {
        public RemoteConnectionRef createFromParcel(Parcel source) {
            return new RemoteConnectionRef(source);
        }

        public RemoteConnectionRef[] newArray(int size) {
            return new RemoteConnectionRef[size];
        }
    };
    private final String href;
    private final String id;

    private RemoteConnectionRef(Builder init) {
        this.id = init.id;
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super("/v7.0/remoteconnection/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public RemoteConnectionRef build() {
            RemoteConnectionRef remoteConnectionRef;
            synchronized (RemoteConnectionRef.class) {
                remoteConnectionRef = new RemoteConnectionRef(this);
            }
            return remoteConnectionRef;
        }
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
    }

    private RemoteConnectionRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
    }
}
