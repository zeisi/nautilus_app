package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.ZeroIsMaxDateTimeField;

abstract class BasicChronology extends AssembledChronology {
    private static final int CACHE_MASK = 1023;
    private static final int CACHE_SIZE = 1024;
    private static final DateTimeField cClockhourOfDayField = new ZeroIsMaxDateTimeField(cHourOfDayField, DateTimeFieldType.clockhourOfDay());
    private static final DateTimeField cClockhourOfHalfdayField = new ZeroIsMaxDateTimeField(cHourOfHalfdayField, DateTimeFieldType.clockhourOfHalfday());
    /* access modifiers changed from: private */
    public static final DurationField cDaysField = new PreciseDurationField(DurationFieldType.days(), 86400000);
    private static final DateTimeField cHalfdayOfDayField = new HalfdayField();
    /* access modifiers changed from: private */
    public static final DurationField cHalfdaysField = new PreciseDurationField(DurationFieldType.halfdays(), 43200000);
    private static final DateTimeField cHourOfDayField = new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), cHoursField, cDaysField);
    private static final DateTimeField cHourOfHalfdayField = new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), cHoursField, cHalfdaysField);
    private static final DurationField cHoursField = new PreciseDurationField(DurationFieldType.hours(), 3600000);
    private static final DurationField cMillisField = MillisDurationField.INSTANCE;
    private static final DateTimeField cMillisOfDayField = new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), cMillisField, cDaysField);
    private static final DateTimeField cMillisOfSecondField = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), cMillisField, cSecondsField);
    private static final DateTimeField cMinuteOfDayField = new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), cMinutesField, cDaysField);
    private static final DateTimeField cMinuteOfHourField = new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), cMinutesField, cHoursField);
    private static final DurationField cMinutesField = new PreciseDurationField(DurationFieldType.minutes(), 60000);
    private static final DateTimeField cSecondOfDayField = new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), cSecondsField, cDaysField);
    private static final DateTimeField cSecondOfMinuteField = new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), cSecondsField, cMinutesField);
    private static final DurationField cSecondsField = new PreciseDurationField(DurationFieldType.seconds(), 1000);
    private static final DurationField cWeeksField = new PreciseDurationField(DurationFieldType.weeks(), 604800000);
    private static final long serialVersionUID = 8283225332206808863L;
    private final int iMinDaysInFirstWeek;
    private final transient YearInfo[] iYearInfoCache = new YearInfo[1024];

    /* access modifiers changed from: package-private */
    public abstract long calculateFirstDayOfYearMillis(int i);

    /* access modifiers changed from: package-private */
    public abstract long getApproxMillisAtEpochDividedByTwo();

    /* access modifiers changed from: package-private */
    public abstract long getAverageMillisPerMonth();

    /* access modifiers changed from: package-private */
    public abstract long getAverageMillisPerYear();

    /* access modifiers changed from: package-private */
    public abstract long getAverageMillisPerYearDividedByTwo();

    /* access modifiers changed from: package-private */
    public abstract int getDaysInMonthMax(int i);

    /* access modifiers changed from: package-private */
    public abstract int getDaysInYearMonth(int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract int getMaxYear();

    /* access modifiers changed from: package-private */
    public abstract int getMinYear();

    /* access modifiers changed from: package-private */
    public abstract int getMonthOfYear(long j, int i);

    /* access modifiers changed from: package-private */
    public abstract long getTotalMillisByYearMonth(int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract long getYearDifference(long j, long j2);

    /* access modifiers changed from: package-private */
    public abstract boolean isLeapYear(int i);

    /* access modifiers changed from: package-private */
    public abstract long setYear(long j, int i);

    BasicChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj);
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Invalid min days in first week: " + i);
        }
        this.iMinDaysInFirstWeek = i;
    }

    public DateTimeZone getZone() {
        Chronology base = getBase();
        if (base != null) {
            return base.getZone();
        }
        return DateTimeZone.UTC;
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfDay(), i4, 0, 86399999);
        return getDateTimeMillis0(i, i2, i3, i4);
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4, int i5, int i6, int i7) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.hourOfDay(), i4, 0, 23);
        FieldUtils.verifyValueBounds(DateTimeFieldType.minuteOfHour(), i5, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.secondOfMinute(), i6, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfSecond(), i7, 0, 999);
        return getDateTimeMillis0(i, i2, i3, (int) ((long) ((DateTimeConstants.MILLIS_PER_HOUR * i4) + (DateTimeConstants.MILLIS_PER_MINUTE * i5) + (i6 * 1000) + i7)));
    }

    private long getDateTimeMillis0(int i, int i2, int i3, int i4) {
        long j;
        long dateMidnightMillis = getDateMidnightMillis(i, i2, i3);
        if (dateMidnightMillis == Long.MIN_VALUE) {
            long dateMidnightMillis2 = getDateMidnightMillis(i, i2, i3 + 1);
            i4 -= DateTimeConstants.MILLIS_PER_DAY;
            j = dateMidnightMillis2;
        } else {
            j = dateMidnightMillis;
        }
        long j2 = ((long) i4) + j;
        if (j2 < 0 && j > 0) {
            return Long.MAX_VALUE;
        }
        if (j2 <= 0 || j >= 0) {
            return j2;
        }
        return Long.MIN_VALUE;
    }

    public int getMinimumDaysInFirstWeek() {
        return this.iMinDaysInFirstWeek;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicChronology basicChronology = (BasicChronology) obj;
        if (getMinimumDaysInFirstWeek() != basicChronology.getMinimumDaysInFirstWeek() || !getZone().equals(basicChronology.getZone())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (getClass().getName().hashCode() * 11) + getZone().hashCode() + getMinimumDaysInFirstWeek();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        String name = getClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(lastIndexOf + 1);
        }
        sb.append(name);
        sb.append('[');
        DateTimeZone zone = getZone();
        if (zone != null) {
            sb.append(zone.getID());
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            sb.append(",mdfw=");
            sb.append(getMinimumDaysInFirstWeek());
        }
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void assemble(AssembledChronology.Fields fields) {
        fields.millis = cMillisField;
        fields.seconds = cSecondsField;
        fields.minutes = cMinutesField;
        fields.hours = cHoursField;
        fields.halfdays = cHalfdaysField;
        fields.days = cDaysField;
        fields.weeks = cWeeksField;
        fields.millisOfSecond = cMillisOfSecondField;
        fields.millisOfDay = cMillisOfDayField;
        fields.secondOfMinute = cSecondOfMinuteField;
        fields.secondOfDay = cSecondOfDayField;
        fields.minuteOfHour = cMinuteOfHourField;
        fields.minuteOfDay = cMinuteOfDayField;
        fields.hourOfDay = cHourOfDayField;
        fields.hourOfHalfday = cHourOfHalfdayField;
        fields.clockhourOfDay = cClockhourOfDayField;
        fields.clockhourOfHalfday = cClockhourOfHalfdayField;
        fields.halfdayOfDay = cHalfdayOfDayField;
        fields.year = new BasicYearDateTimeField(this);
        fields.yearOfEra = new GJYearOfEraDateTimeField(fields.year, this);
        fields.centuryOfEra = new DividedDateTimeField((DateTimeField) new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
        fields.centuries = fields.centuryOfEra.getDurationField();
        fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra), DateTimeFieldType.yearOfCentury(), 1);
        fields.era = new GJEraDateTimeField(this);
        fields.dayOfWeek = new GJDayOfWeekDateTimeField(this, fields.days);
        fields.dayOfMonth = new BasicDayOfMonthDateTimeField(this, fields.days);
        fields.dayOfYear = new BasicDayOfYearDateTimeField(this, fields.days);
        fields.monthOfYear = new GJMonthOfYearDateTimeField(this);
        fields.weekyear = new BasicWeekyearDateTimeField(this);
        fields.weekOfWeekyear = new BasicWeekOfWeekyearDateTimeField(this, fields.weeks);
        fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, fields.centuries, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
        fields.years = fields.year.getDurationField();
        fields.months = fields.monthOfYear.getDurationField();
        fields.weekyears = fields.weekyear.getDurationField();
    }

    /* access modifiers changed from: package-private */
    public int getDaysInYearMax() {
        return 366;
    }

    /* access modifiers changed from: package-private */
    public int getDaysInYear(int i) {
        return isLeapYear(i) ? 366 : 365;
    }

    /* access modifiers changed from: package-private */
    public int getWeeksInYear(int i) {
        return (int) ((getFirstWeekOfYearMillis(i + 1) - getFirstWeekOfYearMillis(i)) / 604800000);
    }

    /* access modifiers changed from: package-private */
    public long getFirstWeekOfYearMillis(int i) {
        long yearMillis = getYearMillis(i);
        int dayOfWeek = getDayOfWeek(yearMillis);
        if (dayOfWeek > 8 - this.iMinDaysInFirstWeek) {
            return yearMillis + (((long) (8 - dayOfWeek)) * 86400000);
        }
        return yearMillis - (((long) (dayOfWeek - 1)) * 86400000);
    }

    /* access modifiers changed from: package-private */
    public long getYearMillis(int i) {
        return getYearInfo(i).iFirstDayMillis;
    }

    /* access modifiers changed from: package-private */
    public long getYearMonthMillis(int i, int i2) {
        return getYearMillis(i) + getTotalMillisByYearMonth(i, i2);
    }

    /* access modifiers changed from: package-private */
    public long getYearMonthDayMillis(int i, int i2, int i3) {
        return getYearMillis(i) + getTotalMillisByYearMonth(i, i2) + (((long) (i3 - 1)) * 86400000);
    }

    /* access modifiers changed from: package-private */
    public int getYear(long j) {
        long j2 = 31536000000L;
        long averageMillisPerYearDividedByTwo = getAverageMillisPerYearDividedByTwo();
        long approxMillisAtEpochDividedByTwo = (j >> 1) + getApproxMillisAtEpochDividedByTwo();
        if (approxMillisAtEpochDividedByTwo < 0) {
            approxMillisAtEpochDividedByTwo = (approxMillisAtEpochDividedByTwo - averageMillisPerYearDividedByTwo) + 1;
        }
        int i = (int) (approxMillisAtEpochDividedByTwo / averageMillisPerYearDividedByTwo);
        long yearMillis = getYearMillis(i);
        long j3 = j - yearMillis;
        if (j3 < 0) {
            return i - 1;
        }
        if (j3 < 31536000000L) {
            return i;
        }
        if (isLeapYear(i)) {
            j2 = 31622400000L;
        }
        if (j2 + yearMillis <= j) {
            return i + 1;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public int getMonthOfYear(long j) {
        return getMonthOfYear(j, getYear(j));
    }

    /* access modifiers changed from: package-private */
    public int getDayOfMonth(long j) {
        int year = getYear(j);
        return getDayOfMonth(j, year, getMonthOfYear(j, year));
    }

    /* access modifiers changed from: package-private */
    public int getDayOfMonth(long j, int i) {
        return getDayOfMonth(j, i, getMonthOfYear(j, i));
    }

    /* access modifiers changed from: package-private */
    public int getDayOfMonth(long j, int i, int i2) {
        return ((int) ((j - (getYearMillis(i) + getTotalMillisByYearMonth(i, i2))) / 86400000)) + 1;
    }

    /* access modifiers changed from: package-private */
    public int getDayOfYear(long j) {
        return getDayOfYear(j, getYear(j));
    }

    /* access modifiers changed from: package-private */
    public int getDayOfYear(long j, int i) {
        return ((int) ((j - getYearMillis(i)) / 86400000)) + 1;
    }

    /* access modifiers changed from: package-private */
    public int getWeekyear(long j) {
        int year = getYear(j);
        int weekOfWeekyear = getWeekOfWeekyear(j, year);
        if (weekOfWeekyear == 1) {
            return getYear(604800000 + j);
        }
        if (weekOfWeekyear > 51) {
            return getYear(j - 1209600000);
        }
        return year;
    }

    /* access modifiers changed from: package-private */
    public int getWeekOfWeekyear(long j) {
        return getWeekOfWeekyear(j, getYear(j));
    }

    /* access modifiers changed from: package-private */
    public int getWeekOfWeekyear(long j, int i) {
        long firstWeekOfYearMillis = getFirstWeekOfYearMillis(i);
        if (j < firstWeekOfYearMillis) {
            return getWeeksInYear(i - 1);
        }
        if (j >= getFirstWeekOfYearMillis(i + 1)) {
            return 1;
        }
        return ((int) ((j - firstWeekOfYearMillis) / 604800000)) + 1;
    }

    /* access modifiers changed from: package-private */
    public int getDayOfWeek(long j) {
        long j2;
        if (j >= 0) {
            j2 = j / 86400000;
        } else {
            j2 = (j - 86399999) / 86400000;
            if (j2 < -3) {
                return ((int) ((j2 + 4) % 7)) + 7;
            }
        }
        return ((int) ((j2 + 3) % 7)) + 1;
    }

    /* access modifiers changed from: package-private */
    public int getMillisOfDay(long j) {
        if (j >= 0) {
            return (int) (j % 86400000);
        }
        return 86399999 + ((int) ((1 + j) % 86400000));
    }

    /* access modifiers changed from: package-private */
    public int getDaysInMonthMax() {
        return 31;
    }

    /* access modifiers changed from: package-private */
    public int getDaysInMonthMax(long j) {
        int year = getYear(j);
        return getDaysInYearMonth(year, getMonthOfYear(j, year));
    }

    /* access modifiers changed from: package-private */
    public int getDaysInMonthMaxForSet(long j, int i) {
        return getDaysInMonthMax(j);
    }

    /* access modifiers changed from: package-private */
    public long getDateMidnightMillis(int i, int i2, int i3) {
        FieldUtils.verifyValueBounds(DateTimeFieldType.year(), i, getMinYear() - 1, getMaxYear() + 1);
        FieldUtils.verifyValueBounds(DateTimeFieldType.monthOfYear(), i2, 1, getMaxMonth(i));
        FieldUtils.verifyValueBounds(DateTimeFieldType.dayOfMonth(), i3, 1, getDaysInYearMonth(i, i2));
        long yearMonthDayMillis = getYearMonthDayMillis(i, i2, i3);
        if (yearMonthDayMillis < 0 && i == getMaxYear() + 1) {
            return Long.MAX_VALUE;
        }
        if (yearMonthDayMillis <= 0 || i != getMinYear() - 1) {
            return yearMonthDayMillis;
        }
        return Long.MIN_VALUE;
    }

    /* access modifiers changed from: package-private */
    public boolean isLeapDay(long j) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getMaxMonth(int i) {
        return getMaxMonth();
    }

    /* access modifiers changed from: package-private */
    public int getMaxMonth() {
        return 12;
    }

    private YearInfo getYearInfo(int i) {
        YearInfo yearInfo = this.iYearInfoCache[i & CACHE_MASK];
        if (yearInfo != null && yearInfo.iYear == i) {
            return yearInfo;
        }
        YearInfo yearInfo2 = new YearInfo(i, calculateFirstDayOfYearMillis(i));
        this.iYearInfoCache[i & CACHE_MASK] = yearInfo2;
        return yearInfo2;
    }

    private static class HalfdayField extends PreciseDateTimeField {
        private static final long serialVersionUID = 581601443656929254L;

        HalfdayField() {
            super(DateTimeFieldType.halfdayOfDay(), BasicChronology.cHalfdaysField, BasicChronology.cDaysField);
        }

        public String getAsText(int i, Locale locale) {
            return GJLocaleSymbols.forLocale(locale).halfdayValueToText(i);
        }

        public long set(long j, String str, Locale locale) {
            return set(j, GJLocaleSymbols.forLocale(locale).halfdayTextToValue(str));
        }

        public int getMaximumTextLength(Locale locale) {
            return GJLocaleSymbols.forLocale(locale).getHalfdayMaxTextLength();
        }
    }

    private static class YearInfo {
        public final long iFirstDayMillis;
        public final int iYear;

        YearInfo(int i, long j) {
            this.iYear = i;
            this.iFirstDayMillis = j;
        }
    }
}
