package com.nautilus.omni.appmodules.tutorial.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;

public class TutorialActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final int TUTORIAL_PAGES = 4;
    @BindView(2131755194)
    ImageButton mImgBtnBack;
    @BindView(2131755196)
    ImageButton mImgBtnNext;
    @BindView(2131755195)
    ImageView mImgViewCenter;
    @BindView(2131755197)
    TextView mTxtViewStart;
    @BindView(2131755198)
    ViewPager mViewPagerTutorial;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_tutorial);
        ButterKnife.bind((Activity) this);
        this.mViewPagerTutorial.addOnPageChangeListener(this);
        this.mViewPagerTutorial.setAdapter(new PagerAdapter(getFragmentManager()));
        setControls();
    }

    public void setImagePosition() {
        int i = this.mViewPagerTutorial.getCurrentItem();
        if (i == 0) {
            this.mImgViewCenter.setImageResource(R.drawable.ic_tutorial_page_1);
        }
        if (i == 1) {
            this.mImgViewCenter.setImageResource(R.drawable.ic_tutorial_page_2);
        }
        if (i == 2) {
            this.mImgViewCenter.setImageResource(R.drawable.ic_tutorial_page_3);
        }
        if (i == 3) {
            this.mImgViewCenter.setImageResource(R.drawable.ic_tutorial_page_4);
        }
    }

    public void setControls() {
        int i = this.mViewPagerTutorial.getCurrentItem();
        if (i == 3) {
            this.mImgBtnNext.setVisibility(4);
            this.mTxtViewStart.setVisibility(0);
            this.mTxtViewStart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TutorialActivity.this.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0).edit().putBoolean(Preferences.TUTORIAL, true).apply();
                    TutorialActivity.this.startActivity(new Intent(TutorialActivity.this, ConnectionWizardActivity.class));
                    TutorialActivity.this.finish();
                }
            });
        } else if (i == 0) {
            this.mImgBtnBack.setVisibility(4);
            this.mTxtViewStart.setVisibility(4);
            this.mImgBtnNext.setVisibility(0);
            this.mImgBtnNext.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TutorialActivity.this.mViewPagerTutorial.setCurrentItem(TutorialActivity.this.mViewPagerTutorial.getCurrentItem() + 1, true);
                }
            });
            this.mImgBtnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TutorialActivity.this.mViewPagerTutorial.setCurrentItem(TutorialActivity.this.mViewPagerTutorial.getCurrentItem() - 1, true);
                }
            });
        } else {
            this.mImgBtnBack.setVisibility(0);
            this.mImgBtnNext.setVisibility(0);
            this.mTxtViewStart.setVisibility(4);
            this.mImgBtnNext.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TutorialActivity.this.mViewPagerTutorial.setCurrentItem(TutorialActivity.this.mViewPagerTutorial.getCurrentItem() + 1, true);
                }
            });
            this.mImgBtnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TutorialActivity.this.mViewPagerTutorial.setCurrentItem(TutorialActivity.this.mViewPagerTutorial.getCurrentItem() - 1, true);
                }
            });
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        setImagePosition();
        setControls();
    }

    public void onPageScrollStateChanged(int state) {
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            TutorialFragment tutorialFragment = new TutorialFragment();
            tutorialFragment.setTutorial(position);
            return tutorialFragment;
        }

        public int getCount() {
            return 4;
        }
    }
}
