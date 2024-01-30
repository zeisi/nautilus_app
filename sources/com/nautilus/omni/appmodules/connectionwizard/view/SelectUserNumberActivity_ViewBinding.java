package com.nautilus.omni.appmodules.connectionwizard.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class SelectUserNumberActivity_ViewBinding implements Unbinder {
    private SelectUserNumberActivity target;

    @UiThread
    public SelectUserNumberActivity_ViewBinding(SelectUserNumberActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public SelectUserNumberActivity_ViewBinding(SelectUserNumberActivity target2, View source) {
        this.target = target2;
        target2.mButtonUser1 = (Button) Utils.findRequiredViewAsType(source, R.id.buttonUser1, "field 'mButtonUser1'", Button.class);
        target2.mButtonUser2 = (Button) Utils.findRequiredViewAsType(source, R.id.buttonUser2, "field 'mButtonUser2'", Button.class);
        target2.mButtonUser3 = (Button) Utils.findRequiredViewAsType(source, R.id.buttonUser3, "field 'mButtonUser3'", Button.class);
        target2.mButtonUser4 = (Button) Utils.findRequiredViewAsType(source, R.id.buttonUser4, "field 'mButtonUser4'", Button.class);
        target2.mProgressBarConnectNow = (ProgressBar) Utils.findRequiredViewAsType(source, R.id.progress_bar_confirm_user, "field 'mProgressBarConnectNow'", ProgressBar.class);
        target2.mTxtViewSkip = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_skip, "field 'mTxtViewSkip'", TextView.class);
        target2.mTxtViewConfirmUser = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_confirm_user, "field 'mTxtViewConfirmUser'", TextView.class);
        target2.mBtnConfirmUser = (RelativeLayout) Utils.findRequiredViewAsType(source, R.id.button_confirm_user, "field 'mBtnConfirmUser'", RelativeLayout.class);
        target2.userSelectionImageView = (ImageView) Utils.findRequiredViewAsType(source, R.id.user_selection_image_view, "field 'userSelectionImageView'", ImageView.class);
    }

    @CallSuper
    public void unbind() {
        SelectUserNumberActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mButtonUser1 = null;
        target2.mButtonUser2 = null;
        target2.mButtonUser3 = null;
        target2.mButtonUser4 = null;
        target2.mProgressBarConnectNow = null;
        target2.mTxtViewSkip = null;
        target2.mTxtViewConfirmUser = null;
        target2.mBtnConfirmUser = null;
        target2.userSelectionImageView = null;
    }
}
