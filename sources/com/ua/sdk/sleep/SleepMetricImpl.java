package com.ua.sdk.sleep;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.sleep.SleepMetric;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class SleepMetricImpl extends ApiTransferObject implements SleepMetric {
    public static final Parcelable.Creator<SleepMetricImpl> CREATOR = new Parcelable.Creator<SleepMetricImpl>() {
        public SleepMetricImpl createFromParcel(Parcel source) {
            return new SleepMetricImpl(source);
        }

        public SleepMetricImpl[] newArray(int size) {
            return new SleepMetricImpl[size];
        }
    };
    @SerializedName("aggregates")
    Aggregates aggregates;
    @SerializedName("created_datetime")
    Date createdDateTime;
    @SerializedName("end_datetime_utc")
    Date endDateTime;
    @SerializedName("recorder_type_key")
    String recordTypeKey;
    @SerializedName("reference_key")
    String referenceKey;
    @SerializedName("start_datetime_utc")
    Date startDateTime;
    @SerializedName("start_datetime_timezone")
    TimeZone startTimeZone;
    @SerializedName("time_series")
    TimeSeries timeSeries;
    @SerializedName("updated_datetime")
    Date updatedDateTime;

    protected SleepMetricImpl(SleepMetricBuilderImpl builder) {
        this.recordTypeKey = builder.recorderTypeKey;
        this.referenceKey = builder.referenceKey;
        this.startDateTime = builder.startDateTime;
        this.endDateTime = builder.endDateTime;
        this.startTimeZone = builder.startTimeZone;
        this.aggregates = builder.aggregates;
        this.timeSeries = builder.timeSeries;
    }

    public String getRecorderTypeKey() {
        return this.recordTypeKey;
    }

    public String getReferenceKey() {
        return this.referenceKey;
    }

    public TimeZone getStartTimeZone() {
        return this.startTimeZone;
    }

    public Date getStartDateTime() {
        return this.startDateTime;
    }

    public Date getEndDateTime() {
        return this.endDateTime;
    }

    public SleepMetric.TimeSeries getTimeSeries() {
        return this.timeSeries;
    }

    public SleepMetric.Aggregates getAggregates() {
        return this.aggregates;
    }

    public Date getUpdatedDateTime() {
        return this.updatedDateTime;
    }

    public Date getCreatedDateTime() {
        return this.createdDateTime;
    }

    public SleepRef getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new SleepRef(self.getId(), self.getHref());
    }

    public static class TimeSeries implements SleepMetric.TimeSeries {
        public static final Parcelable.Creator<TimeSeries> CREATOR = new Parcelable.Creator<TimeSeries>() {
            public TimeSeries createFromParcel(Parcel source) {
                return new TimeSeries(source);
            }

            public TimeSeries[] newArray(int size) {
                return new TimeSeries[size];
            }
        };
        final ArrayList<SleepStateEntry> events;

        public TimeSeries() {
            this.events = new ArrayList<>();
        }

        public int size() {
            return this.events.size();
        }

        public long getEpoch(int index) {
            return this.events.get(index).epoch;
        }

        public SleepMetric.State getState(int index) {
            return this.events.get(index).state;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.events);
        }

        private TimeSeries(Parcel in) {
            this.events = in.readArrayList(SleepStateEntry.class.getClassLoader());
        }
    }

    public static class Aggregates implements SleepMetric.Aggregates {
        public static final Parcelable.Creator<Aggregates> CREATOR = new Parcelable.Creator<Aggregates>() {
            public Aggregates createFromParcel(Parcel source) {
                return new Aggregates(source);
            }

            public Aggregates[] newArray(int size) {
                return new Aggregates[size];
            }
        };
        @SerializedName("time_to_sleep")
        int timeToSleep;
        @SerializedName("times_awakened")
        int timesAwakened;
        @SerializedName("total_deep_sleep")
        int totalDeepSleep;
        @SerializedName("total_light_sleep")
        int totalLightSleep;
        @SerializedName("total_sleep")
        int totalSleep;
        @SerializedName("total_time_awake")
        int totalTimeAwake;

        public int getTotalTimeAwake() {
            return this.totalTimeAwake;
        }

        public int getTotalLightSleep() {
            return this.totalLightSleep;
        }

        public int getTotalDeepSleep() {
            return this.totalDeepSleep;
        }

        public int getTotalSleep() {
            return this.totalSleep;
        }

        public int getTimesAwakened() {
            return this.timesAwakened;
        }

        public int getTimeToSleep() {
            return this.timeToSleep;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.timeToSleep);
            dest.writeInt(this.totalTimeAwake);
            dest.writeInt(this.totalLightSleep);
            dest.writeInt(this.totalDeepSleep);
            dest.writeInt(this.totalSleep);
            dest.writeInt(this.timesAwakened);
        }

        public Aggregates() {
        }

        private Aggregates(Parcel in) {
            this.timeToSleep = in.readInt();
            this.totalTimeAwake = in.readInt();
            this.totalLightSleep = in.readInt();
            this.totalDeepSleep = in.readInt();
            this.totalSleep = in.readInt();
            this.timesAwakened = in.readInt();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j;
        long j2;
        long j3 = -1;
        dest.writeParcelable(this.timeSeries, 0);
        dest.writeString(this.recordTypeKey);
        dest.writeString(this.referenceKey);
        dest.writeLong(this.startDateTime != null ? this.startDateTime.getTime() : -1);
        if (this.endDateTime != null) {
            j = this.endDateTime.getTime();
        } else {
            j = -1;
        }
        dest.writeLong(j);
        dest.writeSerializable(this.startTimeZone);
        dest.writeParcelable(this.aggregates, 0);
        if (this.createdDateTime != null) {
            j2 = this.createdDateTime.getTime();
        } else {
            j2 = -1;
        }
        dest.writeLong(j2);
        if (this.updatedDateTime != null) {
            j3 = this.updatedDateTime.getTime();
        }
        dest.writeLong(j3);
    }

    public SleepMetricImpl() {
    }

    private SleepMetricImpl(Parcel in) {
        Date date = null;
        this.timeSeries = (TimeSeries) in.readParcelable(TimeSeries.class.getClassLoader());
        this.recordTypeKey = in.readString();
        this.referenceKey = in.readString();
        long tmpStartDateTime = in.readLong();
        this.startDateTime = tmpStartDateTime == -1 ? null : new Date(tmpStartDateTime);
        long tmpEndDateTime = in.readLong();
        this.endDateTime = tmpEndDateTime == -1 ? null : new Date(tmpEndDateTime);
        this.startTimeZone = (TimeZone) in.readSerializable();
        this.aggregates = (Aggregates) in.readParcelable(Aggregates.class.getClassLoader());
        long tmpCreatedDateTime = in.readLong();
        this.createdDateTime = tmpCreatedDateTime == -1 ? null : new Date(tmpCreatedDateTime);
        long tmpUpdatedDateTime = in.readLong();
        this.updatedDateTime = tmpUpdatedDateTime != -1 ? new Date(tmpUpdatedDateTime) : date;
    }
}
