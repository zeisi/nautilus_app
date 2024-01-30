package com.nautilus.omni.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.buddybuild.sdk.BuddyBuild;
import com.nautilus.omni.R;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.components.DaggerAppComponent;
import com.nautilus.omni.dependencyinjection.modules.AppModule;
import com.nautilus.omni.model.dto.OmniData;
import java.util.Timer;
import java.util.TimerTask;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class OmniApplication extends Application {
    public static final String TAG = OmniApplication.class.getSimpleName();
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;
    private boolean isWorking;
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    private AppComponent mAppComponent;
    private OmniData mCurrentOmniData;
    public boolean wasInBackground;

    public void onCreate() {
        super.onCreate();
        BuddyBuild.setup((Application) this);
        buildAppComponent();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Lato-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
        this.isWorking = false;
    }

    private void buildAppComponent() {
        this.mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                OmniApplication.this.wasInBackground = true;
            }
        };
        this.mActivityTransitionTimer.schedule(this.mActivityTransitionTimerTask, 2000);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }
        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }
        this.wasInBackground = false;
    }

    public String getAppVersion() {
        StringBuilder version = new StringBuilder();
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version.append(packageInfo.versionName).append(" (").append(packageInfo.versionCode).append(")");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error on getAppVersion", e);
        }
        return version.toString();
    }

    public void onForeground() {
        this.isWorking = false;
    }

    public OmniData getCurrentOmniData() {
        return this.mCurrentOmniData;
    }

    public void setCurrentOmniData(OmniData mCurrentOmniData2) {
        this.mCurrentOmniData = mCurrentOmniData2;
    }
}
