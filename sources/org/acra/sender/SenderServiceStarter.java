package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import org.acra.ACRA;
import org.acra.config.ACRAConfiguration;

public class SenderServiceStarter {
    private final ACRAConfiguration config;
    private final Context context;

    public SenderServiceStarter(@NonNull Context context2, @NonNull ACRAConfiguration config2) {
        this.context = context2;
        this.config = config2;
    }

    public void startService(boolean onlySendSilentReports, boolean approveReportsFirst) {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "About to start SenderService");
        }
        Intent intent = new Intent(this.context, SenderService.class);
        intent.putExtra(SenderService.EXTRA_ONLY_SEND_SILENT_REPORTS, onlySendSilentReports);
        intent.putExtra(SenderService.EXTRA_APPROVE_REPORTS_FIRST, approveReportsFirst);
        intent.putExtra(SenderService.EXTRA_ACRA_CONFIG, this.config);
        this.context.startService(intent);
    }
}
