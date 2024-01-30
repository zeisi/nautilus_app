package org.joda.time.tz;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.DateTimeZone;

public class ZoneInfoProvider implements Provider {
    private final File iFileDir;
    /* access modifiers changed from: private */
    public final ClassLoader iLoader;
    private final String iResourcePath;
    private final Set<String> iZoneInfoKeys;
    private final Map<String, Object> iZoneInfoMap;

    public ZoneInfoProvider(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("No file directory provided");
        } else if (!file.exists()) {
            throw new IOException("File directory doesn't exist: " + file);
        } else if (!file.isDirectory()) {
            throw new IOException("File doesn't refer to a directory: " + file);
        } else {
            this.iFileDir = file;
            this.iResourcePath = null;
            this.iLoader = null;
            this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
            this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet(this.iZoneInfoMap.keySet()));
        }
    }

    public ZoneInfoProvider(String str) throws IOException {
        this(str, (ClassLoader) null, false);
    }

    public ZoneInfoProvider(String str, ClassLoader classLoader) throws IOException {
        this(str, classLoader, true);
    }

    private ZoneInfoProvider(String str, ClassLoader classLoader, boolean z) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("No resource path provided");
        }
        str = !str.endsWith("/") ? str + '/' : str;
        this.iFileDir = null;
        this.iResourcePath = str;
        if (classLoader == null && !z) {
            classLoader = getClass().getClassLoader();
        }
        this.iLoader = classLoader;
        this.iZoneInfoMap = loadZoneInfoMap(openResource("ZoneInfoMap"));
        this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet(this.iZoneInfoMap.keySet()));
    }

    public DateTimeZone getZone(String str) {
        if (str == null) {
            return null;
        }
        Object obj = this.iZoneInfoMap.get(str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof SoftReference) {
            DateTimeZone dateTimeZone = (DateTimeZone) ((SoftReference) obj).get();
            if (dateTimeZone == null) {
                return loadZoneData(str);
            }
            return dateTimeZone;
        } else if (str.equals(obj)) {
            return loadZoneData(str);
        } else {
            return getZone((String) obj);
        }
    }

    public Set<String> getAvailableIDs() {
        return this.iZoneInfoKeys;
    }

    /* access modifiers changed from: protected */
    public void uncaughtException(Exception exc) {
        exc.printStackTrace();
    }

    private InputStream openResource(String str) throws IOException {
        if (this.iFileDir != null) {
            return new FileInputStream(new File(this.iFileDir, str));
        }
        final String concat = this.iResourcePath.concat(str);
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
                if (ZoneInfoProvider.this.iLoader != null) {
                    return ZoneInfoProvider.this.iLoader.getResourceAsStream(concat);
                }
                return ClassLoader.getSystemResourceAsStream(concat);
            }
        });
        if (inputStream != null) {
            return inputStream;
        }
        throw new IOException(new StringBuilder(40).append("Resource not found: \"").append(concat).append("\" ClassLoader: ").append(this.iLoader != null ? this.iLoader.toString() : "system").toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002e A[SYNTHETIC, Splitter:B:19:0x002e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.joda.time.DateTimeZone loadZoneData(java.lang.String r6) {
        /*
            r5 = this;
            r1 = 0
            java.io.InputStream r2 = r5.openResource(r6)     // Catch:{ IOException -> 0x0019, all -> 0x002a }
            org.joda.time.DateTimeZone r0 = org.joda.time.tz.DateTimeZoneBuilder.readFrom((java.io.InputStream) r2, (java.lang.String) r6)     // Catch:{ IOException -> 0x003a }
            java.util.Map<java.lang.String, java.lang.Object> r3 = r5.iZoneInfoMap     // Catch:{ IOException -> 0x003a }
            java.lang.ref.SoftReference r4 = new java.lang.ref.SoftReference     // Catch:{ IOException -> 0x003a }
            r4.<init>(r0)     // Catch:{ IOException -> 0x003a }
            r3.put(r6, r4)     // Catch:{ IOException -> 0x003a }
            if (r2 == 0) goto L_0x0018
            r2.close()     // Catch:{ IOException -> 0x0032 }
        L_0x0018:
            return r0
        L_0x0019:
            r0 = move-exception
            r2 = r1
        L_0x001b:
            r5.uncaughtException(r0)     // Catch:{ all -> 0x0038 }
            java.util.Map<java.lang.String, java.lang.Object> r0 = r5.iZoneInfoMap     // Catch:{ all -> 0x0038 }
            r0.remove(r6)     // Catch:{ all -> 0x0038 }
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ IOException -> 0x0034 }
        L_0x0028:
            r0 = r1
            goto L_0x0018
        L_0x002a:
            r0 = move-exception
            r2 = r1
        L_0x002c:
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ IOException -> 0x0036 }
        L_0x0031:
            throw r0
        L_0x0032:
            r1 = move-exception
            goto L_0x0018
        L_0x0034:
            r0 = move-exception
            goto L_0x0028
        L_0x0036:
            r1 = move-exception
            goto L_0x0031
        L_0x0038:
            r0 = move-exception
            goto L_0x002c
        L_0x003a:
            r0 = move-exception
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.ZoneInfoProvider.loadZoneData(java.lang.String):org.joda.time.DateTimeZone");
    }

    private static Map<String, Object> loadZoneInfoMap(InputStream inputStream) throws IOException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            readZoneInfoMap(dataInputStream, concurrentHashMap);
            concurrentHashMap.put("UTC", new SoftReference(DateTimeZone.UTC));
            return concurrentHashMap;
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
            }
        }
    }

    private static void readZoneInfoMap(DataInputStream dataInputStream, Map<String, Object> map) throws IOException {
        int i = 0;
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        String[] strArr = new String[readUnsignedShort];
        for (int i2 = 0; i2 < readUnsignedShort; i2++) {
            strArr[i2] = dataInputStream.readUTF().intern();
        }
        int readUnsignedShort2 = dataInputStream.readUnsignedShort();
        while (i < readUnsignedShort2) {
            try {
                map.put(strArr[dataInputStream.readUnsignedShort()], strArr[dataInputStream.readUnsignedShort()]);
                i++;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IOException("Corrupt zone info map");
            }
        }
    }
}
