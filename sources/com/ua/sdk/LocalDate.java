package com.ua.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobsandgeeks.saripaar.DateFormats;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalDate implements Parcelable {
    public static final int APRIL = 3;
    public static final int AUGUST = 7;
    public static Parcelable.Creator<LocalDate> CREATOR = new Parcelable.Creator<LocalDate>() {
        public LocalDate createFromParcel(Parcel source) {
            return new LocalDate(source);
        }

        public LocalDate[] newArray(int size) {
            return new LocalDate[size];
        }
    };
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat get() {
            return new SimpleDateFormat(DateFormats.YMD);
        }
    };
    public static final int DECEMBER = 11;
    public static final int FEBUARY = 1;
    public static final int JANUARY = 0;
    public static final int JULY = 6;
    public static final int JUNE = 5;
    public static final int MARCH = 2;
    public static final int MAY = 4;
    public static final int NOVEMBER = 10;
    public static final int OCTOBER = 9;
    public static final int SEPTEMPER = 8;
    private int dayOfMonth;
    private int month;
    private int year;

    public LocalDate(int year2, int month2, int dayOfMonth2) {
        if (month2 < 0 || month2 > 11) {
            throw new IllegalArgumentException("month must be a value 0 - 11");
        }
        this.year = year2;
        this.month = month2;
        this.dayOfMonth = dayOfMonth2;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.dayOfMonth);
    }

    private LocalDate(Parcel in) {
        this.year = in.readInt();
        this.month = in.readInt();
        this.dayOfMonth = in.readInt();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(10);
        sb.append(this.year);
        sb.append('-');
        if (this.month < 9) {
            sb.append('0');
        }
        sb.append(this.month + 1);
        sb.append('-');
        if (this.dayOfMonth < 10) {
            sb.append('0');
        }
        sb.append(this.dayOfMonth);
        return sb.toString();
    }

    public static final LocalDate fromString(String string) {
        if (string == null) {
            return null;
        }
        try {
            SimpleDateFormat format = DATE_FORMAT.get();
            format.parse(string);
            return fromCalendar(format.getCalendar());
        } catch (ParseException e) {
            UaLog.debug("Error parsing LocalDate", (Throwable) e);
            return null;
        }
    }

    public static final LocalDate fromCalendar(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return new LocalDate(cal.get(1), cal.get(2), cal.get(5));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalDate)) {
            return false;
        }
        LocalDate localDate = (LocalDate) o;
        if (this.dayOfMonth != localDate.dayOfMonth) {
            return false;
        }
        if (this.month != localDate.month) {
            return false;
        }
        if (this.year != localDate.year) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.year * 31) + this.month) * 31) + this.dayOfMonth;
    }
}
