package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class PageListImpl extends AbstractEntityList<Page> {
    public static Parcelable.Creator<PageListImpl> CREATOR = new Parcelable.Creator<PageListImpl>() {
        public PageListImpl createFromParcel(Parcel source) {
            return new PageListImpl(source);
        }

        public PageListImpl[] newArray(int size) {
            return new PageListImpl[size];
        }
    };
    public static final String LIST_KEY = "pages";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "pages";
    }

    public PageListImpl() {
    }

    private PageListImpl(Parcel in) {
        super(in);
    }
}
