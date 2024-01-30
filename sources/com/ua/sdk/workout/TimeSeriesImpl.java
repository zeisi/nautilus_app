package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.workout.BaseTimeSeriesEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TimeSeriesImpl<T extends BaseTimeSeriesEntry> implements TimeSeries<T> {
    public static final Parcelable.Creator<TimeSeriesImpl> CREATOR = new Parcelable.Creator<TimeSeriesImpl>() {
        public TimeSeriesImpl createFromParcel(Parcel source) {
            return new TimeSeriesImpl(source);
        }

        public TimeSeriesImpl[] newArray(int size) {
            return new TimeSeriesImpl[size];
        }
    };
    List<T> arrayList;

    public TimeSeriesImpl() {
        this.arrayList = new ArrayList();
    }

    protected TimeSeriesImpl(ArrayList<T> arrayList2) {
        this.arrayList = arrayList2;
    }

    public void add(T object) {
        this.arrayList.add(object);
    }

    public void sort() {
        Collections.sort(this.arrayList);
    }

    public T get(int index) {
        return (BaseTimeSeriesEntry) this.arrayList.get(index);
    }

    public int getSize() {
        return this.arrayList.size();
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int index;

        private MyIterator() {
            this.index = 0;
        }

        public boolean hasNext() {
            return this.index < TimeSeriesImpl.this.getSize();
        }

        public T next() {
            if (this.index < TimeSeriesImpl.this.getSize()) {
                T object = (BaseTimeSeriesEntry) TimeSeriesImpl.this.arrayList.get(this.index);
                this.index++;
                return object;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.arrayList);
    }

    protected TimeSeriesImpl(Parcel in) {
        this.arrayList = new ArrayList();
        in.readList(this.arrayList, BaseTimeSeriesEntry.class.getClassLoader());
    }
}
