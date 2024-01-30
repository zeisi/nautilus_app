package com.ua.sdk.util;

import android.os.Parcel;
import android.os.Parcelable;

public class IntList implements Parcelable {
    public static final Parcelable.Creator<IntList> CREATOR = new Parcelable.Creator<IntList>() {
        public IntList createFromParcel(Parcel source) {
            return new IntList(source);
        }

        public IntList[] newArray(int size) {
            return new IntList[size];
        }
    };
    static final int[] EMPTY = new int[0];
    static final int MIN_CAPACITY_INCREMENT = 12;
    int[] array;
    int size;

    public IntList() {
        this.array = EMPTY;
        this.size = 0;
    }

    public IntList(int capacity) {
        this.array = new int[capacity];
        this.size = 0;
    }

    public IntList(int[] array2) {
        if (array2 != null) {
            this.array = array2;
            this.size = array2.length;
            return;
        }
        this.array = EMPTY;
        this.size = 0;
    }

    public int set(int index, int value) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        int[] array2 = this.array;
        int result = array2[index];
        array2[index] = value;
        return result;
    }

    public int get(int index) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        return this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    public void add(int value) {
        if (this.size == this.array.length) {
            int[] array2 = this.array;
            int length = array2.length;
            int[] newArray = new int[(length < 12 ? length + 12 : length * 2)];
            System.arraycopy(array2, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        int[] iArr = this.array;
        int i = this.size;
        this.size = i + 1;
        iArr[i] = value;
    }

    public void clear() {
        this.size = 0;
    }

    public int[] toArray() {
        int[] copy = new int[this.size];
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
        dest.writeIntArray(this.array);
        dest.writeInt(this.size);
    }

    private IntList(Parcel in) {
        this.array = in.createIntArray();
        this.size = in.readInt();
    }
}
