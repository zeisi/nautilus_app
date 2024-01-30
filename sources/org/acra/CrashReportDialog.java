package org.acra;

import android.os.Bundle;

@Deprecated
public final class CrashReportDialog extends org.acra.dialog.CrashReportDialog {
    /* access modifiers changed from: protected */
    public void buildAndShowDialog(Bundle savedInstanceState) {
        ACRA.log.w(ACRA.LOG_TAG, "org.acra.CrashReportDialog has been deprecated. Please use org.acra.dialog.CrashReportDialog instead");
        super.buildAndShowDialog(savedInstanceState);
    }
}
