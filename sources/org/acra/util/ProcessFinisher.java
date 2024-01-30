package org.acra.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
import org.acra.ACRA;
import org.acra.builder.LastActivityManager;
import org.acra.config.ACRAConfiguration;
import org.acra.sender.SenderService;

public final class ProcessFinisher {
    private final ACRAConfiguration config;
    private final Context context;
    private final LastActivityManager lastActivityManager;

    public ProcessFinisher(@NonNull Context context2, @NonNull ACRAConfiguration config2, @NonNull LastActivityManager lastActivityManager2) {
        this.context = context2;
        this.config = config2;
        this.lastActivityManager = lastActivityManager2;
    }

    public void endApplication(@Nullable Thread uncaughtExceptionThread) {
        finishLastActivity(uncaughtExceptionThread);
        stopServices();
        killProcessAndExit();
    }

    public void finishLastActivity(@Nullable Thread uncaughtExceptionThread) {
        final Activity lastActivity = this.lastActivityManager.getLastActivity();
        if (lastActivity != null) {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Finishing the last Activity prior to killing the Process");
            }
            lastActivity.runOnUiThread(new Runnable() {
                public void run() {
                    lastActivity.finish();
                    if (ACRA.DEV_LOGGING) {
                        ACRA.log.d(ACRA.LOG_TAG, "Finished " + lastActivity.getClass());
                    }
                }
            });
            if (uncaughtExceptionThread != lastActivity.getMainLooper().getThread()) {
                this.lastActivityManager.waitForActivityStop(100);
            }
            this.lastActivityManager.clearLastActivity();
        }
    }

    private void stopServices() {
        if (this.config.stopServicesOnCrash()) {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            int pid = Process.myPid();
            for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
                if (serviceInfo.pid == pid && !SenderService.class.getName().equals(serviceInfo.service.getClassName())) {
                    try {
                        Intent intent = new Intent();
                        intent.setComponent(serviceInfo.service);
                        this.context.stopService(intent);
                    } catch (SecurityException e) {
                        if (ACRA.DEV_LOGGING) {
                            ACRA.log.d(ACRA.LOG_TAG, "Unable to stop Service " + serviceInfo.service.getClassName() + ". Permission denied");
                        }
                    }
                }
            }
        }
    }

    private void killProcessAndExit() {
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
