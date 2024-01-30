package com.buddybuild.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import com.buddybuild.sdk.autoupdater.AutoUpdater;
import com.buddybuild.sdk.crashreports.CrashReports;
import com.buddybuild.sdk.demo.DemoActivity;
import com.buddybuild.sdk.demo.DemoUtils;
import com.buddybuild.sdk.events.OpenAppEvent;
import com.buddybuild.sdk.feedback.FeedbackActivity;
import com.buddybuild.sdk.feedback.FeedbackUtils;
import com.buddybuild.sdk.feedback.ScreenshotCache;
import com.buddybuild.sdk.properties.BuddyBuildProperties;
import com.buddybuild.sdk.utils.ActivityLifecycleManager;
import com.buddybuild.sdk.utils.ContextUtils;
import com.squareup.seismic.ShakeDetector;
import java.lang.ref.WeakReference;

public class BuddyBuild {
    static volatile BuddyBuild mInstance;
    private WeakReference<Activity> mActivity;
    private ActivityLifecycleManager mActivityLifecycleManager;
    private final Context mContext;
    /* access modifiers changed from: private */
    public ShakeDetector mShakeDetector;

    private BuddyBuild(Context context) {
        this.mContext = context;
        if (BuddyBuildProperties.BUDDYBUILD_ENABLE_FEEDBACK.booleanValue()) {
            this.mShakeDetector = new ShakeDetector(new ShakeDetector.Listener() {
                public void hearShake() {
                    Activity activity = BuddyBuild.this.getCurrentActivity();
                    if (activity != null && activity.getClass() != FeedbackActivity.class && activity.getClass() != DemoActivity.class) {
                        BuddyBuild.this.startFeedback();
                    }
                }
            });
        }
    }

    public static void setup(Context context) {
        if (mInstance == null) {
            synchronized (BuddyBuild.class) {
                Constants.loadFromContext(context);
                BuddyBuildProperties.loadFromContext(context);
                if (BuddyBuildProperties.BUDDYBUILD_ENABLE_FEEDBACK.booleanValue() || BuddyBuildProperties.BUDDYBUILD_ENABLE_CRASHREPORT.booleanValue() || BuddyBuildProperties.BUDDYBUILD_ENABLE_AUTOUPDATE.booleanValue()) {
                    mInstance = new BuddyBuild(context);
                    mInstance.init();
                } else {
                    Log.d(Constants.BUDDYBUILD_TAG, "None of the requisite flags to start the BuddyBuild SDK are enabled.");
                }
            }
        }
    }

    public static void setup(Application application) {
        Constants.loadFromContext(application.getApplicationContext());
        BuddyBuildProperties.loadFromContext(application.getApplicationContext());
        if (BuddyBuildProperties.BUDDYBUILD_ENABLE_CRASHREPORT.booleanValue()) {
            CrashReports.attach(application);
        }
        setup(application.getApplicationContext());
        Log.d(Constants.BUDDYBUILD_TAG, "Buddybuild SDK is integrated.");
    }

    /* access modifiers changed from: private */
    @TargetApi(12)
    public void startFeedback() {
        Activity activity = getCurrentActivity();
        Bitmap screenshotBitmap = FeedbackUtils.getActivityScreenshot(this.mActivity);
        if (screenshotBitmap != null) {
            if (this.mShakeDetector != null) {
                this.mShakeDetector.stop();
            }
            String className = activity.getLocalClassName();
            ScreenshotCache.instance().put(className, screenshotBitmap);
            FeedbackUtils.showFeedbackActivity(activity.getApplicationContext(), className);
        }
    }

    /* access modifiers changed from: private */
    public Activity getCurrentActivity() {
        if (this.mActivity != null) {
            return (Activity) this.mActivity.get();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void setCurrentActivity(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    private Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    private void init() {
        new OpenAppEvent().trigger();
        setCurrentActivity(extractActivity(this.mContext));
        this.mActivityLifecycleManager = new ActivityLifecycleManager(this.mContext);
        this.mActivityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
                BuddyBuild.this.setCurrentActivity(activity);
            }

            public void onActivityStarted(Activity activity) {
                BuddyBuild.this.setCurrentActivity(activity);
            }

            public void onActivityResumed(Activity activity) {
                BuddyBuild.this.setCurrentActivity(activity);
                if (BuddyBuild.this.mShakeDetector != null && !ContextUtils.IsInternalContext(activity)) {
                    BuddyBuild.this.mShakeDetector.start((SensorManager) activity.getSystemService("sensor"));
                }
                if (BuddyBuildProperties.BUDDYBUILD_ENABLE_AUTOUPDATE.booleanValue()) {
                    new AutoUpdater(new WeakReference(activity)).check();
                }
                DemoUtils.showDemoActivity(activity.getBaseContext());
            }

            public void onActivityPaused(Activity activity) {
                if (BuddyBuild.this.mShakeDetector != null) {
                    BuddyBuild.this.mShakeDetector.stop();
                }
            }
        });
    }
}
