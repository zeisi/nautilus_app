package org.acra.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.android.internal.util.Predicate;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.collections.BoundedLinkedList;

public final class IOUtils {
    private static final Predicate<String> DEFAULT_FILTER = new Predicate<String>() {
        public boolean apply(String s) {
            return true;
        }
    };
    private static final int NO_LIMIT = -1;
    private static final int READ_TIMEOUT = 3000;

    private IOUtils() {
    }

    public static void safeClose(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    @NonNull
    public static String streamToString(@NonNull InputStream input) throws IOException {
        return streamToString(input, DEFAULT_FILTER, -1);
    }

    @NonNull
    public static String streamToString(@NonNull InputStream input, Predicate<String> filter) throws IOException {
        return streamToString(input, filter, -1);
    }

    @NonNull
    public static String streamToString(@NonNull InputStream input, int limit) throws IOException {
        return streamToString(input, DEFAULT_FILTER, limit);
    }

    @NonNull
    public static String streamToString(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {
        List<String> buffer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(input), 8192);
        if (limit == -1) {
            try {
                buffer = new LinkedList<>();
            } catch (Throwable th) {
                safeClose(reader);
                throw th;
            }
        } else {
            buffer = new BoundedLinkedList<>(limit);
        }
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                String join = TextUtils.join("\n", buffer);
                safeClose(reader);
                return join;
            } else if (filter.apply(line)) {
                buffer.add(line);
            }
        }
    }

    @NonNull
    public static String streamToStringNonBlockingRead(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {
        List<String> buffer;
        String line;
        NonBlockingBufferedReader nonBlockingReader = new NonBlockingBufferedReader(new BufferedReader(new InputStreamReader(input), 8192));
        if (limit == -1) {
            try {
                buffer = new LinkedList<>();
            } catch (InterruptedException e) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "Interrupted while reading stream", e);
                }
            } catch (Throwable th) {
                nonBlockingReader.close();
                throw th;
            }
        } else {
            buffer = new BoundedLinkedList<>(limit);
        }
        long end = System.currentTimeMillis() + 3000;
        while (System.currentTimeMillis() < end && (line = nonBlockingReader.readLine()) != null) {
            if (filter.apply(line)) {
                buffer.add(line);
            }
        }
        String join = TextUtils.join("\n", buffer);
        nonBlockingReader.close();
        return join;
    }

    public static void deleteReport(@NonNull File file) {
        if (!file.delete()) {
            ACRA.log.w(ACRA.LOG_TAG, "Could not delete error report : " + file);
        }
    }

    public static void writeStringToFile(@NonNull File file, @NonNull String content) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), ACRAConstants.UTF8);
        try {
            writer.write(content);
            writer.flush();
        } finally {
            safeClose(writer);
        }
    }
}
