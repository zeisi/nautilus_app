package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import org.joda.time.DurationFieldType;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

public class PeriodFormatterBuilder {
    private static final int DAYS = 3;
    private static final int HOURS = 4;
    private static final int MAX_FIELD = 9;
    private static final int MILLIS = 7;
    private static final int MINUTES = 5;
    private static final int MONTHS = 1;
    /* access modifiers changed from: private */
    public static final ConcurrentMap<String, Pattern> PATTERNS = new ConcurrentHashMap();
    private static final int PRINT_ZERO_ALWAYS = 4;
    private static final int PRINT_ZERO_IF_SUPPORTED = 3;
    private static final int PRINT_ZERO_NEVER = 5;
    private static final int PRINT_ZERO_RARELY_FIRST = 1;
    private static final int PRINT_ZERO_RARELY_LAST = 2;
    private static final int SECONDS = 6;
    private static final int SECONDS_MILLIS = 8;
    private static final int SECONDS_OPTIONAL_MILLIS = 9;
    private static final int WEEKS = 2;
    private static final int YEARS = 0;
    private List<Object> iElementPairs;
    private FieldFormatter[] iFieldFormatters;
    private int iMaxParsedDigits;
    private int iMinPrintedDigits;
    private boolean iNotParser;
    private boolean iNotPrinter;
    private PeriodFieldAffix iPrefix;
    private int iPrintZeroSetting;
    private boolean iRejectSignedValues;

    interface PeriodFieldAffix {
        int calculatePrintedLength(int i);

        void finish(Set<PeriodFieldAffix> set);

        String[] getAffixes();

        int parse(String str, int i);

        void printTo(Writer writer, int i) throws IOException;

        void printTo(StringBuffer stringBuffer, int i);

        int scan(String str, int i);
    }

    public PeriodFormatterBuilder() {
        clear();
    }

    public PeriodFormatter toFormatter() {
        PeriodFormatter formatter = toFormatter(this.iElementPairs, this.iNotPrinter, this.iNotParser);
        for (FieldFormatter fieldFormatter : this.iFieldFormatters) {
            if (fieldFormatter != null) {
                fieldFormatter.finish(this.iFieldFormatters);
            }
        }
        this.iFieldFormatters = (FieldFormatter[]) this.iFieldFormatters.clone();
        return formatter;
    }

    public PeriodPrinter toPrinter() {
        if (this.iNotPrinter) {
            return null;
        }
        return toFormatter().getPrinter();
    }

    public PeriodParser toParser() {
        if (this.iNotParser) {
            return null;
        }
        return toFormatter().getParser();
    }

    public void clear() {
        this.iMinPrintedDigits = 1;
        this.iPrintZeroSetting = 2;
        this.iMaxParsedDigits = 10;
        this.iRejectSignedValues = false;
        this.iPrefix = null;
        if (this.iElementPairs == null) {
            this.iElementPairs = new ArrayList();
        } else {
            this.iElementPairs.clear();
        }
        this.iNotPrinter = false;
        this.iNotParser = false;
        this.iFieldFormatters = new FieldFormatter[10];
    }

    public PeriodFormatterBuilder append(PeriodFormatter periodFormatter) {
        if (periodFormatter == null) {
            throw new IllegalArgumentException("No formatter supplied");
        }
        clearPrefix();
        append0(periodFormatter.getPrinter(), periodFormatter.getParser());
        return this;
    }

    public PeriodFormatterBuilder append(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        if (periodPrinter == null && periodParser == null) {
            throw new IllegalArgumentException("No printer or parser supplied");
        }
        clearPrefix();
        append0(periodPrinter, periodParser);
        return this;
    }

