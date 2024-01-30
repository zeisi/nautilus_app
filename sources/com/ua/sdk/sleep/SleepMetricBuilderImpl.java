package com.ua.sdk.sleep;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.sleep.SleepMetric;
import com.ua.sdk.sleep.SleepMetricImpl;
import java.util.Date;
import java.util.TimeZone;

public class SleepMetricBuilderImpl implements SleepMetricBuilder {
    public static final Parcelable.Creator<SleepMetricBuilderImpl> CREATOR = new Parcelable.Creator<SleepMetricBuilderImpl>() {
        public SleepMetricBuilderImpl createFromParcel(Parcel source) {
            return new SleepMetricBuilderImpl(source);
        }

        public SleepMetricBuilderImpl[] newArray(int size) {
            return new SleepMetricBuilderImpl[size];
        }
    };
    private static final String RECORDER_DEFAULT = "client_manual_creation";
    SleepMetricImpl.Aggregates aggregates;
    int deepSleepSeconds;
    Date endDateTime;
    int lightSleepSeconds;
    String recorderTypeKey;
    String referenceKey;
    Date startDateTime;
    TimeZone startTimeZone;
    int timeAwakeSeconds;
    SleepMetricImpl.TimeSeries timeSeries;
    int timeToSleepSeconds;
    int timesAwakened;

    public SleepMetricBuilder setRecorderTypeKey(String recorderTypeKey2) {
        this.recorderTypeKey = recorderTypeKey2;
        return this;
    }

    public SleepMetricBuilder setReferenceKey(String referenceKey2) {
        this.referenceKey = referenceKey2;
        return this;
    }

    public SleepMetricBuilder setStartTimeZone(TimeZone timeZone) {
        this.startTimeZone = timeZone;
        return this;
    }

    public SleepMetricBuilder setStartDateTime(Date startDateTime2) {
        this.startDateTime = startDateTime2;
        return this;
    }

    public SleepMetricBuilder setEndDateTime(Date endDateTime2) {
        this.endDateTime = endDateTime2;
        return this;
    }

    public SleepMetricBuilder setTotalTimeAwake(int timeAwakeSeconds2) {
        this.timeAwakeSeconds = timeAwakeSeconds2;
        return this;
    }

    public SleepMetricBuilder setTotalLightSleep(int lightSleepSeconds2) {
        this.lightSleepSeconds = lightSleepSeconds2;
        return this;
    }

    public SleepMetricBuilder setTotalDeepSleep(int deepSleepSeconds2) {
        this.deepSleepSeconds = deepSleepSeconds2;
        return this;
    }

    public SleepMetricBuilder setTimesAwakened(int timesAwakened2) {
        this.timesAwakened = timesAwakened2;
        return this;
    }

    public SleepMetricBuilder setTimeToSleep(int timeToSleepSeconds2) {
        this.timeToSleepSeconds = timeToSleepSeconds2;
        return this;
    }

    public SleepMetricBuilder addEvent(long epoch, SleepMetric.State state) {
        if (this.timeSeries == null) {
            this.timeSeries = new SleepMetricImpl.TimeSeries();
        }
        this.timeSeries.events.add(new SleepStateEntry(epoch, state));
        return this;
    }

