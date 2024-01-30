package com.ua.sdk.util;

import android.os.Parcel;
import android.os.Parcelable;

public class LongList implements Parcelable {
    public static final Parcelable.Creator<LongList> CREATOR = new Parcelable.Creator<LongList>() {
        public LongList createFromParcel(Parcel source) {
            return new LongList(source);
        }

        public LongList[] newArray(int size) {
            return new LongList[size];
        }
    };
    static final long[] EMPTY = new long[0];
    static final int MIN_CAPACITY_INCREMENT = 12;
    long[] array;
    int size;

    public LongList() {
        this.array = EMPTY;
        this.size = 0;
    }

    public LongList(int capacity) {
        this.array = new long[capacity];
        this.size = 0;
    }

    public LongList(long[] array2) {
        if (array2 != null) {
            this.array = array2;
            this.size = array2.length;
            return;
        }
        this.array = EMPTY;
        this.size = 0;
    }

    public long set(int index, long value) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        long[] array2 = this.array;
        long result = array2[index];
        array2[index] = value;
        return result;
    }

    public long get(int index) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        return this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    public void add(long value) {
        if (this.size == this.array.length) {
            long[] array2 = this.array;
            int length = array2.length;
            long[] newArray = new long[(length < 12 ? length + 12 : length * 2)];
            System.arraycopy(array2, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        long[] jArr = this.array;
        int i = this.size;
        this.size = i + 1;
        jArr[i] = value;
    }

    public void clear() {
        this.size = 0;
    }

    public long[] toArray() {
        long[] copy = new long[this.size];
        System.arraycopy(this.array, 0, copy, 0, this.size);
        return copy;
    }

    static IndexOutOfBoundsException throwIndexOutOfBoundsException(int index, int size2) {
        throw new IndexOutOfBoundsException("Invalid index " + index + ", size is " + size2);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(this.array);
        dest.writeInt(this.size);
    }

    private LongList(Parcel in) {
        this.array = in.createLongArray();
        this.size = in.readInt();
    }
}
