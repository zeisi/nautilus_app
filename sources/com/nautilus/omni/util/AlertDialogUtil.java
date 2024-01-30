package com.nautilus.omni.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;

public class AlertDialogUtil {
    public static void buildSimpleAlertDialog(String dialogMessage, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }

    public static void buildRestartConnectionDialog(final Context context, String dialogMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setPositiveButton(R.string.restart_connection_wizard, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                AlertDialogUtil.startConnectionWizard(context);
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    /* access modifiers changed from: private */
    public static void startConnectionWizard(Context context) {
        Intent intent = new Intent(context, ConnectionWizardActivity.class);
        intent.setFlags(268468224);
        context.startActivity(intent);
    }

    public static void buildAlertDialog(String dialogTitle, String dialogMessage, DialogInterface.OnClickListener positiveButtonListener, DialogInterface.OnClickListener negativeButtonListener, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setPositiveButton(R.string.ok, positiveButtonListener);
        alertDialogBuilder.setNegativeButton(R.string.cancel, negativeButtonListener);
        alertDialogBuilder.create().show();
    }

    public static void buildAlertDialog(String dialogTitle, String dialogMessage, int positiveButtonLabel, int negativeButtonLabel, DialogInterface.OnClickListener positiveButtonListener, DialogInterface.OnClickListener negativeButtonListener, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setPositiveButton(positiveButtonLabel, positiveButtonListener);
        alertDialogBuilder.setNegativeButton(negativeButtonLabel, negativeButtonListener);
        alertDialogBuilder.create().show();
    }
}
