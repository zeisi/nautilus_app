package com.nautilus.omni.appmodules.home.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import com.nautilus.omni.appmodules.home.view.AchievementsFragment;

public class HomeAwardsAdapter extends FragmentPagerAdapter {
    public HomeAwardsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void update() {
        ((AchievementsFragment) getItem(0)).updateAwards();
        ((AchievementsFragment) getItem(1)).updateAwards();
        notifyDataSetChanged();
    }

    public int getCount() {
        return 2;
    }

    public Fragment getItem(int position) {
        AchievementsFragment achivementsFragment = new AchievementsFragment();
        achivementsFragment.setKind(position);
        return achivementsFragment;
    }
}
