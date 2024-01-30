package com.buddybuild.sdk.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.buddybuild.sdk.R;
import com.squareup.seismic.ShakeDetector;
import org.acra.ACRAConstants;

public class DemoActivity extends Activity implements ShakeDetector.Listener {
    /* access modifiers changed from: private */
    public boolean mDialogShowing = false;
    private ShakeDetector mShakeDetector;

    /* access modifiers changed from: protected */
    @TargetApi(14)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_demo_activity);
        this.mShakeDetector = new ShakeDetector(this);
        findViewById(R.id.bb_dismiss_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DemoActivity.this.finish();
            }
        });
        findViewById(R.id.bb_shake_da_phone).startAnimation(AnimationUtils.loadAnimation(this, R.anim.bb_shake));
        findViewById(R.id.bb_shake_bg).animate().setStartDelay(350).setDuration(750).alpha(1.0f);
        try {
            Vibrator vib = (Vibrator) getSystemService("vibrator");
            if (vib != null) {
                vib.vibrate(800);
            }
        } catch (Exception e) {
        }
    }

    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        this.mShakeDetector.start((SensorManager) getSystemService("sensor"));
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        this.mShakeDetector.stop();
    }

    public void hearShake() {
        if (!this.mDialogShowing) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hold your horses!").setMessage("Tap the \"Dismiss\" button first").setPositiveButton(ACRAConstants.DEFAULT_DIALOG_POSITIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    boolean unused = DemoActivity.this.mDialogShowing = false;
                }
            });
            builder.create().show();
            this.mDialogShowing = true;
        }
    }
}
