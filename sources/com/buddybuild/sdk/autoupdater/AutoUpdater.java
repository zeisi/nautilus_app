package com.buddybuild.sdk.autoupdater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.R;
import com.buddybuild.sdk.utils.AsyncTaskResponse;
import com.buddybuild.sdk.utils.HttpMakeRequestAsyncTask;
import java.lang.ref.WeakReference;
import java.util.concurrent.Semaphore;
import org.json.JSONObject;

public class AutoUpdater {
    /* access modifiers changed from: private */
    public WeakReference<Activity> mWeakReference;
    private final String sPreferenceKey = "buddybuild_auto_updater_last_url";
    /* access modifiers changed from: private */
    public final Semaphore sShowDialogGate = new Semaphore(1);

    public AutoUpdater(WeakReference<Activity> weakReference) {
        this.mWeakReference = weakReference;
    }

    public void check() {
        new HttpMakeRequestAsyncTask(new AsyncTaskResponse() {
            public void onResponse(String data) {
                if (TextUtils.isEmpty(data)) {
                    Log.d(Constants.BUDDYBUILD_TAG, "The autotupdate http request returned a null response ... exiting");
                    return;
                }
                try {
                    final String url = new JSONObject(data).getString("url");
                    if (TextUtils.isEmpty(url)) {
                        Log.d(Constants.BUDDYBUILD_TAG, "The autotupdate url response is null ... exiting");
                        return;
                    }
                    final Activity activity = (Activity) AutoUpdater.this.mWeakReference.get();
                    if (activity != null) {
                        final SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, 0);
                        if (url.equalsIgnoreCase(sharedPref.getString("buddybuild_auto_updater_last_url", (String) null))) {
                            Log.d(Constants.BUDDYBUILD_TAG, "The autoupdate url response matches the last url ... exiting: " + url);
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(R.string.bb_autoupdate_title).setCancelable(false).setMessage(R.string.bb_autoupdate_message).setPositiveButton(R.string.bb_autoupdate_positive, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent("android.intent.action.VIEW", Uri.parse(url));
                                dialog.dismiss();
                                activity.startActivity(i);
                            }
                        }).setNegativeButton(R.string.bb_autoupdate_negative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        if (AutoUpdater.this.sShowDialogGate.tryAcquire()) {
                            AlertDialog dialog = builder.create();
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                public void onDismiss(DialogInterface dialog) {
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("buddybuild_auto_updater_last_url", url);
                                    editor.commit();
                                    AutoUpdater.this.sShowDialogGate.release();
                                }
                            });
                            dialog.show();
                        }
                    }
                } catch (Exception e) {
                    Log.e(Constants.BUDDYBUILD_TAG, "Exception thrown when attempting to autoupdate:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).execute(new String[]{Constants.AUTO_UPDATE_PARTIAL_URL});
    }
}