    public PeriodFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        clearPrefix();
        Literal literal = new Literal(str);
        append0(literal, literal);
        return this;
    }

    public PeriodFormatterBuilder minimumPrintedDigits(int i) {
        this.iMinPrintedDigits = i;
        return this;
    }

    public PeriodFormatterBuilder maximumParsedDigits(int i) {
        this.iMaxParsedDigits = i;
        return this;
    }

    public PeriodFormatterBuilder rejectSignedValues(boolean z) {
        this.iRejectSignedValues = z;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyLast() {
        this.iPrintZeroSetting = 2;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyFirst() {
        this.iPrintZeroSetting = 1;
        return this;
    }

    public PeriodFormatterBuilder printZeroIfSupported() {
        this.iPrintZeroSetting = 3;
        return this;
    }

    public PeriodFormatterBuilder printZeroAlways() {
        this.iPrintZeroSetting = 4;
        return this;
    }

    public PeriodFormatterBuilder printZeroNever() {
        this.iPrintZeroSetting = 5;
        return this;
    }

    public PeriodFormatterBuilder appendPrefix(String str) {
        if (str != null) {
            return appendPrefix((PeriodFieldAffix) new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendPrefix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendPrefix((PeriodFieldAffix) new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendPrefix(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length >= 1 && strArr.length == strArr2.length) {
            return appendPrefix((PeriodFieldAffix) new RegExAffix(strArr, strArr2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendPrefix(PeriodFieldAffix periodFieldAffix) {
        if (periodFieldAffix == null) {
            throw new IllegalArgumentException();
        }
        if (this.iPrefix != null) {
            periodFieldAffix = new CompositeAffix(this.iPrefix, periodFieldAffix);
        }
        this.iPrefix = periodFieldAffix;
        return this;
    }

    public PeriodFormatterBuilder appendYears() {
        appendField(0);
        return this;
    }

    public PeriodFormatterBuilder appendMonths() {
        appendField(1);
        return this;
    }

    public PeriodFormatterBuilder appendWeeks() {
        appendField(2);
        return this;
    }

    public PeriodFormatterBuilder appendDays() {
        appendField(3);
        return this;
    }

    public PeriodFormatterBuilder appendHours() {
        appendField(4);
        return this;
    }

    public PeriodFormatterBuilder appendMinutes() {
        appendField(5);
        return this;
    }

    public PeriodFormatterBuilder appendSeconds() {
        appendField(6);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithMillis() {
        appendField(8);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithOptionalMillis() {
        appendField(9);
        return this;
    }

    public PeriodFormatterBuilder appendMillis() {
        appendField(7);
        return this;
    }

    public PeriodFormatterBuilder appendMillis3Digit() {
        appendField(7, 3);
        return this;
    }

    private void appendField(int i) {
        appendField(i, this.iMinPrintedDigits);
    }

    private void appendField(int i, int i2) {
        FieldFormatter fieldFormatter = new FieldFormatter(i2, this.iPrintZeroSetting, this.iMaxParsedDigits, this.iRejectSignedValues, i, this.iFieldFormatters, this.iPrefix, (PeriodFieldAffix) null);
        append0(fieldFormatter, fieldFormatter);
        this.iFieldFormatters[i] = fieldFormatter;
        this.iPrefix = null;
    }

    public PeriodFormatterBuilder appendSuffix(String str) {
        if (str != null) {
            return appendSuffix((PeriodFieldAffix) new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSuffix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendSuffix((PeriodFieldAffix) new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSuffix(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length >= 1 && strArr.length == strArr2.length) {
            return appendSuffix((PeriodFieldAffix) new RegExAffix(strArr, strArr2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendSuffix(PeriodFieldAffix periodFieldAffix) {
        Object obj;
        Object obj2 = null;
        if (this.iElementPairs.size() > 0) {
            obj2 = this.iElementPairs.get(this.iElementPairs.size() - 2);
            obj = this.iElementPairs.get(this.iElementPairs.size() - 1);
        } else {
            obj = null;
        }
        if (obj2 == null || obj == null || obj2 != obj || !(obj2 instanceof FieldFormatter)) {
            throw new IllegalStateException("No field to apply suffix to");
        }
        clearPrefix();
        FieldFormatter fieldFormatter = new FieldFormatter((FieldFormatter) obj2, periodFieldAffix);
        this.iElementPairs.set(this.iElementPairs.size() - 2, fieldFormatter);
        this.iElementPairs.set(this.iElementPairs.size() - 1, fieldFormatter);
        this.iFieldFormatters[fieldFormatter.getFieldType()] = fieldFormatter;
        return this;
    }

    public PeriodFormatterBuilder appendSeparator(String str) {
        return appendSeparator(str, str, (String[]) null, true, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsAfter(String str) {
        return appendSeparator(str, str, (String[]) null, false, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsBefore(String str) {
        return appendSeparator(str, str, (String[]) null, true, false);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2) {
        return appendSeparator(str, str2, (String[]) null, true, true);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr) {
        return appendSeparator(str, str2, strArr, true, true);
    }

    private PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr, boolean z, boolean z2) {
        List<Object> list;
        if (str == null || str2 == null) {
            throw new IllegalArgumentException();
        }
        clearPrefix();
        List<Object> list2 = this.iElementPairs;
        if (list2.size() != 0) {
            Separator separator = null;
            int size = list2.size();
            while (true) {
                int i = size - 1;
                if (i < 0) {
                    list = list2;
                    break;
                } else if (list2.get(i) instanceof Separator) {
                    separator = (Separator) list2.get(i);
                    list = list2.subList(i + 1, list2.size());
                    break;
                } else {
                    size = i - 1;
                }
            }
            if (separator == null || list.size() != 0) {
                Object[] createComposite = createComposite(list);
                list.clear();
                Separator separator2 = new Separator(str, str2, strArr, (PeriodPrinter) createComposite[0], (PeriodParser) createComposite[1], z, z2);
                list.add(separator2);
                list.add(separator2);
            } else {
                throw new IllegalStateException("Cannot have two adjacent separators");
            }
        } else if (z2 && !z) {
            Separator separator3 = new Separator(str, str2, strArr, Literal.EMPTY, Literal.EMPTY, z, z2);
            append0(separator3, separator3);
        }
        return this;
    }

    private void clearPrefix() throws IllegalStateException {
        if (this.iPrefix != null) {
            throw new IllegalStateException("Prefix not followed by field");
        }
        this.iPrefix = null;
    }

    private PeriodFormatterBuilder append0(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        boolean z;
        boolean z2 = true;
        this.iElementPairs.add(periodPrinter);
        this.iElementPairs.add(periodParser);
        boolean z3 = this.iNotPrinter;
        if (periodPrinter == null) {
            z = true;
        } else {
            z = false;
        }
        this.iNotPrinter = z | z3;
        boolean z4 = this.iNotParser;
        if (periodParser != null) {
            z2 = false;
        }
        this.iNotParser = z4 | z2;
        return this;
    }

    private static PeriodFormatter toFormatter(List<Object> list, boolean z, boolean z2) {
        if (!z || !z2) {
            int size = list.size();
            if (size >= 2 && (list.get(0) instanceof Separator)) {
                Separator separator = (Separator) list.get(0);
                if (separator.iAfterParser == null && separator.iAfterPrinter == null) {
                    PeriodFormatter formatter = toFormatter(list.subList(2, size), z, z2);
                    Separator finish = separator.finish(formatter.getPrinter(), formatter.getParser());
                    return new PeriodFormatter(finish, finish);
                }
            }
            Object[] createComposite = createComposite(list);
            if (z) {
                return new PeriodFormatter((PeriodPrinter) null, (PeriodParser) createComposite[1]);
            }
            if (z2) {
                return new PeriodFormatter((PeriodPrinter) createComposite[0], (PeriodParser) null);
            }
            return new PeriodFormatter((PeriodPrinter) createComposite[0], (PeriodParser) createComposite[1]);
        }
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }

    private static Object[] createComposite(List<Object> list) {
        switch (list.size()) {
            case 0:
                return new Object[]{Literal.EMPTY, Literal.EMPTY};
            case 1:
                return new Object[]{list.get(0), list.get(1)};
            default:
                Composite composite = new Composite(list);
                return new Object[]{composite, composite};
        }
    }

    static abstract class IgnorableAffix implements PeriodFieldAffix {
        private volatile String[] iOtherAffixes;

        IgnorableAffix() {
        }

        public void finish(Set<PeriodFieldAffix> set) {
            int i;
            if (this.iOtherAffixes == null) {
                int i2 = Integer.MAX_VALUE;
                String str = null;
                String[] affixes = getAffixes();
                int length = affixes.length;
                int i3 = 0;
                while (i3 < length) {
                    String str2 = affixes[i3];
                    if (str2.length() < i2) {
                        i = str2.length();
                    } else {
                        str2 = str;
                        i = i2;
                    }
                    i3++;
                    i2 = i;
                    str = str2;
                }
                HashSet hashSet = new HashSet();
                for (PeriodFieldAffix next : set) {
                    if (next != null) {
                        for (String str3 : next.getAffixes()) {
                            if (str3.length() > i2 || (str3.equalsIgnoreCase(str) && !str3.equals(str))) {
                                hashSet.add(str3);
                            }
                        }
                    }
                }
                this.iOtherAffixes = (String[]) hashSet.toArray(new String[hashSet.size()]);
            }
        }

        /* access modifiers changed from: protected */
        public boolean matchesOtherAffix(int i, String str, int i2) {
            if (this.iOtherAffixes != null) {
                for (String str2 : this.iOtherAffixes) {
                    int length = str2.length();
                    if ((i < length && str.regionMatches(true, i2, str2, 0, length)) || (i == length && str.regionMatches(false, i2, str2, 0, length))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class SimpleAffix extends IgnorableAffix {
        private final String iText;

        SimpleAffix(String str) {
            this.iText = str;
        }

        public int calculatePrintedLength(int i) {
            return this.iText.length();
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            stringBuffer.append(this.iText);
        }

        public void printTo(Writer writer, int i) throws IOException {
            writer.write(this.iText);
        }

        public int parse(String str, int i) {
            String str2 = this.iText;
            int length = str2.length();
            if (!str.regionMatches(true, i, str2, 0, length) || matchesOtherAffix(length, str, i)) {
                return i ^ -1;
            }
            return i + length;
        }

        public int scan(String str, int i) {
            String str2 = this.iText;
            int length = str2.length();
            int length2 = str.length();
            int i2 = i;
            while (i2 < length2) {
                if (str.regionMatches(true, i2, str2, 0, length) && !matchesOtherAffix(length, str, i2)) {
                    return i2;
                }
                switch (str.charAt(i2)) {
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        i2++;
                }
                return i ^ -1;
            }
            return i ^ -1;
        }

        public String[] getAffixes() {
            return new String[]{this.iText};
        }
    }

    static class PluralAffix extends IgnorableAffix {
        private final String iPluralText;
        private final String iSingularText;

        PluralAffix(String str, String str2) {
            this.iSingularText = str;
            this.iPluralText = str2;
        }

        public int calculatePrintedLength(int i) {
            return (i == 1 ? this.iSingularText : this.iPluralText).length();
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            stringBuffer.append(i == 1 ? this.iSingularText : this.iPluralText);
        }

        public void printTo(Writer writer, int i) throws IOException {
            writer.write(i == 1 ? this.iSingularText : this.iPluralText);
        }

        public int parse(String str, int i) {
            String str2;
            String str3;
            String str4 = this.iPluralText;
            String str5 = this.iSingularText;
            if (str4.length() < str5.length()) {
                str2 = str4;
                str3 = str5;
            } else {
                str2 = str5;
                str3 = str4;
            }
            if (str.regionMatches(true, i, str3, 0, str3.length()) && !matchesOtherAffix(str3.length(), str, i)) {
                return str3.length() + i;
            }
            if (!str.regionMatches(true, i, str2, 0, str2.length()) || matchesOtherAffix(str2.length(), str, i)) {
                return i ^ -1;
            }
            return str2.length() + i;
        }

        public int scan(String str, int i) {
            String str2;
            String str3 = this.iPluralText;
            String str4 = this.iSingularText;
            if (str3.length() < str4.length()) {
                str2 = str4;
            } else {
                str2 = str3;
                str3 = str4;
            }
            int length = str2.length();
            int length2 = str3.length();
            int length3 = str.length();
            for (int i2 = i; i2 < length3; i2++) {
                if (str.regionMatches(true, i2, str2, 0, length) && !matchesOtherAffix(str2.length(), str, i2)) {
                    return i2;
                }
                if (str.regionMatches(true, i2, str3, 0, length2) && !matchesOtherAffix(str3.length(), str, i2)) {
                    return i2;
                }
            }
            return i ^ -1;
        }

        public String[] getAffixes() {
            return new String[]{this.iSingularText, this.iPluralText};
        }
    }

    static class RegExAffix extends IgnorableAffix {
        private static final Comparator<String> LENGTH_DESC_COMPARATOR = new Comparator<String>() {
            public int compare(String str, String str2) {
                return str2.length() - str.length();
            }
        };
        private final Pattern[] iPatterns;
        private final String[] iSuffixes;
        private final String[] iSuffixesSortedDescByLength;

        RegExAffix(String[] strArr, String[] strArr2) {
            this.iSuffixes = (String[]) strArr2.clone();
            this.iPatterns = new Pattern[strArr.length];
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < strArr.length) {
                    Pattern pattern = (Pattern) PeriodFormatterBuilder.PATTERNS.get(strArr[i2]);
                    if (pattern == null) {
                        pattern = Pattern.compile(strArr[i2]);
                        PeriodFormatterBuilder.PATTERNS.putIfAbsent(strArr[i2], pattern);
                    }
                    this.iPatterns[i2] = pattern;
                    i = i2 + 1;
                } else {
                    this.iSuffixesSortedDescByLength = (String[]) this.iSuffixes.clone();
                    Arrays.sort(this.iSuffixesSortedDescByLength, LENGTH_DESC_COMPARATOR);
                    return;
                }
            }
        }

        private int selectSuffixIndex(int i) {
            String valueOf = String.valueOf(i);
            for (int i2 = 0; i2 < this.iPatterns.length; i2++) {
                if (this.iPatterns[i2].matcher(valueOf).matches()) {
                    return i2;
                }
            }
            return this.iPatterns.length - 1;
        }

        public int calculatePrintedLength(int i) {
            return this.iSuffixes[selectSuffixIndex(i)].length();
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            stringBuffer.append(this.iSuffixes[selectSuffixIndex(i)]);
        }

        public void printTo(Writer writer, int i) throws IOException {
            writer.write(this.iSuffixes[selectSuffixIndex(i)]);
        }

        public int parse(String str, int i) {
            for (String str2 : this.iSuffixesSortedDescByLength) {
                if (str.regionMatches(true, i, str2, 0, str2.length()) && !matchesOtherAffix(str2.length(), str, i)) {
                    return str2.length() + i;
                }
            }
            return i ^ -1;
        }

        public int scan(String str, int i) {
            int length = str.length();
            for (int i2 = i; i2 < length; i2++) {
                for (String str2 : this.iSuffixesSortedDescByLength) {
                    if (str.regionMatches(true, i2, str2, 0, str2.length()) && !matchesOtherAffix(str2.length(), str, i2)) {
                        return i2;
                    }
                }
            }
            return i ^ -1;
        }

        public String[] getAffixes() {
            return (String[]) this.iSuffixes.clone();
        }
    }

    static class CompositeAffix extends IgnorableAffix {
        private final PeriodFieldAffix iLeft;
        private final String[] iLeftRightCombinations;
        private final PeriodFieldAffix iRight;

        CompositeAffix(PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iLeft = periodFieldAffix;
            this.iRight = periodFieldAffix2;
            HashSet hashSet = new HashSet();
            for (String str : this.iLeft.getAffixes()) {
                String[] affixes = this.iRight.getAffixes();
                int length = affixes.length;
                for (int i = 0; i < length; i++) {
                    hashSet.add(str + affixes[i]);
                }
            }
            this.iLeftRightCombinations = (String[]) hashSet.toArray(new String[hashSet.size()]);
        }

        public int calculatePrintedLength(int i) {
            return this.iLeft.calculatePrintedLength(i) + this.iRight.calculatePrintedLength(i);
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            this.iLeft.printTo(stringBuffer, i);
            this.iRight.printTo(stringBuffer, i);
        }

        public void printTo(Writer writer, int i) throws IOException {
            this.iLeft.printTo(writer, i);
            this.iRight.printTo(writer, i);
        }

        public int parse(String str, int i) {
            int parse = this.iLeft.parse(str, i);
            if (parse < 0) {
                return parse;
            }
            int parse2 = this.iRight.parse(str, parse);
            if (parse2 < 0 || !matchesOtherAffix(parse(str, parse2) - parse2, str, i)) {
                return parse2;
            }
            return i ^ -1;
        }

        public int scan(String str, int i) {
            int scan;
            int scan2 = this.iLeft.scan(str, i);
            if (scan2 < 0 || ((scan = this.iRight.scan(str, this.iLeft.parse(str, scan2))) >= 0 && matchesOtherAffix(this.iRight.parse(str, scan) - scan2, str, i))) {
                return i ^ -1;
            }
            return scan2 > 0 ? scan2 : scan;
        }

        public String[] getAffixes() {
            return (String[]) this.iLeftRightCombinations.clone();
        }
    }

    static class FieldFormatter implements PeriodPrinter, PeriodParser {
        private final FieldFormatter[] iFieldFormatters;
        private final int iFieldType;
        private final int iMaxParsedDigits;
        private final int iMinPrintedDigits;
        private final PeriodFieldAffix iPrefix;
        private final int iPrintZeroSetting;
        private final boolean iRejectSignedValues;
        private final PeriodFieldAffix iSuffix;

        FieldFormatter(int i, int i2, int i3, boolean z, int i4, FieldFormatter[] fieldFormatterArr, PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iMinPrintedDigits = i;
            this.iPrintZeroSetting = i2;
            this.iMaxParsedDigits = i3;
            this.iRejectSignedValues = z;
            this.iFieldType = i4;
            this.iFieldFormatters = fieldFormatterArr;
            this.iPrefix = periodFieldAffix;
            this.iSuffix = periodFieldAffix2;
        }

        FieldFormatter(FieldFormatter fieldFormatter, PeriodFieldAffix periodFieldAffix) {
            this.iMinPrintedDigits = fieldFormatter.iMinPrintedDigits;
            this.iPrintZeroSetting = fieldFormatter.iPrintZeroSetting;
            this.iMaxParsedDigits = fieldFormatter.iMaxParsedDigits;
            this.iRejectSignedValues = fieldFormatter.iRejectSignedValues;
            this.iFieldType = fieldFormatter.iFieldType;
            this.iFieldFormatters = fieldFormatter.iFieldFormatters;
            this.iPrefix = fieldFormatter.iPrefix;
            this.iSuffix = fieldFormatter.iSuffix != null ? new CompositeAffix(fieldFormatter.iSuffix, periodFieldAffix) : periodFieldAffix;
        }

        public void finish(FieldFormatter[] fieldFormatterArr) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (FieldFormatter fieldFormatter : fieldFormatterArr) {
                if (fieldFormatter != null && !equals(fieldFormatter)) {
                    hashSet.add(fieldFormatter.iPrefix);
                    hashSet2.add(fieldFormatter.iSuffix);
                }
            }
            if (this.iPrefix != null) {
                this.iPrefix.finish(hashSet);
            }
            if (this.iSuffix != null) {
                this.iSuffix.finish(hashSet2);
            }
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            if (i <= 0) {
                return 0;
            }
            if (this.iPrintZeroSetting == 4 || getFieldValue(readablePeriod) != Long.MAX_VALUE) {
                return 1;
            }
            return 0;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue == Long.MAX_VALUE) {
                return 0;
            }
            int max = Math.max(FormatUtils.calculateDigitCount(fieldValue), this.iMinPrintedDigits);
            if (this.iFieldType >= 8) {
                max = (fieldValue < 0 ? Math.max(max, 5) : Math.max(max, 4)) + 1;
                if (this.iFieldType == 9 && Math.abs(fieldValue) % 1000 == 0) {
                    max -= 4;
                }
                fieldValue /= 1000;
            }
            int i = (int) fieldValue;
            if (this.iPrefix != null) {
                max += this.iPrefix.calculatePrintedLength(i);
            }
            if (this.iSuffix != null) {
                return max + this.iSuffix.calculatePrintedLength(i);
            }
            return max;
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue != Long.MAX_VALUE) {
                int i = (int) fieldValue;
                if (this.iFieldType >= 8) {
                    i = (int) (fieldValue / 1000);
                }
                if (this.iPrefix != null) {
                    this.iPrefix.printTo(stringBuffer, i);
                }
                int length = stringBuffer.length();
                int i2 = this.iMinPrintedDigits;
                if (i2 <= 1) {
                    FormatUtils.appendUnpaddedInteger(stringBuffer, i);
                } else {
                    FormatUtils.appendPaddedInteger(stringBuffer, i, i2);
                }
                if (this.iFieldType >= 8) {
                    int abs = (int) (Math.abs(fieldValue) % 1000);
                    if (this.iFieldType == 8 || abs > 0) {
                        if (fieldValue < 0 && fieldValue > -1000) {
                            stringBuffer.insert(length, '-');
                        }
                        stringBuffer.append('.');
                        FormatUtils.appendPaddedInteger(stringBuffer, abs, 3);
                    }
                }
                if (this.iSuffix != null) {
                    this.iSuffix.printTo(stringBuffer, i);
                }
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue != Long.MAX_VALUE) {
                int i = (int) fieldValue;
                if (this.iFieldType >= 8) {
                    i = (int) (fieldValue / 1000);
                }
                if (this.iPrefix != null) {
                    this.iPrefix.printTo(writer, i);
                }
                int i2 = this.iMinPrintedDigits;
                if (i2 <= 1) {
                    FormatUtils.writeUnpaddedInteger(writer, i);
                } else {
                    FormatUtils.writePaddedInteger(writer, i, i2);
                }
                if (this.iFieldType >= 8) {
                    int abs = (int) (Math.abs(fieldValue) % 1000);
                    if (this.iFieldType == 8 || abs > 0) {
                        writer.write(46);
                        FormatUtils.writePaddedInteger(writer, abs, 3);
                    }
                }
                if (this.iSuffix != null) {
                    this.iSuffix.printTo(writer, i);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:105:0x0156, code lost:
            r4 = r5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.ReadWritablePeriod r10, java.lang.String r11, int r12, java.util.Locale r13) {
            /*
                r9 = this;
                int r0 = r9.iPrintZeroSetting
                r1 = 4
                if (r0 != r1) goto L_0x0011
                r0 = 1
            L_0x0006:
                int r1 = r11.length()
                if (r12 < r1) goto L_0x0013
                if (r0 == 0) goto L_0x0010
                r12 = r12 ^ -1
            L_0x0010:
                return r12
            L_0x0011:
                r0 = 0
                goto L_0x0006
            L_0x0013:
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r1 = r9.iPrefix
                if (r1 == 0) goto L_0x0020
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r1 = r9.iPrefix
                int r12 = r1.parse(r11, r12)
                if (r12 < 0) goto L_0x0085
                r0 = 1
            L_0x0020:
                r1 = -1
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r2 = r9.iSuffix
                if (r2 == 0) goto L_0x0159
                if (r0 != 0) goto L_0x0159
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r1 = r9.iSuffix
                int r1 = r1.scan(r11, r12)
                if (r1 < 0) goto L_0x008a
                r0 = 1
                r6 = r1
            L_0x0031:
                if (r0 != 0) goto L_0x003f
                org.joda.time.PeriodType r0 = r10.getPeriodType()
                int r1 = r9.iFieldType
                boolean r0 = r9.isSupported(r0, r1)
                if (r0 == 0) goto L_0x0010
            L_0x003f:
                if (r6 <= 0) goto L_0x0091
                int r0 = r9.iMaxParsedDigits
                int r1 = r6 - r12
                int r0 = java.lang.Math.min(r0, r1)
            L_0x0049:
                r1 = 0
                r3 = -1
                r2 = 0
                r5 = 0
                r4 = r0
                r0 = r1
                r1 = r12
            L_0x0050:
                if (r0 >= r4) goto L_0x0156
                int r7 = r1 + r0
                char r7 = r11.charAt(r7)
                if (r0 != 0) goto L_0x00b2
                r8 = 45
                if (r7 == r8) goto L_0x0062
                r8 = 43
                if (r7 != r8) goto L_0x00b2
            L_0x0062:
                boolean r8 = r9.iRejectSignedValues
                if (r8 != 0) goto L_0x00b2
                r5 = 45
                if (r7 != r5) goto L_0x009d
                r5 = 1
            L_0x006b:
                int r7 = r0 + 1
                if (r7 >= r4) goto L_0x0156
                int r7 = r1 + r0
                int r7 = r7 + 1
                char r7 = r11.charAt(r7)
                r8 = 48
                if (r7 < r8) goto L_0x0156
                r8 = 57
                if (r7 <= r8) goto L_0x009f
                r4 = r5
            L_0x0080:
                if (r2 != 0) goto L_0x00e6
                r12 = r1 ^ -1
                goto L_0x0010
            L_0x0085:
                if (r0 != 0) goto L_0x0010
                r12 = r12 ^ -1
                goto L_0x0010
            L_0x008a:
                if (r0 != 0) goto L_0x008f
                r12 = r1 ^ -1
                goto L_0x0010
            L_0x008f:
                r12 = r1
                goto L_0x0010
            L_0x0091:
                int r0 = r9.iMaxParsedDigits
                int r1 = r11.length()
                int r1 = r1 - r12
                int r0 = java.lang.Math.min(r0, r1)
                goto L_0x0049
            L_0x009d:
                r5 = 0
                goto L_0x006b
            L_0x009f:
                if (r5 == 0) goto L_0x00af
                int r0 = r0 + 1
            L_0x00a3:
                int r4 = r4 + 1
                int r7 = r11.length()
                int r7 = r7 - r1
                int r4 = java.lang.Math.min(r4, r7)
                goto L_0x0050
            L_0x00af:
                int r1 = r1 + 1
                goto L_0x00a3
            L_0x00b2:
                r8 = 48
                if (r7 < r8) goto L_0x00be
                r8 = 57
                if (r7 > r8) goto L_0x00be
                r2 = 1
            L_0x00bb:
                int r0 = r0 + 1
                goto L_0x0050
            L_0x00be:
                r8 = 46
                if (r7 == r8) goto L_0x00c6
                r8 = 44
                if (r7 != r8) goto L_0x0156
            L_0x00c6:
                int r7 = r9.iFieldType
                r8 = 8
                if (r7 == r8) goto L_0x00d2
                int r7 = r9.iFieldType
                r8 = 9
                if (r7 != r8) goto L_0x0156
            L_0x00d2:
                if (r3 < 0) goto L_0x00d6
                r4 = r5
                goto L_0x0080
            L_0x00d6:
                int r3 = r1 + r0
                int r3 = r3 + 1
                int r4 = r4 + 1
                int r7 = r11.length()
                int r7 = r7 - r1
                int r4 = java.lang.Math.min(r4, r7)
                goto L_0x00bb
            L_0x00e6:
                if (r6 < 0) goto L_0x00ef
                int r2 = r1 + r0
                if (r2 == r6) goto L_0x00ef
                r12 = r1
                goto L_0x0010
            L_0x00ef:
                int r2 = r9.iFieldType
                r5 = 8
                if (r2 == r5) goto L_0x0114
                int r2 = r9.iFieldType
                r5 = 9
                if (r2 == r5) goto L_0x0114
                int r2 = r9.iFieldType
                int r3 = r9.parseInt(r11, r1, r0)
                r9.setFieldValue(r10, r2, r3)
            L_0x0104:
                int r0 = r0 + r1
                if (r0 < 0) goto L_0x0111
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r1 = r9.iSuffix
                if (r1 == 0) goto L_0x0111
                org.joda.time.format.PeriodFormatterBuilder$PeriodFieldAffix r1 = r9.iSuffix
                int r0 = r1.parse(r11, r0)
            L_0x0111:
                r12 = r0
                goto L_0x0010
            L_0x0114:
                if (r3 >= 0) goto L_0x0124
                r2 = 6
                int r3 = r9.parseInt(r11, r1, r0)
                r9.setFieldValue(r10, r2, r3)
                r2 = 7
                r3 = 0
                r9.setFieldValue(r10, r2, r3)
                goto L_0x0104
            L_0x0124:
                int r2 = r3 - r1
                int r2 = r2 + -1
                int r5 = r9.parseInt(r11, r1, r2)
                r2 = 6
                r9.setFieldValue(r10, r2, r5)
                int r2 = r1 + r0
                int r2 = r2 - r3
                if (r2 > 0) goto L_0x013b
                r2 = 0
            L_0x0136:
                r3 = 7
                r9.setFieldValue(r10, r3, r2)
                goto L_0x0104
            L_0x013b:
                r6 = 3
                if (r2 < r6) goto L_0x0149
                r2 = 3
                int r2 = r9.parseInt(r11, r3, r2)
            L_0x0143:
                if (r4 != 0) goto L_0x0147
                if (r5 >= 0) goto L_0x0136
            L_0x0147:
                int r2 = -r2
                goto L_0x0136
            L_0x0149:
                int r3 = r9.parseInt(r11, r3, r2)
                r6 = 1
                if (r2 != r6) goto L_0x0153
                int r2 = r3 * 100
                goto L_0x0143
            L_0x0153:
                int r2 = r3 * 10
                goto L_0x0143
            L_0x0156:
                r4 = r5
                goto L_0x0080
            L_0x0159:
                r6 = r1
                goto L_0x0031
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.FieldFormatter.parseInto(org.joda.time.ReadWritablePeriod, java.lang.String, int, java.util.Locale):int");
        }

        private int parseInt(String str, int i, int i2) {
            int i3;
            boolean z = false;
            if (i2 >= 10) {
                return Integer.parseInt(str.substring(i, i + i2));
            }
            if (i2 <= 0) {
                return 0;
            }
            int i4 = i + 1;
            char charAt = str.charAt(i);
            int i5 = i2 - 1;
            if (charAt == '-') {
                i5--;
                if (i5 < 0) {
                    return 0;
                }
                z = true;
                i3 = i4 + 1;
                charAt = str.charAt(i4);
            } else {
                i3 = i4;
            }
            int i6 = charAt - '0';
            int i7 = i3;
            while (true) {
                int i8 = i5 - 1;
                if (i5 <= 0) {
                    break;
                }
                i6 = (((i6 << 1) + (i6 << 3)) + str.charAt(i7)) - 48;
                i7++;
                i5 = i8;
            }
            return z ? -i6 : i6;
        }

        /* access modifiers changed from: package-private */
        public long getFieldValue(ReadablePeriod readablePeriod) {
            PeriodType periodType;
            long j;
            if (this.iPrintZeroSetting == 4) {
                periodType = null;
            } else {
                periodType = readablePeriod.getPeriodType();
            }
            if (periodType != null && !isSupported(periodType, this.iFieldType)) {
                return Long.MAX_VALUE;
            }
            switch (this.iFieldType) {
                case 0:
                    j = (long) readablePeriod.get(DurationFieldType.years());
                    break;
                case 1:
                    j = (long) readablePeriod.get(DurationFieldType.months());
                    break;
                case 2:
                    j = (long) readablePeriod.get(DurationFieldType.weeks());
                    break;
                case 3:
                    j = (long) readablePeriod.get(DurationFieldType.days());
                    break;
                case 4:
                    j = (long) readablePeriod.get(DurationFieldType.hours());
                    break;
                case 5:
                    j = (long) readablePeriod.get(DurationFieldType.minutes());
                    break;
                case 6:
                    j = (long) readablePeriod.get(DurationFieldType.seconds());
                    break;
                case 7:
                    j = (long) readablePeriod.get(DurationFieldType.millis());
                    break;
                case 8:
                case 9:
                    j = ((long) readablePeriod.get(DurationFieldType.millis())) + (((long) readablePeriod.get(DurationFieldType.seconds())) * 1000);
                    break;
                default:
                    return Long.MAX_VALUE;
            }
            if (j == 0) {
                switch (this.iPrintZeroSetting) {
                    case 1:
                        if (!isZero(readablePeriod) || this.iFieldFormatters[this.iFieldType] != this) {
                            return Long.MAX_VALUE;
                        }
                        int min = Math.min(this.iFieldType, 8) - 1;
                        while (min >= 0 && min <= 9) {
                            if (isSupported(periodType, min) && this.iFieldFormatters[min] != null) {
                                return Long.MAX_VALUE;
                            }
                            min--;
                        }
                        break;
                    case 2:
                        if (isZero(readablePeriod) && this.iFieldFormatters[this.iFieldType] == this) {
                            for (int i = this.iFieldType + 1; i <= 9; i++) {
                                if (isSupported(periodType, i) && this.iFieldFormatters[i] != null) {
                                    return Long.MAX_VALUE;
                                }
                            }
                            break;
                        } else {
                            return Long.MAX_VALUE;
                        }
                        break;
                    case 5:
                        return Long.MAX_VALUE;
                }
            }
            return j;
        }

        /* access modifiers changed from: package-private */
        public boolean isZero(ReadablePeriod readablePeriod) {
            int size = readablePeriod.size();
            for (int i = 0; i < size; i++) {
                if (readablePeriod.getValue(i) != 0) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean isSupported(PeriodType periodType, int i) {
            switch (i) {
                case 0:
                    return periodType.isSupported(DurationFieldType.years());
                case 1:
                    return periodType.isSupported(DurationFieldType.months());
                case 2:
                    return periodType.isSupported(DurationFieldType.weeks());
                case 3:
                    return periodType.isSupported(DurationFieldType.days());
                case 4:
                    return periodType.isSupported(DurationFieldType.hours());
                case 5:
                    return periodType.isSupported(DurationFieldType.minutes());
                case 6:
                    return periodType.isSupported(DurationFieldType.seconds());
                case 7:
                    return periodType.isSupported(DurationFieldType.millis());
                case 8:
                case 9:
                    return periodType.isSupported(DurationFieldType.seconds()) || periodType.isSupported(DurationFieldType.millis());
                default:
                    return false;
            }
        }

        /* access modifiers changed from: package-private */
        public void setFieldValue(ReadWritablePeriod readWritablePeriod, int i, int i2) {
            switch (i) {
                case 0:
                    readWritablePeriod.setYears(i2);
                    return;
                case 1:
                    readWritablePeriod.setMonths(i2);
                    return;
                case 2:
                    readWritablePeriod.setWeeks(i2);
                    return;
                case 3:
                    readWritablePeriod.setDays(i2);
                    return;
                case 4:
                    readWritablePeriod.setHours(i2);
                    return;
                case 5:
                    readWritablePeriod.setMinutes(i2);
                    return;
                case 6:
                    readWritablePeriod.setSeconds(i2);
                    return;
                case 7:
                    readWritablePeriod.setMillis(i2);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: package-private */
        public int getFieldType() {
            return this.iFieldType;
        }
    }

    static class Literal implements PeriodPrinter, PeriodParser {
        static final Literal EMPTY = new Literal("");
        private final String iText;

        Literal(String str) {
            this.iText = str;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            return 0;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            return this.iText.length();
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            stringBuffer.append(this.iText);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            writer.write(this.iText);
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            if (str.regionMatches(true, i, this.iText, 0, this.iText.length())) {
                return this.iText.length() + i;
            }
            return i ^ -1;
        }
    }

    static class Separator implements PeriodPrinter, PeriodParser {
        /* access modifiers changed from: private */
        public volatile PeriodParser iAfterParser;
        /* access modifiers changed from: private */
        public volatile PeriodPrinter iAfterPrinter;
        private final PeriodParser iBeforeParser;
        private final PeriodPrinter iBeforePrinter;
        private final String iFinalText;
        private final String[] iParsedForms;
        private final String iText;
        private final boolean iUseAfter;
        private final boolean iUseBefore;

        Separator(String str, String str2, String[] strArr, PeriodPrinter periodPrinter, PeriodParser periodParser, boolean z, boolean z2) {
            this.iText = str;
            this.iFinalText = str2;
            if ((str2 == null || str.equals(str2)) && (strArr == null || strArr.length == 0)) {
                this.iParsedForms = new String[]{str};
            } else {
                TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                treeSet.add(str);
                treeSet.add(str2);
                if (strArr != null) {
                    int length = strArr.length;
                    while (true) {
                        length--;
                        if (length < 0) {
                            break;
                        }
                        treeSet.add(strArr[length]);
                    }
                }
                ArrayList arrayList = new ArrayList(treeSet);
                Collections.reverse(arrayList);
                this.iParsedForms = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            this.iBeforePrinter = periodPrinter;
            this.iBeforeParser = periodParser;
            this.iUseBefore = z;
            this.iUseAfter = z2;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            int countFieldsToPrint = this.iBeforePrinter.countFieldsToPrint(readablePeriod, i, locale);
            if (countFieldsToPrint < i) {
                return countFieldsToPrint + this.iAfterPrinter.countFieldsToPrint(readablePeriod, i, locale);
            }
            return countFieldsToPrint;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            int i;
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            int calculatePrintedLength = periodPrinter.calculatePrintedLength(readablePeriod, locale) + periodPrinter2.calculatePrintedLength(readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) <= 0) {
                    return calculatePrintedLength;
                }
                if (!this.iUseAfter) {
                    return calculatePrintedLength + this.iText.length();
                }
                int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                if (countFieldsToPrint > 0) {
                    i = (countFieldsToPrint > 1 ? this.iText : this.iFinalText).length() + calculatePrintedLength;
                } else {
                    i = calculatePrintedLength;
                }
                return i;
            } else if (!this.iUseAfter || periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) <= 0) {
                return calculatePrintedLength;
            } else {
                return calculatePrintedLength + this.iText.length();
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(stringBuffer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.iUseAfter) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (countFieldsToPrint > 0) {
                            stringBuffer.append(countFieldsToPrint > 1 ? this.iText : this.iFinalText);
                        }
                    } else {
                        stringBuffer.append(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                stringBuffer.append(this.iText);
            }
            periodPrinter2.printTo(stringBuffer, readablePeriod, locale);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(writer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.iUseAfter) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (countFieldsToPrint > 0) {
                            writer.write(countFieldsToPrint > 1 ? this.iText : this.iFinalText);
                        }
                    } else {
                        writer.write(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                writer.write(this.iText);
            }
            periodPrinter2.printTo(writer, readablePeriod, locale);
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0034  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x003f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.ReadWritablePeriod r11, java.lang.String r12, int r13, java.util.Locale r14) {
            /*
                r10 = this;
                r1 = 1
                r4 = 0
                org.joda.time.format.PeriodParser r0 = r10.iBeforeParser
                int r2 = r0.parseInto(r11, r12, r13, r14)
                if (r2 >= 0) goto L_0x000b
            L_0x000a:
                return r2
            L_0x000b:
                r6 = -1
                if (r2 <= r13) goto L_0x0055
                java.lang.String[] r8 = r10.iParsedForms
                int r9 = r8.length
                r7 = r4
            L_0x0012:
                if (r7 >= r9) goto L_0x0055
                r3 = r8[r7]
                if (r3 == 0) goto L_0x0029
                int r0 = r3.length()
                if (r0 == 0) goto L_0x0029
                int r5 = r3.length()
                r0 = r12
                boolean r0 = r0.regionMatches(r1, r2, r3, r4, r5)
                if (r0 == 0) goto L_0x003b
            L_0x0029:
                if (r3 != 0) goto L_0x0036
            L_0x002b:
                int r2 = r2 + r4
            L_0x002c:
                org.joda.time.format.PeriodParser r0 = r10.iAfterParser
                int r0 = r0.parseInto(r11, r12, r2, r14)
                if (r0 >= 0) goto L_0x003f
                r2 = r0
                goto L_0x000a
            L_0x0036:
                int r4 = r3.length()
                goto L_0x002b
            L_0x003b:
                int r0 = r7 + 1
                r7 = r0
                goto L_0x0012
            L_0x003f:
                if (r1 == 0) goto L_0x0048
                if (r0 != r2) goto L_0x0048
                if (r4 <= 0) goto L_0x0048
                r2 = r2 ^ -1
                goto L_0x000a
            L_0x0048:
                if (r0 <= r2) goto L_0x0053
                if (r1 != 0) goto L_0x0053
                boolean r1 = r10.iUseBefore
                if (r1 != 0) goto L_0x0053
                r2 = r2 ^ -1
                goto L_0x000a
            L_0x0053:
                r2 = r0
                goto L_0x000a
            L_0x0055:
                r1 = r4
                r4 = r6
                goto L_0x002c
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.Separator.parseInto(org.joda.time.ReadWritablePeriod, java.lang.String, int, java.util.Locale):int");
        }

        /* access modifiers changed from: package-private */
        public Separator finish(PeriodPrinter periodPrinter, PeriodParser periodParser) {
            this.iAfterPrinter = periodPrinter;
            this.iAfterParser = periodParser;
            return this;
        }
    }

    static class Composite implements PeriodPrinter, PeriodParser {
        private final PeriodParser[] iParsers;
        private final PeriodPrinter[] iPrinters;

        Composite(List<Object> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.size() <= 0) {
                this.iPrinters = null;
            } else {
                this.iPrinters = (PeriodPrinter[]) arrayList.toArray(new PeriodPrinter[arrayList.size()]);
            }
            if (arrayList2.size() <= 0) {
                this.iParsers = null;
            } else {
                this.iParsers = (PeriodParser[]) arrayList2.toArray(new PeriodParser[arrayList2.size()]);
            }
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            int i2 = 0;
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            while (i2 < i) {
                length--;
                if (length < 0) {
                    break;
                }
                i2 += periodPrinterArr[length].countFieldsToPrint(readablePeriod, Integer.MAX_VALUE, locale);
            }
            return i2;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            int i = 0;
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                i += periodPrinterArr[length].calculatePrintedLength(readablePeriod, locale);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            for (PeriodPrinter printTo : this.iPrinters) {
                printTo.printTo(stringBuffer, readablePeriod, locale);
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            for (PeriodPrinter printTo : this.iPrinters) {
                printTo.printTo(writer, readablePeriod, locale);
            }
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            PeriodParser[] periodParserArr = this.iParsers;
            if (periodParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = periodParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2++) {
                i = periodParserArr[i2].parseInto(readWritablePeriod, str, i, locale);
            }
            return i;
        }

        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof PeriodPrinter) {
                    if (obj instanceof Composite) {
                        addArrayToList(list2, ((Composite) obj).iPrinters);
                    } else {
                        list2.add(obj);
                    }
                }
                Object obj2 = list.get(i + 1);
                if (obj2 instanceof PeriodParser) {
                    if (obj2 instanceof Composite) {
                        addArrayToList(list3, ((Composite) obj2).iParsers);
                    } else {
                        list3.add(obj2);
                    }
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
}
