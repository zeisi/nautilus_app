package com.nautilus.omni.appmodules.settings.mainsection.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class SettingsActivity_ViewBinding implements Unbinder {
    private SettingsActivity target;
    private View view2131755182;
    private View view2131755184;
    private View view2131755186;
    private View view2131755188;
    private View view2131755190;
    private View view2131755191;
    private View view2131755192;

    @UiThread
    public SettingsActivity_ViewBinding(SettingsActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public SettingsActivity_ViewBinding(final SettingsActivity target2, View source) {
        this.target = target2;
        target2.mToolbar = (Toolbar) Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
        target2.mTxtViewToolbarTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'mTxtViewToolbarTitle'", TextView.class);
        target2.mTxtViewVersion = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_version, "field 'mTxtViewVersion'", TextView.class);
        target2.mTxtViewGoogleFitStatus = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_google_fit_status, "field 'mTxtViewGoogleFitStatus'", TextView.class);
        target2.mTxtViewMFPStatus = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_mfp_status, "field 'mTxtViewMFPStatus'", TextView.class);
        target2.mTxtViewUnderArmourStatus = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_under_armour_status, "field 'mTxtViewUnderArmourStatus'", TextView.class);
        target2.mSpinnerUnits = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinnerUnits, "field 'mSpinnerUnits'", Spinner.class);
        View view = Utils.findRequiredView(source, R.id.settings_btn_privacyPolicy, "method 'settingsButtonPrivacyPolicyClick'");
        this.view2131755190 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonPrivacyPolicyClick();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.settings_btn_eula, "method 'settingsButtonEulaClick'");
        this.view2131755191 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonEulaClick();
            }
        });
        View view3 = Utils.findRequiredView(source, R.id.settings_btn_attributions, "method 'settingsButtonAttributions'");
        this.view2131755192 = view3;
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonAttributions();
            }
        });
        View view4 = Utils.findRequiredView(source, R.id.settings_btn_google_fit, "method 'settingsButtonGoogleFitClick'");
        this.view2131755182 = view4;
        view4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonGoogleFitClick();
            }
        });
        View view5 = Utils.findRequiredView(source, R.id.settings_btn_mfp, "method 'settingsButtonMFPClick'");
        this.view2131755184 = view5;
        view5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonMFPClick();
            }
        });
        View view6 = Utils.findRequiredView(source, R.id.settings_email_us, "method 'settingsEmailUsClick'");
        this.view2131755188 = view6;
        view6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsEmailUsClick();
            }
        });
        View view7 = Utils.findRequiredView(source, R.id.settings_btn_under_armour, "method 'settingsButtonUnderArmourClick'");
        this.view2131755186 = view7;
        view7.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.settingsButtonUnderArmourClick();
            }
        });
    }

    @CallSuper
    public void unbind() {
        SettingsActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mToolbar = null;
        target2.mTxtViewToolbarTitle = null;
        target2.mTxtViewVersion = null;
        target2.mTxtViewGoogleFitStatus = null;
        target2.mTxtViewMFPStatus = null;
        target2.mTxtViewUnderArmourStatus = null;
        target2.mSpinnerUnits = null;
        this.view2131755190.setOnClickListener((View.OnClickListener) null);
        this.view2131755190 = null;
        this.view2131755191.setOnClickListener((View.OnClickListener) null);
        this.view2131755191 = null;
        this.view2131755192.setOnClickListener((View.OnClickListener) null);
        this.view2131755192 = null;
        this.view2131755182.setOnClickListener((View.OnClickListener) null);
        this.view2131755182 = null;
        this.view2131755184.setOnClickListener((View.OnClickListener) null);
        this.view2131755184 = null;
        this.view2131755188.setOnClickListener((View.OnClickListener) null);
        this.view2131755188 = null;
        this.view2131755186.setOnClickListener((View.OnClickListener) null);
        this.view2131755186 = null;
    }
}
