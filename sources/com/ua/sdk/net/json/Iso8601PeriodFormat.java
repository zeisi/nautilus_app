package com.ua.sdk.net.json;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.UaException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Iso8601PeriodFormat implements Parcelable {
    public static Parcelable.Creator<Iso8601PeriodFormat> CREATOR = new Parcelable.Creator<Iso8601PeriodFormat>() {
        public Iso8601PeriodFormat createFromParcel(Parcel source) {
            return new Iso8601PeriodFormat(source);
        }

        public Iso8601PeriodFormat[] newArray(int size) {
            return new Iso8601PeriodFormat[size];
        }
    };
    private static final String PERIOD = "P";
    final List<Duration> durations;

    private Iso8601PeriodFormat(Builder init) {
        this.durations = new ArrayList();
        this.durations.addAll(init.durations);
    }

    private Iso8601PeriodFormat(Parcel in) {
        this.durations = new ArrayList();
        this.durations.addAll(in.readArrayList(Duration.class.getClassLoader()));
    }

    public static Iso8601PeriodFormat parse(String period) throws UaException {
        try {
            period = period.toUpperCase();
            String[] buckets = period.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            if (buckets.length == 0 || !buckets[0].equalsIgnoreCase(PERIOD) || buckets.length % 2 == 0) {
                throw new UaException("Unable to parse '" + period + "'");
            }
            Builder builder = new Builder();
            for (int i = 1; i < buckets.length; i += 2) {
                builder.addDuration(new Duration(Integer.valueOf(buckets[i]).intValue(), Interval.parse(buckets[i + 1])));
            }
            return builder.build();
        } catch (Exception e) {
            throw new UaException("Unable to parse '" + period + "'", (Throwable) e);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.durations);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PERIOD);
        for (Duration duration : this.durations) {
            sb.append(duration.toString());
        }
        return sb.toString().toUpperCase();
    }

    static class Builder {
        final List<Duration> durations = new ArrayList();
        final Set<Interval> intervals = Collections.synchronizedSet(EnumSet.noneOf(Interval.class));

        public Builder addDuration(Duration duration) {
            if (this.intervals.add(duration.interval)) {
                this.durations.add(duration);
            }
            return this;
        }

        public Iso8601PeriodFormat build() {
            Collections.sort(this.durations, new Comparator<Duration>() {
                public int compare(Duration lhs, Duration rhs) {
                    if (rhs.interval.weight > lhs.interval.weight) {
                        return 1;
                    }
                    return rhs.interval.weight == lhs.interval.weight ? 0 : -1;
                }
            });
            return new Iso8601PeriodFormat(this);
        }
    }

    static class Duration implements Parcelable {
        public static Parcelable.Creator<Duration> CREATOR = new Parcelable.Creator<Duration>() {
            public Duration createFromParcel(Parcel source) {
                return new Duration(source);
            }

            public Duration[] newArray(int size) {
                return new Duration[size];
            }
        };
        final int elapsed;
        final Interval interval;

        public Duration(int elapsed2, Interval interval2) {
            this.elapsed = elapsed2;
            this.interval = interval2;
        }

        private Duration(Parcel in) {
            this.elapsed = in.readInt();
            this.interval = (Interval) in.readValue(Interval.class.getClassLoader());
        }

        public String toString() {
            return this.elapsed + this.interval.toString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.elapsed);
            dest.writeValue(this.interval);
        }
    }

    enum Interval {
        DAYS("D", 0),
        WEEKS("W", 1),
        MONTHS("M", 2),
        YEARS("Y", 3);
        
        String name;
        int weight;

        private Interval(String name2, int weight2) {
            this.name = name2;
            this.weight = weight2;
        }

        public static Interval parse(String value) throws UaException {
            for (Interval interval : values()) {
                if (interval.name.equalsIgnoreCase(value)) {
                    return interval;
                }
            }
            throw new UaException(value + " is currently not supported!");
        }

        public String toString() {
            return this.name;
        }
    }
}
