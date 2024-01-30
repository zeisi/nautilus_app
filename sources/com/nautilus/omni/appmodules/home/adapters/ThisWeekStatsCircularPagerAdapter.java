package com.nautilus.omni.appmodules.home.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragment;
import com.nautilus.omni.util.Util;

public class ThisWeekStatsCircularPagerAdapter extends FragmentPagerAdapter {
    public static final int AMOUNT_OF_TIMES_GOALS_LABEL_SHOULD_BE_SHOWN = 4;
    public static final int GOALS_EXPLANATION_LBL_POSITION = 1;
    private int mCount;
    private int[] mPageIDsArray;
    private boolean mShouldShowGoalsExplanationLabel;
    ThisWeekSectionFragment[] mThisWeekSection;

    public ThisWeekStatsCircularPagerAdapter(FragmentManager fm, boolean enableGoals, int weekLabel, Context c) {
        super(fm);
        if (Util.hasWatts(c)) {
            if (!enableGoals || weekLabel >= 4) {
                this.mCount = 10;
                this.mPageIDsArray = new int[]{6, 0, 1, 2, 3, 4, 5, 6, 7, 0};
                this.mShouldShowGoalsExplanationLabel = false;
            } else {
                this.mCount = 11;
                this.mPageIDsArray = new int[]{6, 8, 0, 1, 2, 3, 4, 5, 6, 7, 8};
                this.mShouldShowGoalsExplanationLabel = true;
            }
        } else if (!enableGoals || weekLabel >= 4) {
            this.mCount = 9;
            this.mPageIDsArray = new int[]{6, 0, 1, 2, 3, 4, 5, 6, 0};
            this.mShouldShowGoalsExplanationLabel = false;
        } else {
            this.mCount = 10;
            this.mPageIDsArray = new int[]{6, 8, 0, 1, 2, 3, 4, 5, 6, 8};
            this.mShouldShowGoalsExplanationLabel = true;
        }
        this.mThisWeekSection = new ThisWeekSectionFragment[this.mCount];
    }

    public void update() {
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void notifyDataSetChanged() {
        for (ThisWeekSectionFragment aThisWeekSection : this.mThisWeekSection) {
            if (aThisWeekSection != null) {
                aThisWeekSection.refreshData();
            }
        }
        super.notifyDataSetChanged();
    }

    public int getCount() {
        return this.mCount;
    }

    public Fragment getItem(int position) {
        ThisWeekSectionFragment thisWeekSectionFragment = new ThisWeekSectionFragment();
        thisWeekSectionFragment.setKind(this.mPageIDsArray[position]);
        this.mThisWeekSection[position] = thisWeekSectionFragment;
        return thisWeekSectionFragment;
    }

    public boolean shouldShowGoalsExplanationLabel() {
        return this.mShouldShowGoalsExplanationLabel;
    }
}
