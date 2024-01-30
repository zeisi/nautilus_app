package com.buddybuild.sdk.crashreports;

import android.app.Application;
import android.util.Log;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.properties.BuddyBuildProperties;
import com.buddybuild.sdk.utils.SystemUtils;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.config.ConfigurationBuilder;
import org.json.JSONObject;

public final class CrashReports {
    public static void attach(Application application) {
        try {
            ACRA.init(application, ((ConfigurationBuilder) ((ConfigurationBuilder) ((ConfigurationBuilder) new ConfigurationBuilder(application).setFormUri(BuddyBuildProperties.BUDDYBUILD_ENDPOINT + Constants.CRASHREPORT_PARTIAL_URL)).setBuildConfigClass(application.getApplicationContext().getClass().getPackage().getName().getClass())).setLogcatArguments("-t", "500", "-v", "time")).build());
            ErrorReporter reporter = ACRA.getErrorReporter();
            reporter.putCustomData("BUDDYBUILD_APP_ID", BuddyBuildProperties.BUDDYBUILD_APP_ID);
            reporter.putCustomData("BUDDYBUILD_BUILD_ID", BuddyBuildProperties.BUDDYBUILD_BUILD_ID);
            reporter.putCustomData("BUDDYBUILD_APPLICATION_VARIANT_NAME", BuddyBuildProperties.BUDDYBUILD_APPLICATION_VARIANT_NAME);
            reporter.putCustomData("BUDDYBUILD_ENDPOINT", BuddyBuildProperties.BUDDYBUILD_ENDPOINT);
            reporter.putCustomData("BUDDYBUILD_ENVIRONMENT", BuddyBuildProperties.BUDDYBUILD_ENVIRONMENT);
            reporter.putCustomData("BUDDYBUILD_EMAIL", BuddyBuildProperties.BUDDYBUILD_EMAIL);
            reporter.putCustomData("BUDDYBUILD_SYSTEM", new JSONObject(SystemUtils.getSystemInfo(application)).toString());
        } catch (Exception e) {
            Log.e(Constants.BUDDYBUILD_TAG, "Failed to attach ACRA crash reporting: ");
            e.printStackTrace();
        }
    }
}
