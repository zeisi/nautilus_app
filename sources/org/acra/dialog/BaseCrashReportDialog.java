package org.acra.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.file.BulkReportDeleter;
import org.acra.file.CrashReportPersister;
import org.acra.sender.SenderServiceStarter;
import org.acra.util.ToastSender;
import org.json.JSONException;

public abstract class BaseCrashReportDialog extends FragmentActivity {
    private ACRAConfiguration config;
    private Throwable exception;
    private File reportFile;

    /* access modifiers changed from: protected */
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        preInit(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "CrashReportDialog extras=" + getIntent().getExtras());
        }
        Serializable sConfig = getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_CONFIG);
        Serializable sReportFile = getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_FILE);
        Serializable sException = getIntent().getSerializableExtra(ACRAConstants.EXTRA_REPORT_EXCEPTION);
        if (getIntent().getBooleanExtra(ACRAConstants.EXTRA_FORCE_CANCEL, false)) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Forced reports deletion.");
            }
            cancelReports();
            finish();
        } else if (!(sConfig instanceof ACRAConfiguration) || !(sReportFile instanceof File) || (!(sException instanceof Throwable) && sException != null)) {
            ACRA.log.w(ACRA.LOG_TAG, "Illegal or incomplete call of BaseCrashReportDialog.");
            finish();
        } else {
            this.config = (ACRAConfiguration) sConfig;
            this.reportFile = (File) sReportFile;
            this.exception = (Throwable) sException;
            init(savedInstanceState);
        }
    }

    /* access modifiers changed from: protected */
    public void preInit(@Nullable Bundle savedInstanceState) {
    }

    /* access modifiers changed from: protected */
    public void init(@Nullable Bundle savedInstanceState) {
    }

    /* access modifiers changed from: protected */
    public final void cancelReports() {
        new BulkReportDeleter(getApplicationContext()).deleteReports(false, 0);
    }

    /* access modifiers changed from: protected */
    public final void sendCrash(@Nullable String comment, @Nullable String userEmail) {
        CrashReportPersister persister = new CrashReportPersister();
        try {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Add user comment to " + this.reportFile);
            }
            CrashReportData crashData = persister.load(this.reportFile);
            ReportField reportField = ReportField.USER_COMMENT;
            if (comment == null) {
                comment = "";
            }
            crashData.putString(reportField, comment);
            ReportField reportField2 = ReportField.USER_EMAIL;
            if (userEmail == null) {
                userEmail = "";
            }
            crashData.putString(reportField2, userEmail);
            persister.store(crashData, this.reportFile);
        } catch (IOException e) {
            ACRA.log.w(ACRA.LOG_TAG, "User comment not added: ", e);
        } catch (JSONException e2) {
            ACRA.log.w(ACRA.LOG_TAG, "User comment not added: ", e2);
        }
        new SenderServiceStarter(getApplicationContext(), this.config).startService(false, true);
        int toastId = this.config.resDialogOkToast();
        if (toastId != 0) {
            ToastSender.sendToast(getApplicationContext(), toastId, 1);
        }
    }

    /* access modifiers changed from: protected */
    public final ACRAConfiguration getConfig() {
        return this.config;
    }

    /* access modifiers changed from: protected */
    public final Throwable getException() {
        return this.exception;
    }
}
