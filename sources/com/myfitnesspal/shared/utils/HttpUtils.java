package com.myfitnesspal.shared.utils;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class HttpUtils {
    public static final boolean makeGetRequest(String url) {
        return makeRequest("GET", url);
    }

    private static final boolean makeRequest(String method, String urlString) {
        if (Strings.isEmpty(method)) {
            Ln.d("No method provided, bail", new Object[0]);
            return false;
        } else if (Strings.isEmpty(urlString)) {
            Ln.d("Empty URL string, bail", new Object[0]);
            return false;
        } else {
            Ln.d("makeRequest: %s %s", method, urlString);
            try {
                try {
                    final HttpURLConnection rc = (HttpURLConnection) new URL(urlString).openConnection();
                    rc.setRequestMethod(method);
                    AsyncTask<HttpURLConnection, Integer, String> task = new AsyncTask<HttpURLConnection, Integer, String>() {
                        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: java.lang.Object[]} */
                        /* access modifiers changed from: protected */
                        /* JADX WARNING: Multi-variable type inference failed */
                        /* JADX WARNING: Removed duplicated region for block: B:14:0x004e  */
                        /* JADX WARNING: Removed duplicated region for block: B:31:0x0081  */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public java.lang.String doInBackground(java.net.HttpURLConnection... r10) {
                            /*
                                r9 = this;
                                r7 = 0
                                java.lang.String r6 = "Making request..."
                                java.lang.Object[] r7 = new java.lang.Object[r7]
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)
                                r2 = 0
                                java.net.HttpURLConnection r6 = r1     // Catch:{ IOException -> 0x0095 }
                                r6.connect()     // Catch:{ IOException -> 0x0095 }
                                java.lang.String r6 = "Making request..."
                                r7 = 0
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0095 }
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)     // Catch:{ IOException -> 0x0095 }
                                java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0095 }
                                java.net.HttpURLConnection r6 = r1     // Catch:{ IOException -> 0x0095 }
                                java.io.InputStream r6 = r6.getInputStream()     // Catch:{ IOException -> 0x0095 }
                                r3.<init>(r6)     // Catch:{ IOException -> 0x0095 }
                                java.lang.String r6 = "Making request..."
                                r7 = 0
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                r5.<init>()     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                r6 = 1024(0x400, float:1.435E-42)
                                char[] r0 = new char[r6]     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                            L_0x0032:
                                int r6 = r3.read(r0)     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                if (r6 < 0) goto L_0x005c
                                java.lang.String r6 = "read %s"
                                r7 = 1
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                r8 = 0
                                r7[r8] = r0     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                r5.append(r0)     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                goto L_0x0032
                            L_0x0047:
                                r1 = move-exception
                                r2 = r3
                            L_0x0049:
                                com.myfitnesspal.shared.utils.Ln.e(r1)     // Catch:{ all -> 0x007e }
                                if (r2 == 0) goto L_0x0059
                                java.lang.String r6 = "Closing stream"
                                r7 = 0
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0079 }
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)     // Catch:{ IOException -> 0x0079 }
                                r2.close()     // Catch:{ IOException -> 0x0079 }
                            L_0x0059:
                                java.lang.String r4 = ""
                            L_0x005b:
                                return r4
                            L_0x005c:
                                java.lang.String r4 = r5.toString()     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                java.net.HttpURLConnection r6 = r1     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                r6.disconnect()     // Catch:{ IOException -> 0x0047, all -> 0x0092 }
                                if (r3 == 0) goto L_0x0072
                                java.lang.String r6 = "Closing stream"
                                r7 = 0
                                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0074 }
                                com.myfitnesspal.shared.utils.Ln.d(r6, r7)     // Catch:{ IOException -> 0x0074 }
                                r3.close()     // Catch:{ IOException -> 0x0074 }
                            L_0x0072:
                                r2 = r3
                                goto L_0x005b
                            L_0x0074:
                                r1 = move-exception
                                com.myfitnesspal.shared.utils.Ln.e(r1)
                                goto L_0x0072
                            L_0x0079:
                                r1 = move-exception
                                com.myfitnesspal.shared.utils.Ln.e(r1)
                                goto L_0x0059
                            L_0x007e:
                                r6 = move-exception
                            L_0x007f:
                                if (r2 == 0) goto L_0x008c
                                java.lang.String r7 = "Closing stream"
                                r8 = 0
                                java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ IOException -> 0x008d }
                                com.myfitnesspal.shared.utils.Ln.d(r7, r8)     // Catch:{ IOException -> 0x008d }
                                r2.close()     // Catch:{ IOException -> 0x008d }
                            L_0x008c:
                                throw r6
                            L_0x008d:
                                r1 = move-exception
                                com.myfitnesspal.shared.utils.Ln.e(r1)
                                goto L_0x008c
                            L_0x0092:
                                r6 = move-exception
                                r2 = r3
                                goto L_0x007f
                            L_0x0095:
                                r1 = move-exception
                                goto L_0x0049
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.myfitnesspal.shared.utils.HttpUtils.AnonymousClass1.doInBackground(java.net.HttpURLConnection[]):java.lang.String");
                        }
                    };
                    rc.setDoOutput(true);
                    rc.setDoInput(true);
                    rc.setConnectTimeout(15000);
                    task.execute(new HttpURLConnection[]{rc});
                    return true;
                } catch (IOException e) {
                    Ln.e(e);
                    return false;
                }
            } catch (MalformedURLException e2) {
                Ln.e(e2);
                return false;
            }
        }
    }
}
