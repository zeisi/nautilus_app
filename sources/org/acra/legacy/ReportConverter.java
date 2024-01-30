package org.acra.legacy;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.file.CrashReportPersister;
import org.acra.file.ReportLocator;
import org.acra.util.IOUtils;

class ReportConverter {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private final Context context;

    ReportConverter(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    public void convert() {
        ACRA.log.i(ACRA.LOG_TAG, "Converting unsent ACRA reports to json");
        ReportLocator locator = new ReportLocator(this.context);
        CrashReportPersister persister = new CrashReportPersister();
        List<File> reportFiles = new ArrayList<>();
        reportFiles.addAll(Arrays.asList(locator.getUnapprovedReports()));
        reportFiles.addAll(Arrays.asList(locator.getApprovedReports()));
        int converted = 0;
        for (File report : reportFiles) {
            InputStream in = null;
            try {
                InputStream in2 = new BufferedInputStream(new FileInputStream(report), 8192);
                try {
                    CrashReportData data = legacyLoad(new InputStreamReader(in2, "ISO8859-1"));
                    if (!data.containsKey(ReportField.REPORT_ID) || !data.containsKey(ReportField.USER_CRASH_DATE)) {
                        IOUtils.deleteReport(report);
                    } else {
                        persister.store(data, report);
                        converted++;
                    }
                    IOUtils.safeClose(in2);
                    InputStream inputStream = in2;
                } catch (Throwable th) {
                    th = th;
                    in = in2;
                    IOUtils.safeClose(in);
                    throw th;
                }
            } catch (Throwable th2) {
                e = th2;
                try {
                    persister.load(report);
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Tried to convert already converted report file " + report.getPath() + ". Ignoring");
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
                IOUtils.safeClose(in);
            }
        }
        ACRA.log.i(ACRA.LOG_TAG, "Converted " + converted + " unsent reports");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized org.acra.collector.CrashReportData legacyLoad(@android.support.annotation.NonNull java.io.Reader r28) throws java.io.IOException {
        /*
            r27 = this;
            monitor-enter(r27)
            r17 = 0
            r23 = 0
            r6 = 0
            r25 = 40
            r0 = r25
            char[] r5 = new char[r0]     // Catch:{ all -> 0x004a }
            r20 = 0
            r16 = -1
            r13 = 1
            org.acra.collector.CrashReportData r7 = new org.acra.collector.CrashReportData     // Catch:{ all -> 0x004a }
            r7.<init>()     // Catch:{ all -> 0x004a }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ all -> 0x004a }
            r25 = 8192(0x2000, float:1.14794E-41)
            r0 = r28
            r1 = r25
            r4.<init>(r0, r1)     // Catch:{ all -> 0x004a }
            r21 = r20
        L_0x0023:
            int r14 = r4.read()     // Catch:{ all -> 0x0043 }
            r25 = -1
            r0 = r25
            if (r14 != r0) goto L_0x004d
            r25 = 2
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x0220
            r25 = 4
            r0 = r25
            if (r6 > r0) goto L_0x0220
            java.lang.IllegalArgumentException r25 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0043 }
            java.lang.String r26 = "luni.08"
            r25.<init>(r26)     // Catch:{ all -> 0x0043 }
            throw r25     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r25 = move-exception
            r20 = r21
        L_0x0046:
            org.acra.util.IOUtils.safeClose(r4)     // Catch:{ all -> 0x004a }
            throw r25     // Catch:{ all -> 0x004a }
        L_0x004a:
            r25 = move-exception
            monitor-exit(r27)
            throw r25
        L_0x004d:
            char r0 = (char) r14
            r19 = r0
            int r0 = r5.length     // Catch:{ all -> 0x0043 }
            r25 = r0
            r0 = r21
            r1 = r25
            if (r0 != r1) goto L_0x0075
            int r0 = r5.length     // Catch:{ all -> 0x0043 }
            r25 = r0
            int r25 = r25 * 2
            r0 = r25
            char[] r0 = new char[r0]     // Catch:{ all -> 0x0043 }
            r18 = r0
            r25 = 0
            r26 = 0
            r0 = r25
            r1 = r18
            r2 = r26
            r3 = r21
            java.lang.System.arraycopy(r5, r0, r1, r2, r3)     // Catch:{ all -> 0x0043 }
            r5 = r18
        L_0x0075:
            r25 = 2
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x00c4
            r25 = 16
            r0 = r19
            r1 = r25
            int r8 = java.lang.Character.digit(r0, r1)     // Catch:{ all -> 0x0043 }
            if (r8 < 0) goto L_0x00b4
            int r25 = r23 << 4
            int r23 = r25 + r8
            int r6 = r6 + 1
            r25 = 4
            r0 = r25
            if (r6 < r0) goto L_0x0023
        L_0x0095:
            r17 = 0
            int r20 = r21 + 1
            r0 = r23
            char r0 = (char) r0
            r25 = r0
            r5[r21] = r25     // Catch:{ all -> 0x02c2 }
            r25 = 10
            r0 = r19
            r1 = r25
            if (r0 == r1) goto L_0x00c2
            r25 = 133(0x85, float:1.86E-43)
            r0 = r19
            r1 = r25
            if (r0 == r1) goto L_0x00c2
            r21 = r20
            goto L_0x0023
        L_0x00b4:
            r25 = 4
            r0 = r25
            if (r6 > r0) goto L_0x0095
            java.lang.IllegalArgumentException r25 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0043 }
            java.lang.String r26 = "luni.09"
            r25.<init>(r26)     // Catch:{ all -> 0x0043 }
            throw r25     // Catch:{ all -> 0x0043 }
        L_0x00c2:
            r21 = r20
        L_0x00c4:
            r25 = 1
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x0104
            r17 = 0
            switch(r19) {
                case 10: goto L_0x00ea;
                case 13: goto L_0x00e6;
                case 98: goto L_0x00ee;
                case 102: goto L_0x00f1;
                case 110: goto L_0x00f4;
                case 114: goto L_0x00f7;
                case 116: goto L_0x00fa;
                case 117: goto L_0x00fd;
                case 133: goto L_0x00ea;
                default: goto L_0x00d1;
            }
        L_0x00d1:
            r13 = 0
            r25 = 4
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x00de
            r16 = r21
            r17 = 0
        L_0x00de:
            int r20 = r21 + 1
            r5[r21] = r19     // Catch:{ all -> 0x02c2 }
            r21 = r20
            goto L_0x0023
        L_0x00e6:
            r17 = 3
            goto L_0x0023
        L_0x00ea:
            r17 = 5
            goto L_0x0023
        L_0x00ee:
            r19 = 8
            goto L_0x00d1
        L_0x00f1:
            r19 = 12
            goto L_0x00d1
        L_0x00f4:
            r19 = 10
            goto L_0x00d1
        L_0x00f7:
            r19 = 13
            goto L_0x00d1
        L_0x00fa:
            r19 = 9
            goto L_0x00d1
        L_0x00fd:
            r17 = 2
            r6 = 0
            r23 = r6
            goto L_0x0023
        L_0x0104:
            switch(r19) {
                case 10: goto L_0x015c;
                case 13: goto L_0x0168;
                case 33: goto L_0x0133;
                case 35: goto L_0x0133;
                case 58: goto L_0x01fe;
                case 61: goto L_0x01fe;
                case 92: goto L_0x01f0;
                case 133: goto L_0x0168;
                default: goto L_0x0107;
            }
        L_0x0107:
            boolean r25 = java.lang.Character.isWhitespace(r19)     // Catch:{ all -> 0x0043 }
            if (r25 == 0) goto L_0x020c
            r25 = 3
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x0117
            r17 = 5
        L_0x0117:
            if (r21 == 0) goto L_0x0023
            r0 = r21
            r1 = r16
            if (r0 == r1) goto L_0x0023
            r25 = 5
            r0 = r17
            r1 = r25
            if (r0 == r1) goto L_0x0023
            r25 = -1
            r0 = r16
            r1 = r25
            if (r0 != r1) goto L_0x020c
            r17 = 4
            goto L_0x0023
        L_0x0133:
            if (r13 == 0) goto L_0x0107
        L_0x0135:
            int r14 = r4.read()     // Catch:{ all -> 0x0043 }
            r25 = -1
            r0 = r25
            if (r14 == r0) goto L_0x0023
            char r0 = (char) r14     // Catch:{ all -> 0x0043 }
            r19 = r0
            r25 = 13
            r0 = r19
            r1 = r25
            if (r0 == r1) goto L_0x0023
            r25 = 10
            r0 = r19
            r1 = r25
            if (r0 == r1) goto L_0x0023
            r25 = 133(0x85, float:1.86E-43)
            r0 = r19
            r1 = r25
            if (r0 != r1) goto L_0x0135
            goto L_0x0023
        L_0x015c:
            r25 = 3
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x0168
            r17 = 5
            goto L_0x0023
        L_0x0168:
            r17 = 0
            r13 = 1
            if (r21 > 0) goto L_0x0171
            if (r21 != 0) goto L_0x01ac
            if (r16 != 0) goto L_0x01ac
        L_0x0171:
            r25 = -1
            r0 = r16
            r1 = r25
            if (r0 != r1) goto L_0x017b
            r16 = r21
        L_0x017b:
            java.lang.String r22 = new java.lang.String     // Catch:{ all -> 0x0043 }
            r25 = 0
            r0 = r22
            r1 = r25
            r2 = r21
            r0.<init>(r5, r1, r2)     // Catch:{ all -> 0x0043 }
            r0 = r22
            r1 = r16
            java.lang.String r12 = r0.substring(r1)     // Catch:{ all -> 0x0043 }
            org.acra.model.ComplexElement r11 = new org.acra.model.ComplexElement     // Catch:{ JSONException -> 0x01b4 }
            r11.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x01b4 }
        L_0x0195:
            java.lang.Class<org.acra.ReportField> r25 = org.acra.ReportField.class
            r26 = 0
            r0 = r22
            r1 = r26
            r2 = r16
            java.lang.String r26 = r0.substring(r1, r2)     // Catch:{ all -> 0x0043 }
            java.lang.Enum r25 = java.lang.Enum.valueOf(r25, r26)     // Catch:{ all -> 0x0043 }
            r0 = r25
            r7.put(r0, r11)     // Catch:{ all -> 0x0043 }
        L_0x01ac:
            r16 = -1
            r20 = 0
            r21 = r20
            goto L_0x0023
        L_0x01b4:
            r9 = move-exception
            org.acra.model.NumberElement r11 = new org.acra.model.NumberElement     // Catch:{ NumberFormatException -> 0x01c1 }
            java.lang.Double r25 = java.lang.Double.valueOf(r12)     // Catch:{ NumberFormatException -> 0x01c1 }
            r0 = r25
            r11.<init>(r0)     // Catch:{ NumberFormatException -> 0x01c1 }
            goto L_0x0195
        L_0x01c1:
            r10 = move-exception
            java.lang.String r25 = "true"
            r0 = r25
            boolean r25 = r12.equals(r0)     // Catch:{ all -> 0x0043 }
            if (r25 == 0) goto L_0x01d6
            org.acra.model.BooleanElement r11 = new org.acra.model.BooleanElement     // Catch:{ all -> 0x0043 }
            r25 = 1
            r0 = r25
            r11.<init>(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x0195
        L_0x01d6:
            java.lang.String r25 = "false"
            r0 = r25
            boolean r25 = r12.equals(r0)     // Catch:{ all -> 0x0043 }
            if (r25 == 0) goto L_0x01ea
            org.acra.model.BooleanElement r11 = new org.acra.model.BooleanElement     // Catch:{ all -> 0x0043 }
            r25 = 0
            r0 = r25
            r11.<init>(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x0195
        L_0x01ea:
            org.acra.model.StringElement r11 = new org.acra.model.StringElement     // Catch:{ all -> 0x0043 }
            r11.<init>(r12)     // Catch:{ all -> 0x0043 }
            goto L_0x0195
        L_0x01f0:
            r25 = 4
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x01fa
            r16 = r21
        L_0x01fa:
            r17 = 1
            goto L_0x0023
        L_0x01fe:
            r25 = -1
            r0 = r16
            r1 = r25
            if (r0 != r1) goto L_0x0107
            r17 = 0
            r16 = r21
            goto L_0x0023
        L_0x020c:
            r25 = 5
            r0 = r17
            r1 = r25
            if (r0 == r1) goto L_0x021c
            r25 = 3
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x00d1
        L_0x021c:
            r17 = 0
            goto L_0x00d1
        L_0x0220:
            r25 = -1
            r0 = r16
            r1 = r25
            if (r0 != r1) goto L_0x022c
            if (r21 <= 0) goto L_0x022c
            r16 = r21
        L_0x022c:
            if (r16 < 0) goto L_0x0280
            java.lang.String r22 = new java.lang.String     // Catch:{ all -> 0x0043 }
            r25 = 0
            r0 = r22
            r1 = r25
            r2 = r21
            r0.<init>(r5, r1, r2)     // Catch:{ all -> 0x0043 }
            java.lang.Class<org.acra.ReportField> r25 = org.acra.ReportField.class
            r26 = 0
            r0 = r22
            r1 = r26
            r2 = r16
            java.lang.String r26 = r0.substring(r1, r2)     // Catch:{ all -> 0x0043 }
            java.lang.Enum r15 = java.lang.Enum.valueOf(r25, r26)     // Catch:{ all -> 0x0043 }
            org.acra.ReportField r15 = (org.acra.ReportField) r15     // Catch:{ all -> 0x0043 }
            r0 = r22
            r1 = r16
            java.lang.String r24 = r0.substring(r1)     // Catch:{ all -> 0x0043 }
            r25 = 1
            r0 = r17
            r1 = r25
            if (r0 != r1) goto L_0x0276
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ all -> 0x0043 }
            r25.<init>()     // Catch:{ all -> 0x0043 }
            r0 = r25
            r1 = r24
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ all -> 0x0043 }
            java.lang.String r26 = "\u0000"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ all -> 0x0043 }
            java.lang.String r24 = r25.toString()     // Catch:{ all -> 0x0043 }
        L_0x0276:
            org.acra.model.ComplexElement r11 = new org.acra.model.ComplexElement     // Catch:{ JSONException -> 0x0288 }
            r0 = r24
            r11.<init>((java.lang.String) r0)     // Catch:{ JSONException -> 0x0288 }
        L_0x027d:
            r7.put(r15, r11)     // Catch:{ all -> 0x0043 }
        L_0x0280:
            org.acra.util.IOUtils.safeClose(r28)     // Catch:{ all -> 0x0043 }
            org.acra.util.IOUtils.safeClose(r4)     // Catch:{ all -> 0x004a }
            monitor-exit(r27)
            return r7
        L_0x0288:
            r9 = move-exception
            org.acra.model.NumberElement r11 = new org.acra.model.NumberElement     // Catch:{ NumberFormatException -> 0x0295 }
            java.lang.Double r25 = java.lang.Double.valueOf(r24)     // Catch:{ NumberFormatException -> 0x0295 }
            r0 = r25
            r11.<init>(r0)     // Catch:{ NumberFormatException -> 0x0295 }
            goto L_0x027d
        L_0x0295:
            r10 = move-exception
            java.lang.String r25 = "true"
            boolean r25 = r24.equals(r25)     // Catch:{ all -> 0x0043 }
            if (r25 == 0) goto L_0x02a8
            org.acra.model.BooleanElement r11 = new org.acra.model.BooleanElement     // Catch:{ all -> 0x0043 }
            r25 = 1
            r0 = r25
            r11.<init>(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x027d
        L_0x02a8:
            java.lang.String r25 = "false"
            boolean r25 = r24.equals(r25)     // Catch:{ all -> 0x0043 }
            if (r25 == 0) goto L_0x02ba
            org.acra.model.BooleanElement r11 = new org.acra.model.BooleanElement     // Catch:{ all -> 0x0043 }
            r25 = 0
            r0 = r25
            r11.<init>(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x027d
        L_0x02ba:
            org.acra.model.StringElement r11 = new org.acra.model.StringElement     // Catch:{ all -> 0x0043 }
            r0 = r24
            r11.<init>(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x027d
        L_0x02c2:
            r25 = move-exception
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.legacy.ReportConverter.legacyLoad(java.io.Reader):org.acra.collector.CrashReportData");
    }
}
