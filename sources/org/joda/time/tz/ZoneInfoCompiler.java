package org.joda.time.tz;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.LenientChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ZoneInfoCompiler {
    static Chronology cLenientISO;
    static DateTimeOfYear cStartOfYear;
    private List<String> iBackLinks = new ArrayList();
    private List<String> iGoodLinks = new ArrayList();
    private Map<String, RuleSet> iRuleSets = new HashMap();
    private List<Zone> iZones = new ArrayList();

    public static void main(String[] strArr) throws Exception {
        File file = null;
        int i = 0;
        if (strArr.length == 0) {
            printUsage();
            return;
        }
        int i2 = 0;
        boolean z = false;
        File file2 = null;
        while (true) {
            if (i2 >= strArr.length) {
                break;
            }
            try {
                if ("-src".equals(strArr[i2])) {
                    i2++;
                    file2 = new File(strArr[i2]);
                } else if ("-dst".equals(strArr[i2])) {
                    i2++;
                    file = new File(strArr[i2]);
                } else if ("-verbose".equals(strArr[i2])) {
                    z = true;
                } else if ("-?".equals(strArr[i2])) {
                    printUsage();
                    return;
                }
                i2++;
            } catch (IndexOutOfBoundsException e) {
                printUsage();
                return;
            }
        }
        if (i2 >= strArr.length) {
            printUsage();
            return;
        }
        File[] fileArr = new File[(strArr.length - i2)];
        while (i2 < strArr.length) {
            fileArr[i] = file2 == null ? new File(strArr[i2]) : new File(file2, strArr[i2]);
            i2++;
            i++;
        }
        ZoneInfoLogger.set(z);
        new ZoneInfoCompiler().compile(file, fileArr);
    }

    private static void printUsage() {
        System.out.println("Usage: java org.joda.time.tz.ZoneInfoCompiler <options> <source files>");
        System.out.println("where possible options include:");
        System.out.println("  -src <directory>    Specify where to read source files");
        System.out.println("  -dst <directory>    Specify where to write generated files");
        System.out.println("  -verbose            Output verbosely (default false)");
    }

    static DateTimeOfYear getStartOfYear() {
        if (cStartOfYear == null) {
            cStartOfYear = new DateTimeOfYear();
        }
        return cStartOfYear;
    }

    static Chronology getLenientISOChronology() {
        if (cLenientISO == null) {
            cLenientISO = LenientChronology.getInstance(ISOChronology.getInstanceUTC());
        }
        return cLenientISO;
    }

    static void writeZoneInfoMap(DataOutputStream dataOutputStream, Map<String, DateTimeZone> map) throws IOException {
        HashMap hashMap = new HashMap(map.size());
        TreeMap treeMap = new TreeMap();
        short s = 0;
        Iterator<Map.Entry<String, DateTimeZone>> it = map.entrySet().iterator();
        while (true) {
            short s2 = s;
            if (it.hasNext()) {
                Map.Entry next = it.next();
                String str = (String) next.getKey();
                if (!hashMap.containsKey(str)) {
                    Short valueOf = Short.valueOf(s2);
                    hashMap.put(str, valueOf);
                    treeMap.put(valueOf, str);
                    s2 = (short) (s2 + 1);
                    if (s2 == 0) {
                        throw new InternalError("Too many time zone ids");
                    }
                }
                String id = ((DateTimeZone) next.getValue()).getID();
                if (!hashMap.containsKey(id)) {
                    Short valueOf2 = Short.valueOf(s2);
                    hashMap.put(id, valueOf2);
                    treeMap.put(valueOf2, id);
                    s = (short) (s2 + 1);
                    if (s == 0) {
                        throw new InternalError("Too many time zone ids");
                    }
                } else {
                    s = s2;
                }
            } else {
                dataOutputStream.writeShort(treeMap.size());
                for (String writeUTF : treeMap.values()) {
                    dataOutputStream.writeUTF(writeUTF);
                }
                dataOutputStream.writeShort(map.size());
                for (Map.Entry next2 : map.entrySet()) {
                    dataOutputStream.writeShort(((Short) hashMap.get((String) next2.getKey())).shortValue());
                    dataOutputStream.writeShort(((Short) hashMap.get(((DateTimeZone) next2.getValue()).getID())).shortValue());
                }
                return;
            }
        }
    }

    static int parseYear(String str, int i) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.equals("minimum") || lowerCase.equals("min")) {
            return Integer.MIN_VALUE;
        }
        if (lowerCase.equals("maximum") || lowerCase.equals("max")) {
            return Integer.MAX_VALUE;
        }
        return !lowerCase.equals("only") ? Integer.parseInt(lowerCase) : i;
    }

    static int parseMonth(String str) {
        DateTimeField monthOfYear = ISOChronology.getInstanceUTC().monthOfYear();
        return monthOfYear.get(monthOfYear.set(0, str, Locale.ENGLISH));
    }

    static int parseDayOfWeek(String str) {
        DateTimeField dayOfWeek = ISOChronology.getInstanceUTC().dayOfWeek();
        return dayOfWeek.get(dayOfWeek.set(0, str, Locale.ENGLISH));
    }

    static String parseOptional(String str) {
        if (str.equals("-")) {
            return null;
        }
        return str;
    }

    static int parseTime(String str) {
        DateTimeFormatter hourMinuteSecondFraction = ISODateTimeFormat.hourMinuteSecondFraction();
        MutableDateTime mutableDateTime = new MutableDateTime(0, getLenientISOChronology());
        int i = 0;
        if (str.startsWith("-")) {
            i = 1;
        }
        if (hourMinuteSecondFraction.parseInto(mutableDateTime, str, i) == (i ^ -1)) {
            throw new IllegalArgumentException(str);
        }
        int millis = (int) mutableDateTime.getMillis();
        if (i == 1) {
            return -millis;
        }
        return millis;
    }

    static char parseZoneChar(char c) {
        switch (c) {
            case 'G':
            case 'U':
            case 'Z':
            case 'g':
            case 'u':
            case 'z':
                return 'u';
            case 'S':
            case 's':
                return 's';
            default:
                return 'w';
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x006d, code lost:
        if (r1 < 0) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x006f, code lost:
        r4 = r12.previousTransition(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0075, code lost:
        if (r4 == r2) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0079, code lost:
        if (r4 >= r6) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0118, code lost:
        r2 = ((java.lang.Long) r10.get(r1)).longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0128, code lost:
        if ((r2 - 1) == r4) goto L_0x0172;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x012a, code lost:
        java.lang.System.out.println("*r* Error in " + r12.getID() + com.nautilus.omni.util.Constants.EMPTY_SPACE + new org.joda.time.DateTime(r4, (org.joda.time.Chronology) org.joda.time.chrono.ISOChronology.getInstanceUTC()) + " != " + new org.joda.time.DateTime(r2 - 1, (org.joda.time.Chronology) org.joda.time.chrono.ISOChronology.getInstanceUTC()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0172, code lost:
        r0 = r1;
        r2 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x006b, code lost:
        r1 = r0 - 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean test(java.lang.String r11, org.joda.time.DateTimeZone r12) {
        /*
            java.lang.String r0 = r12.getID()
            boolean r0 = r11.equals(r0)
            if (r0 != 0) goto L_0x000c
            r0 = 1
        L_0x000b:
            return r0
        L_0x000c:
            org.joda.time.chrono.ISOChronology r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            org.joda.time.DateTimeField r0 = r0.year()
            r2 = 0
            r1 = 1850(0x73a, float:2.592E-42)
            long r2 = r0.set((long) r2, (int) r1)
            org.joda.time.chrono.ISOChronology r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            org.joda.time.DateTimeField r0 = r0.year()
            r4 = 0
            r1 = 2050(0x802, float:2.873E-42)
            long r8 = r0.set((long) r4, (int) r1)
            int r1 = r12.getOffset((long) r2)
            java.lang.String r0 = r12.getNameKey(r2)
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r4 = r2
            r2 = r1
        L_0x003b:
            long r6 = r12.nextTransition(r4)
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0047
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 <= 0) goto L_0x007d
        L_0x0047:
            org.joda.time.chrono.ISOChronology r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            org.joda.time.DateTimeField r0 = r0.year()
            r2 = 0
            r1 = 2050(0x802, float:2.873E-42)
            long r2 = r0.set((long) r2, (int) r1)
            org.joda.time.chrono.ISOChronology r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            org.joda.time.DateTimeField r0 = r0.year()
            r4 = 0
            r1 = 1850(0x73a, float:2.592E-42)
            long r6 = r0.set((long) r4, (int) r1)
            int r0 = r10.size()
        L_0x006b:
            int r1 = r0 + -1
            if (r1 < 0) goto L_0x007b
            long r4 = r12.previousTransition(r2)
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x007b
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x0118
        L_0x007b:
            r0 = 1
            goto L_0x000b
        L_0x007d:
            int r3 = r12.getOffset((long) r6)
            java.lang.String r1 = r12.getNameKey(r6)
            if (r2 != r3) goto L_0x00bf
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00bf
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "*d* Error in "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r12.getID()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)
            org.joda.time.DateTime r2 = new org.joda.time.DateTime
            org.joda.time.chrono.ISOChronology r3 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            r2.<init>((long) r6, (org.joda.time.Chronology) r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            r0 = 0
            goto L_0x000b
        L_0x00bf:
            if (r1 == 0) goto L_0x00d0
            int r0 = r1.length()
            r2 = 3
            if (r0 >= r2) goto L_0x010c
            java.lang.String r0 = "??"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x010c
        L_0x00d0:
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "*s* Error in "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r12.getID()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " "
            java.lang.StringBuilder r2 = r2.append(r3)
            org.joda.time.DateTime r3 = new org.joda.time.DateTime
            org.joda.time.chrono.ISOChronology r4 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            r3.<init>((long) r6, (org.joda.time.Chronology) r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = ", nameKey="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            r0 = 0
            goto L_0x000b
        L_0x010c:
            java.lang.Long r0 = java.lang.Long.valueOf(r6)
            r10.add(r0)
            r0 = r1
            r2 = r3
            r4 = r6
            goto L_0x003b
        L_0x0118:
            java.lang.Object r0 = r10.get(r1)
            java.lang.Long r0 = (java.lang.Long) r0
            long r2 = r0.longValue()
            r8 = 1
            long r8 = r2 - r8
            int r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0172
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "*r* Error in "
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r6 = r12.getID()
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r6 = " "
            java.lang.StringBuilder r1 = r1.append(r6)
            org.joda.time.DateTime r6 = new org.joda.time.DateTime
            org.joda.time.chrono.ISOChronology r7 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            r6.<init>((long) r4, (org.joda.time.Chronology) r7)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r4 = " != "
            java.lang.StringBuilder r1 = r1.append(r4)
            org.joda.time.DateTime r4 = new org.joda.time.DateTime
            r6 = 1
            long r2 = r2 - r6
            org.joda.time.chrono.ISOChronology r5 = org.joda.time.chrono.ISOChronology.getInstanceUTC()
            r4.<init>((long) r2, (org.joda.time.Chronology) r5)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            r0 = 0
            goto L_0x000b
        L_0x0172:
            r0 = r1
            r2 = r4
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.ZoneInfoCompiler.test(java.lang.String, org.joda.time.DateTimeZone):boolean");
    }

    public Map<String, DateTimeZone> compile(File file, File[] fileArr) throws IOException {
        if (fileArr != null) {
            for (int i = 0; i < fileArr.length; i++) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileArr[i]));
                parseDataFile(bufferedReader, "backward".equals(fileArr[i].getName()));
                bufferedReader.close();
            }
        }
        if (file != null) {
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException("Destination directory doesn't exist and cannot be created: " + file);
            } else if (!file.isDirectory()) {
                throw new IOException("Destination is not a directory: " + file);
            }
        }
        TreeMap treeMap = new TreeMap();
        TreeMap treeMap2 = new TreeMap();
        System.out.println("Writing zoneinfo files");
        for (int i2 = 0; i2 < this.iZones.size(); i2++) {
            Zone zone = this.iZones.get(i2);
            DateTimeZoneBuilder dateTimeZoneBuilder = new DateTimeZoneBuilder();
            zone.addToBuilder(dateTimeZoneBuilder, this.iRuleSets);
            DateTimeZone dateTimeZone = dateTimeZoneBuilder.toDateTimeZone(zone.iName, true);
            if (test(dateTimeZone.getID(), dateTimeZone)) {
                treeMap.put(dateTimeZone.getID(), dateTimeZone);
                treeMap2.put(dateTimeZone.getID(), zone);
                if (file != null) {
                    writeZone(file, dateTimeZoneBuilder, dateTimeZone);
                }
            }
        }
        for (int i3 = 0; i3 < this.iGoodLinks.size(); i3 += 2) {
            String str = this.iGoodLinks.get(i3);
            String str2 = this.iGoodLinks.get(i3 + 1);
            Zone zone2 = (Zone) treeMap2.get(str);
            if (zone2 == null) {
                System.out.println("Cannot find source zone '" + str + "' to link alias '" + str2 + "' to");
            } else {
                DateTimeZoneBuilder dateTimeZoneBuilder2 = new DateTimeZoneBuilder();
                zone2.addToBuilder(dateTimeZoneBuilder2, this.iRuleSets);
                DateTimeZone dateTimeZone2 = dateTimeZoneBuilder2.toDateTimeZone(str2, true);
                if (test(dateTimeZone2.getID(), dateTimeZone2)) {
                    treeMap.put(dateTimeZone2.getID(), dateTimeZone2);
                    if (file != null) {
                        writeZone(file, dateTimeZoneBuilder2, dateTimeZone2);
                    }
                }
                treeMap.put(dateTimeZone2.getID(), dateTimeZone2);
                if (ZoneInfoLogger.verbose()) {
                    System.out.println("Good link: " + str2 + " -> " + str + " revived");
                }
            }
        }
        for (int i4 = 0; i4 < 2; i4++) {
            for (int i5 = 0; i5 < this.iBackLinks.size(); i5 += 2) {
                String str3 = this.iBackLinks.get(i5);
                String str4 = this.iBackLinks.get(i5 + 1);
                DateTimeZone dateTimeZone3 = (DateTimeZone) treeMap.get(str3);
                if (dateTimeZone3 != null) {
                    treeMap.put(str4, dateTimeZone3);
                    if (ZoneInfoLogger.verbose()) {
                        System.out.println("Back link: " + str4 + " -> " + dateTimeZone3.getID());
                    }
                } else if (i4 > 0) {
                    System.out.println("Cannot find time zone '" + str3 + "' to link alias '" + str4 + "' to");
                }
            }
        }
        if (file != null) {
            System.out.println("Writing ZoneInfoMap");
            File file2 = new File(file, "ZoneInfoMap");
            if (!file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file2));
            try {
                TreeMap treeMap3 = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                treeMap3.putAll(treeMap);
                writeZoneInfoMap(dataOutputStream, treeMap3);
            } finally {
                dataOutputStream.close();
            }
        }
        return treeMap;
    }

    /* JADX INFO: finally extract failed */
    private void writeZone(File file, DateTimeZoneBuilder dateTimeZoneBuilder, DateTimeZone dateTimeZone) throws IOException {
        if (ZoneInfoLogger.verbose()) {
            System.out.println("Writing " + dateTimeZone.getID());
        }
        File file2 = new File(file, dateTimeZone.getID());
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            dateTimeZoneBuilder.writeTo(dateTimeZone.getID(), (OutputStream) fileOutputStream);
            fileOutputStream.close();
            FileInputStream fileInputStream = new FileInputStream(file2);
            DateTimeZone readFrom = DateTimeZoneBuilder.readFrom((InputStream) fileInputStream, dateTimeZone.getID());
            fileInputStream.close();
            if (!dateTimeZone.equals(readFrom)) {
                System.out.println("*e* Error in " + dateTimeZone.getID() + ": Didn't read properly from file");
            }
        } catch (Throwable th) {
            fileOutputStream.close();
            throw th;
        }
    }

    public void parseDataFile(BufferedReader bufferedReader, boolean z) throws IOException {
        Zone zone = null;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                String trim = readLine.trim();
                if (!(trim.length() == 0 || trim.charAt(0) == '#')) {
                    int indexOf = readLine.indexOf(35);
                    if (indexOf >= 0) {
                        readLine = readLine.substring(0, indexOf);
                    }
                    StringTokenizer stringTokenizer = new StringTokenizer(readLine, " \t");
                    if (!Character.isWhitespace(readLine.charAt(0)) || !stringTokenizer.hasMoreTokens()) {
                        if (zone != null) {
                            this.iZones.add(zone);
                        }
                        if (stringTokenizer.hasMoreTokens()) {
                            String nextToken = stringTokenizer.nextToken();
                            if (nextToken.equalsIgnoreCase("Rule")) {
                                Rule rule = new Rule(stringTokenizer);
                                RuleSet ruleSet = this.iRuleSets.get(rule.iName);
                                if (ruleSet == null) {
                                    this.iRuleSets.put(rule.iName, new RuleSet(rule));
                                } else {
                                    ruleSet.addRule(rule);
                                }
                                zone = null;
                            } else if (nextToken.equalsIgnoreCase("Zone")) {
                                if (stringTokenizer.countTokens() < 4) {
                                    throw new IllegalArgumentException("Attempting to create a Zone from an incomplete tokenizer");
                                }
                                zone = new Zone(stringTokenizer);
                            } else if (nextToken.equalsIgnoreCase("Link")) {
                                String nextToken2 = stringTokenizer.nextToken();
                                String nextToken3 = stringTokenizer.nextToken();
                                if (z || nextToken3.equals("US/Pacific-New") || nextToken3.startsWith("Etc/") || nextToken3.equals("GMT")) {
                                    this.iBackLinks.add(nextToken2);
                                    this.iBackLinks.add(nextToken3);
                                } else {
                                    this.iGoodLinks.add(nextToken2);
                                    this.iGoodLinks.add(nextToken3);
                                }
                                zone = null;
                            } else {
                                System.out.println("Unknown line: " + readLine);
                            }
                        }
                        zone = null;
                    } else if (zone != null) {
                        zone.chain(stringTokenizer);
                    }
                }
            } else if (zone != null) {
                this.iZones.add(zone);
                return;
            } else {
                return;
            }
        }
    }

    static class DateTimeOfYear {
        public final boolean iAdvanceDayOfWeek;
        public final int iDayOfMonth;
        public final int iDayOfWeek;
        public final int iMillisOfDay;
        public final int iMonthOfYear;
        public final char iZoneChar;

        DateTimeOfYear() {
            this.iMonthOfYear = 1;
            this.iDayOfMonth = 1;
            this.iDayOfWeek = 0;
            this.iAdvanceDayOfWeek = false;
            this.iMillisOfDay = 0;
            this.iZoneChar = 'w';
        }

        DateTimeOfYear(StringTokenizer stringTokenizer) {
            char c;
            int i;
            int i2;
            int i3;
            int i4;
            boolean z;
            boolean z2 = true;
            boolean z3 = false;
            if (stringTokenizer.hasMoreTokens()) {
                int parseMonth = ZoneInfoCompiler.parseMonth(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    String nextToken = stringTokenizer.nextToken();
                    if (nextToken.startsWith("last")) {
                        i = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(4));
                        i2 = -1;
                        z = false;
                    } else {
                        try {
                            i = 0;
                            i2 = Integer.parseInt(nextToken);
                            z = false;
                        } catch (NumberFormatException e) {
                            int indexOf = nextToken.indexOf(SimpleComparison.GREATER_THAN_EQUAL_TO_OPERATION);
                            if (indexOf > 0) {
                                i2 = Integer.parseInt(nextToken.substring(indexOf + 2));
                                i = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(0, indexOf));
                                z = true;
                            } else {
                                int indexOf2 = nextToken.indexOf(SimpleComparison.LESS_THAN_EQUAL_TO_OPERATION);
                                if (indexOf2 > 0) {
                                    i2 = Integer.parseInt(nextToken.substring(indexOf2 + 2));
                                    i = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(0, indexOf2));
                                    z = false;
                                } else {
                                    throw new IllegalArgumentException(nextToken);
                                }
                            }
                        }
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        String nextToken2 = stringTokenizer.nextToken();
                        char parseZoneChar = ZoneInfoCompiler.parseZoneChar(nextToken2.charAt(nextToken2.length() - 1));
                        if (!nextToken2.equals("24:00")) {
                            i4 = ZoneInfoCompiler.parseTime(nextToken2);
                            i3 = parseMonth;
                            z3 = z;
                            c = parseZoneChar;
                        } else if (parseMonth == 12 && i2 == 31) {
                            i4 = ZoneInfoCompiler.parseTime("23:59:59.999");
                            i3 = parseMonth;
                            z3 = z;
                            c = parseZoneChar;
                        } else {
                            LocalDate plusMonths = i2 == -1 ? new LocalDate(2001, parseMonth, 1).plusMonths(1) : new LocalDate(2001, parseMonth, i2).plusDays(1);
                            z2 = (i2 == -1 || i == 0) ? false : z2;
                            int monthOfYear = plusMonths.getMonthOfYear();
                            i2 = plusMonths.getDayOfMonth();
                            i = i != 0 ? (((i - 1) + 1) % 7) + 1 : i;
                            i3 = monthOfYear;
                            c = parseZoneChar;
                            z3 = z2;
                            i4 = 0;
                        }
                    } else {
                        i4 = 0;
                        i3 = parseMonth;
                        z3 = z;
                        c = 'w';
                    }
                } else {
                    c = 'w';
                    i = 0;
                    i2 = 1;
                    i3 = parseMonth;
                    i4 = 0;
                }
            } else {
                c = 'w';
                i = 0;
                i2 = 1;
                i3 = 1;
                i4 = 0;
            }
            this.iMonthOfYear = i3;
            this.iDayOfMonth = i2;
            this.iDayOfWeek = i;
            this.iAdvanceDayOfWeek = z3;
            this.iMillisOfDay = i4;
            this.iZoneChar = c;
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str, int i, int i2, int i3) {
            dateTimeZoneBuilder.addRecurringSavings(str, i, i2, i3, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public void addCutover(DateTimeZoneBuilder dateTimeZoneBuilder, int i) {
            dateTimeZoneBuilder.addCutover(i, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public String toString() {
            return "MonthOfYear: " + this.iMonthOfYear + "\n" + "DayOfMonth: " + this.iDayOfMonth + "\n" + "DayOfWeek: " + this.iDayOfWeek + "\n" + "AdvanceDayOfWeek: " + this.iAdvanceDayOfWeek + "\n" + "MillisOfDay: " + this.iMillisOfDay + "\n" + "ZoneChar: " + this.iZoneChar + "\n";
        }
    }

    private static class Rule {
        public final DateTimeOfYear iDateTimeOfYear;
        public final int iFromYear;
        public final String iLetterS;
        public final String iName;
        public final int iSaveMillis;
        public final int iToYear;
        public final String iType;

        Rule(StringTokenizer stringTokenizer) {
            if (stringTokenizer.countTokens() < 6) {
                throw new IllegalArgumentException("Attempting to create a Rule from an incomplete tokenizer");
            }
            this.iName = stringTokenizer.nextToken().intern();
            this.iFromYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), 0);
            this.iToYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), this.iFromYear);
            if (this.iToYear < this.iFromYear) {
                throw new IllegalArgumentException();
            }
            this.iType = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iDateTimeOfYear = new DateTimeOfYear(stringTokenizer);
            this.iSaveMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iLetterS = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str) {
            DateTimeZoneBuilder dateTimeZoneBuilder2 = dateTimeZoneBuilder;
            this.iDateTimeOfYear.addRecurring(dateTimeZoneBuilder2, formatName(str), this.iSaveMillis, this.iFromYear, this.iToYear);
        }

        private String formatName(String str) {
            String str2;
            int indexOf = str.indexOf(47);
            if (indexOf <= 0) {
                int indexOf2 = str.indexOf("%s");
                if (indexOf2 < 0) {
                    return str;
                }
                String substring = str.substring(0, indexOf2);
                String substring2 = str.substring(indexOf2 + 2);
                if (this.iLetterS == null) {
                    str2 = substring.concat(substring2);
                } else {
                    str2 = substring + this.iLetterS + substring2;
                }
                return str2.intern();
            } else if (this.iSaveMillis == 0) {
                return str.substring(0, indexOf).intern();
            } else {
                return str.substring(indexOf + 1).intern();
            }
        }

        public String toString() {
            return "[Rule]\nName: " + this.iName + "\n" + "FromYear: " + this.iFromYear + "\n" + "ToYear: " + this.iToYear + "\n" + "Type: " + this.iType + "\n" + this.iDateTimeOfYear + "SaveMillis: " + this.iSaveMillis + "\n" + "LetterS: " + this.iLetterS + "\n";
        }
    }

    private static class RuleSet {
        private List<Rule> iRules = new ArrayList();

        RuleSet(Rule rule) {
            this.iRules.add(rule);
        }

        /* access modifiers changed from: package-private */
        public void addRule(Rule rule) {
            if (!rule.iName.equals(this.iRules.get(0).iName)) {
                throw new IllegalArgumentException("Rule name mismatch");
            }
            this.iRules.add(rule);
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.iRules.size()) {
                    this.iRules.get(i2).addRecurring(dateTimeZoneBuilder, str);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private static class Zone {
        public final String iFormat;
        public final String iName;
        private Zone iNext;
        public final int iOffsetMillis;
        public final String iRules;
        public final DateTimeOfYear iUntilDateTimeOfYear;
        public final int iUntilYear;

        Zone(StringTokenizer stringTokenizer) {
            this(stringTokenizer.nextToken(), stringTokenizer);
        }

        private Zone(String str, StringTokenizer stringTokenizer) {
            this.iName = str.intern();
            this.iOffsetMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iRules = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iFormat = stringTokenizer.nextToken().intern();
            int i = Integer.MAX_VALUE;
            DateTimeOfYear startOfYear = ZoneInfoCompiler.getStartOfYear();
            if (stringTokenizer.hasMoreTokens()) {
                i = Integer.parseInt(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    startOfYear = new DateTimeOfYear(stringTokenizer);
                }
            }
            this.iUntilYear = i;
            this.iUntilDateTimeOfYear = startOfYear;
        }

        /* access modifiers changed from: package-private */
        public void chain(StringTokenizer stringTokenizer) {
            if (this.iNext != null) {
                this.iNext.chain(stringTokenizer);
            } else {
                this.iNext = new Zone(this.iName, stringTokenizer);
            }
        }

        public void addToBuilder(DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            addToBuilder(this, dateTimeZoneBuilder, map);
        }

        private static void addToBuilder(Zone zone, DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            while (zone != null) {
                dateTimeZoneBuilder.setStandardOffset(zone.iOffsetMillis);
                if (zone.iRules == null) {
                    dateTimeZoneBuilder.setFixedSavings(zone.iFormat, 0);
                } else {
                    try {
                        dateTimeZoneBuilder.setFixedSavings(zone.iFormat, ZoneInfoCompiler.parseTime(zone.iRules));
                    } catch (Exception e) {
                        RuleSet ruleSet = map.get(zone.iRules);
                        if (ruleSet == null) {
                            throw new IllegalArgumentException("Rules not found: " + zone.iRules);
                        }
                        ruleSet.addRecurring(dateTimeZoneBuilder, zone.iFormat);
                    }
                }
                if (zone.iUntilYear != Integer.MAX_VALUE) {
                    zone.iUntilDateTimeOfYear.addCutover(dateTimeZoneBuilder, zone.iUntilYear);
                    zone = zone.iNext;
                } else {
                    return;
                }
            }
        }

        public String toString() {
            String str = "[Zone]\nName: " + this.iName + "\n" + "OffsetMillis: " + this.iOffsetMillis + "\n" + "Rules: " + this.iRules + "\n" + "Format: " + this.iFormat + "\n" + "UntilYear: " + this.iUntilYear + "\n" + this.iUntilDateTimeOfYear;
            return this.iNext == null ? str : str + "...\n" + this.iNext.toString();
        }
    }
}
