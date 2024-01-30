package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class RemoteConnectionPageImpl extends AbstractEntityList<RemoteConnection> {
    public static Parcelable.Creator<RemoteConnectionPageImpl> CREATOR = new Parcelable.Creator<RemoteConnectionPageImpl>() {
        public RemoteConnectionPageImpl createFromParcel(Parcel source) {
            return new RemoteConnectionPageImpl(source);
        }

        public RemoteConnectionPageImpl[] newArray(int size) {
            return new RemoteConnectionPageImpl[size];
        }
    };
    private static final String LIST_KEY = "remote_connections";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public RemoteConnectionPageImpl() {
    }

    private RemoteConnectionPageImpl(Parcel in) {
        super(in);
    }
}
