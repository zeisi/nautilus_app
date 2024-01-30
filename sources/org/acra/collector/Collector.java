package org.acra.collector;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import java.util.Set;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;

abstract class Collector {
    private final ReportField[] reportFields;

    /* access modifiers changed from: package-private */
    @NonNull
    public abstract Element collect(ReportField reportField, ReportBuilder reportBuilder);

    Collector(@Size(min = 1) @NonNull ReportField... reportFields2) {
        this.reportFields = reportFields2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final ReportField[] canCollect() {
        return this.reportFields;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return crashReportFields.contains(collect);
    }
}
