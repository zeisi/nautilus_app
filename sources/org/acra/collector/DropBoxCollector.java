package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.os.DropBoxManager;
import android.support.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.acra.util.PackageManagerWrapper;

final class DropBoxCollector extends Collector {
    private static final String[] SYSTEM_TAGS = {"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};
    private final ACRAConfiguration config;
    private final Context context;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault());
    private final PackageManagerWrapper pm;

    DropBoxCollector(Context context2, ACRAConfiguration config2, PackageManagerWrapper pm2) {
        super(ReportField.DROPBOX);
        this.context = context2;
        this.config = config2;
        this.pm = pm2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        try {
            DropBoxManager dropbox = (DropBoxManager) this.context.getSystemService("dropbox");
            Calendar calendar = Calendar.getInstance();
            calendar.roll(12, -this.config.dropboxCollectionMinutes());
            long time = calendar.getTimeInMillis();
            this.dateFormat.format(calendar.getTime());
            List<String> tags = new ArrayList<>();
            if (this.config.includeDropBoxSystemTags()) {
                tags.addAll(Arrays.asList(SYSTEM_TAGS));
            }
            List<String> additionalTags = this.config.additionalDropBoxTags();
            if (!additionalTags.isEmpty()) {
                tags.addAll(additionalTags);
            }
            if (tags.isEmpty()) {
                return ACRAConstants.NOT_AVAILABLE;
            }
            ComplexElement dropboxContent = new ComplexElement();
            for (String tag : tags) {
                StringBuilder builder = new StringBuilder();
                DropBoxManager.Entry entry = dropbox.getNextEntry(tag, time);
                if (entry == null) {
                    builder.append("Nothing.").append(10);
                } else {
                    while (entry != null) {
                        long msec = entry.getTimeMillis();
                        calendar.setTimeInMillis(msec);
                        builder.append('@').append(this.dateFormat.format(calendar.getTime())).append(10);
                        String text = entry.getText(500);
                        if (text != null) {
                            builder.append("Text: ").append(text).append(10);
                        } else {
                            builder.append("Not Text!").append(10);
                        }
                        entry.close();
                        entry = dropbox.getNextEntry(tag, msec);
                    }
                    dropboxContent.put(tag, builder.toString());
                }
            }
            return dropboxContent;
        } catch (Exception e) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "DropBoxManager not available.");
            }
            return ACRAConstants.NOT_AVAILABLE;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder) && (this.pm.hasPermission("android.permission.READ_LOGS") || Build.VERSION.SDK_INT >= 16);
    }
}
