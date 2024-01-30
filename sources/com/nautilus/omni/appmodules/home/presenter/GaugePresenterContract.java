package com.nautilus.omni.appmodules.home.presenter;

import com.nautilus.omni.appmodules.home.view.GaugeFragmentContract;

public interface GaugePresenterContract {
    void checkData();

    void loadDistanceBasedOnCurrentUnit();

    void loadSpeedBasedOnCurrentUnit();

    void loadTextData(int i);

    void setGauceFragment(GaugeFragmentContract gaugeFragmentContract);
}
