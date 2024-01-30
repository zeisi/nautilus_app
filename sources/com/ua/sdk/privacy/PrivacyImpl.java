package com.ua.sdk.privacy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.privacy.Privacy;

public class PrivacyImpl extends ApiTransferObject implements Privacy, Parcelable {
    public static Parcelable.Creator<PrivacyImpl> CREATOR = new Parcelable.Creator<PrivacyImpl>() {
        public PrivacyImpl createFromParcel(Parcel source) {
            return new PrivacyImpl(source);
        }

        public PrivacyImpl[] newArray(int size) {
            return new PrivacyImpl[size];
        }
    };
    private String mDescription;
    private Privacy.Level mLevel;

    protected PrivacyImpl(Privacy.Level level, String description) {
        this.mLevel = (Privacy.Level) Precondition.isNotNull(level);
        this.mDescription = description;
    }

    public String getPrivacyDescription() {
        return this.mDescription;
    }

    public Privacy.Level getLevel() {
        return this.mLevel;
    }

    public EntityRef<Privacy> getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mLevel.toString());
        dest.writeString(this.mDescription);
    }

    private PrivacyImpl(Parcel in) {
        super(in);
        this.mLevel = Privacy.Level.valueOf(in.readString());
        this.mDescription = in.readString();
    }
}
