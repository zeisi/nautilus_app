package org.acra.collector;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;

final class CustomDataCollector extends Collector {
    private final Map<String, String> customParameters;

    CustomDataCollector(Map<String, String> customParameters2) {
        super(ReportField.CUSTOM_DATA);
        this.customParameters = customParameters2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        return createCustomInfoElement(reportBuilder.getCustomData());
    }

    @NonNull
    private Element createCustomInfoElement(@Nullable Map<String, String> reportCustomData) {
        Map<String, String> params = this.customParameters;
        if (reportCustomData != null) {
            Map<String, String> params2 = new HashMap<>(params);
            params2.putAll(reportCustomData);
            params = params2;
        }
        return new ComplexElement((Map<String, ?>) params);
    }
}
