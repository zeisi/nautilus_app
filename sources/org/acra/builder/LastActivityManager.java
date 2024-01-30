package org.acra.builder;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import org.acra.ACRA;
import org.acra.dialog.BaseCrashReportDialog;

public final class LastActivityManager {
    /* access modifiers changed from: private */
    @NonNull
    public WeakReference<Activity> lastActivityCreated = new WeakReference<>((Object) null);

    public LastActivityManager(@NonNull Application application) {
        if (Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityCreated " + activity.getClass());
                    }
                    if (!(activity instanceof BaseCrashReportDialog)) {
                        WeakReference unused = LastActivityManager.this.lastActivityCreated = new WeakReference(activity);
                    }
                }

                public void onActivityStarted(@NonNull Activity activity) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityStarted " + activity.getClass());
                    }
                }

                public void onActivityResumed(@NonNull Activity activity) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityResumed " + activity.getClass());
                    }
                }

                public void onActivityPaused(@NonNull Activity activity) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityPaused " + activity.getClass());
                    }
                }

                public void onActivityStopped(@NonNull Activity activity) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityStopped " + activity.getClass());
                    }
                    synchronized (this) {
                        notify();
                    }
                }

                public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle outState) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivitySaveInstanceState " + activity.getClass());
                    }
                }

                public void onActivityDestroyed(@NonNull Activity activity) {
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "onActivityDestroyed " + activity.getClass());
                    }
                }
            });
        }
    }

    @Nullable
    public Activity getLastActivity() {
        return (Activity) this.lastActivityCreated.get();
    }

    public void clearLastActivity() {
        this.lastActivityCreated.clear();
    }

    public void waitForActivityStop(int timeOutInMillis) {
        synchronized (this) {
            try {
                wait((long) timeOutInMillis);
            } catch (InterruptedException e) {
            }
        }
    }
}