    /* access modifiers changed from: protected */
    public void createAggregates() {
        int timeAwakeSeconds2 = 0;
        int lightSleepSeconds2 = 0;
        int deepSleepSeconds2 = 0;
        int timesAwakened2 = 0;
        int timeToSleepSeconds2 = 0;
        boolean sleepStarted = false;
        if (this.timeSeries != null && !this.timeSeries.events.isEmpty()) {
            SleepStateEntry prevEntry = this.timeSeries.events.get(0);
            int size = this.timeSeries.events.size();
            for (int i = 1; i < size; i++) {
                SleepStateEntry entry = this.timeSeries.events.get(i);
                if (sleepStarted) {
                    long seconds = entry.epoch - prevEntry.epoch;
                    switch (prevEntry.state) {
                        case LIGHT_SLEEP:
                            lightSleepSeconds2 = (int) (((long) lightSleepSeconds2) + seconds);
                            break;
                        case DEEP_SLEEP:
                            deepSleepSeconds2 = (int) (((long) deepSleepSeconds2) + seconds);
                            break;
                        case AWAKE:
                            timesAwakened2++;
                            timeAwakeSeconds2 = (int) (((long) timeAwakeSeconds2) + seconds);
                            break;
                    }
                } else if (entry.state != SleepMetric.State.AWAKE) {
                    sleepStarted = true;
                    timeToSleepSeconds2 = (int) (entry.epoch - (this.startDateTime.getTime() / 1000));
                }
                prevEntry = entry;
            }
            long seconds2 = (this.endDateTime.getTime() / 1000) - prevEntry.epoch;
            switch (prevEntry.state) {
                case LIGHT_SLEEP:
                    lightSleepSeconds2 = (int) (((long) lightSleepSeconds2) + seconds2);
                    break;
                case DEEP_SLEEP:
                    deepSleepSeconds2 = (int) (((long) deepSleepSeconds2) + seconds2);
                    break;
                case AWAKE:
                    timesAwakened2++;
                    timeAwakeSeconds2 = (int) (((long) timeAwakeSeconds2) + seconds2);
                    break;
            }
        }
        SleepMetricImpl.Aggregates aggregates2 = new SleepMetricImpl.Aggregates();
        if (this.timeToSleepSeconds >= 0) {
            timeToSleepSeconds2 = this.timeToSleepSeconds;
        }
        aggregates2.timeToSleep = timeToSleepSeconds2;
        if (this.timesAwakened >= 0) {
            timesAwakened2 = this.timesAwakened;
        }
        aggregates2.timesAwakened = timesAwakened2;
        if (this.timeAwakeSeconds >= 0) {
            timeAwakeSeconds2 = this.timeAwakeSeconds;
        }
        aggregates2.totalTimeAwake = timeAwakeSeconds2;
        if (this.lightSleepSeconds >= 0) {
            lightSleepSeconds2 = this.lightSleepSeconds;
        }
        aggregates2.totalLightSleep = lightSleepSeconds2;
        if (this.deepSleepSeconds >= 0) {
            deepSleepSeconds2 = this.deepSleepSeconds;
        }
        aggregates2.totalDeepSleep = deepSleepSeconds2;
        aggregates2.totalSleep = aggregates2.totalLightSleep + aggregates2.totalDeepSleep;
        this.aggregates = aggregates2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0063 A[EDGE_INSN: B:37:0x0063->B:23:0x0063 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ua.sdk.sleep.SleepMetricImpl build() {
        /*
            r10 = this;
            r8 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r2 = r10.recorderTypeKey
            if (r2 != 0) goto L_0x000a
            java.lang.String r2 = "client_manual_creation"
            r10.recorderTypeKey = r2
        L_0x000a:
            java.lang.String r2 = r10.referenceKey
            if (r2 != 0) goto L_0x0018
            java.util.UUID r2 = java.util.UUID.randomUUID()
            java.lang.String r2 = r2.toString()
            r10.referenceKey = r2
        L_0x0018:
            java.util.TimeZone r2 = r10.startTimeZone
            if (r2 != 0) goto L_0x0022
            java.util.TimeZone r2 = java.util.TimeZone.getDefault()
            r10.startTimeZone = r2
        L_0x0022:
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r2 = r10.timeSeries
            if (r2 == 0) goto L_0x00a7
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r2 = r10.timeSeries
            java.util.ArrayList<com.ua.sdk.sleep.SleepStateEntry> r2 = r2.events
            java.util.Collections.sort(r2)
            java.util.Date r2 = r10.startDateTime
            if (r2 != 0) goto L_0x007c
            int r2 = r10.timeToSleepSeconds
            if (r2 <= 0) goto L_0x0063
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r2 = r10.timeSeries
            java.util.ArrayList<com.ua.sdk.sleep.SleepStateEntry> r2 = r2.events
            java.util.Iterator r1 = r2.iterator()
        L_0x003d:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0063
            java.lang.Object r0 = r1.next()
            com.ua.sdk.sleep.SleepStateEntry r0 = (com.ua.sdk.sleep.SleepStateEntry) r0
            com.ua.sdk.sleep.SleepMetric$State r2 = r0.state
            com.ua.sdk.sleep.SleepMetric$State r3 = com.ua.sdk.sleep.SleepMetric.State.LIGHT_SLEEP
            if (r2 == r3) goto L_0x0055
            com.ua.sdk.sleep.SleepMetric$State r2 = r0.state
            com.ua.sdk.sleep.SleepMetric$State r3 = com.ua.sdk.sleep.SleepMetric.State.DEEP_SLEEP
            if (r2 != r3) goto L_0x003d
        L_0x0055:
            java.util.Date r2 = new java.util.Date
            long r4 = r0.epoch
            int r3 = r10.timeToSleepSeconds
            long r6 = (long) r3
            long r4 = r4 - r6
            long r4 = r4 * r8
            r2.<init>(r4)
            r10.startDateTime = r2
        L_0x0063:
            java.util.Date r2 = r10.startDateTime
            if (r2 != 0) goto L_0x007c
            java.util.Date r3 = new java.util.Date
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r2 = r10.timeSeries
            java.util.ArrayList<com.ua.sdk.sleep.SleepStateEntry> r2 = r2.events
            r4 = 0
            java.lang.Object r2 = r2.get(r4)
            com.ua.sdk.sleep.SleepStateEntry r2 = (com.ua.sdk.sleep.SleepStateEntry) r2
            long r4 = r2.epoch
            long r4 = r4 * r8
            r3.<init>(r4)
            r10.startDateTime = r3
        L_0x007c:
            java.util.Date r2 = r10.endDateTime
            if (r2 != 0) goto L_0x009e
            java.util.Date r3 = new java.util.Date
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r2 = r10.timeSeries
            java.util.ArrayList<com.ua.sdk.sleep.SleepStateEntry> r2 = r2.events
            com.ua.sdk.sleep.SleepMetricImpl$TimeSeries r4 = r10.timeSeries
            java.util.ArrayList<com.ua.sdk.sleep.SleepStateEntry> r4 = r4.events
            int r4 = r4.size()
            int r4 = r4 + -1
            java.lang.Object r2 = r2.get(r4)
            com.ua.sdk.sleep.SleepStateEntry r2 = (com.ua.sdk.sleep.SleepStateEntry) r2
            long r4 = r2.epoch
            long r4 = r4 * r8
            r3.<init>(r4)
            r10.endDateTime = r3
        L_0x009e:
            r10.createAggregates()
            com.ua.sdk.sleep.SleepMetricImpl r2 = new com.ua.sdk.sleep.SleepMetricImpl
            r2.<init>((com.ua.sdk.sleep.SleepMetricBuilderImpl) r10)
            return r2
        L_0x00a7:
            java.util.Date r2 = r10.startDateTime
            if (r2 == 0) goto L_0x00af
            java.util.Date r2 = r10.endDateTime
            if (r2 != 0) goto L_0x009e
        L_0x00af:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "start datetime and end datetime must be specified if no time series data is set."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.sdk.sleep.SleepMetricBuilderImpl.build():com.ua.sdk.sleep.SleepMetricImpl");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recorderTypeKey);
        dest.writeString(this.referenceKey);
        dest.writeValue(this.startDateTime);
        dest.writeValue(this.endDateTime);
        dest.writeSerializable(this.startTimeZone);
        dest.writeParcelable(this.timeSeries, 0);
        dest.writeParcelable(this.aggregates, 0);
        dest.writeInt(this.timeAwakeSeconds);
        dest.writeInt(this.lightSleepSeconds);
        dest.writeInt(this.deepSleepSeconds);
        dest.writeInt(this.timesAwakened);
        dest.writeInt(this.timeToSleepSeconds);
    }

    public SleepMetricBuilderImpl() {
        this.timeAwakeSeconds = -1;
        this.lightSleepSeconds = -1;
        this.deepSleepSeconds = -1;
        this.timesAwakened = -1;
        this.timeToSleepSeconds = -1;
    }

    private SleepMetricBuilderImpl(Parcel in) {
        this.timeAwakeSeconds = -1;
        this.lightSleepSeconds = -1;
        this.deepSleepSeconds = -1;
        this.timesAwakened = -1;
        this.timeToSleepSeconds = -1;
        this.recorderTypeKey = in.readString();
        this.referenceKey = in.readString();
        this.startDateTime = (Date) in.readValue(Date.class.getClassLoader());
        this.endDateTime = (Date) in.readValue(Date.class.getClassLoader());
        this.startTimeZone = (TimeZone) in.readSerializable();
        this.timeSeries = (SleepMetricImpl.TimeSeries) in.readParcelable(SleepMetricImpl.TimeSeries.class.getClassLoader());
        this.aggregates = (SleepMetricImpl.Aggregates) in.readParcelable(SleepMetricImpl.Aggregates.class.getClassLoader());
        this.timeAwakeSeconds = in.readInt();
        this.lightSleepSeconds = in.readInt();
        this.deepSleepSeconds = in.readInt();
        this.timesAwakened = in.readInt();
        this.timeToSleepSeconds = in.readInt();
    }
}
