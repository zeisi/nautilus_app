package org.joda.time.format;

import java.util.Collection;
import java.util.HashSet;
import org.joda.time.DateTimeFieldType;

public class ISODateTimeFormat {
    protected ISODateTimeFormat() {
    }

    public static DateTimeFormatter forFields(Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException("The fields must not be null or empty");
        }
        HashSet hashSet = new HashSet(collection);
        int size = hashSet.size();
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        if (hashSet.contains(DateTimeFieldType.monthOfYear())) {
            z3 = dateByMonth(dateTimeFormatterBuilder, hashSet, z, z2);
        } else if (hashSet.contains(DateTimeFieldType.dayOfYear())) {
            z3 = dateByOrdinal(dateTimeFormatterBuilder, hashSet, z, z2);
        } else if (hashSet.contains(DateTimeFieldType.weekOfWeekyear())) {
            z3 = dateByWeek(dateTimeFormatterBuilder, hashSet, z, z2);
        } else if (hashSet.contains(DateTimeFieldType.dayOfMonth())) {
            z3 = dateByMonth(dateTimeFormatterBuilder, hashSet, z, z2);
        } else if (hashSet.contains(DateTimeFieldType.dayOfWeek())) {
            z3 = dateByWeek(dateTimeFormatterBuilder, hashSet, z, z2);
        } else if (hashSet.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.ye);
            z3 = true;
        } else if (hashSet.remove(DateTimeFieldType.weekyear())) {
            dateTimeFormatterBuilder.append(Constants.we);
            z3 = true;
        } else {
            z3 = false;
        }
        if (hashSet.size() < size) {
            z4 = true;
        } else {
            z4 = false;
        }
        time(dateTimeFormatterBuilder, hashSet, z, z2, z3, z4);
        if (!dateTimeFormatterBuilder.canBuildFormatter()) {
            throw new IllegalArgumentException("No valid format for fields: " + collection);
        }
        try {
            collection.retainAll(hashSet);
        } catch (UnsupportedOperationException e) {
        }
        return dateTimeFormatterBuilder.toFormatter();
    }

    private static boolean dateByMonth(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.ye);
            if (collection.remove(DateTimeFieldType.monthOfYear())) {
                if (collection.remove(DateTimeFieldType.dayOfMonth())) {
                    appendSeparator(dateTimeFormatterBuilder, z);
                    dateTimeFormatterBuilder.appendMonthOfYear(2);
                    appendSeparator(dateTimeFormatterBuilder, z);
                    dateTimeFormatterBuilder.appendDayOfMonth(2);
                    return false;
                }
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendMonthOfYear(2);
                return true;
            } else if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                return true;
            } else {
                checkNotStrictISO(collection, z2);
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfMonth(2);
                return false;
            }
        } else if (collection.remove(DateTimeFieldType.monthOfYear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendMonthOfYear(2);
            if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfMonth(2);
            return false;
        } else if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
            return false;
        } else {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfMonth(2);
            return false;
        }
    }

    private static boolean dateByOrdinal(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.ye);
            if (!collection.remove(DateTimeFieldType.dayOfYear())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfYear(3);
            return false;
        } else if (!collection.remove(DateTimeFieldType.dayOfYear())) {
            return false;
        } else {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfYear(3);
            return false;
        }
    }

    private static boolean dateByWeek(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.weekyear())) {
            dateTimeFormatterBuilder.append(Constants.we);
            if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
                appendSeparator(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
                if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                    return true;
                }
                appendSeparator(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendDayOfWeek(1);
                return false;
            } else if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                return true;
            } else {
                checkNotStrictISO(collection, z2);
                appendSeparator(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfWeek(1);
                return false;
            }
        } else if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
            if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfWeek(1);
            return false;
        } else if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
            return false;
        } else {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfWeek(1);
            return false;
        }
    }

    private static void time(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean remove = collection.remove(DateTimeFieldType.hourOfDay());
        boolean remove2 = collection.remove(DateTimeFieldType.minuteOfHour());
        boolean remove3 = collection.remove(DateTimeFieldType.secondOfMinute());
        boolean remove4 = collection.remove(DateTimeFieldType.millisOfSecond());
        if (remove || remove2 || remove3 || remove4) {
            if (remove || remove2 || remove3 || remove4) {
                if (z2 && z3) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields because Date was reduced precision: " + collection);
                } else if (z4) {
                    dateTimeFormatterBuilder.appendLiteral('T');
                }
            }
            if ((!remove || !remove2 || !remove3) && (!remove || remove3 || remove4)) {
                if (z2 && z4) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields because Time was truncated: " + collection);
                } else if ((remove || ((!remove2 || !remove3) && ((!remove2 || remove4) && !remove3))) && z2) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields: " + collection);
                }
            }
            if (remove) {
                dateTimeFormatterBuilder.appendHourOfDay(2);
            } else if (remove2 || remove3 || remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z && remove && remove2) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (remove2) {
                dateTimeFormatterBuilder.appendMinuteOfHour(2);
            } else if (remove3 || remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z && remove2 && remove3) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (remove3) {
                dateTimeFormatterBuilder.appendSecondOfMinute(2);
            } else if (remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (remove4) {
                dateTimeFormatterBuilder.appendLiteral('.');
                dateTimeFormatterBuilder.appendMillisOfSecond(3);
            }
        }
    }

    private static void checkNotStrictISO(Collection<DateTimeFieldType> collection, boolean z) {
        if (z) {
            throw new IllegalArgumentException("No valid ISO8601 format for fields: " + collection);
        }
    }

    private static void appendSeparator(DateTimeFormatterBuilder dateTimeFormatterBuilder, boolean z) {
        if (z) {
            dateTimeFormatterBuilder.appendLiteral('-');
        }
    }

    public static DateTimeFormatter dateParser() {
        return Constants.dp;
    }

    public static DateTimeFormatter localDateParser() {
        return Constants.ldp;
    }

    public static DateTimeFormatter dateElementParser() {
        return Constants.dpe;
    }

    public static DateTimeFormatter timeParser() {
        return Constants.tp;
    }

    public static DateTimeFormatter localTimeParser() {
        return Constants.ltp;
    }

    public static DateTimeFormatter timeElementParser() {
        return Constants.tpe;
    }

    public static DateTimeFormatter dateTimeParser() {
        return Constants.dtp;
    }

    public static DateTimeFormatter dateOptionalTimeParser() {
        return Constants.dotp;
    }

    public static DateTimeFormatter localDateOptionalTimeParser() {
        return Constants.ldotp;
    }

    public static DateTimeFormatter date() {
        return yearMonthDay();
    }

    public static DateTimeFormatter time() {
        return Constants.t;
    }

    public static DateTimeFormatter timeNoMillis() {
        return Constants.tx;
    }

    public static DateTimeFormatter tTime() {
        return Constants.tt;
    }

    public static DateTimeFormatter tTimeNoMillis() {
        return Constants.ttx;
    }

    public static DateTimeFormatter dateTime() {
        return Constants.dt;
    }

    public static DateTimeFormatter dateTimeNoMillis() {
        return Constants.dtx;
    }

    public static DateTimeFormatter ordinalDate() {
        return Constants.od;
    }

    public static DateTimeFormatter ordinalDateTime() {
        return Constants.odt;
    }

    public static DateTimeFormatter ordinalDateTimeNoMillis() {
        return Constants.odtx;
    }

    public static DateTimeFormatter weekDate() {
        return Constants.wwd;
    }

    public static DateTimeFormatter weekDateTime() {
        return Constants.wdt;
    }

    public static DateTimeFormatter weekDateTimeNoMillis() {
        return Constants.wdtx;
    }

    public static DateTimeFormatter basicDate() {
        return Constants.bd;
    }

    public static DateTimeFormatter basicTime() {
        return Constants.bt;
    }

    public static DateTimeFormatter basicTimeNoMillis() {
        return Constants.btx;
    }

    public static DateTimeFormatter basicTTime() {
        return Constants.btt;
    }

    public static DateTimeFormatter basicTTimeNoMillis() {
        return Constants.bttx;
    }

    public static DateTimeFormatter basicDateTime() {
        return Constants.bdt;
    }

    public static DateTimeFormatter basicDateTimeNoMillis() {
        return Constants.bdtx;
    }

    public static DateTimeFormatter basicOrdinalDate() {
        return Constants.bod;
    }

    public static DateTimeFormatter basicOrdinalDateTime() {
        return Constants.bodt;
    }

    public static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
        return Constants.bodtx;
    }

    public static DateTimeFormatter basicWeekDate() {
        return Constants.bwd;
    }

    public static DateTimeFormatter basicWeekDateTime() {
        return Constants.bwdt;
    }

    public static DateTimeFormatter basicWeekDateTimeNoMillis() {
        return Constants.bwdtx;
    }

    public static DateTimeFormatter year() {
        return Constants.ye;
    }

    public static DateTimeFormatter yearMonth() {
        return Constants.ym;
    }

    public static DateTimeFormatter yearMonthDay() {
        return Constants.ymd;
    }

    public static DateTimeFormatter weekyear() {
        return Constants.we;
    }

    public static DateTimeFormatter weekyearWeek() {
        return Constants.ww;
    }

    public static DateTimeFormatter weekyearWeekDay() {
        return Constants.wwd;
    }

    public static DateTimeFormatter hour() {
        return Constants.hde;
    }

    public static DateTimeFormatter hourMinute() {
        return Constants.hm;
    }

    public static DateTimeFormatter hourMinuteSecond() {
        return Constants.hms;
    }

    public static DateTimeFormatter hourMinuteSecondMillis() {
        return Constants.hmsl;
    }

    public static DateTimeFormatter hourMinuteSecondFraction() {
        return Constants.hmsf;
    }

    public static DateTimeFormatter dateHour() {
        return Constants.dh;
    }

    public static DateTimeFormatter dateHourMinute() {
        return Constants.dhm;
    }

    public static DateTimeFormatter dateHourMinuteSecond() {
        return Constants.dhms;
    }

    public static DateTimeFormatter dateHourMinuteSecondMillis() {
        return Constants.dhmsl;
    }

    public static DateTimeFormatter dateHourMinuteSecondFraction() {
        return Constants.dhmsf;
    }

    static final class Constants {
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bd = basicDate();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bdt = basicDateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bdtx = basicDateTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bod = basicOrdinalDate();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bodt = basicOrdinalDateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bodtx = basicOrdinalDateTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bt = basicTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter btt = basicTTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bttx = basicTTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter btx = basicTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bwd = basicWeekDate();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bwdt = basicWeekDateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter bwdtx = basicWeekDateTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dh = dateHour();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dhm = dateHourMinute();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dhms = dateHourMinuteSecond();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dhmsf = dateHourMinuteSecondFraction();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dhmsl = dateHourMinuteSecondMillis();
        private static final DateTimeFormatter dme = dayOfMonthElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dotp = dateOptionalTimeParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dp = dateParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dpe = dateElementParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dt = dateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dtp = dateTimeParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter dtx = dateTimeNoMillis();
        private static final DateTimeFormatter dwe = dayOfWeekElement();
        private static final DateTimeFormatter dye = dayOfYearElement();
        private static final DateTimeFormatter fse = fractionElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter hde = hourElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter hm = hourMinute();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter hms = hourMinuteSecond();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter hmsf = hourMinuteSecondFraction();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter hmsl = hourMinuteSecondMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ldotp = localDateOptionalTimeParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ldp = localDateParser();
        private static final DateTimeFormatter lte = literalTElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ltp = localTimeParser();
        private static final DateTimeFormatter mhe = minuteElement();
        private static final DateTimeFormatter mye = monthElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter od = ordinalDate();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter odt = ordinalDateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter odtx = ordinalDateTimeNoMillis();
        private static final DateTimeFormatter sme = secondElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter t = time();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter tp = timeParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter tpe = timeElementParser();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter tt = tTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ttx = tTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter tx = timeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter wdt = weekDateTime();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter wdtx = weekDateTimeNoMillis();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter we = weekyearElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ww = weekyearWeek();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter wwd = weekyearWeekDay();
        private static final DateTimeFormatter wwe = weekElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ye = yearElement();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ym = yearMonth();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ymd = yearMonthDay();
        private static final DateTimeFormatter ze = offsetElement();

        Constants() {
        }

        private static DateTimeFormatter dateParser() {
            if (dp != null) {
                return dp;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(offsetElement()).toParser()).toFormatter();
        }

        private static DateTimeFormatter localDateParser() {
            if (ldp == null) {
                return dateElementParser().withZoneUTC();
            }
            return ldp;
        }

        private static DateTimeFormatter dateElementParser() {
            if (dpe != null) {
                return dpe;
            }
            return new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(yearElement()).appendOptional(new DateTimeFormatterBuilder().append(monthElement()).appendOptional(dayOfMonthElement().getParser()).toParser()).toParser(), new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).appendOptional(dayOfWeekElement().getParser()).toParser(), new DateTimeFormatterBuilder().append(yearElement()).append(dayOfYearElement()).toParser()}).toFormatter();
        }

        private static DateTimeFormatter timeParser() {
            if (tp == null) {
                return new DateTimeFormatterBuilder().appendOptional(literalTElement().getParser()).append(timeElementParser()).appendOptional(offsetElement().getParser()).toFormatter();
            }
            return tp;
        }

        private static DateTimeFormatter localTimeParser() {
            if (ltp == null) {
                return new DateTimeFormatterBuilder().appendOptional(literalTElement().getParser()).append(timeElementParser()).toFormatter().withZoneUTC();
            }
            return ltp;
        }

        private static DateTimeFormatter timeElementParser() {
            if (tpe != null) {
                return tpe;
            }
            DateTimeParser parser = new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().appendLiteral('.').toParser(), new DateTimeFormatterBuilder().appendLiteral(',').toParser()}).toParser();
            return new DateTimeFormatterBuilder().append(hourElement()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(minuteElement()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(secondElement()).appendOptional(new DateTimeFormatterBuilder().append(parser).appendFractionOfSecond(1, 9).toParser()).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfMinute(1, 9).toParser(), null}).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfHour(1, 9).toParser(), null}).toFormatter();
        }

        private static DateTimeFormatter dateTimeParser() {
            if (dtp != null) {
                return dtp;
            }
            DateTimeParser parser = new DateTimeFormatterBuilder().appendLiteral('T').append(timeElementParser()).appendOptional(offsetElement().getParser()).toParser();
            return new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{parser, dateOptionalTimeParser().getParser()}).toFormatter();
        }

        private static DateTimeFormatter dateOptionalTimeParser() {
            if (dotp != null) {
                return dotp;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').appendOptional(timeElementParser().getParser()).appendOptional(offsetElement().getParser()).toParser()).toFormatter();
        }

        private static DateTimeFormatter localDateOptionalTimeParser() {
            if (ldotp != null) {
                return ldotp;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(timeElementParser()).toParser()).toFormatter().withZoneUTC();
        }

        private static DateTimeFormatter time() {
            if (t == null) {
                return new DateTimeFormatterBuilder().append(hourMinuteSecondFraction()).append(offsetElement()).toFormatter();
            }
            return t;
        }

        private static DateTimeFormatter timeNoMillis() {
            if (tx == null) {
                return new DateTimeFormatterBuilder().append(hourMinuteSecond()).append(offsetElement()).toFormatter();
            }
            return tx;
        }

        private static DateTimeFormatter tTime() {
            if (tt == null) {
                return new DateTimeFormatterBuilder().append(literalTElement()).append(time()).toFormatter();
            }
            return tt;
        }

        private static DateTimeFormatter tTimeNoMillis() {
            if (ttx == null) {
                return new DateTimeFormatterBuilder().append(literalTElement()).append(timeNoMillis()).toFormatter();
            }
            return ttx;
        }

        private static DateTimeFormatter dateTime() {
            if (dt == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(tTime()).toFormatter();
            }
            return dt;
        }

        private static DateTimeFormatter dateTimeNoMillis() {
            if (dtx == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(tTimeNoMillis()).toFormatter();
            }
            return dtx;
        }

        private static DateTimeFormatter ordinalDate() {
            if (od == null) {
                return new DateTimeFormatterBuilder().append(yearElement()).append(dayOfYearElement()).toFormatter();
            }
            return od;
        }

        private static DateTimeFormatter ordinalDateTime() {
            if (odt == null) {
                return new DateTimeFormatterBuilder().append(ordinalDate()).append(tTime()).toFormatter();
            }
            return odt;
        }

        private static DateTimeFormatter ordinalDateTimeNoMillis() {
            if (odtx == null) {
                return new DateTimeFormatterBuilder().append(ordinalDate()).append(tTimeNoMillis()).toFormatter();
            }
            return odtx;
        }

        private static DateTimeFormatter weekDateTime() {
            if (wdt == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(tTime()).toFormatter();
            }
            return wdt;
        }

        private static DateTimeFormatter weekDateTimeNoMillis() {
            if (wdtx == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(tTimeNoMillis()).toFormatter();
            }
            return wdtx;
        }

        private static DateTimeFormatter basicDate() {
            if (bd == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2).toFormatter();
            }
            return bd;
        }

        private static DateTimeFormatter basicTime() {
            if (bt == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendLiteral('.').appendFractionOfSecond(3, 9).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return bt;
        }

        private static DateTimeFormatter basicTimeNoMillis() {
            if (btx == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return btx;
        }

        private static DateTimeFormatter basicTTime() {
            if (btt == null) {
                return new DateTimeFormatterBuilder().append(literalTElement()).append(basicTime()).toFormatter();
            }
            return btt;
        }

        private static DateTimeFormatter basicTTimeNoMillis() {
            if (bttx == null) {
                return new DateTimeFormatterBuilder().append(literalTElement()).append(basicTimeNoMillis()).toFormatter();
            }
            return bttx;
        }

        private static DateTimeFormatter basicDateTime() {
            if (bdt == null) {
                return new DateTimeFormatterBuilder().append(basicDate()).append(basicTTime()).toFormatter();
            }
            return bdt;
        }

        private static DateTimeFormatter basicDateTimeNoMillis() {
            if (bdtx == null) {
                return new DateTimeFormatterBuilder().append(basicDate()).append(basicTTimeNoMillis()).toFormatter();
            }
            return bdtx;
        }

        private static DateTimeFormatter basicOrdinalDate() {
            if (bod == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.dayOfYear(), 3).toFormatter();
            }
            return bod;
        }

        private static DateTimeFormatter basicOrdinalDateTime() {
            if (bodt == null) {
                return new DateTimeFormatterBuilder().append(basicOrdinalDate()).append(basicTTime()).toFormatter();
            }
            return bodt;
        }

        private static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
            if (bodtx == null) {
                return new DateTimeFormatterBuilder().append(basicOrdinalDate()).append(basicTTimeNoMillis()).toFormatter();
            }
            return bodtx;
        }

        private static DateTimeFormatter basicWeekDate() {
            if (bwd == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 4).appendLiteral('W').appendFixedDecimal(DateTimeFieldType.weekOfWeekyear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfWeek(), 1).toFormatter();
            }
            return bwd;
        }

        private static DateTimeFormatter basicWeekDateTime() {
            if (bwdt == null) {
                return new DateTimeFormatterBuilder().append(basicWeekDate()).append(basicTTime()).toFormatter();
            }
            return bwdt;
        }

        private static DateTimeFormatter basicWeekDateTimeNoMillis() {
            if (bwdtx == null) {
                return new DateTimeFormatterBuilder().append(basicWeekDate()).append(basicTTimeNoMillis()).toFormatter();
            }
            return bwdtx;
        }

        private static DateTimeFormatter yearMonth() {
            if (ym == null) {
                return new DateTimeFormatterBuilder().append(yearElement()).append(monthElement()).toFormatter();
            }
            return ym;
        }

        private static DateTimeFormatter yearMonthDay() {
            if (ymd == null) {
                return new DateTimeFormatterBuilder().append(yearElement()).append(monthElement()).append(dayOfMonthElement()).toFormatter();
            }
            return ymd;
        }

        private static DateTimeFormatter weekyearWeek() {
            if (ww == null) {
                return new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).toFormatter();
            }
            return ww;
        }

        private static DateTimeFormatter weekyearWeekDay() {
            if (wwd == null) {
                return new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).append(dayOfWeekElement()).toFormatter();
            }
            return wwd;
        }

        private static DateTimeFormatter hourMinute() {
            if (hm == null) {
                return new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).toFormatter();
            }
            return hm;
        }

        private static DateTimeFormatter hourMinuteSecond() {
            if (hms == null) {
                return new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).toFormatter();
            }
            return hms;
        }

        private static DateTimeFormatter hourMinuteSecondMillis() {
            if (hmsl == null) {
                return new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).appendLiteral('.').appendFractionOfSecond(3, 3).toFormatter();
            }
            return hmsl;
        }

        private static DateTimeFormatter hourMinuteSecondFraction() {
            if (hmsf == null) {
                return new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).append(fractionElement()).toFormatter();
            }
            return hmsf;
        }

        private static DateTimeFormatter dateHour() {
            if (dh == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(ISODateTimeFormat.hour()).toFormatter();
            }
            return dh;
        }

        private static DateTimeFormatter dateHourMinute() {
            if (dhm == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinute()).toFormatter();
            }
            return dhm;
        }

        private static DateTimeFormatter dateHourMinuteSecond() {
            if (dhms == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecond()).toFormatter();
            }
            return dhms;
        }

        private static DateTimeFormatter dateHourMinuteSecondMillis() {
            if (dhmsl == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecondMillis()).toFormatter();
            }
            return dhmsl;
        }

        private static DateTimeFormatter dateHourMinuteSecondFraction() {
            if (dhmsf == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecondFraction()).toFormatter();
            }
            return dhmsf;
        }

        private static DateTimeFormatter yearElement() {
            if (ye == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 9).toFormatter();
            }
            return ye;
        }

        private static DateTimeFormatter monthElement() {
            if (mye == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendMonthOfYear(2).toFormatter();
            }
            return mye;
        }

        private static DateTimeFormatter dayOfMonthElement() {
            if (dme == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfMonth(2).toFormatter();
            }
            return dme;
        }

        private static DateTimeFormatter weekyearElement() {
            if (we == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 9).toFormatter();
            }
            return we;
        }

        private static DateTimeFormatter weekElement() {
            if (wwe == null) {
                return new DateTimeFormatterBuilder().appendLiteral("-W").appendWeekOfWeekyear(2).toFormatter();
            }
            return wwe;
        }

        private static DateTimeFormatter dayOfWeekElement() {
            if (dwe == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfWeek(1).toFormatter();
            }
            return dwe;
        }

        private static DateTimeFormatter dayOfYearElement() {
            if (dye == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfYear(3).toFormatter();
            }
            return dye;
        }

        private static DateTimeFormatter literalTElement() {
            if (lte == null) {
                return new DateTimeFormatterBuilder().appendLiteral('T').toFormatter();
            }
            return lte;
        }

        private static DateTimeFormatter hourElement() {
            if (hde == null) {
                return new DateTimeFormatterBuilder().appendHourOfDay(2).toFormatter();
            }
            return hde;
        }

        private static DateTimeFormatter minuteElement() {
            if (mhe == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendMinuteOfHour(2).toFormatter();
            }
            return mhe;
        }

        private static DateTimeFormatter secondElement() {
            if (sme == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendSecondOfMinute(2).toFormatter();
            }
            return sme;
        }

        private static DateTimeFormatter fractionElement() {
            if (fse == null) {
                return new DateTimeFormatterBuilder().appendLiteral('.').appendFractionOfSecond(3, 9).toFormatter();
            }
            return fse;
        }

        private static DateTimeFormatter offsetElement() {
            if (ze == null) {
                return new DateTimeFormatterBuilder().appendTimeZoneOffset("Z", true, 2, 4).toFormatter();
            }
            return ze;
        }
    }
}
