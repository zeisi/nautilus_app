package com.nautilus.omni.util.bleutil;

import com.nautilus.omni.util.Util;
import java.nio.ByteBuffer;

public class BLEUtil {
    private static final String TAG = Util.class.getSimpleName();

    public static ByteBuffer getMFGData(byte[] advertisedData) {
        int offset = 0;
        ByteBuffer adData = null;
        while (true) {
            if (offset < advertisedData.length - 2) {
                int offset2 = offset + 1;
                byte len = advertisedData[offset];
                if (len != 0) {
                    int offset3 = offset2 + 1;
                    switch (advertisedData[offset2]) {
                        case -1:
                            adData = ByteBuffer.allocate(len);
                            adData.put(advertisedData, offset3, len);
                            offset = offset3 + (len - 1);
                            break;
                        default:
                            offset = offset3 + (len - 1);
                            break;
                    }
                } else {
                    int i = offset2;
                }
            }
        }
        return adData;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: byte} */
    /* JADX WARNING: CFG modification limit reached, blocks count: 133 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:13|14|15|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0049, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        android.util.Log.d(TAG, r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0082, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0083, code lost:
        r7 = r7 + 15;
        r6 = r6 - 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0087, code lost:
        throw r14;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<java.util.UUID> parseUUIDs(byte[] r18) {
        /*
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r7 = 0
        L_0x0006:
            r0 = r18
            int r14 = r0.length
            int r14 = r14 + -2
            if (r7 >= r14) goto L_0x0014
            int r10 = r7 + 1
            byte r6 = r18[r7]
            if (r6 != 0) goto L_0x0015
            r7 = r10
        L_0x0014:
            return r13
        L_0x0015:
            int r7 = r10 + 1
            byte r11 = r18[r10]
            switch(r11) {
                case 2: goto L_0x008d;
                case 3: goto L_0x008d;
                case 4: goto L_0x001c;
                case 5: goto L_0x001c;
                case 6: goto L_0x008b;
                case 7: goto L_0x008b;
                default: goto L_0x001c;
            }
        L_0x001c:
            int r14 = r6 + -1
            int r7 = r7 + r14
            goto L_0x0006
        L_0x0020:
            r14 = 1
            if (r6 <= r14) goto L_0x0088
            int r7 = r10 + 1
            byte r12 = r18[r10]
            int r10 = r7 + 1
            byte r14 = r18[r7]
            int r14 = r14 << 8
            int r12 = r12 + r14
            int r6 = r6 + -2
            java.lang.String r14 = "%08x-0000-1000-8000-00805f9b34fb"
            r15 = 1
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r16 = 0
            java.lang.Integer r17 = java.lang.Integer.valueOf(r12)
            r15[r16] = r17
            java.lang.String r14 = java.lang.String.format(r14, r15)
            java.util.UUID r14 = java.util.UUID.fromString(r14)
            r13.add(r14)
            goto L_0x0020
        L_0x0049:
            r3 = move-exception
            java.lang.String r14 = TAG     // Catch:{ all -> 0x0082 }
            java.lang.String r15 = r3.toString()     // Catch:{ all -> 0x0082 }
            android.util.Log.d(r14, r15)     // Catch:{ all -> 0x0082 }
            int r7 = r7 + 15
            int r6 = r6 + -16
            r10 = r7
        L_0x0058:
            r14 = 16
            if (r6 < r14) goto L_0x0088
            int r7 = r10 + 1
            r14 = 16
            r0 = r18
            java.nio.ByteBuffer r14 = java.nio.ByteBuffer.wrap(r0, r10, r14)     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            java.nio.ByteOrder r15 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            java.nio.ByteBuffer r2 = r14.order(r15)     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            long r8 = r2.getLong()     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            long r4 = r2.getLong()     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            java.util.UUID r14 = new java.util.UUID     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            r14.<init>(r4, r8)     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            r13.add(r14)     // Catch:{ IndexOutOfBoundsException -> 0x0049 }
            int r7 = r7 + 15
            int r6 = r6 + -16
            r10 = r7
            goto L_0x0058
        L_0x0082:
            r14 = move-exception
            int r7 = r7 + 15
            int r6 = r6 + -16
            throw r14
        L_0x0088:
            r7 = r10
            goto L_0x0006
        L_0x008b:
            r10 = r7
            goto L_0x0058
        L_0x008d:
            r10 = r7
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nautilus.omni.util.bleutil.BLEUtil.parseUUIDs(byte[]):java.util.List");
    }
}
