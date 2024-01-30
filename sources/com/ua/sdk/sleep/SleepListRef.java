package com.ua.sdk.sleep;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.oss.org.codehaus.jackson.map.util.Iso8601DateFormat;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import java.util.Date;

public class SleepListRef implements EntityListRef<SleepMetric>, Parcelable {
    public static final Parcelable.Creator<SleepListRef> CREATOR = new Parcelable.Creator<SleepListRef>() {
        public SleepListRef createFromParcel(Parcel source) {
            return new SleepListRef(source);
        }

        public SleepListRef[] newArray(int size) {
            return new SleepListRef[size];
        }
    };
    private final String href;

    private SleepListRef(Builder builder) {
        this.href = builder.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static class Builder extends BaseReferenceBuilder {
        public static final String END = "target_end_datetime";
        public static final String START = "target_start_datetime";

        protected Builder() {
            super("/api/0.1/sleep/");
        }

        public Builder setTargetStartDateTime(Date start) {
            setParam(START, Iso8601DateFormat.format(start));
            return this;
        }

        public Builder setTargetEndDateTime(Date end) {
            setParam(END, Iso8601DateFormat.format(end));
            return this;
        }

        public SleepListRef build() {
            if (getParam(START) != null) {
                return new SleepListRef(this);
            }
            throw new IllegalStateException("Must specify targetStartDateTime.");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private SleepListRef(Parcel in) {
        this.href = in.readString();
    }
}
