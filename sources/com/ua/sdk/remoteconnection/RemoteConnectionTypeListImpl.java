package com.ua.sdk.remoteconnection;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class RemoteConnectionTypeListImpl extends AbstractEntityList<RemoteConnectionType> {
    public static Parcelable.Creator<RemoteConnectionTypeListImpl> CREATOR = new Parcelable.Creator<RemoteConnectionTypeListImpl>() {
        public RemoteConnectionTypeListImpl createFromParcel(Parcel parcel) {
            return new RemoteConnectionTypeListImpl(parcel);
        }

        public RemoteConnectionTypeListImpl[] newArray(int i) {
            return new RemoteConnectionTypeListImpl[i];
        }
    };
    public static final String LIST_KEY = "remoteConnectionTypes";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public RemoteConnectionTypeListImpl() {
    }

    private RemoteConnectionTypeListImpl(Parcel in) {
        super(in);
    }
}
