package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.config.ACRAConfiguration;
import org.acra.model.Element;
import org.acra.util.PackageManagerWrapper;

public final class CrashReportDataFactory {
    private final Calendar appStartDate;
    private final ACRAConfiguration config;
    private final Context context;
    private final Map<String, String> customParameters = new LinkedHashMap();
    private final Element initialConfiguration;
    private final SharedPreferences prefs;

    public CrashReportDataFactory(@NonNull Context context2, @NonNull ACRAConfiguration config2, @NonNull SharedPreferences prefs2, @NonNull Calendar appStartDate2, @NonNull Element initialConfiguration2) {
        this.context = context2;
        this.config = config2;
        this.prefs = prefs2;
        this.appStartDate = appStartDate2;
        this.initialConfiguration = initialConfiguration2;
    }

    public String putCustomData(@NonNull String key, String value) {
        return this.customParameters.put(key, value);
    }

    public String removeCustomData(@NonNull String key) {
        return this.customParameters.remove(key);
    }

    public void clearCustomData() {
        this.customParameters.clear();
    }

    public String getCustomData(@NonNull String key) {
        return this.customParameters.get(key);
    }

    @NonNull
    public CrashReportData createCrashData(@NonNull ReportBuilder builder) {
        CrashReportData crashReportData = new CrashReportData();
        try {
            Set<ReportField> crashReportFields = this.config.reportContent();
            for (Collector collector : getCollectorsOrdered()) {
                try {
                    for (ReportField reportField : collector.canCollect()) {
                        try {
                            if (collector.shouldCollect(crashReportFields, reportField, builder)) {
                                crashReportData.put(reportField, collector.collect(reportField, builder));
                            }
                        } catch (RuntimeException e) {
                            ACRA.log.e(ACRA.LOG_TAG, "Error while retrieving " + reportField.name() + " data", e);
                        }
                    }
                } catch (RuntimeException e2) {
                    ACRA.log.e(ACRA.LOG_TAG, "Error in collector " + collector.getClass().getSimpleName(), e2);
                }
            }
        } catch (RuntimeException e3) {
            ACRA.log.e(ACRA.LOG_TAG, "Error while retrieving crash data", e3);
        }
        return crashReportData;
    }

    private List<Collector> getCollectorsOrdered() {
        List<Collector> collectors = new ArrayList<>();
        PackageManagerWrapper pm = new PackageManagerWrapper(this.context);
        collectors.add(new LogCatCollector(this.config, pm));
        collectors.add(new DropBoxCollector(this.context, this.config, pm));
        collectors.add(new StacktraceCollector());
        collectors.add(new TimeCollector(this.appStartDate));
        collectors.add(new SimpleValuesCollector(this.context));
        collectors.add(new ConfigurationCollector(this.context, this.initialConfiguration));
        collectors.add(new MemoryInfoCollector());
        collectors.add(new ReflectionCollector(this.context, this.config));
        collectors.add(new DisplayManagerCollector(this.context));
        collectors.add(new CustomDataCollector(this.customParameters));
        collectors.add(new SharedPreferencesCollector(this.context, this.config, this.prefs));
        collectors.add(new DeviceFeaturesCollector(this.context));
        collectors.add(new SettingsCollector(this.context, this.config));
        collectors.add(new PackageManagerCollector(pm));
        collectors.add(new DeviceIdCollector(this.context, pm, this.prefs));
        collectors.add(new LogFileCollector(this.context, this.config));
        collectors.add(new MediaCodecListCollector());
        collectors.add(new ThreadCollector());
        return collectors;
    }
}
