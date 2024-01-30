package com.ua.sdk.bodymass;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.Date;

public class BodyMassImpl extends ApiTransferObject implements BodyMass {
    public static Parcelable.Creator<BodyMassImpl> CREATOR = new Parcelable.Creator<BodyMassImpl>() {
        public BodyMassImpl createFromParcel(Parcel source) {
            return new BodyMassImpl(source);
        }

        public BodyMassImpl[] newArray(int size) {
            return new BodyMassImpl[size];
        }
    };
    private static final String LINK_USER = "user";
    @SerializedName("bmi")
    String bmi;
    @SerializedName("created_datetime")
    Date createdDateTime;
    @SerializedName("datetime_timezone")
    String dateTimeTimezone;
    @SerializedName("datetime_utc")
    Date dateTimeUtc;
    @SerializedName("fat_mass")
    String fatMass;
    @SerializedName("fat_percent")
    String fatPercent;
    @SerializedName("lean_mass")
    String leanMass;
    @SerializedName("mass")
    String mass;
    @SerializedName("recorder_type_key")
    String recorderType;
    @SerializedName("reference_key")
    String referenceKey;
    @SerializedName("updated_datetime")
    Date updatedDateTime;
    private transient LinkEntityRef<User> userRef;

    public BodyMassImpl() {
    }

    private BodyMassImpl(Parcel in) {
        super(in);
        this.dateTimeUtc = (Date) in.readValue(Date.class.getClassLoader());
        this.dateTimeTimezone = in.readString();
        this.createdDateTime = (Date) in.readValue(Date.class.getClassLoader());
        this.updatedDateTime = (Date) in.readValue(Date.class.getClassLoader());
        this.recorderType = in.readString();
        this.referenceKey = in.readString();
        this.mass = in.readString();
        this.bmi = in.readString();
        this.fatPercent = in.readString();
        this.leanMass = in.readString();
        this.fatMass = in.readString();
    }

    public Date getDateTimeUtc() {
        return this.dateTimeUtc;
    }

    public void setDateTimeUtc(Date dateTimeUtc2) {
        this.dateTimeUtc = dateTimeUtc2;
    }

    public String getDateTimeTimezone() {
        return this.dateTimeTimezone;
    }

    public void setDateTimeTimezone(String dateTimeTimezone2) {
        this.dateTimeTimezone = dateTimeTimezone2;
    }

    public Date getCreatedDateTime() {
        return this.createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime2) {
        this.createdDateTime = createdDateTime2;
    }

    public Date getUpdatedDateTime() {
        return this.updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime2) {
        this.updatedDateTime = updatedDateTime2;
    }

    public String getRecorderType() {
        return this.recorderType;
    }

    public void setRecorderType(String recorderType2) {
        this.recorderType = recorderType2;
    }

    public String getReferenceKey() {
        return this.referenceKey;
    }

    public void setReferenceKey(String referenceKey2) {
        this.referenceKey = referenceKey2;
    }

    public String getMass() {
        return this.mass;
    }

    public void setMass(String mass2) {
        this.mass = mass2;
    }

    public String getBmi() {
        return this.bmi;
    }

    public void setBmi(String bmi2) {
        this.bmi = bmi2;
    }

    public String getFatPercent() {
        return this.fatPercent;
    }

    public void setFatPercent(String fatPercent2) {
        this.fatPercent = fatPercent2;
    }

    public String getLeanMass() {
        return this.leanMass;
    }

    public void setLeanMass(String leanMass2) {
        this.leanMass = leanMass2;
    }

    public String getFatMass() {
        return this.fatMass;
    }

    public void setFatMass(String fatMass2) {
        this.fatMass = fatMass2;
    }

    public EntityRef<User> getUserRef() {
        Link ref;
        if (this.userRef == null && (ref = getLink("user")) != null) {
            this.userRef = new LinkEntityRef<>(ref.getId(), ref.getHref());
        }
        return this.userRef;
    }

    public EntityRef<BodyMass> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.dateTimeUtc);
        dest.writeString(this.dateTimeTimezone);
        dest.writeValue(this.createdDateTime);
        dest.writeValue(this.updatedDateTime);
        dest.writeString(this.recorderType);
        dest.writeString(this.referenceKey);
        dest.writeString(this.mass);
        dest.writeString(this.bmi);
        dest.writeString(this.fatPercent);
        dest.writeString(this.leanMass);
        dest.writeString(this.fatMass);
    }
}
