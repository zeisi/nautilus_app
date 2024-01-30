package com.ua.sdk.util;

import android.os.Parcel;
import android.os.Parcelable;

public class DoubleList implements Parcelable {
    public static final Parcelable.Creator<DoubleList> CREATOR = new Parcelable.Creator<DoubleList>() {
        public DoubleList createFromParcel(Parcel source) {
            return new DoubleList(source);
        }

        public DoubleList[] newArray(int size) {
            return new DoubleList[size];
        }
    };
    static final double[] EMPTY = new double[0];
    static final int MIN_CAPACITY_INCREMENT = 12;
    double[] array;
    int size;

    public DoubleList() {
        this.array = EMPTY;
        this.size = 0;
    }

    public DoubleList(int capacity) {
        this.array = new double[capacity];
        this.size = 0;
    }

    public DoubleList(double[] array2) {
        if (array2 != null) {
            this.array = array2;
            this.size = array2.length;
            return;
        }
        this.array = EMPTY;
        this.size = 0;
    }

    public double set(int index, double value) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        double[] array2 = this.array;
        double result = array2[index];
        array2[index] = value;
        return result;
    }

    public double get(int index) {
        if (index < 0 || index > this.size) {
            throwIndexOutOfBoundsException(index, this.size);
        }
        return this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    public void add(double value) {
        if (this.size == this.array.length) {
            double[] array2 = this.array;
            int length = array2.length;
            double[] newArray = new double[(length < 12 ? length + 12 : length * 2)];
            System.arraycopy(array2, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        double[] dArr = this.array;
        int i = this.size;
        this.size = i + 1;
        dArr[i] = value;
    }

    public void clear() {
        this.size = 0;
    }

    public double[] toArray() {
        double[] copy = new double[this.size];
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
        dest.writeDoubleArray(this.array);
        dest.writeInt(this.size);
    }

    private DoubleList(Parcel in) {
        this.array = in.createDoubleArray();
        this.size = in.readInt();
    }
}
