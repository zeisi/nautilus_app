package com.ua.sdk.moderation;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;

public class ModerationActionImpl extends ApiTransferObject implements ModerationAction {
    public static final Parcelable.Creator<ModerationActionImpl> CREATOR = new Parcelable.Creator<ModerationActionImpl>() {
        public ModerationActionImpl createFromParcel(Parcel source) {
            return new ModerationActionImpl(source);
        }

        public ModerationActionImpl[] newArray(int size) {
            return new ModerationActionImpl[size];
        }
    };
    @SerializedName("action")
    String actionHref;
    @SerializedName("resource")
    String resourceHref;

    public ModerationActionImpl() {
    }

    public String getAction() {
        return this.actionHref;
    }

    public String getResource() {
        return this.resourceHref;
    }

    public void setAction(String href) {
        this.actionHref = href;
    }

    public void setResource(String href) {
        this.resourceHref = href;
    }

    public EntityRef getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.actionHref);
        dest.writeString(this.resourceHref);
    }

    private ModerationActionImpl(Parcel in) {
        super(in);
        this.actionHref = in.readString();
        this.resourceHref = in.readString();
    }
}
