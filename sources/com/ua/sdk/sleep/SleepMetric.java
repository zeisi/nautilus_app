package com.ua.sdk.sleep;

import android.os.Parcelable;
import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;
import java.util.Date;
import java.util.TimeZone;

public interface SleepMetric extends Entity<EntityRef<SleepMetric>> {
    public static final int AWAKE = 1;
    public static final int DEEP = 3;
    public static final int LIGHT = 2;

    public interface Aggregates extends Parcelable {
        int getTimeToSleep();

        int getTimesAwakened();

        int getTotalDeepSleep();

        int getTotalLightSleep();

        int getTotalSleep();

        int getTotalTimeAwake();
    }

    public interface TimeSeries extends Parcelable {
        long getEpoch(int i) throws IndexOutOfBoundsException;

        State getState(int i) throws IndexOutOfBoundsException;

        int size();
    }

    Aggregates getAggregates();

    Date getCreatedDateTime();

    Date getEndDateTime();

    String getRecorderTypeKey();

    SleepRef getRef();

    String getReferenceKey();

    Date getStartDateTime();

    TimeZone getStartTimeZone();

    TimeSeries getTimeSeries();

    Date getUpdatedDateTime();

    public enum State {
        AWAKE(1),
        LIGHT_SLEEP(2),
        DEEP_SLEEP(3);
        
        public final int value;

        private State(int value2) {
            this.value = value2;
        }

        public static final State getState(int value2) {
            if (value2 == AWAKE.value) {
                return AWAKE;
            }
            if (value2 == LIGHT_SLEEP.value) {
                return LIGHT_SLEEP;
            }
            if (value2 == DEEP_SLEEP.value) {
                return DEEP_SLEEP;
            }
            return null;
        }
    }
}
