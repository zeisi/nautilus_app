package com.ua.sdk.page.association;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class PageAssociationList extends AbstractEntityList<PageAssociation> {
    public static Parcelable.Creator<PageAssociationList> CREATOR = new Parcelable.Creator<PageAssociationList>() {
        public PageAssociationList createFromParcel(Parcel source) {
            return new PageAssociationList(source);
        }

        public PageAssociationList[] newArray(int size) {
            return new PageAssociationList[size];
        }
    };
    private static final String LIST_KEY = "page_associations";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public PageAssociationList() {
    }

    private PageAssociationList(Parcel in) {
        super(in);
    }
}
