package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class RemoteConnectionTypeRef implements EntityRef<RemoteConnectionType>, Parcelable {
    public static final Parcelable.Creator<RemoteConnectionTypeRef> CREATOR = new Parcelable.Creator<RemoteConnectionTypeRef>() {
        public RemoteConnectionTypeRef createFromParcel(Parcel source) {
            return new RemoteConnectionTypeRef(source);
        }

        public RemoteConnectionTypeRef[] newArray(int size) {
            return new RemoteConnectionTypeRef[size];
        }
    };
    private final String href;
    private final String id;
    private final String recorderTypeKey;

    private RemoteConnectionTypeRef(Builder init) {
        this.id = init.id;
        this.href = init.getHref();
        this.recorderTypeKey = init.recorderTypeKey;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        String id;
        String recorderTypeKey;

        private Builder() {
            super("/v7.0/remoteconnectiontype/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public Builder setRecorderTypeKey(String recorderTypeKey2) {
            this.recorderTypeKey = recorderTypeKey2;
            return this;
        }

        public RemoteConnectionTypeRef build() {
            RemoteConnectionTypeRef remoteConnectionTypeRef;
            synchronized (RemoteConnectionTypeRef.class) {
                remoteConnectionTypeRef = new RemoteConnectionTypeRef(this);
            }
            return remoteConnectionTypeRef;
        }
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public String getRecorderTypeKey() {
        return this.recorderTypeKey;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
        dest.writeString(this.recorderTypeKey);
    }

    private RemoteConnectionTypeRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
        this.recorderTypeKey = in.readString();
    }
}
