package com.nautilus.omni.appmodules.settings.myfitnesspal.presenter;

import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionViewContract;

public interface MyFitnessPalPresenterContract {
    void checkMFPStatus();

    void disconnectMyFitnessPalApp();

    void saveMyFitnessPalCredentials(String str, String str2, String str3);

    void setiMyFitnessPalConnectionActivity(MyFitnessPalConnectionViewContract myFitnessPalConnectionViewContract);

    void startSync();

    void updateMyFitnessPalDownloadPreference(boolean z);
}
