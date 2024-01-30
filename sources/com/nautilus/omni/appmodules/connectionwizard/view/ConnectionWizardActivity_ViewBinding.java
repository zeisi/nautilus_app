package com.nautilus.omni.appmodules.connectionwizard.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class ConnectionWizardActivity_ViewBinding implements Unbinder {
    private ConnectionWizardActivity target;

    @UiThread
    public ConnectionWizardActivity_ViewBinding(ConnectionWizardActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public ConnectionWizardActivity_ViewBinding(ConnectionWizardActivity target2, View source) {
        this.target = target2;
        target2.mTxtViewConnectNow = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_connect, "field 'mTxtViewConnectNow'", TextView.class);
        target2.mProgressBarConnectNow = (ProgressBar) Utils.findRequiredViewAsType(source, R.id.progress_bar_connect_now, "field 'mProgressBarConnectNow'", ProgressBar.class);
        target2.mTxtViewSkip = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_skip, "field 'mTxtViewSkip'", TextView.class);
        target2.mConnectNowButtonContainer = (RelativeLayout) Utils.findRequiredViewAsType(source, R.id.connect_now_button_container, "field 'mConnectNowButtonContainer'", RelativeLayout.class);
    }

    @CallSuper
    public void unbind() {
        ConnectionWizardActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mTxtViewConnectNow = null;
        target2.mProgressBarConnectNow = null;
        target2.mTxtViewSkip = null;
        target2.mConnectNowButtonContainer = null;
    }
}
