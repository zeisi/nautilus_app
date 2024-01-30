package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dd.CircularProgressButton;
import com.nautilus.omni.R;

public class ConfigGoogleFitActivity_ViewBinding implements Unbinder {
    private ConfigGoogleFitActivity target;
    private View view2131755160;
    private View view2131755161;

    @UiThread
    public ConfigGoogleFitActivity_ViewBinding(ConfigGoogleFitActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public ConfigGoogleFitActivity_ViewBinding(final ConfigGoogleFitActivity target2, View source) {
        this.target = target2;
        target2.mSwitchTime = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_time, "field 'mSwitchTime'", SwitchCompat.class);
        target2.mSwitchCalories = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_calories, "field 'mSwitchCalories'", SwitchCompat.class);
        target2.mSwitchAvgHeartRate = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_avg_heart_rate, "field 'mSwitchAvgHeartRate'", SwitchCompat.class);
        target2.mSwitchAvgRPM = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_rpm, "field 'mSwitchAvgRPM'", SwitchCompat.class);
        target2.mSwitchDistance = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_distance, "field 'mSwitchDistance'", SwitchCompat.class);
        View view = Utils.findRequiredView(source, R.id.button_google_fit_desconnect, "field 'mBtnDisconnect' and method 'disconnect'");
        target2.mBtnDisconnect = (Button) Utils.castView(view, R.id.button_google_fit_desconnect, "field 'mBtnDisconnect'", Button.class);
        this.view2131755160 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.disconnect();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.button_google_fit_sync, "field 'mBtnSyncProgress' and method 'sync'");
        target2.mBtnSyncProgress = (CircularProgressButton) Utils.castView(view2, R.id.button_google_fit_sync, "field 'mBtnSyncProgress'", CircularProgressButton.class);
        this.view2131755161 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.sync();
            }
        });
    }

    @CallSuper
    public void unbind() {
        ConfigGoogleFitActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mSwitchTime = null;
        target2.mSwitchCalories = null;
        target2.mSwitchAvgHeartRate = null;
        target2.mSwitchAvgRPM = null;
        target2.mSwitchDistance = null;
        target2.mBtnDisconnect = null;
        target2.mBtnSyncProgress = null;
        this.view2131755160.setOnClickListener((View.OnClickListener) null);
        this.view2131755160 = null;
        this.view2131755161.setOnClickListener((View.OnClickListener) null);
        this.view2131755161 = null;
    }
}
