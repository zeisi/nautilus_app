package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class RemoteConnectionListImpl extends AbstractEntityList<RemoteConnection> {
    public static Parcelable.Creator<RemoteConnectionListImpl> CREATOR = new Parcelable.Creator<RemoteConnectionListImpl>() {
        public RemoteConnectionListImpl createFromParcel(Parcel source) {
            return new RemoteConnectionListImpl(source);
        }

        public RemoteConnectionListImpl[] newArray(int size) {
            return new RemoteConnectionListImpl[size];
        }
    };
    public static final String LIST_KEY = "remoteConnectionPages";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public RemoteConnectionListImpl() {
    }

    private RemoteConnectionListImpl(Parcel in) {
        super(in);
    }
}
