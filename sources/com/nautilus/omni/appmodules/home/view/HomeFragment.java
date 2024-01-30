package com.nautilus.omni.appmodules.home.view;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.appmodules.home.adapters.GaugePagerAdapter;
import com.nautilus.omni.appmodules.home.adapters.HomeAwardsAdapter;
import com.nautilus.omni.appmodules.home.adapters.ThisWeekStatsCircularPagerAdapter;
import com.nautilus.omni.appmodules.home.presenter.HomePresenterContract;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.BroadcastKeys;
import com.viewpagerindicator.CirclePageIndicator;
import java.lang.reflect.Field;
import javax.inject.Inject;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HomeFragment extends BaseFragment implements HomeFragmentContract {
    private static final String IS_ACHIEVEMNTS_OPEN = "IS_ACHIEVEMNTS_OPEN";
    public static final String TAG = HomeFragment.class.getName();
    @BindView(2131755314)
    CirclePageIndicator mCirclePageIndicator;
    private DateTimeFormatter mDateFormatter;
    GaugePagerAdapter mGaugePagerAdapter;
    @BindView(2131755311)
    View mHomeAchievementFragment;
    @BindView(2131755316)
    ViewPager mHomeAchievementsSection;
    HomeAwardsAdapter mHomeAwardsAdapter;
    @Inject
    HomePresenterContract mIHomePresenter;
    @BindView(2131755307)
    ImageView mImgViewAchievementsRow;
    private boolean mOpenYourAchievementsFragment;
    @BindView(2131755297)
    ViewPager mPagerGauge;
    @BindView(2131755303)
    ViewPager mPagerThisWeek;
    ThisWeekStatsCircularPagerAdapter mThisWeekStatsCircularPagerAdapter;
    @BindView(2131755310)
    TextView mTxtViewAchievementsCopy;
    @BindView(2131755298)
    TextView mTxtViewDate;
    @BindView(2131755309)
    TextView mTxtViewHomeDateOfAchievement;
    @BindView(2131755308)
    TextView mTxtViewHomeNameAchievement;
    @BindView(2131755312)
    TextView mTxtViewYourAchievements;
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            HomeFragment.this.mIHomePresenter.loadPagerAdapters();
        }
    };

    public boolean isOpenYourAchievementsFragment() {
        return this.mOpenYourAchievementsFragment;
    }

    public void setOpenYourAchievementsFragment(boolean openYourAchievementsFragment) {
        this.mOpenYourAchievementsFragment = openYourAchievementsFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    @OnClick({2131755299})
    public void homeGaugeBackClick() {
        this.mPagerGauge.setCurrentItem(this.mPagerGauge.getCurrentItem() - 1);
    }

    @OnClick({2131755300})
    public void homeGaugeForwardClick() {
        this.mPagerGauge.setCurrentItem(this.mPagerGauge.getCurrentItem() + 1);
    }

    @OnClick({2131755305})
    public void homeAchievementsUpClick() {
        this.mIHomePresenter.checkIsAvailableAchievements();
    }

    @OnClick({2131755313})
    public void homeAchievementsDownClick() {
        closeDetailView();
        this.mOpenYourAchievementsFragment = false;
    }

    @OnClick({2131755302})
    public void imageViewMiddleSectionNextClick() {
        if (this.mPagerThisWeek != null) {
            this.mPagerThisWeek.setCurrentItem(this.mPagerThisWeek.getCurrentItem() + 1);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mIHomePresenter.setHomeFragment(this);
        setUpFragment();
        this.mIHomePresenter.loadPagerAdapters();
        if (savedInstanceState != null) {
            this.mPagerGauge.invalidate();
            if (savedInstanceState.getBoolean(IS_ACHIEVEMNTS_OPEN)) {
                this.mHomeAchievementFragment.setVisibility(0);
            }
        }
    }

    public void refreshData() {
        updateHomeFragments();
    }

    private void updateHomeFragments() {
        if (this.mGaugePagerAdapter != null) {
            this.mGaugePagerAdapter.notifyDataSetChanged();
        }
        if (this.mThisWeekStatsCircularPagerAdapter != null) {
            this.mThisWeekStatsCircularPagerAdapter.notifyDataSetChanged();
        }
        if (this.mHomeAchievementFragment != null) {
            this.mIHomePresenter.loadAchievementsSection();
        }
    }

    private void setUpFragment() {
        setUpPagers();
        if (DateFormat.is24HourFormat(getActivity().getApplicationContext())) {
            this.mDateFormatter = DateTimeFormat.forPattern(getActivity().getString(R.string.home_date_extended24));
        } else {
            this.mDateFormatter = DateTimeFormat.forPattern(getActivity().getString(R.string.home_date_extended));
        }
        this.mIHomePresenter.loadAchievementsSection();
    }

    public void setUpPagers() {
        this.mGaugePagerAdapter = new GaugePagerAdapter(getChildFragmentManager(), getActivity());
        this.mPagerGauge.setAdapter(this.mGaugePagerAdapter);
        this.mThisWeekStatsCircularPagerAdapter = new ThisWeekStatsCircularPagerAdapter(getChildFragmentManager(), this.mIHomePresenter.isGoalsEnable(), this.mIHomePresenter.getThisWeekLabel(), getActivity());
        this.mPagerThisWeek.setAdapter(this.mThisWeekStatsCircularPagerAdapter);
        this.mPagerThisWeek.setCurrentItem(1);
        this.mPagerThisWeek.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                int pageCount = HomeFragment.this.mThisWeekStatsCircularPagerAdapter.getCount();
                if (position == 0) {
                    HomeFragment.this.mPagerThisWeek.setCurrentItem(pageCount - 2, false);
                } else if (position == pageCount - 1) {
                    HomeFragment.this.mPagerThisWeek.setCurrentItem(1, false);
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        this.mIHomePresenter.updateThisWeekLabel();
        configureAchievementsSection();
    }

    public void configureAchievementsSection() {
        this.mHomeAwardsAdapter = new HomeAwardsAdapter(getChildFragmentManager());
        this.mHomeAchievementsSection.setAdapter(this.mHomeAwardsAdapter);
        if (this.mHomeAchievementsSection.getCurrentItem() == 0) {
            this.mTxtViewYourAchievements.setText(R.string.home_last_achievement);
        } else {
            this.mTxtViewYourAchievements.setText(R.string.home_last_record);
        }
        configureCircleIndicator();
    }

    /* access modifiers changed from: package-private */
    public void configureCircleIndicator() {
        this.mCirclePageIndicator.setViewPager(this.mHomeAchievementsSection);
        this.mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    HomeFragment.this.mTxtViewYourAchievements.setText(R.string.home_last_achievement);
                } else {
                    HomeFragment.this.mTxtViewYourAchievements.setText(R.string.home_last_record);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onResume() {
        super.onResume();
        registerBroadcastReceivers();
        this.mIHomePresenter.onResume();
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
    }

    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ACHIEVEMNTS_OPEN, this.mHomeAchievementFragment.getVisibility() == 0);
    }

    private void setDate(Workout lastWorkout) {
        if (lastWorkout == null) {
            this.mTxtViewDate.setText(getString(R.string.home_no_data));
        } else {
            this.mTxtViewDate.setText(lastWorkout.getmWorkoutDate().toString(this.mDateFormatter).toUpperCase());
        }
    }

    public String getStringForAchievement(GoalType goalType, Context ctx) {
        if (goalType.equals(GoalType.CALORIES_PER_WORKOUT)) {
            return ctx.getString(R.string.home_won_calorie_goal_title);
        }
        if (goalType.equals(GoalType.WORKOUTS_PER_WEEK)) {
            return ctx.getString(R.string.home_won_workout_number_goal_title);
        }
        return ctx.getString(R.string.home_won_workout_time_goal_title);
    }

    public void showDetailView() {
        this.mOpenYourAchievementsFragment = true;
        this.mHomeAchievementFragment.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_slide_up));
        this.mHomeAchievementFragment.setVisibility(0);
    }

    public void achievementsSectionLastAwardAndAchievementNoAvailable() {
        this.mImgViewAchievementsRow.setVisibility(4);
        this.mTxtViewHomeNameAchievement.setVisibility(4);
        this.mTxtViewHomeDateOfAchievement.setVisibility(4);
        this.mTxtViewAchievementsCopy.setVisibility(0);
    }

    public void achievementsSectionLastAchievementIsAvailable(Achievement achievement) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(getResources().getString(R.string.home_date_achievements));
        this.mTxtViewAchievementsCopy.setVisibility(4);
        this.mImgViewAchievementsRow.setVisibility(0);
        this.mTxtViewHomeNameAchievement.setVisibility(0);
        this.mTxtViewHomeDateOfAchievement.setVisibility(0);
        this.mImgViewAchievementsRow.setImageResource(R.drawable.ic_goal_achieved_award);
        this.mTxtViewHomeDateOfAchievement.setText(dtfOut.print((ReadableInstant) achievement.getmGoalAchievementDate()));
        this.mTxtViewHomeNameAchievement.setText(getStringForAchievement(achievement.getmGoalType(), getActivity().getApplicationContext()).toUpperCase());
    }

    public void achievementsSectionLastAwardIsAvailable(int awardImgId, String awardName, String date) {
        this.mTxtViewAchievementsCopy.setVisibility(4);
        this.mImgViewAchievementsRow.setVisibility(0);
        this.mTxtViewHomeNameAchievement.setVisibility(0);
        this.mTxtViewHomeDateOfAchievement.setVisibility(0);
        this.mImgViewAchievementsRow.setImageDrawable(ResourcesCompat.getDrawable(getResources(), awardImgId, (Resources.Theme) null));
        this.mTxtViewHomeDateOfAchievement.setText(date);
        this.mTxtViewHomeNameAchievement.setText(awardName);
    }

    public void closeDetailView() {
        if (this.mHomeAchievementFragment.getVisibility() == 0) {
            this.mHomeAchievementFragment.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_slide_down));
            this.mHomeAchievementFragment.setVisibility(8);
        }
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, (Object) null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public void setPagerAdapter(Workout lastWorkout) {
        this.mGaugePagerAdapter.update();
        this.mThisWeekStatsCircularPagerAdapter.update();
        checkIfShouldShowGoalsExplanationLabel();
        this.mHomeAwardsAdapter.update();
        this.mIHomePresenter.loadAchievementsSection();
        setDate(lastWorkout);
    }

    private void checkIfShouldShowGoalsExplanationLabel() {
        if (this.mThisWeekStatsCircularPagerAdapter.shouldShowGoalsExplanationLabel()) {
            this.mPagerThisWeek.post(new Runnable() {
                public void run() {
                    HomeFragment.this.mPagerThisWeek.setCurrentItem(1);
                }
            });
        }
    }
}
