package com.buddybuild.sdk.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

public class ActivityLifecycleManager {
    private final Application mApplication;
    private ActivityLifecycleCallbacksWrapper mCallbacksWrapper;

    public ActivityLifecycleManager(Context context) {
        this.mApplication = (Application) context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 14) {
            this.mCallbacksWrapper = new ActivityLifecycleCallbacksWrapper(this.mApplication);
        }
    }

    public boolean registerCallbacks(Callbacks callbacks) {
        return this.mCallbacksWrapper != null && this.mCallbacksWrapper.registerLifecycleCallbacks(callbacks);
    }

    public void clearCallbacks() {
        if (this.mCallbacksWrapper != null) {
            this.mCallbacksWrapper.clearCallbacks();
        }
    }

    private static class ActivityLifecycleCallbacksWrapper {
        private final Application application;
        private final Set<Application.ActivityLifecycleCallbacks> registeredCallbacks = new HashSet();

        ActivityLifecycleCallbacksWrapper(Application application2) {
            this.application = application2;
        }

        /* access modifiers changed from: private */
        @TargetApi(14)
        public void clearCallbacks() {
            for (Application.ActivityLifecycleCallbacks callback : this.registeredCallbacks) {
                this.application.unregisterActivityLifecycleCallbacks(callback);
            }
        }

        /* access modifiers changed from: private */
        @TargetApi(14)
        public boolean registerLifecycleCallbacks(final Callbacks callbacks) {
            if (this.application == null) {
                return false;
            }
            Application.ActivityLifecycleCallbacks callbackWrapper = new Application.ActivityLifecycleCallbacks() {
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    callbacks.onActivityCreated(activity, bundle);
                }

                public void onActivityStarted(Activity activity) {
                    callbacks.onActivityStarted(activity);
                }

                public void onActivityResumed(Activity activity) {
                    callbacks.onActivityResumed(activity);
                }

                public void onActivityPaused(Activity activity) {
                    callbacks.onActivityPaused(activity);
                }

                public void onActivityStopped(Activity activity) {
                    callbacks.onActivityStopped(activity);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    callbacks.onActivitySaveInstanceState(activity, bundle);
                }

                public void onActivityDestroyed(Activity activity) {
                    callbacks.onActivityDestroyed(activity);
                }
            };
            this.application.registerActivityLifecycleCallbacks(callbackWrapper);
            this.registeredCallbacks.add(callbackWrapper);
            return true;
        }
    }

    public static abstract class Callbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }
}
