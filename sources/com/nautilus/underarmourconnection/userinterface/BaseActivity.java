package com.nautilus.underarmourconnection.userinterface;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void showErrorAlert(String message, int positiveButtonMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) message);
        alertDialogBuilder.setPositiveButton(positiveButtonMessage, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }
}
