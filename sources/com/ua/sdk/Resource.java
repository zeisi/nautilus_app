package com.ua.sdk;

import android.os.Parcelable;
import com.ua.sdk.Reference;

public interface Resource<R extends Reference> extends Parcelable {
    R getRef();
}
