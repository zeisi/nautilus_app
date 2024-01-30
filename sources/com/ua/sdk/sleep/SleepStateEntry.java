package com.ua.sdk.sleep;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.sleep.SleepMetric;

public class SleepStateEntry implements Comparable<SleepStateEntry>, Parcelable {
    public static final Parcelable.Creator<SleepStateEntry> CREATOR = new Parcelable.Creator<SleepStateEntry>() {
        public SleepStateEntry createFromParcel(Parcel source) {
            return new SleepStateEntry(source);
        }

        public SleepStateEntry[] newArray(int size) {
            return new SleepStateEntry[size];
        }
    };
    public final long epoch;
    public final SleepMetric.State state;

    public SleepStateEntry(long epoch2, SleepMetric.State state2) {
        this.epoch = epoch2;
        this.state = state2;
    }

    public int compareTo(SleepStateEntry another) {
        if (this.epoch < another.epoch) {
            return -1;
        }
        return this.epoch == another.epoch ? 0 : 1;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.epoch);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
    }

    private SleepStateEntry(Parcel in) {
        this.epoch = in.readLong();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : SleepMetric.State.values()[tmpState];
    }
}
