package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ActigraphyListRef implements EntityListRef<Actigraphy> {
    public static Parcelable.Creator<ActigraphyListRef> CREATOR = new Parcelable.Creator<ActigraphyListRef>() {
        public ActigraphyListRef createFromParcel(Parcel source) {
            return new ActigraphyListRef(source);
        }

        public ActigraphyListRef[] newArray(int size) {
            return new ActigraphyListRef[size];
        }
    };
    private String href;

    public enum TimeInterval {
        DEFAULT("0"),
        FIFTEEN_MINUTES("900"),
        THIRTY_MINUTES("1800"),
        ONE_HOUR("3600");
        
        private String value;

        private TimeInterval(String value2) {
            this.value = value2;
        }

        public String getValue() {
            return this.value;
        }
    }

    private ActigraphyListRef(Builder init) {
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getHref() {
        return this.href;
    }

    public String getId() {
        return null;
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String DATE_FORMAT = "yyyy-MM-dd";
        private static final int DEFAULT_FETCH_LIMIT = 40;
        private static final int DEFAULT_OFFSET = 0;
        private Date endDate;
        private int limit;
        private int offset;
        private Date startDate;
        private TimeInterval timeInterval;
        private TimeZone timeZone;
        private EntityRef<User> user;

        private Builder() {
            super((String) null);
            this.timeInterval = TimeInterval.DEFAULT;
            this.limit = 40;
            this.offset = 0;
        }

        public Builder setStartDate(Date startDate2) {
            Precondition.isNotNull(startDate2, "startDate");
            this.startDate = startDate2;
            validateTimeLine();
            return this;
        }

        public Builder setEndDate(Date endDate2) {
            this.endDate = endDate2;
            if (endDate2 != null) {
                validateTimeLine();
            }
            return this;
        }

        public Builder setTimeZone(TimeZone timeZone2) {
            Precondition.isNotNull(timeZone2, "timeZone");
            this.timeZone = timeZone2;
            return this;
        }

        public Builder setTimeZone(String timeZone2) {
            Precondition.isNotNull(timeZone2, "timeZone");
            return setTimeZone(TimeZone.getTimeZone(timeZone2));
        }

        public Builder setTimeInterval(TimeInterval timeInterval2) {
            Precondition.isNotNull(timeInterval2, "timeInterval");
            this.timeInterval = timeInterval2;
            return this;
        }

        public Builder setUserRef(EntityRef<User> user2) {
            Precondition.isNotNull(user2, "user");
            this.user = user2;
            return this;
        }

        public Builder setLimit(int limit2) {
            Precondition.isNotNegative(limit2, "limit");
            this.limit = limit2;
            return this;
        }

        public Builder setOffset(int offset2) {
            Precondition.isNotNegative(offset2, "offset");
            this.offset = offset2;
            return this;
        }

        public ActigraphyListRef build() {
            Precondition.isNotNull(this.startDate, "startDate");
            if (this.timeZone == null) {
                setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(this.timeZone);
            String start = simpleDateFormat.format(this.startDate);
            String end = null;
            if (this.endDate != null) {
                end = simpleDateFormat.format(this.endDate);
            }
            setParam(TrainingGoal.START_DATE, start);
            if (end != null) {
                start = end;
            }
            setParam("end_date", start);
            if (this.user != null) {
                setParam("user", this.user.getId());
            }
            if (this.timeInterval != TimeInterval.DEFAULT) {
                setParam("time_series_interval", this.timeInterval.getValue());
            }
            setParam("limit", Integer.toString(this.limit));
            setParam("offset", Integer.toString(this.offset));
            return new ActigraphyListRef(this);
        }

        private void validateTimeLine() {
            if (this.startDate != null && this.endDate != null && this.startDate.getTime() > this.endDate.getTime()) {
                throw new IllegalStateException("the start date can not be greater than the end date.");
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private ActigraphyListRef(Parcel in) {
        this.href = in.readString();
    }
}
