package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class LocalLog implements Log {
    private static final Log.Level DEFAULT_LEVEL = Log.Level.DEBUG;
    public static final String LOCAL_LOG_FILE_PROPERTY = "com.j256.ormlite.logger.file";
    public static final String LOCAL_LOG_LEVEL_PROPERTY = "com.j256.ormlite.logger.level";
    public static final String LOCAL_LOG_PROPERTIES_FILE = "/ormliteLocalLog.properties";
    private static final List<PatternLevel> classLevels = readLevelResourceFile(LocalLog.class.getResourceAsStream(LOCAL_LOG_PROPERTIES_FILE));
    private static final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
        /* access modifiers changed from: protected */
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        }
    };
    private static PrintStream printStream;
    private final String className;
    private final Log.Level level;

    static {
        openLogFile(System.getProperty(LOCAL_LOG_FILE_PROPERTY));
    }

    public LocalLog(String className2) {
        this.className = LoggerFactory.getSimpleClassName(className2);
        Log.Level level2 = null;
        if (classLevels != null) {
            for (PatternLevel patternLevel : classLevels) {
                if (patternLevel.pattern.matcher(className2).matches() && (level2 == null || patternLevel.level.ordinal() < level2.ordinal())) {
                    level2 = patternLevel.level;
                }
            }
        }
        if (level2 == null) {
            String levelName = System.getProperty(LOCAL_LOG_LEVEL_PROPERTY);
            if (levelName == null) {
                level2 = DEFAULT_LEVEL;
            } else {
                try {
                    level2 = Log.Level.valueOf(levelName.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Level '" + levelName + "' was not found", e);
                }
            }
        }
        this.level = level2;
    }

    public static void openLogFile(String logPath) {
        if (logPath == null) {
            printStream = System.out;
            return;
        }
        try {
            printStream = new PrintStream(new File(logPath));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Log file " + logPath + " was not found", e);
        }
    }

    public boolean isLevelEnabled(Log.Level level2) {
        return this.level.isEnabled(level2);
    }

    public void log(Log.Level level2, String msg) {
        printMessage(level2, msg, (Throwable) null);
    }

    public void log(Log.Level level2, String msg, Throwable throwable) {
        printMessage(level2, msg, throwable);
    }

    /* access modifiers changed from: package-private */
    public void flush() {
        printStream.flush();
    }

    static List<PatternLevel> readLevelResourceFile(InputStream stream) {
        List<PatternLevel> levels = null;
        if (stream != null) {
            try {
                levels = configureClassLevels(stream);
                try {
                    stream.close();
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                System.err.println("IO exception reading the log properties file '/ormliteLocalLog.properties': " + e2);
                try {
                    stream.close();
                } catch (IOException e3) {
                }
            } catch (Throwable th) {
                try {
                    stream.close();
                } catch (IOException e4) {
                }
                throw th;
            }
        }
        return levels;
    }

    private static List<PatternLevel> configureClassLevels(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<PatternLevel> list = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return list;
            }
            if (!(line.length() == 0 || line.charAt(0) == '#')) {
                String[] parts = line.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (parts.length != 2) {
                    System.err.println("Line is not in the format of 'pattern = level': " + line);
                } else {
                    try {
                        list.add(new PatternLevel(Pattern.compile(parts[0].trim()), Log.Level.valueOf(parts[1].trim())));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Level '" + parts[1] + "' was not found");
                    }
                }
            }
        }
    }

    private void printMessage(Log.Level level2, String message, Throwable throwable) {
        if (isLevelEnabled(level2)) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(dateFormatThreadLocal.get().format(new Date()));
            sb.append(" [").append(level2.name()).append("] ");
            sb.append(this.className).append(' ');
            sb.append(message);
            printStream.println(sb.toString());
            if (throwable != null) {
                throwable.printStackTrace(printStream);
            }
        }
    }

    private static class PatternLevel {
        Log.Level level;
        Pattern pattern;

        public PatternLevel(Pattern pattern2, Log.Level level2) {
            this.pattern = pattern2;
            this.level = level2;
        }
    }
}
