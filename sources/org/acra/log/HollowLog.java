package org.acra.log;

import android.support.annotation.Nullable;

public final class HollowLog implements ACRALog {
    public int v(String tag, String msg) {
        return 0;
    }

    public int v(String tag, String msg, Throwable tr) {
        return 0;
    }

    public int d(String tag, String msg) {
        return 0;
    }

    public int d(String tag, String msg, Throwable tr) {
        return 0;
    }

    public int i(String tag, String msg) {
        return 0;
    }

    public int i(String tag, String msg, Throwable tr) {
        return 0;
    }

    public int w(String tag, String msg) {
        return 0;
    }

    public int w(String tag, String msg, Throwable tr) {
        return 0;
    }

    public int w(String tag, Throwable tr) {
        return 0;
    }

    public int e(String tag, String msg) {
        return 0;
    }

    public int e(String tag, String msg, Throwable tr) {
        return 0;
    }

    @Nullable
    public String getStackTraceString(Throwable tr) {
        return null;
    }
}
