package com.ua.sdk.page.follow;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class PageFollowsListImpl extends AbstractEntityList<PageFollow> {
    public static Parcelable.Creator<PageFollowsListImpl> CREATOR = new Parcelable.Creator<PageFollowsListImpl>() {
        public PageFollowsListImpl createFromParcel(Parcel source) {
            return new PageFollowsListImpl(source);
        }

        public PageFollowsListImpl[] newArray(int size) {
            return new PageFollowsListImpl[size];
        }
    };
    public static final String LIST_KEY = "page_follows";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "page_follows";
    }

    public PageFollowsListImpl() {
    }

    private PageFollowsListImpl(Parcel in) {
        super(in);
    }
}
