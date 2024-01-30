package com.myfitnesspal.shared.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.nautilus.omni.util.Constants;

public class Ln {
    protected static BaseConfig config = new BaseConfig();
    protected static Print print = new Print();

    public interface Config {
        int getLoggingLevel();

        void setLoggingLevel(int i);
    }

    private Ln() {
    }

    public static int v(Throwable t) {
        if (config.minimumLogLevel <= 2) {
            return print.println(2, Log.getStackTraceString(t));
        }
        return 0;
    }

    public static int v(Object s1, Object... args) {
        String message;
        if (config.minimumLogLevel > 2) {
            return 0;
        }
        String s = Strings.toString(s1);
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return print.println(2, message);
    }

    public static int v(Throwable throwable, Object s1, Object... args) {
        if (config.minimumLogLevel > 2) {
            return 0;
        }
        String s = Strings.toString(s1);
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return print.println(2, sb.append(s).append(10).append(Log.getStackTraceString(throwable)).toString());
    }

    public static int d(Throwable t) {
        if (config.minimumLogLevel <= 3) {
            return print.println(3, Log.getStackTraceString(t));
        }
        return 0;
    }

    public static int d(Object s1, Object... args) {
        String message;
        if (config.minimumLogLevel > 3) {
            return 0;
        }
        String s = Strings.toString(s1);
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return print.println(3, message);
    }

    public static int d(Throwable throwable, Object s1, Object... args) {
        if (config.minimumLogLevel > 3) {
            return 0;
        }
        String s = Strings.toString(s1);
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return print.println(3, sb.append(s).append(10).append(Log.getStackTraceString(throwable)).toString());
    }

    public static int i(Throwable t) {
        if (config.minimumLogLevel <= 4) {
            return print.println(4, Log.getStackTraceString(t));
        }
        return 0;
    }

    public static int i(Object s1, Object... args) {
        String message;
        if (config.minimumLogLevel > 4) {
            return 0;
        }
        String s = Strings.toString(s1);
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return print.println(4, message);
    }

    public static int i(Throwable throwable, Object s1, Object... args) {
        if (config.minimumLogLevel > 4) {
            return 0;
        }
        String s = Strings.toString(s1);
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return print.println(4, sb.append(s).append(10).append(Log.getStackTraceString(throwable)).toString());
    }

    public static int w(Throwable t) {
        if (config.minimumLogLevel <= 5) {
            return print.println(5, Log.getStackTraceString(t));
        }
        return 0;
    }

    public static int w(Object s1, Object... args) {
        String message;
        if (config.minimumLogLevel > 5) {
            return 0;
        }
        String s = Strings.toString(s1);
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return print.println(5, message);
    }

    public static int w(Throwable throwable, Object s1, Object... args) {
        if (config.minimumLogLevel > 5) {
            return 0;
        }
        String s = Strings.toString(s1);
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return print.println(5, sb.append(s).append(10).append(Log.getStackTraceString(throwable)).toString());
    }

    public static int e(Throwable t) {
        if (config.minimumLogLevel <= 6) {
            return print.println(6, Log.getStackTraceString(t));
        }
        return 0;
    }

    public static int e(Object s1, Object... args) {
        String message;
        if (config.minimumLogLevel > 6) {
            return 0;
        }
        String s = Strings.toString(s1);
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return print.println(6, message);
    }

    public static int e(Throwable throwable, Object s1, Object... args) {
        if (config.minimumLogLevel > 6) {
            return 0;
        }
        String s = Strings.toString(s1);
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return print.println(6, sb.append(s).append(10).append(Log.getStackTraceString(throwable)).toString());
    }

    public static boolean isDebugEnabled() {
        return config.minimumLogLevel <= 3;
    }

    public static boolean isVerboseEnabled() {
        return config.minimumLogLevel <= 2;
    }

    public static Config getConfig() {
        return config;
    }

    public static class BaseConfig implements Config {
        protected int minimumLogLevel = 2;
        protected String packageName = "";
        protected String scope = "";

        protected BaseConfig() {
        }

        public BaseConfig(Application context) {
            int i = 2;
            int flags = 0;
            try {
                this.packageName = context.getPackageName();
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(this.packageName, 0);
                this.minimumLogLevel = ((info != null ? info.flags : flags) & 2) == 0 ? 4 : i;
                this.scope = this.packageName != null ? this.packageName.toUpperCase() : "";
                Ln.d("Configuring Logging, minimum log level is %s", Ln.logLevelToString(this.minimumLogLevel));
            } catch (Exception e) {
                Log.e(this.packageName, "Error configuring logger for package '" + this.packageName + "'", e);
            }
        }

        public int getLoggingLevel() {
            return this.minimumLogLevel;
        }

        public void setLoggingLevel(int level) {
            this.minimumLogLevel = level;
        }
    }

    public static String logLevelToString(int loglevel) {
        switch (loglevel) {
            case 2:
                return "VERBOSE";
            case 3:
                return "DEBUG";
            case 4:
                return "INFO";
            case 5:
                return "WARN";
            case 6:
                return "ERROR";
            case 7:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    public static class Print {
        public int println(int priority, String msg) {
            return Log.println(priority, getScope(), processMessage(msg));
        }

        /* access modifiers changed from: protected */
        public String processMessage(String msg) {
            if (Ln.config.minimumLogLevel > 3) {
                return msg;
            }
            return String.format("%s %s %s", new Object[]{"MFP", Thread.currentThread().getName(), msg});
        }

        protected static String getScope() {
            if (Ln.config.minimumLogLevel > 3) {
                return Ln.config.scope;
            }
            StackTraceElement trace = Thread.currentThread().getStackTrace()[5];
            return Ln.config.scope + "/" + trace.getFileName() + Constants.COLON_SEPARATOR + trace.getLineNumber();
        }
    }
}
