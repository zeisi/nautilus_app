package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class PageTypeListImpl extends AbstractEntityList<PageType> {
    public static Parcelable.Creator<PageTypeListImpl> CREATOR = new Parcelable.Creator<PageTypeListImpl>() {
        public PageTypeListImpl createFromParcel(Parcel source) {
            return new PageTypeListImpl(source);
        }

        public PageTypeListImpl[] newArray(int size) {
            return new PageTypeListImpl[size];
        }
    };
    public static final String LIST_KEY = "page_types";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public PageTypeListImpl() {
    }

    private PageTypeListImpl(Parcel in) {
        super(in);
    }
}
