package com.nautilus.omni.util;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

public class DateUtil {
    public static int DEFAULT_DAY_FIRST = 1;
    public static int DEFAULT_MONTH_JANUARY = 1;
    public static int DEFAULT_OLD_YEAR = 2013;

    public static DateTime buildWorkoutValidDate(int year, int month, int day) {
        DateTime currentDate = new DateTime();
        boolean isLeapYear = isLeapYear(year);
        if (isYearBefore2013(year)) {
            return currentDate.withYear(DEFAULT_OLD_YEAR).withMonthOfYear(DEFAULT_MONTH_JANUARY).withDayOfMonth(DEFAULT_DAY_FIRST);
        }
        if (isInvalidYear(year) || isInvalidMonth(month) || isInvalidDay(day, month, isLeapYear) || currentDate.withYear(year).withMonthOfYear(month).withDayOfMonth(day).isAfter((ReadableInstant) currentDate)) {
            return currentDate;
        }
        return new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
    }

    private static boolean isYearBefore2013(int year) {
        return year < DEFAULT_OLD_YEAR;
    }

    private static boolean isInvalidYear(int year) {
        if (year > new DateTime().getYear()) {
            return true;
        }
        return false;
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        if (year % 4 == 0 && year % 100 == 0 && year % MorphingAnimation.DURATION_NORMAL == 0) {
            return true;
        }
        return false;
    }

    private static boolean isInvalidMonth(int month) {
        if (month < 1 || month > 12) {
            return true;
        }
        return false;
    }

    private static boolean isInvalidDay(int day, int month, boolean isLeapYear) {
        if (day < 1 || day > 31) {
            return true;
        }
        if (day > 30 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            return true;
        }
        if (day > 28 && month == 2 && !isLeapYear) {
            return true;
        }
        if (day <= 29 || month != 2) {
            return false;
        }
        return true;
    }

    public static DateTime getFirstDayOfCurrentWeek() {
        DateTime currentDate = new DateTime();
        if (currentDate.getDayOfWeek() != 7) {
            return currentDate.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).minusDays(1);
        }
        return currentDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
    }

    public static DateTime getFirstDayOftWeek(DateTime date) {
        if (date.getDayOfWeek() != 7) {
            return date.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).minusDays(1);
        }
        return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
    }

    public static DateTime getFirstDayOfYear(DateTime yearDate) {
        return yearDate.withMonthOfYear(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    public static DateTime getLastDayOfYear(DateTime yearDate) {
        return yearDate.withMonthOfYear(12).withDayOfMonth(31).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }

    public static DateTime getLastDayOfCurrentWeek() {
        DateTime currentDate = new DateTime();
        if (currentDate.getDayOfWeek() != 7) {
            return currentDate.withDayOfWeek(6).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        }
        return currentDate.plusDays(6).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }

    public static DateTime getFirstTimeOfDay(DateTime date) {
        return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
    }

    public static DateTime getLastTimeOfDay(DateTime date) {
        return date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }

    public static DateTime getLastDayOfWeek(DateTime date) {
        if (date.getDayOfWeek() != 7) {
            return date.withDayOfWeek(6).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        }
        return date.plusDays(6).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
    }

    public static boolean isDateBetween(DateTime startDate, DateTime currentDate, DateTime endDate) {
        if ((currentDate.isEqual(startDate.getMillis()) || currentDate.isAfter(startDate.getMillis())) && (currentDate.isBefore(endDate.getMillis()) || currentDate.isEqual(endDate.getMillis()))) {
            return true;
        }
        return false;
    }

    public static boolean isSameDay(DateTime firstDate, DateTime secondDate) {
        return firstDate.getYear() == secondDate.getYear() && firstDate.getDayOfYear() == secondDate.getDayOfYear();
    }

    public static int getValidHour(int hour) {
        if (hour > 23 || hour < 0) {
            return 0;
        }
        return hour;
    }

    public static int getValidMinute(int minutes) {
        if (minutes > 59 || minutes < 0) {
            return 0;
        }
        return minutes;
    }

    public static int getValidSecond(int seconds) {
        if (seconds > 59 || seconds < 0) {
            return 0;
        }
        return seconds;
    }
}
