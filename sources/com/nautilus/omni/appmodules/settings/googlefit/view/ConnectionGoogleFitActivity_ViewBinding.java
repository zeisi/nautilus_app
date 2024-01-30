package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class ConnectionGoogleFitActivity_ViewBinding implements Unbinder {
    private ConnectionGoogleFitActivity target;
    private View view2131755154;

    @UiThread
    public ConnectionGoogleFitActivity_ViewBinding(ConnectionGoogleFitActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public ConnectionGoogleFitActivity_ViewBinding(final ConnectionGoogleFitActivity target2, View source) {
        this.target = target2;
        View view = Utils.findRequiredView(source, R.id.button_google_fit, "method 'connectToGoogleFitOnClick'");
        this.view2131755154 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.connectToGoogleFitOnClick();
            }
        });
    }

    @CallSuper
    public void unbind() {
        if (this.target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        this.view2131755154.setOnClickListener((View.OnClickListener) null);
        this.view2131755154 = null;
    }
}
