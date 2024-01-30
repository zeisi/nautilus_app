package com.nautilus.omni.appmodules.home.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import com.nautilus.omni.appmodules.home.view.GaugeFragment;
import com.nautilus.omni.util.Util;

public class GaugePagerAdapter extends FragmentPagerAdapter {
    GaugeFragment[] gauges;

    public GaugePagerAdapter(FragmentManager fragmentManager, Context c) {
        super(fragmentManager);
        if (Util.hasWatts(c)) {
            this.gauges = new GaugeFragment[7];
        } else {
            this.gauges = new GaugeFragment[6];
        }
    }

    public void update() {
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        for (GaugeFragment gauge : this.gauges) {
            if (gauge != null) {
                gauge.refreshData();
            }
        }
        super.notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public int getCount() {
        return this.gauges.length;
    }

    public Fragment getItem(int position) {
        GaugeFragment gaugeFragment = new GaugeFragment();
        gaugeFragment.setRetainInstance(false);
        gaugeFragment.setKind(position);
        this.gauges[position] = gaugeFragment;
        return gaugeFragment;
    }
}
