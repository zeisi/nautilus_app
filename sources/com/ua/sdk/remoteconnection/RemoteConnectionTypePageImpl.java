package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class RemoteConnectionTypePageImpl extends AbstractEntityList<RemoteConnectionType> {
    public static Parcelable.Creator<RemoteConnectionTypePageImpl> CREATOR = new Parcelable.Creator<RemoteConnectionTypePageImpl>() {
        public RemoteConnectionTypePageImpl createFromParcel(Parcel source) {
            return new RemoteConnectionTypePageImpl(source);
        }

        public RemoteConnectionTypePageImpl[] newArray(int size) {
            return new RemoteConnectionTypePageImpl[size];
        }
    };
    private static final String LIST_KEY = "remote_connection_types";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public RemoteConnectionTypePageImpl() {
    }

    private RemoteConnectionTypePageImpl(Parcel in) {
        super(in);
    }
}
