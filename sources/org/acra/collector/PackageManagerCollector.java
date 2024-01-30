package org.acra.collector;

import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;
import org.acra.model.NumberElement;
import org.acra.model.StringElement;
import org.acra.util.PackageManagerWrapper;

final class PackageManagerCollector extends Collector {
    private final PackageManagerWrapper pm;

    PackageManagerCollector(PackageManagerWrapper pm2) {
        super(ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE);
        this.pm = pm2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        PackageInfo info = this.pm.getPackageInfo();
        if (info != null) {
            switch (reportField) {
                case APP_VERSION_NAME:
                    return new StringElement(info.versionName);
                case APP_VERSION_CODE:
                    return new NumberElement(Integer.valueOf(info.versionCode));
            }
        }
        return ACRAConstants.NOT_AVAILABLE;
    }
}
