package org.acra.collector;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.support.annotation.NonNull;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;

final class DeviceFeaturesCollector extends Collector {
    private final Context context;

    DeviceFeaturesCollector(Context context2) {
        super(ReportField.DEVICE_FEATURES);
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        ComplexElement result = new ComplexElement();
        try {
            for (FeatureInfo feature : this.context.getPackageManager().getSystemAvailableFeatures()) {
                String featureName = feature.name;
                if (featureName != null) {
                    result.put(featureName, true);
                } else {
                    result.put("glEsVersion", feature.getGlEsVersion());
                }
            }
        } catch (Throwable e) {
            ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve DeviceFeatures for " + this.context.getPackageName(), e);
        }
        return result;
    }
}
