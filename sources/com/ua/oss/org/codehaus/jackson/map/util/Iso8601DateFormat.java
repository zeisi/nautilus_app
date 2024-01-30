package com.ua.oss.org.codehaus.jackson.map.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTimeConstants;

public class Iso8601DateFormat {
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone(GMT_ID);

    public static TimeZone timeZoneGMT() {
        return TIMEZONE_GMT;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        int length;
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-ddThh:mm:ss".length() + (millis ? ".sss".length() : 0);
        if (tz.getRawOffset() == 0) {
            length = "Z".length();
        } else {
            length = "+hh:mm".length();
        }
        StringBuilder formatted = new StringBuilder(capacity + length);
        padInt(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / DateTimeConstants.MILLIS_PER_MINUTE) / 60);
            int minutes = Math.abs((offset / DateTimeConstants.MILLIS_PER_MINUTE) % 60);
            formatted.append(offset < 0 ? '-' : '+');
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x019d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Date parse(java.lang.String r21) {
        /*
            r5 = 0
            r11 = 0
            int r12 = r11 + 4
            r0 = r21
            int r17 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r18 = 45
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 2
            r0 = r21
            int r10 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r18 = 45
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 2
            r0 = r21
            int r3 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r18 = 84
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 2
            r0 = r21
            int r6 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r18 = 58
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 2
            r0 = r21
            int r9 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r18 = 58
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 2
            r0 = r21
            int r13 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r8 = 0
            r0 = r21
            char r18 = r0.charAt(r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r19 = 46
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x01c5
            r18 = 46
            r0 = r21
            r1 = r18
            checkOffset(r0, r12, r1)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            int r11 = r12 + 1
            int r12 = r11 + 3
            r0 = r21
            int r8 = parseInt(r0, r11, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01c1, NumberFormatException -> 0x01be, IllegalArgumentException -> 0x0198 }
            r11 = r12
        L_0x008c:
            r15 = 0
            r0 = r21
            char r16 = r0.charAt(r11)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
        L_0x0093:
            r18 = 43
            r0 = r16
            r1 = r18
            if (r0 == r1) goto L_0x010b
            r18 = 45
            r0 = r16
            r1 = r18
            if (r0 == r1) goto L_0x010b
            r18 = 90
            r0 = r16
            r1 = r18
            if (r0 == r1) goto L_0x010b
            int r11 = r11 + 1
            int r18 = r21.length()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r0 = r18
            if (r11 != r0) goto L_0x0104
            java.lang.IndexOutOfBoundsException r18 = new java.lang.IndexOutOfBoundsException     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r19.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.String r20 = "Invalid time zone indicator "
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r0 = r19
            r1 = r16
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.String r19 = r19.toString()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18.<init>(r19)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            throw r18     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
        L_0x00d2:
            r4 = move-exception
        L_0x00d3:
            r5 = r4
        L_0x00d4:
            if (r21 != 0) goto L_0x019d
            r7 = 0
        L_0x00d7:
            java.lang.IllegalArgumentException r18 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "Failed to parse date ["
            java.lang.StringBuilder r19 = r19.append(r20)
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r7)
            java.lang.String r20 = "]: "
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = r5.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r0 = r18
            r1 = r19
            r0.<init>(r1, r5)
            throw r18
        L_0x0104:
            r0 = r21
            char r16 = r0.charAt(r11)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            goto L_0x0093
        L_0x010b:
            r18 = 43
            r0 = r16
            r1 = r18
            if (r0 == r1) goto L_0x011b
            r18 = 45
            r0 = r16
            r1 = r18
            if (r0 != r1) goto L_0x014d
        L_0x011b:
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.String r19 = "GMT"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r0 = r21
            java.lang.String r19 = r0.substring(r11)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.String r15 = r18.toString()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
        L_0x0134:
            java.util.TimeZone r14 = java.util.TimeZone.getTimeZone(r15)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.lang.String r18 = r14.getID()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r0 = r18
            boolean r18 = r0.equals(r15)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            if (r18 != 0) goto L_0x0150
            java.lang.IndexOutOfBoundsException r18 = new java.lang.IndexOutOfBoundsException     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            throw r18     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
        L_0x014a:
            r4 = move-exception
        L_0x014b:
            r5 = r4
            goto L_0x00d4
        L_0x014d:
            java.lang.String r15 = "GMT"
            goto L_0x0134
        L_0x0150:
            java.util.GregorianCalendar r2 = new java.util.GregorianCalendar     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r2.<init>(r14)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 0
            r0 = r18
            r2.setLenient(r0)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 1
            r0 = r18
            r1 = r17
            r2.set(r0, r1)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 2
            int r19 = r10 + -1
            r0 = r18
            r1 = r19
            r2.set(r0, r1)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 5
            r0 = r18
            r2.set(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 11
            r0 = r18
            r2.set(r0, r6)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 12
            r0 = r18
            r2.set(r0, r9)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 13
            r0 = r18
            r2.set(r0, r13)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            r18 = 14
            r0 = r18
            r2.set(r0, r8)     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            java.util.Date r18 = r2.getTime()     // Catch:{ IndexOutOfBoundsException -> 0x00d2, NumberFormatException -> 0x014a, IllegalArgumentException -> 0x01bc }
            return r18
        L_0x0198:
            r4 = move-exception
            r11 = r12
        L_0x019a:
            r5 = r4
            goto L_0x00d4
        L_0x019d:
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            r19 = 34
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r18
            r1 = r21
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r19 = "'"
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r7 = r18.toString()
            goto L_0x00d7
        L_0x01bc:
            r4 = move-exception
            goto L_0x019a
        L_0x01be:
            r4 = move-exception
            r11 = r12
            goto L_0x014b
        L_0x01c1:
            r4 = move-exception
            r11 = r12
            goto L_0x00d3
        L_0x01c5:
            r11 = r12
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ua.oss.org.codehaus.jackson.map.util.Iso8601DateFormat.parse(java.lang.String):java.util.Date");
    }

    private static void checkOffset(String value, int offset, char expected) throws IndexOutOfBoundsException {
        char found = value.charAt(offset);
        if (found != expected) {
            throw new IndexOutOfBoundsException("Expected '" + expected + "' character but found '" + found + "'");
        }
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        int i;
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int i2 = beginIndex;
        int result = 0;
        if (i2 < endIndex) {
            i = i2 + 1;
            int digit = Character.digit(value.charAt(i2), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result = -digit;
        } else {
            i = i2;
        }
        while (i < endIndex) {
            int i3 = i + 1;
            int digit2 = Character.digit(value.charAt(i), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result = (result * 10) - digit2;
            i = i3;
        }
        return -result;
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }
}
