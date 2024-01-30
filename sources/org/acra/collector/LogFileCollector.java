package org.acra.collector;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.file.Directory;
import org.acra.model.Element;
import org.acra.model.StringElement;
import org.acra.util.IOUtils;

final class LogFileCollector extends Collector {
    private final ACRAConfiguration config;
    private final Context context;

    LogFileCollector(Context context2, ACRAConfiguration config2) {
        super(ReportField.APPLICATION_LOG);
        this.context = context2;
        this.config = config2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        try {
            return new StringElement(IOUtils.streamToString(getStream(this.config.applicationLogFileDir(), this.config.applicationLogFile()), this.config.applicationLogFileLines()));
        } catch (IOException e) {
            return ACRAConstants.NOT_AVAILABLE;
        }
    }

    @NonNull
    private InputStream getStream(@NonNull Directory directory, @NonNull String fileName) {
        File file = directory.getFile(this.context, fileName);
        if (!file.exists()) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Log file '" + file.getPath() + "' does not exist");
            }
        } else if (file.isDirectory()) {
            ACRA.log.e(ACRA.LOG_TAG, "Log file '" + file.getPath() + "' is a directory");
        } else if (!file.canRead()) {
            ACRA.log.e(ACRA.LOG_TAG, "Log file '" + file.getPath() + "' can't be read");
        } else {
            try {
                return new FileInputStream(file);
            } catch (IOException e) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not open stream for log file '" + file.getPath() + "'");
            }
        }
        return new ByteArrayInputStream(new byte[0]);
    }
}
