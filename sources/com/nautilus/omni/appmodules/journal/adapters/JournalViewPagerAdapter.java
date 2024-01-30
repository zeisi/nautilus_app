package com.nautilus.omni.appmodules.journal.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Parcelable;
import android.support.v13.app.FragmentPagerAdapter;
import com.nautilus.omni.appmodules.journal.view.day.JournalDayFragment;
import com.nautilus.omni.appmodules.journal.view.week.JournalWeekFragment;
import com.nautilus.omni.appmodules.journal.view.year.JournalYearFragment;

public class JournalViewPagerAdapter extends FragmentPagerAdapter {
    public static final int DAY_FRAGMENT_INDEX = 0;
    public static final int WEEK_FRAGMENT_INDEX = 1;
    public static final int YEAR_FRAGMENT_INDEX = 2;
    private int mNumbOfTabs;
    String[] mTitles;

    public JournalViewPagerAdapter(FragmentManager fm, int numbOfTabs, String[] titles) {
        super(fm);
        this.mNumbOfTabs = numbOfTabs;
        this.mTitles = titles;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new JournalDayFragment();
            case 1:
                return new JournalWeekFragment();
            case 2:
                return new JournalYearFragment();
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        return this.mTitles[position];
    }

    public int getCount() {
        return this.mNumbOfTabs;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
