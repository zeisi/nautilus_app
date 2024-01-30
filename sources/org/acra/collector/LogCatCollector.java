package org.acra.collector;

import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.internal.util.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.Element;
import org.acra.model.StringElement;
import org.acra.util.IOUtils;
import org.acra.util.PackageManagerWrapper;

final class LogCatCollector extends Collector {
    private final ACRAConfiguration config;
    private final PackageManagerWrapper pm;

    LogCatCollector(ACRAConfiguration config2, PackageManagerWrapper pm2) {
        super(ReportField.LOGCAT, ReportField.EVENTSLOG, ReportField.RADIOLOG);
        this.config = config2;
        this.pm = pm2;
    }

    private Element collectLogCat(@Nullable String bufferName) {
        int tailCount;
        int myPid = Process.myPid();
        final String myPidStr = (!this.config.logcatFilterByPid() || myPid <= 0) ? null : Integer.toString(myPid) + "):";
        List<String> commandLine = new ArrayList<>();
        commandLine.add("logcat");
        if (bufferName != null) {
            commandLine.add("-b");
            commandLine.add(bufferName);
        }
        List<String> logcatArgumentsList = this.config.logcatArguments();
        int tailIndex = logcatArgumentsList.indexOf("-t");
        if (tailIndex <= -1 || tailIndex >= logcatArgumentsList.size()) {
            tailCount = -1;
        } else {
            tailCount = Integer.parseInt(logcatArgumentsList.get(tailIndex + 1));
        }
        commandLine.addAll(logcatArgumentsList);
        try {
            Process process = new ProcessBuilder(new String[0]).command(commandLine).redirectErrorStream(true).start();
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Retrieving logcat output...");
            }
            Element logcat = new StringElement(streamToString(process.getInputStream(), new Predicate<String>() {
                public boolean apply(String s) {
                    return myPidStr == null || s.contains(myPidStr);
                }
            }, tailCount));
            process.destroy();
            return logcat;
        } catch (IOException e) {
            ACRA.log.e(ACRA.LOG_TAG, "LogCatCollector.collectLogCat could not retrieve data.", e);
            return ACRAConstants.NOT_AVAILABLE;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder) && (this.pm.hasPermission("android.permission.READ_LOGS") || Build.VERSION.SDK_INT >= 16);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        String bufferName = null;
        switch (reportField) {
            case LOGCAT:
                bufferName = null;
                break;
            case EVENTSLOG:
                bufferName = "events";
                break;
            case RADIOLOG:
                bufferName = "radio";
                break;
        }
        return collectLogCat(bufferName);
    }

    @NonNull
    private String streamToString(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {
        if (this.config.nonBlockingReadForLogcat()) {
            return IOUtils.streamToStringNonBlockingRead(input, filter, limit);
        }
        return IOUtils.streamToString(input, filter, limit);
    }
}
