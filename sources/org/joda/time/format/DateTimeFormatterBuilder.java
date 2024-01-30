package org.joda.time.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadablePartial;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.PreciseDateTimeField;

public class DateTimeFormatterBuilder {
    private ArrayList<Object> iElementPairs = new ArrayList<>();
    private Object iFormatter;

    public DateTimeFormatter toFormatter() {
        InternalPrinter internalPrinter;
        InternalParser internalParser;
        Object formatter = getFormatter();
        if (isPrinter(formatter)) {
            internalPrinter = (InternalPrinter) formatter;
        } else {
            internalPrinter = null;
        }
        if (isParser(formatter)) {
            internalParser = (InternalParser) formatter;
        } else {
            internalParser = null;
        }
        if (internalPrinter != null || internalParser != null) {
            return new DateTimeFormatter(internalPrinter, internalParser);
        }
        throw new UnsupportedOperationException("Both printing and parsing not supported");
    }

    public DateTimePrinter toPrinter() {
        Object formatter = getFormatter();
        if (isPrinter(formatter)) {
            return InternalPrinterDateTimePrinter.of((InternalPrinter) formatter);
        }
        throw new UnsupportedOperationException("Printing is not supported");
    }

    public DateTimeParser toParser() {
        Object formatter = getFormatter();
        if (isParser(formatter)) {
            return InternalParserDateTimeParser.of((InternalParser) formatter);
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    public boolean canBuildFormatter() {
        return isFormatter(getFormatter());
    }

    public boolean canBuildPrinter() {
        return isPrinter(getFormatter());
    }

    public boolean canBuildParser() {
        return isParser(getFormatter());
    }

    public void clear() {
        this.iFormatter = null;
        this.iElementPairs.clear();
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter != null) {
            return append0(dateTimeFormatter.getPrinter0(), dateTimeFormatter.getParser0());
        }
        throw new IllegalArgumentException("No formatter supplied");
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter) {
        checkPrinter(dateTimePrinter);
        return append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), (InternalParser) null);
    }

    public DateTimeFormatterBuilder append(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0((InternalPrinter) null, DateTimeParserInternalParser.of(dateTimeParser));
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        checkPrinter(dateTimePrinter);
        checkParser(dateTimeParser);
        return append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), DateTimeParserInternalParser.of(dateTimeParser));
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser[] dateTimeParserArr) {
        int i = 0;
        if (dateTimePrinter != null) {
            checkPrinter(dateTimePrinter);
        }
        if (dateTimeParserArr == null) {
            throw new IllegalArgumentException("No parsers supplied");
        }
        int length = dateTimeParserArr.length;
        if (length != 1) {
            InternalParser[] internalParserArr = new InternalParser[length];
            while (i < length - 1) {
                InternalParser of = DateTimeParserInternalParser.of(dateTimeParserArr[i]);
                internalParserArr[i] = of;
                if (of == null) {
                    throw new IllegalArgumentException("Incomplete parser array");
                }
                i++;
            }
            internalParserArr[i] = DateTimeParserInternalParser.of(dateTimeParserArr[i]);
            return append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), new MatchingParser(internalParserArr));
        } else if (dateTimeParserArr[0] != null) {
            return append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), DateTimeParserInternalParser.of(dateTimeParserArr[0]));
        } else {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0((InternalPrinter) null, new MatchingParser(new InternalParser[]{DateTimeParserInternalParser.of(dateTimeParser), null}));
    }

    private void checkParser(DateTimeParser dateTimeParser) {
        if (dateTimeParser == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private void checkPrinter(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    private DateTimeFormatterBuilder append0(Object obj) {
        this.iFormatter = null;
        this.iElementPairs.add(obj);
        this.iElementPairs.add(obj);
        return this;
    }

    private DateTimeFormatterBuilder append0(InternalPrinter internalPrinter, InternalParser internalParser) {
        this.iFormatter = null;
        this.iElementPairs.add(internalPrinter);
        this.iElementPairs.add(internalParser);
        return this;
    }

    public DateTimeFormatterBuilder appendLiteral(char c) {
        return append0(new CharacterLiteral(c));
    }

    public DateTimeFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        switch (str.length()) {
            case 0:
                return this;
            case 1:
                return append0(new CharacterLiteral(str.charAt(0)));
            default:
                return append0(new StringLiteral(str));
        }
    }

    public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return append0(new UnpaddedNumber(dateTimeFieldType, i2, false));
        } else {
            return append0(new PaddedNumber(dateTimeFieldType, i2, false, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i, false));
        } else {
            throw new IllegalArgumentException("Illegal number of digits: " + i);
        }
    }

    public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return append0(new UnpaddedNumber(dateTimeFieldType, i2, true));
        } else {
            return append0(new PaddedNumber(dateTimeFieldType, i2, true, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedSignedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i, true));
        } else {
            throw new IllegalArgumentException("Illegal number of digits: " + i);
        }
    }

    public DateTimeFormatterBuilder appendText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, false));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendShortText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, true));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendFraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i >= 0 && i2 > 0) {
            return append0(new Fraction(dateTimeFieldType, i, i2));
        }
        throw new IllegalArgumentException();
    }

    public DateTimeFormatterBuilder appendFractionOfSecond(int i, int i2) {
        return appendFraction(DateTimeFieldType.secondOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfMinute(int i, int i2) {
        return appendFraction(DateTimeFieldType.minuteOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfHour(int i, int i2) {
        return appendFraction(DateTimeFieldType.hourOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfDay(int i, int i2) {
        return appendFraction(DateTimeFieldType.dayOfYear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMillisOfSecond(int i) {
        return appendDecimal(DateTimeFieldType.millisOfSecond(), i, 3);
    }

    public DateTimeFormatterBuilder appendMillisOfDay(int i) {
        return appendDecimal(DateTimeFieldType.millisOfDay(), i, 8);
    }

    public DateTimeFormatterBuilder appendSecondOfMinute(int i) {
        return appendDecimal(DateTimeFieldType.secondOfMinute(), i, 2);
    }

    public DateTimeFormatterBuilder appendSecondOfDay(int i) {
        return appendDecimal(DateTimeFieldType.secondOfDay(), i, 5);
    }

    public DateTimeFormatterBuilder appendMinuteOfHour(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfHour(), i, 2);
    }

    public DateTimeFormatterBuilder appendMinuteOfDay(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfDay(), i, 4);
    }

    public DateTimeFormatterBuilder appendHourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.hourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendHourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.hourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfWeek(int i) {
        return appendDecimal(DateTimeFieldType.dayOfWeek(), i, 1);
    }

    public DateTimeFormatterBuilder appendDayOfMonth(int i) {
        return appendDecimal(DateTimeFieldType.dayOfMonth(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfYear(int i) {
        return appendDecimal(DateTimeFieldType.dayOfYear(), i, 3);
    }

    public DateTimeFormatterBuilder appendWeekOfWeekyear(int i) {
        return appendDecimal(DateTimeFieldType.weekOfWeekyear(), i, 2);
    }

    public DateTimeFormatterBuilder appendWeekyear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.weekyear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMonthOfYear(int i) {
        return appendDecimal(DateTimeFieldType.monthOfYear(), i, 2);
    }

    public DateTimeFormatterBuilder appendYear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.year(), i, i2);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i) {
        return appendTwoDigitYear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i, boolean z) {
        return append0(new TwoDigitYear(DateTimeFieldType.year(), i, z));
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i) {
        return appendTwoDigitWeekyear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i, boolean z) {
        return append0(new TwoDigitYear(DateTimeFieldType.weekyear(), i, z));
    }

    public DateTimeFormatterBuilder appendYearOfEra(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendYearOfCentury(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfCentury(), i, i2);
    }

    public DateTimeFormatterBuilder appendCenturyOfEra(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.centuryOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendHalfdayOfDayText() {
        return appendText(DateTimeFieldType.halfdayOfDay());
    }

    public DateTimeFormatterBuilder appendDayOfWeekText() {
        return appendText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfWeekShortText() {
        return appendShortText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendMonthOfYearText() {
        return appendText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendMonthOfYearShortText() {
        return appendShortText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendEraText() {
        return appendText(DateTimeFieldType.era());
    }

    public DateTimeFormatterBuilder appendTimeZoneName() {
        return append0(new TimeZoneName(0, (Map<String, DateTimeZone>) null), (InternalParser) null);
    }

    public DateTimeFormatterBuilder appendTimeZoneName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(0, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName() {
        return append0(new TimeZoneName(1, (Map<String, DateTimeZone>) null), (InternalParser) null);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(1, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneId() {
        return append0(TimeZoneId.INSTANCE, TimeZoneId.INSTANCE);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, boolean z, int i, int i2) {
        return append0(new TimeZoneOffset(str, str, z, i, i2));
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
        return append0(new TimeZoneOffset(str, str2, z, i, i2));
    }

    public DateTimeFormatterBuilder appendPattern(String str) {
        DateTimeFormat.appendPatternTo(this, str);
        return this;
    }

    private Object getFormatter() {
        Object obj = this.iFormatter;
        if (obj == null) {
            if (this.iElementPairs.size() == 2) {
                Object obj2 = this.iElementPairs.get(0);
                Object obj3 = this.iElementPairs.get(1);
                if (obj2 == null) {
                    obj = obj3;
                } else if (obj2 == obj3 || obj3 == null) {
                    obj = obj2;
                }
            }
            if (obj == null) {
                obj = new Composite(this.iElementPairs);
            }
            this.iFormatter = obj;
        }
        return obj;
    }

    private boolean isPrinter(Object obj) {
        if (!(obj instanceof InternalPrinter)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).isPrinter();
        }
        return true;
    }

    private boolean isParser(Object obj) {
        if (!(obj instanceof InternalParser)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).isParser();
        }
        return true;
    }

    private boolean isFormatter(Object obj) {
        return isPrinter(obj) || isParser(obj);
    }

    static void appendUnknownString(Appendable appendable, int i) throws IOException {
        while (true) {
            i--;
            if (i >= 0) {
                appendable.append(65533);
            } else {
                return;
            }
        }
    }

    static class CharacterLiteral implements InternalPrinter, InternalParser {
        private final char iValue;

        CharacterLiteral(char c) {
            this.iValue = c;
        }

        public int estimatePrintedLength() {
            return 1;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }

        public int estimateParsedLength() {
            return 1;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            char upperCase;
            char upperCase2;
            if (i >= charSequence.length()) {
                return i ^ -1;
            }
            char charAt = charSequence.charAt(i);
            char c = this.iValue;
            if (charAt == c || (upperCase = Character.toUpperCase(charAt)) == (upperCase2 = Character.toUpperCase(c)) || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2)) {
                return i + 1;
            }
            return i ^ -1;
        }
    }

    static class StringLiteral implements InternalPrinter, InternalParser {
        private final String iValue;

        StringLiteral(String str) {
            this.iValue = str;
        }

        public int estimatePrintedLength() {
            return this.iValue.length();
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }

        public int estimateParsedLength() {
            return this.iValue.length();
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            if (DateTimeFormatterBuilder.csStartsWithIgnoreCase(charSequence, i, this.iValue)) {
                return this.iValue.length() + i;
            }
            return i ^ -1;
        }
    }

    static abstract class NumberFormatter implements InternalPrinter, InternalParser {
        protected final DateTimeFieldType iFieldType;
        protected final int iMaxParsedDigits;
        protected final boolean iSigned;

        NumberFormatter(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.iFieldType = dateTimeFieldType;
            this.iMaxParsedDigits = i;
            this.iSigned = z;
        }

        public int estimateParsedLength() {
            return this.iMaxParsedDigits;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c1, code lost:
            r3 = r1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r13, java.lang.CharSequence r14, int r15) {
            /*
                r12 = this;
                r10 = 48
                r9 = 45
                r8 = 43
                r2 = 1
                r3 = 0
                int r0 = r12.iMaxParsedDigits
                int r1 = r14.length()
                int r1 = r1 - r15
                int r0 = java.lang.Math.min(r0, r1)
                r4 = r3
                r1 = r3
                r5 = r0
                r0 = r3
            L_0x0017:
                if (r4 >= r5) goto L_0x00c1
                int r6 = r15 + r4
                char r6 = r14.charAt(r6)
                if (r4 != 0) goto L_0x0059
                if (r6 == r9) goto L_0x0025
                if (r6 != r8) goto L_0x0059
            L_0x0025:
                boolean r7 = r12.iSigned
                if (r7 == 0) goto L_0x0059
                if (r6 != r9) goto L_0x0047
                r1 = r2
            L_0x002c:
                if (r6 != r8) goto L_0x0049
                r0 = r2
            L_0x002f:
                int r6 = r4 + 1
                if (r6 >= r5) goto L_0x00c1
                int r6 = r15 + r4
                int r6 = r6 + 1
                char r6 = r14.charAt(r6)
                if (r6 < r10) goto L_0x00c1
                r7 = 57
                if (r6 <= r7) goto L_0x004b
                r3 = r1
            L_0x0042:
                if (r4 != 0) goto L_0x0064
                r1 = r15 ^ -1
            L_0x0046:
                return r1
            L_0x0047:
                r1 = r3
                goto L_0x002c
            L_0x0049:
                r0 = r3
                goto L_0x002f
            L_0x004b:
                int r4 = r4 + 1
                int r5 = r5 + 1
                int r6 = r14.length()
                int r6 = r6 - r15
                int r5 = java.lang.Math.min(r5, r6)
                goto L_0x0017
            L_0x0059:
                if (r6 < r10) goto L_0x00c1
                r7 = 57
                if (r6 <= r7) goto L_0x0061
                r3 = r1
                goto L_0x0042
            L_0x0061:
                int r4 = r4 + 1
                goto L_0x0017
            L_0x0064:
                r1 = 9
                if (r4 < r1) goto L_0x008f
                if (r0 == 0) goto L_0x0080
                int r0 = r15 + 1
                int r1 = r15 + r4
                java.lang.CharSequence r0 = r14.subSequence(r0, r1)
                java.lang.String r0 = r0.toString()
                int r0 = java.lang.Integer.parseInt(r0)
            L_0x007a:
                org.joda.time.DateTimeFieldType r2 = r12.iFieldType
                r13.saveField((org.joda.time.DateTimeFieldType) r2, (int) r0)
                goto L_0x0046
            L_0x0080:
                int r1 = r15 + r4
                java.lang.CharSequence r0 = r14.subSequence(r15, r1)
                java.lang.String r0 = r0.toString()
                int r0 = java.lang.Integer.parseInt(r0)
                goto L_0x007a
            L_0x008f:
                if (r3 != 0) goto L_0x0093
                if (r0 == 0) goto L_0x00bf
            L_0x0093:
                int r0 = r15 + 1
                r1 = r0
            L_0x0096:
                int r0 = r1 + 1
                char r1 = r14.charAt(r1)     // Catch:{ StringIndexOutOfBoundsException -> 0x00b7 }
                int r2 = r1 + -48
                int r1 = r15 + r4
                r11 = r0
                r0 = r2
                r2 = r11
            L_0x00a3:
                if (r2 >= r1) goto L_0x00bb
                int r4 = r0 << 3
                int r0 = r0 << 1
                int r4 = r4 + r0
                int r0 = r2 + 1
                char r2 = r14.charAt(r2)
                int r2 = r2 + r4
                int r2 = r2 + -48
                r11 = r0
                r0 = r2
                r2 = r11
                goto L_0x00a3
            L_0x00b7:
                r0 = move-exception
                r1 = r15 ^ -1
                goto L_0x0046
            L_0x00bb:
                if (r3 == 0) goto L_0x007a
                int r0 = -r0
                goto L_0x007a
            L_0x00bf:
                r1 = r15
                goto L_0x0096
            L_0x00c1:
                r3 = r1
                goto L_0x0042
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.NumberFormatter.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.CharSequence, int):int");
        }
    }

    static class UnpaddedNumber extends NumberFormatter {
        protected UnpaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z);
        }

        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.appendUnpaddedInteger(appendable, this.iFieldType.getField(chronology).get(j));
            } catch (RuntimeException e) {
                appendable.append(65533);
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.appendUnpaddedInteger(appendable, readablePartial.get(this.iFieldType));
                } catch (RuntimeException e) {
                    appendable.append(65533);
                }
            } else {
                appendable.append(65533);
            }
        }
    }

    static class PaddedNumber extends NumberFormatter {
        protected final int iMinPrintedDigits;

        protected PaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z, int i2) {
            super(dateTimeFieldType, i, z);
            this.iMinPrintedDigits = i2;
        }

        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.appendPaddedInteger(appendable, this.iFieldType.getField(chronology).get(j), this.iMinPrintedDigits);
            } catch (RuntimeException e) {
                DateTimeFormatterBuilder.appendUnknownString(appendable, this.iMinPrintedDigits);
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.appendPaddedInteger(appendable, readablePartial.get(this.iFieldType), this.iMinPrintedDigits);
                } catch (RuntimeException e) {
                    DateTimeFormatterBuilder.appendUnknownString(appendable, this.iMinPrintedDigits);
                }
            } else {
                DateTimeFormatterBuilder.appendUnknownString(appendable, this.iMinPrintedDigits);
            }
        }
    }

    static class FixedNumber extends PaddedNumber {
        protected FixedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z, i);
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            int i2;
            char charAt;
            int parseInto = super.parseInto(dateTimeParserBucket, charSequence, i);
            if (parseInto < 0 || parseInto == (i2 = this.iMaxParsedDigits + i)) {
                return parseInto;
            }
            if (this.iSigned && ((charAt = charSequence.charAt(i)) == '-' || charAt == '+')) {
                i2++;
            }
            if (parseInto > i2) {
                return (i2 + 1) ^ -1;
            }
            if (parseInto < i2) {
                return parseInto ^ -1;
            }
            return parseInto;
        }
    }

    static class TwoDigitYear implements InternalPrinter, InternalParser {
        private final boolean iLenientParse;
        private final int iPivot;
        private final DateTimeFieldType iType;

        TwoDigitYear(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.iType = dateTimeFieldType;
            this.iPivot = i;
            this.iLenientParse = z;
        }

        public int estimateParsedLength() {
            return this.iLenientParse ? 4 : 2;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            int i2;
            int i3;
            int i4;
            int i5;
            boolean z;
            int i6 = 0;
            int length = charSequence.length() - i;
            if (this.iLenientParse) {
                int i7 = 0;
                boolean z2 = false;
                boolean z3 = false;
                int i8 = length;
                while (i7 < i8) {
                    char charAt = charSequence.charAt(i + i7);
                    if (i7 != 0 || (charAt != '-' && charAt != '+')) {
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i7++;
                    } else {
                        if (charAt == '-') {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            i7++;
                            z2 = z;
                            z3 = true;
                        } else {
                            i++;
                            z3 = true;
                            i8--;
                            z2 = z;
                        }
                    }
                }
                if (i7 == 0) {
                    return i ^ -1;
                }
                if (z3 || i7 != 2) {
                    if (i7 >= 9) {
                        i4 = i + i7;
                        i5 = Integer.parseInt(charSequence.subSequence(i, i4).toString());
                    } else {
                        if (z2) {
                            i3 = i + 1;
                        } else {
                            i3 = i;
                        }
                        int i9 = i3 + 1;
                        try {
                            int charAt2 = charSequence.charAt(i3) - '0';
                            i4 = i + i7;
                            int i10 = i9;
                            i5 = charAt2;
                            for (int i11 = i10; i11 < i4; i11++) {
                                i5 = (charSequence.charAt(i11) + ((i5 << 3) + (i5 << 1))) - 48;
                            }
                            if (z2) {
                                i5 = -i5;
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            return i ^ -1;
                        }
                    }
                    dateTimeParserBucket.saveField(this.iType, i5);
                    return i4;
                }
            } else if (Math.min(2, length) < 2) {
                return i ^ -1;
            }
            char charAt3 = charSequence.charAt(i);
            if (charAt3 < '0' || charAt3 > '9') {
                return i ^ -1;
            }
            int i12 = charAt3 - '0';
            char charAt4 = charSequence.charAt(i + 1);
            if (charAt4 < '0' || charAt4 > '9') {
                return i ^ -1;
            }
            int i13 = (((i12 << 1) + (i12 << 3)) + charAt4) - 48;
            int i14 = this.iPivot;
            if (dateTimeParserBucket.getPivotYear() != null) {
                i14 = dateTimeParserBucket.getPivotYear().intValue();
            }
            int i15 = i14 - 50;
            if (i15 >= 0) {
                i2 = i15 % 100;
            } else {
                i2 = ((i15 + 1) % 100) + 99;
            }
            if (i13 < i2) {
                i6 = 100;
            }
            dateTimeParserBucket.saveField(this.iType, ((i6 + i15) - i2) + i13);
            return i + 2;
        }

        public int estimatePrintedLength() {
            return 2;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(j, chronology);
            if (twoDigitYear < 0) {
                appendable.append(65533);
                appendable.append(65533);
                return;
            }
            FormatUtils.appendPaddedInteger(appendable, twoDigitYear, 2);
        }

        private int getTwoDigitYear(long j, Chronology chronology) {
            try {
                int i = this.iType.getField(chronology).get(j);
                if (i < 0) {
                    i = -i;
                }
                return i % 100;
            } catch (RuntimeException e) {
                return -1;
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(readablePartial);
            if (twoDigitYear < 0) {
                appendable.append(65533);
                appendable.append(65533);
                return;
            }
            FormatUtils.appendPaddedInteger(appendable, twoDigitYear, 2);
        }

        private int getTwoDigitYear(ReadablePartial readablePartial) {
            if (readablePartial.isSupported(this.iType)) {
                try {
                    int i = readablePartial.get(this.iType);
                    if (i < 0) {
                        i = -i;
                    }
                    return i % 100;
                } catch (RuntimeException e) {
                }
            }
            return -1;
        }
    }

    static class TextField implements InternalPrinter, InternalParser {
        private static Map<Locale, Map<DateTimeFieldType, Object[]>> cParseCache = new ConcurrentHashMap();
        private final DateTimeFieldType iFieldType;
        private final boolean iShort;

        TextField(DateTimeFieldType dateTimeFieldType, boolean z) {
            this.iFieldType = dateTimeFieldType;
            this.iShort = z;
        }

        public int estimatePrintedLength() {
            return this.iShort ? 6 : 20;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                appendable.append(print(j, chronology, locale));
            } catch (RuntimeException e) {
                appendable.append(65533);
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            try {
                appendable.append(print(readablePartial, locale));
            } catch (RuntimeException e) {
                appendable.append(65533);
            }
        }

        private String print(long j, Chronology chronology, Locale locale) {
            DateTimeField field = this.iFieldType.getField(chronology);
            if (this.iShort) {
                return field.getAsShortText(j, locale);
            }
            return field.getAsText(j, locale);
        }

        private String print(ReadablePartial readablePartial, Locale locale) {
            if (!readablePartial.isSupported(this.iFieldType)) {
                return "�";
            }
            DateTimeField field = this.iFieldType.getField(readablePartial.getChronology());
            if (this.iShort) {
                return field.getAsShortText(readablePartial, locale);
            }
            return field.getAsText(readablePartial, locale);
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            ConcurrentHashMap concurrentHashMap;
            Map map;
            int intValue;
            Locale locale = dateTimeParserBucket.getLocale();
            Map map2 = cParseCache.get(locale);
            if (map2 == null) {
                ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
                cParseCache.put(locale, concurrentHashMap2);
                concurrentHashMap = concurrentHashMap2;
            } else {
                concurrentHashMap = map2;
            }
            Object[] objArr = (Object[]) concurrentHashMap.get(this.iFieldType);
            if (objArr == null) {
                ConcurrentHashMap concurrentHashMap3 = new ConcurrentHashMap(32);
                MutableDateTime.Property property = new MutableDateTime(0, DateTimeZone.UTC).property(this.iFieldType);
                int minimumValueOverall = property.getMinimumValueOverall();
                int maximumValueOverall = property.getMaximumValueOverall();
                if (maximumValueOverall - minimumValueOverall > 32) {
                    return i ^ -1;
                }
                intValue = property.getMaximumTextLength(locale);
                while (minimumValueOverall <= maximumValueOverall) {
                    property.set(minimumValueOverall);
                    concurrentHashMap3.put(property.getAsShortText(locale), Boolean.TRUE);
                    concurrentHashMap3.put(property.getAsShortText(locale).toLowerCase(locale), Boolean.TRUE);
                    concurrentHashMap3.put(property.getAsShortText(locale).toUpperCase(locale), Boolean.TRUE);
                    concurrentHashMap3.put(property.getAsText(locale), Boolean.TRUE);
                    concurrentHashMap3.put(property.getAsText(locale).toLowerCase(locale), Boolean.TRUE);
                    concurrentHashMap3.put(property.getAsText(locale).toUpperCase(locale), Boolean.TRUE);
                    minimumValueOverall++;
                }
                if ("en".equals(locale.getLanguage()) && this.iFieldType == DateTimeFieldType.era()) {
                    concurrentHashMap3.put("BCE", Boolean.TRUE);
                    concurrentHashMap3.put("bce", Boolean.TRUE);
                    concurrentHashMap3.put("CE", Boolean.TRUE);
                    concurrentHashMap3.put("ce", Boolean.TRUE);
                    intValue = 3;
                }
                concurrentHashMap.put(this.iFieldType, new Object[]{concurrentHashMap3, Integer.valueOf(intValue)});
                map = concurrentHashMap3;
            } else {
                map = (Map) objArr[0];
                intValue = ((Integer) objArr[1]).intValue();
            }
            for (int min = Math.min(charSequence.length(), intValue + i); min > i; min--) {
                String obj = charSequence.subSequence(i, min).toString();
                if (map.containsKey(obj)) {
                    dateTimeParserBucket.saveField(this.iFieldType, obj, locale);
                    return min;
                }
            }
            return i ^ -1;
        }
    }

    static class Fraction implements InternalPrinter, InternalParser {
        private final DateTimeFieldType iFieldType;
        protected int iMaxDigits;
        protected int iMinDigits;

        protected Fraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
            this.iFieldType = dateTimeFieldType;
            i2 = i2 > 18 ? 18 : i2;
            this.iMinDigits = i;
            this.iMaxDigits = i2;
        }

        public int estimatePrintedLength() {
            return this.iMaxDigits;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            printTo(appendable, j, chronology);
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            printTo(appendable, readablePartial.getChronology().set(readablePartial, 0), readablePartial.getChronology());
        }

        /* access modifiers changed from: protected */
        public void printTo(Appendable appendable, long j, Chronology chronology) throws IOException {
            String l;
            DateTimeField field = this.iFieldType.getField(chronology);
            int i = this.iMinDigits;
            try {
                long remainder = field.remainder(j);
                if (remainder == 0) {
                    while (true) {
                        i--;
                        if (i >= 0) {
                            appendable.append('0');
                        } else {
                            return;
                        }
                    }
                } else {
                    long[] fractionData = getFractionData(remainder, field);
                    long j2 = fractionData[0];
                    int i2 = (int) fractionData[1];
                    if ((2147483647L & j2) == j2) {
                        l = Integer.toString((int) j2);
                    } else {
                        l = Long.toString(j2);
                    }
                    int length = l.length();
                    while (length < i2) {
                        appendable.append('0');
                        i--;
                        i2--;
                    }
                    if (i < i2) {
                        while (i < i2 && length > 1 && l.charAt(length - 1) == '0') {
                            i2--;
                            length--;
                        }
                        if (length < l.length()) {
                            for (int i3 = 0; i3 < length; i3++) {
                                appendable.append(l.charAt(i3));
                            }
                            return;
                        }
                    }
                    appendable.append(l);
                }
            } catch (RuntimeException e) {
                DateTimeFormatterBuilder.appendUnknownString(appendable, i);
            }
        }

        private long[] getFractionData(long j, DateTimeField dateTimeField) {
            int i;
            long j2;
            long unitMillis = dateTimeField.getDurationField().getUnitMillis();
            int i2 = this.iMaxDigits;
            while (true) {
                switch (i) {
                    case 1:
                        j2 = 10;
                        break;
                    case 2:
                        j2 = 100;
                        break;
                    case 3:
                        j2 = 1000;
                        break;
                    case 4:
                        j2 = 10000;
                        break;
                    case 5:
                        j2 = 100000;
                        break;
                    case 6:
                        j2 = 1000000;
                        break;
                    case 7:
                        j2 = 10000000;
                        break;
                    case 8:
                        j2 = 100000000;
                        break;
                    case 9:
                        j2 = 1000000000;
                        break;
                    case 10:
                        j2 = 10000000000L;
                        break;
                    case 11:
                        j2 = 100000000000L;
                        break;
                    case 12:
                        j2 = 1000000000000L;
                        break;
                    case 13:
                        j2 = 10000000000000L;
                        break;
                    case 14:
                        j2 = 100000000000000L;
                        break;
                    case 15:
                        j2 = 1000000000000000L;
                        break;
                    case 16:
                        j2 = 10000000000000000L;
                        break;
                    case 17:
                        j2 = 100000000000000000L;
                        break;
                    case 18:
                        j2 = 1000000000000000000L;
                        break;
                    default:
                        j2 = 1;
                        break;
                }
                if ((unitMillis * j2) / j2 == unitMillis) {
                    return new long[]{(j2 * j) / unitMillis, (long) i};
                }
                i2 = i - 1;
            }
        }

        public int estimateParsedLength() {
            return this.iMaxDigits;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            DateTimeField field = this.iFieldType.getField(dateTimeParserBucket.getChronology());
            int min = Math.min(this.iMaxDigits, charSequence.length() - i);
            long j = 0;
            long unitMillis = field.getDurationField().getUnitMillis() * 10;
            int i2 = 0;
            while (i2 < min) {
                char charAt = charSequence.charAt(i + i2);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i2++;
                unitMillis /= 10;
                j += ((long) (charAt - '0')) * unitMillis;
            }
            long j2 = j / 10;
            if (i2 == 0) {
                return i ^ -1;
            }
            if (j2 > 2147483647L) {
                return i ^ -1;
            }
            dateTimeParserBucket.saveField((DateTimeField) new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), MillisDurationField.INSTANCE, field.getDurationField()), (int) j2);
            return i2 + i;
        }
    }

    static class TimeZoneOffset implements InternalPrinter, InternalParser {
        private final int iMaxFields;
        private final int iMinFields;
        private final boolean iShowSeparators;
        private final String iZeroOffsetParseText;
        private final String iZeroOffsetPrintText;

        TimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
            int i3 = 4;
            this.iZeroOffsetPrintText = str;
            this.iZeroOffsetParseText = str2;
            this.iShowSeparators = z;
            if (i <= 0 || i2 < i) {
                throw new IllegalArgumentException();
            }
            if (i > 4) {
                i2 = 4;
            } else {
                i3 = i;
            }
            this.iMinFields = i3;
            this.iMaxFields = i2;
        }

        public int estimatePrintedLength() {
            int i = (this.iMinFields + 1) << 1;
            if (this.iShowSeparators) {
                i += this.iMinFields - 1;
            }
            if (this.iZeroOffsetPrintText == null || this.iZeroOffsetPrintText.length() <= i) {
                return i;
            }
            return this.iZeroOffsetPrintText.length();
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            if (dateTimeZone != null) {
                if (i != 0 || this.iZeroOffsetPrintText == null) {
                    if (i >= 0) {
                        appendable.append('+');
                    } else {
                        appendable.append('-');
                        i = -i;
                    }
                    int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
                    FormatUtils.appendPaddedInteger(appendable, i2, 2);
                    if (this.iMaxFields != 1) {
                        int i3 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
                        if (i3 != 0 || this.iMinFields > 1) {
                            int i4 = i3 / DateTimeConstants.MILLIS_PER_MINUTE;
                            if (this.iShowSeparators) {
                                appendable.append(':');
                            }
                            FormatUtils.appendPaddedInteger(appendable, i4, 2);
                            if (this.iMaxFields != 2) {
                                int i5 = i3 - (i4 * DateTimeConstants.MILLIS_PER_MINUTE);
                                if (i5 != 0 || this.iMinFields > 2) {
                                    int i6 = i5 / 1000;
                                    if (this.iShowSeparators) {
                                        appendable.append(':');
                                    }
                                    FormatUtils.appendPaddedInteger(appendable, i6, 2);
                                    if (this.iMaxFields != 3) {
                                        int i7 = i5 - (i6 * 1000);
                                        if (i7 != 0 || this.iMinFields > 3) {
                                            if (this.iShowSeparators) {
                                                appendable.append('.');
                                            }
                                            FormatUtils.appendPaddedInteger(appendable, i7, 3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                appendable.append(this.iZeroOffsetPrintText);
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            boolean z;
            int i2;
            int i3;
            int i4;
            char charAt;
            boolean z2 = false;
            int length = charSequence.length() - i;
            if (this.iZeroOffsetParseText != null) {
                if (this.iZeroOffsetParseText.length() == 0) {
                    if (length <= 0 || !((charAt = charSequence.charAt(i)) == '-' || charAt == '+')) {
                        dateTimeParserBucket.setOffset((Integer) 0);
                        return i;
                    }
                } else if (DateTimeFormatterBuilder.csStartsWithIgnoreCase(charSequence, i, this.iZeroOffsetParseText)) {
                    dateTimeParserBucket.setOffset((Integer) 0);
                    return i + this.iZeroOffsetParseText.length();
                }
            }
            if (length <= 1) {
                return i ^ -1;
            }
            char charAt2 = charSequence.charAt(i);
            if (charAt2 == '-') {
                z = true;
            } else if (charAt2 != '+') {
                return i ^ -1;
            } else {
                z = false;
            }
            int i5 = length - 1;
            int i6 = i + 1;
            if (digitCount(charSequence, i6, 2) < 2) {
                return i6 ^ -1;
            }
            int parseTwoDigits = FormatUtils.parseTwoDigits(charSequence, i6);
            if (parseTwoDigits > 23) {
                return i6 ^ -1;
            }
            int i7 = parseTwoDigits * DateTimeConstants.MILLIS_PER_HOUR;
            int i8 = i5 - 2;
            int i9 = i6 + 2;
            if (i8 <= 0) {
                i2 = i7;
                i3 = i9;
            } else {
                char charAt3 = charSequence.charAt(i9);
                if (charAt3 == ':') {
                    i9++;
                    i8--;
                    z2 = true;
                } else if (charAt3 < '0' || charAt3 > '9') {
                    i2 = i7;
                    i3 = i9;
                }
                int digitCount = digitCount(charSequence, i9, 2);
                if (digitCount == 0 && !z2) {
                    i2 = i7;
                    i3 = i9;
                } else if (digitCount < 2) {
                    return i9 ^ -1;
                } else {
                    int parseTwoDigits2 = FormatUtils.parseTwoDigits(charSequence, i9);
                    if (parseTwoDigits2 > 59) {
                        return i9 ^ -1;
                    }
                    int i10 = i7 + (parseTwoDigits2 * DateTimeConstants.MILLIS_PER_MINUTE);
                    int i11 = i8 - 2;
                    int i12 = i9 + 2;
                    if (i11 <= 0) {
                        i2 = i10;
                        i3 = i12;
                    } else {
                        if (z2) {
                            if (charSequence.charAt(i12) != ':') {
                                i2 = i10;
                                i3 = i12;
                            } else {
                                i11--;
                                i12++;
                            }
                        }
                        int digitCount2 = digitCount(charSequence, i12, 2);
                        if (digitCount2 == 0 && !z2) {
                            i2 = i10;
                            i3 = i12;
                        } else if (digitCount2 < 2) {
                            return i12 ^ -1;
                        } else {
                            int parseTwoDigits3 = FormatUtils.parseTwoDigits(charSequence, i12);
                            if (parseTwoDigits3 > 59) {
                                return i12 ^ -1;
                            }
                            int i13 = i10 + (parseTwoDigits3 * 1000);
                            int i14 = i11 - 2;
                            int i15 = i12 + 2;
                            if (i14 <= 0) {
                                i2 = i13;
                                i3 = i15;
                            } else {
                                if (z2) {
                                    if (charSequence.charAt(i15) == '.' || charSequence.charAt(i15) == ',') {
                                        int i16 = i14 - 1;
                                        i15++;
                                    } else {
                                        i2 = i13;
                                        i3 = i15;
                                    }
                                }
                                int digitCount3 = digitCount(charSequence, i15, 3);
                                if (digitCount3 == 0 && !z2) {
                                    i2 = i13;
                                    i3 = i15;
                                } else if (digitCount3 < 1) {
                                    return i15 ^ -1;
                                } else {
                                    int i17 = i15 + 1;
                                    int charAt4 = ((charSequence.charAt(i15) - '0') * 100) + i13;
                                    if (digitCount3 > 1) {
                                        int i18 = i17 + 1;
                                        i2 = ((charSequence.charAt(i17) - '0') * 10) + charAt4;
                                        if (digitCount3 > 2) {
                                            i2 += charSequence.charAt(i18) - '0';
                                            i3 = i18 + 1;
                                        } else {
                                            i3 = i18;
                                        }
                                    } else {
                                        i2 = charAt4;
                                        i3 = i17;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (z) {
                i4 = -i2;
            } else {
                i4 = i2;
            }
            dateTimeParserBucket.setOffset(Integer.valueOf(i4));
            return i3;
        }

        private int digitCount(CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            for (int min = Math.min(charSequence.length() - i, i2); min > 0; min--) {
                char charAt = charSequence.charAt(i + i3);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i3++;
            }
            return i3;
        }
    }

    static class TimeZoneName implements InternalPrinter, InternalParser {
        static final int LONG_NAME = 0;
        static final int SHORT_NAME = 1;
        private final Map<String, DateTimeZone> iParseLookup;
        private final int iType;

        TimeZoneName(int i, Map<String, DateTimeZone> map) {
            this.iType = i;
            this.iParseLookup = map;
        }

        public int estimatePrintedLength() {
            return this.iType == 1 ? 4 : 20;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(print(j - ((long) i), dateTimeZone, locale));
        }

        private String print(long j, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone == null) {
                return "";
            }
            switch (this.iType) {
                case 0:
                    return dateTimeZone.getName(j, locale);
                case 1:
                    return dateTimeZone.getShortName(j, locale);
                default:
                    return "";
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return this.iType == 1 ? 4 : 20;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            Map<String, DateTimeZone> map = this.iParseLookup;
            Map<String, DateTimeZone> defaultTimeZoneNames = map != null ? map : DateTimeUtils.getDefaultTimeZoneNames();
            String str = null;
            for (String next : defaultTimeZoneNames.keySet()) {
                if (!DateTimeFormatterBuilder.csStartsWith(charSequence, i, next) || (str != null && next.length() <= str.length())) {
                    next = str;
                }
                str = next;
            }
            if (str == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone(defaultTimeZoneNames.get(str));
            return str.length() + i;
        }
    }

    enum TimeZoneId implements InternalPrinter, InternalParser {
        INSTANCE;
        
        private static final List<String> ALL_IDS = null;
        static final int MAX_LENGTH = 0;

        static {
            int i;
            ALL_IDS = new ArrayList(DateTimeZone.getAvailableIDs());
            Collections.sort(ALL_IDS);
            Iterator<String> it = ALL_IDS.iterator();
            while (true) {
                int i2 = i;
                if (it.hasNext()) {
                    i = Math.max(i2, it.next().length());
                } else {
                    MAX_LENGTH = i2;
                    return;
                }
            }
        }

        public int estimatePrintedLength() {
            return MAX_LENGTH;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(dateTimeZone != null ? dateTimeZone.getID() : "");
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return MAX_LENGTH;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            String str = null;
            int prefixedStartPosition = prefixedStartPosition(charSequence, i);
            while (prefixedStartPosition < ALL_IDS.size()) {
                String str2 = ALL_IDS.get(prefixedStartPosition);
                if (!DateTimeFormatterBuilder.csStartsWith(charSequence, i, str2)) {
                    break;
                }
                if (str != null && str2.length() <= str.length()) {
                    str2 = str;
                }
                prefixedStartPosition++;
                str = str2;
            }
            if (str == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone(DateTimeZone.forID(str));
            return str.length() + i;
        }

        private static int prefixedStartPosition(CharSequence charSequence, int i) {
            int i2;
            int i3;
            int i4 = 0;
            int size = ALL_IDS.size() - 1;
            while (i4 <= size) {
                int i5 = (i4 + size) >>> 1;
                int csCompare = DateTimeFormatterBuilder.csCompare(charSequence, i, ALL_IDS.get(i5));
                if (csCompare > 0) {
                    i3 = i5 - 1;
                    i2 = i4;
                } else if (csCompare >= 0) {
                    return i5;
                } else {
                    int i6 = size;
                    i2 = i5 + 1;
                    i3 = i6;
                }
                i4 = i2;
                size = i3;
            }
            return i4;
        }
    }

    static class Composite implements InternalPrinter, InternalParser {
        private final int iParsedLengthEstimate;
        private final InternalParser[] iParsers;
        private final int iPrintedLengthEstimate;
        private final InternalPrinter[] iPrinters;

        Composite(List<Object> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.contains((Object) null) || arrayList.isEmpty()) {
                this.iPrinters = null;
                this.iPrintedLengthEstimate = 0;
            } else {
                int size = arrayList.size();
                this.iPrinters = new InternalPrinter[size];
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    InternalPrinter internalPrinter = (InternalPrinter) arrayList.get(i2);
                    i += internalPrinter.estimatePrintedLength();
                    this.iPrinters[i2] = internalPrinter;
                }
                this.iPrintedLengthEstimate = i;
            }
            if (arrayList2.contains((Object) null) || arrayList2.isEmpty()) {
                this.iParsers = null;
                this.iParsedLengthEstimate = 0;
                return;
            }
            int size2 = arrayList2.size();
            this.iParsers = new InternalParser[size2];
            int i3 = 0;
            for (int i4 = 0; i4 < size2; i4++) {
                InternalParser internalParser = (InternalParser) arrayList2.get(i4);
                i3 += internalParser.estimateParsedLength();
                this.iParsers[i4] = internalParser;
            }
            this.iParsedLengthEstimate = i3;
        }

        public int estimatePrintedLength() {
            return this.iPrintedLengthEstimate;
        }

        public void printTo(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            Locale locale2;
            InternalPrinter[] internalPrinterArr = this.iPrinters;
            if (internalPrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale2 = Locale.getDefault();
            } else {
                locale2 = locale;
            }
            for (InternalPrinter printTo : internalPrinterArr) {
                printTo.printTo(appendable, j, chronology, i, dateTimeZone, locale2);
            }
        }

        public void printTo(Appendable appendable, ReadablePartial readablePartial, Locale locale) throws IOException {
            InternalPrinter[] internalPrinterArr = this.iPrinters;
            if (internalPrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            for (InternalPrinter printTo : internalPrinterArr) {
                printTo.printTo(appendable, readablePartial, locale);
            }
        }

        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            InternalParser[] internalParserArr = this.iParsers;
            if (internalParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = internalParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2++) {
                i = internalParserArr[i2].parseInto(dateTimeParserBucket, charSequence, i);
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        public boolean isPrinter() {
            return this.iPrinters != null;
        }

        /* access modifiers changed from: package-private */
        public boolean isParser() {
            return this.iParsers != null;
        }

        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof Composite) {
                    addArrayToList(list2, ((Composite) obj).iPrinters);
                } else {
                    list2.add(obj);
                }
                Object obj2 = list.get(i + 1);
                if (obj2 instanceof Composite) {
                    addArrayToList(list3, ((Composite) obj2).iParsers);
                } else {
                    list3.add(obj2);
                }
            }
        }

        private void addArrayToList(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (Object add : objArr) {
                    list.add(add);
                }
            }
        }
    }

    static class MatchingParser implements InternalParser {
        private final int iParsedLengthEstimate;
        private final InternalParser[] iParsers;

        MatchingParser(InternalParser[] internalParserArr) {
            int i;
            this.iParsers = internalParserArr;
            int i2 = 0;
            int length = internalParserArr.length;
            while (true) {
                int i3 = length - 1;
                if (i3 >= 0) {
                    InternalParser internalParser = internalParserArr[i3];
                    if (internalParser == null || (i = internalParser.estimateParsedLength()) <= i2) {
                        i = i2;
                    }
                    i2 = i;
                    length = i3;
                } else {
                    this.iParsedLengthEstimate = i2;
                    return;
                }
            }
        }

        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
            r11.restoreState(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
            return r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            return r0 ^ -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            if (r4 > r13) goto L_0x001c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0018, code lost:
            if (r4 != r13) goto L_0x0055;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
            if (r1 == false) goto L_0x0055;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
            if (r2 == null) goto L_0x0021;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r11, java.lang.CharSequence r12, int r13) {
            /*
                r10 = this;
                r5 = 0
                org.joda.time.format.InternalParser[] r7 = r10.iParsers
                int r8 = r7.length
                java.lang.Object r9 = r11.saveState()
                r2 = 0
                r6 = r5
                r0 = r13
                r4 = r13
            L_0x000c:
                if (r6 >= r8) goto L_0x005b
                r1 = r7[r6]
                if (r1 != 0) goto L_0x0023
                if (r4 > r13) goto L_0x0015
            L_0x0014:
                return r13
            L_0x0015:
                r1 = 1
            L_0x0016:
                if (r4 > r13) goto L_0x001c
                if (r4 != r13) goto L_0x0055
                if (r1 == 0) goto L_0x0055
            L_0x001c:
                if (r2 == 0) goto L_0x0021
                r11.restoreState(r2)
            L_0x0021:
                r13 = r4
                goto L_0x0014
            L_0x0023:
                int r3 = r1.parseInto(r11, r12, r13)
                if (r3 < r13) goto L_0x004b
                if (r3 <= r4) goto L_0x0058
                int r1 = r12.length()
                if (r3 >= r1) goto L_0x003b
                int r1 = r6 + 1
                if (r1 >= r8) goto L_0x003b
                int r1 = r6 + 1
                r1 = r7[r1]
                if (r1 != 0) goto L_0x003d
            L_0x003b:
                r13 = r3
                goto L_0x0014
            L_0x003d:
                java.lang.Object r1 = r11.saveState()
                r2 = r3
            L_0x0042:
                r11.restoreState(r9)
                int r3 = r6 + 1
                r6 = r3
                r4 = r2
                r2 = r1
                goto L_0x000c
            L_0x004b:
                if (r3 >= 0) goto L_0x0058
                r1 = r3 ^ -1
                if (r1 <= r0) goto L_0x0058
                r0 = r1
                r1 = r2
                r2 = r4
                goto L_0x0042
            L_0x0055:
                r13 = r0 ^ -1
                goto L_0x0014
            L_0x0058:
                r1 = r2
                r2 = r4
                goto L_0x0042
            L_0x005b:
                r1 = r5
                goto L_0x0016
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.MatchingParser.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.CharSequence, int):int");
        }
    }

    static int csCompare(CharSequence charSequence, int i, String str) {
        int length = charSequence.length() - i;
        int length2 = str.length();
        int min = Math.min(length, length2);
        for (int i2 = 0; i2 < min; i2++) {
            int charAt = str.charAt(i2) - charSequence.charAt(i + i2);
            if (charAt != 0) {
                return charAt;
            }
        }
        return length2 - length;
    }

    static boolean csStartsWith(CharSequence charSequence, int i, String str) {
        int length = str.length();
        if (charSequence.length() - i < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (charSequence.charAt(i + i2) != str.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    static boolean csStartsWithIgnoreCase(CharSequence charSequence, int i, String str) {
        char upperCase;
        char upperCase2;
        int length = str.length();
        if (charSequence.length() - i < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = charSequence.charAt(i + i2);
            char charAt2 = str.charAt(i2);
            if (charAt != charAt2 && (upperCase = Character.toUpperCase(charAt)) != (upperCase2 = Character.toUpperCase(charAt2)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                return false;
            }
        }
        return true;
    }
}
