package com.ua.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class Log {
    private static final String LOG_TAG = "sdk";
    private static boolean debugEnabled = true;

    private Log() {
    }

    public static void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void exception(String msg, String[] errors) {
        android.util.Log.e(LOG_TAG, msg, (Throwable) null);
        if (errors != null) {
            for (String error : errors) {
                android.util.Log.e(LOG_TAG, error, (Throwable) null);
            }
        }
    }

    public static void exception(String msg, List<String> errors) {
        android.util.Log.e(LOG_TAG, msg, (Throwable) null);
        if (errors != null) {
            for (String error : errors) {
                android.util.Log.e(LOG_TAG, error, (Throwable) null);
            }
        }
    }

    public static void exception(String msg, Throwable tr) {
        android.util.Log.e(LOG_TAG, msg, tr);
    }

    public static void exception(Throwable tr) {
        android.util.Log.e(LOG_TAG, "", tr);
    }

    public static void exception(String msg) {
        android.util.Log.e(LOG_TAG, msg);
    }

    public static void debug(String msg) {
        debug(msg, (Throwable) null);
    }

    public static void debug(Throwable tr) {
        debug((String) null, tr);
    }

    public static void debug(String msg, Throwable tr) {
        if (debugEnabled) {
            if (msg != null && msg.length() > 0) {
                android.util.Log.d(LOG_TAG, msg + "\n");
            }
            if (tr != null) {
                android.util.Log.d(LOG_TAG, tr.getMessage());
                StackTraceElement[] arr$ = tr.getStackTrace();
                int len$ = arr$.length;
                for (int i$ = 0; i$ < len$; i$++) {
                    android.util.Log.d(LOG_TAG, "\t" + arr$[i$].toString());
                }
            }
        }
    }

    public static InputStream debug(InputStream is) {
        return debug((String) null, is);
    }

    public static InputStream debug(String title, InputStream is) {
        if (!debugEnabled || is == null) {
            return is;
        }
        byte[] data = new byte[2048];
        ByteArrayOutputStream copy = new ByteArrayOutputStream();
        while (true) {
            try {
                int chunk = is.read(data);
                if (-1 == chunk) {
                    break;
                }
                copy.write(data, 0, chunk);
            } catch (IOException e) {
                exception((Throwable) e);
            }
        }
        is.close();
        InputStream myStream = new ByteArrayInputStream(copy.toByteArray());
        StringBuffer stringBuffer = new StringBuffer();
        if (title == null) {
            title = "";
        }
        stringBuffer.append(title);
        while (true) {
            try {
                int chunk2 = myStream.read(data);
                if (chunk2 == -1) {
                    break;
                }
                stringBuffer.append(new String(data, 0, chunk2));
            } catch (IOException e2) {
                exception((Throwable) e2);
            }
        }
        myStream.close();
        debug(stringBuffer.toString());
        return new ByteArrayInputStream(copy.toByteArray());
    }
}
