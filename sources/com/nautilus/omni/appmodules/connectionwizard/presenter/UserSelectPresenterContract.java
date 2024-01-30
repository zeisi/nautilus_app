package com.nautilus.omni.appmodules.connectionwizard.presenter;

public interface UserSelectPresenterContract {
    void deleteOmnidata();

    void pause();

    void resetCurrentUserPreference();

    void resume();

    void saveCurrentUserIndexInSharedPreferences(int i);

    void saveDefaultDevicePreference();

    void showSyncingToast();

    void showUnableToConnectDialog();

    void updateSyncPreference(boolean z);
}
