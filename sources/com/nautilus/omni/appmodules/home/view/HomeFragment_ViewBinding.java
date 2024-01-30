package com.nautilus.omni.appmodules.home.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;
import com.viewpagerindicator.CirclePageIndicator;

public class HomeFragment_ViewBinding implements Unbinder {
    private HomeFragment target;
    private View view2131755299;
    private View view2131755300;
    private View view2131755302;
    private View view2131755305;
    private View view2131755313;

    @UiThread
    public HomeFragment_ViewBinding(final HomeFragment target2, View source) {
        this.target = target2;
        target2.mPagerGauge = (ViewPager) Utils.findRequiredViewAsType(source, R.id.pagerGauge, "field 'mPagerGauge'", ViewPager.class);
        target2.mPagerThisWeek = (ViewPager) Utils.findRequiredViewAsType(source, R.id.pagerCircular, "field 'mPagerThisWeek'", ViewPager.class);
        target2.mHomeAchievementsSection = (ViewPager) Utils.findRequiredViewAsType(source, R.id.homeAchievementsSection, "field 'mHomeAchievementsSection'", ViewPager.class);
        target2.mHomeAchievementFragment = Utils.findRequiredView(source, R.id.homeAchievementFragment, "field 'mHomeAchievementFragment'");
        target2.mTxtViewYourAchievements = (TextView) Utils.findRequiredViewAsType(source, R.id.lblYourAchievements, "field 'mTxtViewYourAchievements'", TextView.class);
        target2.mImgViewAchievementsRow = (ImageView) Utils.findRequiredViewAsType(source, R.id.imageViewHomeLastAchievement, "field 'mImgViewAchievementsRow'", ImageView.class);
        target2.mTxtViewHomeNameAchievement = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewHomeNameAchievement, "field 'mTxtViewHomeNameAchievement'", TextView.class);
        target2.mTxtViewHomeDateOfAchievement = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewHomeDateOfAchievement, "field 'mTxtViewHomeDateOfAchievement'", TextView.class);
        target2.mTxtViewAchievementsCopy = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewAchievementsCopy, "field 'mTxtViewAchievementsCopy'", TextView.class);
        target2.mTxtViewDate = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewDate, "field 'mTxtViewDate'", TextView.class);
        target2.mCirclePageIndicator = (CirclePageIndicator) Utils.findRequiredViewAsType(source, R.id.circlePageIndicatorAchievements, "field 'mCirclePageIndicator'", CirclePageIndicator.class);
        View view = Utils.findRequiredView(source, R.id.homeGaugeBack, "method 'homeGaugeBackClick'");
        this.view2131755299 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.homeGaugeBackClick();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.homeGaugeForward, "method 'homeGaugeForwardClick'");
        this.view2131755300 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.homeGaugeForwardClick();
            }
        });
        View view3 = Utils.findRequiredView(source, R.id.homeAchievementsUp, "method 'homeAchievementsUpClick'");
        this.view2131755305 = view3;
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.homeAchievementsUpClick();
            }
        });
        View view4 = Utils.findRequiredView(source, R.id.homeAchievementsDown, "method 'homeAchievementsDownClick'");
        this.view2131755313 = view4;
        view4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.homeAchievementsDownClick();
            }
        });
        View view5 = Utils.findRequiredView(source, R.id.imageViewMiddleSectionNext, "method 'imageViewMiddleSectionNextClick'");
        this.view2131755302 = view5;
        view5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.imageViewMiddleSectionNextClick();
            }
        });
    }

    @CallSuper
    public void unbind() {
        HomeFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mPagerGauge = null;
        target2.mPagerThisWeek = null;
        target2.mHomeAchievementsSection = null;
        target2.mHomeAchievementFragment = null;
        target2.mTxtViewYourAchievements = null;
        target2.mImgViewAchievementsRow = null;
        target2.mTxtViewHomeNameAchievement = null;
        target2.mTxtViewHomeDateOfAchievement = null;
        target2.mTxtViewAchievementsCopy = null;
        target2.mTxtViewDate = null;
        target2.mCirclePageIndicator = null;
        this.view2131755299.setOnClickListener((View.OnClickListener) null);
        this.view2131755299 = null;
        this.view2131755300.setOnClickListener((View.OnClickListener) null);
        this.view2131755300 = null;
        this.view2131755305.setOnClickListener((View.OnClickListener) null);
        this.view2131755305 = null;
        this.view2131755313.setOnClickListener((View.OnClickListener) null);
        this.view2131755313 = null;
        this.view2131755302.setOnClickListener((View.OnClickListener) null);
        this.view2131755302 = null;
    }
}
