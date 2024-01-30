package com.nautilus.omni.appmodules.tutorial.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class TutorialActivity_ViewBinding implements Unbinder {
    private TutorialActivity target;

    @UiThread
    public TutorialActivity_ViewBinding(TutorialActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public TutorialActivity_ViewBinding(TutorialActivity target2, View source) {
        this.target = target2;
        target2.mViewPagerTutorial = (ViewPager) Utils.findRequiredViewAsType(source, R.id.pager_tutorial, "field 'mViewPagerTutorial'", ViewPager.class);
        target2.mImgBtnBack = (ImageButton) Utils.findRequiredViewAsType(source, R.id.btn_tutorial_Back, "field 'mImgBtnBack'", ImageButton.class);
        target2.mImgBtnNext = (ImageButton) Utils.findRequiredViewAsType(source, R.id.btn_tutorial_Forward, "field 'mImgBtnNext'", ImageButton.class);
        target2.mImgViewCenter = (ImageView) Utils.findRequiredViewAsType(source, R.id.img_tutorial_progress, "field 'mImgViewCenter'", ImageView.class);
        target2.mTxtViewStart = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_start, "field 'mTxtViewStart'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        TutorialActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mViewPagerTutorial = null;
        target2.mImgBtnBack = null;
        target2.mImgBtnNext = null;
        target2.mImgViewCenter = null;
        target2.mTxtViewStart = null;
    }
}
