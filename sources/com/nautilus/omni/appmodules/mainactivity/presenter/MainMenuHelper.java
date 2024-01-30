package com.nautilus.omni.appmodules.mainactivity.presenter;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.MenuItem;
import com.example.sharelibrary.OnSelectPackageListener;
import com.example.sharelibrary.ShareDialogComponent;
import com.nautilus.omni.R;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.syncservices.syncserviceshelpers.MainActivitySyncHelper;
import com.nautilus.omni.util.Util;
import java.lang.ref.WeakReference;

public class MainMenuHelper {
    private static final int USER_1 = 1;
    private static final int USER_2 = 2;
    private static final int USER_3 = 3;
    private static final int USER_4 = 4;
    private Menu mActionbarMenu;
    /* access modifiers changed from: private */
    public MainActivity mMainActivity = ((MainActivity) this.mMainActivityReference.get());
    private WeakReference<MainActivity> mMainActivityReference;
    private PermissionPresenter mPermissionPresenter;
    private SharedPreferences mSettings;
    private MainActivitySyncHelper mSyncDataHelper;
    /* access modifiers changed from: private */
    public ShareDialogComponent shareDialogComponent;

    public MainMenuHelper(MainActivity mainActivity, SharedPreferences settings, MainActivitySyncHelper syncDataHelper, PermissionPresenter permissionPresenter) {
        this.mMainActivityReference = new WeakReference<>(mainActivity);
        this.mSettings = settings;
        this.mPermissionPresenter = permissionPresenter;
        this.mSyncDataHelper = syncDataHelper;
        this.shareDialogComponent = new ShareDialogComponent(mainActivity);
        this.shareDialogComponent.setOnPackageSelectListener(new OnSelectPackageListener() {
            public void onPackageSelect(String pPackage, ResolveInfo resolveInfo) {
                Util.updateShareMessageAccordingWithAppSelected(MainMenuHelper.this.mMainActivity, Util.buildShareIntent(MainMenuHelper.this.mMainActivity, pPackage, resolveInfo), MainMenuHelper.this.mMainActivity, R.id.content);
                MainMenuHelper.this.shareDialogComponent.hide();
            }
        });
    }

    public void initMenu(Menu menu) {
        this.mActionbarMenu = menu;
        this.mSyncDataHelper.setActionbarMenu(this.mActionbarMenu);
        updateToolbarMenu();
    }

    public void executeMenuTransition(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bluetooth:
                this.mMainActivity.changeFragmentWithTitle(this.mMainActivity.getString(R.string.title_device), DeviceFragment.TAG, false);
                return;
            case R.id.action_settings:
                this.mMainActivity.startActivity(new Intent(this.mMainActivity, SettingsActivity.class));
                return;
            case R.id.action_devices:
                this.mMainActivity.changeFragmentWithTitle(this.mMainActivity.getString(R.string.title_device), DeviceFragment.TAG, false);
                return;
            case R.id.action_awards_types:
                this.mMainActivity.startActivity(new Intent(this.mMainActivity, AwardTypesActivity.class));
                return;
            case R.id.action_share:
                this.mPermissionPresenter.setPermissionCallbacks(this.mMainActivity);
                this.mPermissionPresenter.requestWriteExternalStorangePermission();
                return;
            case R.id.action_sync_now:
                this.mPermissionPresenter.setPermissionCallbacks(this.mMainActivity);
                this.mMainActivity.startScanProcess();
                return;
            default:
                return;
        }
    }

    public void startSyncManual() {
        this.mSyncDataHelper.startManualSyncProcess();
    }

    public void showShareComponent() {
        this.shareDialogComponent.show();
    }

    public void hideShareComponent() {
        this.shareDialogComponent.hide();
    }

    public void updateToolbarMenu() {
        if (this.mActionbarMenu != null) {
            MenuItem item_share = this.mActionbarMenu.findItem(R.id.action_share);
            MenuItem item_settings = this.mActionbarMenu.findItem(R.id.action_settings);
            MenuItem item_bluetooth = this.mActionbarMenu.findItem(R.id.action_bluetooth);
            MenuItem item_users = this.mActionbarMenu.findItem(R.id.action_users);
            MenuItem item_devices = this.mActionbarMenu.findItem(R.id.action_devices);
            MenuItem item_award_types = this.mActionbarMenu.findItem(R.id.action_awards_types);
            MenuItem item_sync = this.mActionbarMenu.findItem(R.id.action_sync_now);
            Fragment fragmentJournal = this.mMainActivity.getFragmentManager().findFragmentByTag(JournalFragment.TAG);
            Fragment fragmentGoals = this.mMainActivity.getFragmentManager().findFragmentByTag(GoalsFragment.TAG);
            Fragment fragmentAwards = this.mMainActivity.getFragmentManager().findFragmentByTag(AwardsFragment.TAG);
            updateBluetoothIconAccordingToSyncState(item_bluetooth);
            if (fragmentJournal != null && fragmentJournal.isVisible()) {
                initMenuForJournalScreen(item_share, item_settings, item_bluetooth, item_devices, item_users, item_award_types);
            } else if (fragmentGoals != null && fragmentGoals.isVisible()) {
                initMenuForGoalsScreen(item_share, item_users, item_settings, item_devices, item_bluetooth, item_award_types);
            } else if (fragmentAwards == null || !fragmentAwards.isVisible()) {
                initMenuForAllRemainingScreens(item_share, item_users, item_settings, item_devices, item_bluetooth, item_award_types);
            } else {
                initMenuForAwardsScreen(item_share, item_users, item_settings, item_devices, item_bluetooth, item_award_types);
            }
            initSyncMenu(item_sync);
        }
    }

    private void updateBluetoothIconAccordingToSyncState(MenuItem item_bluetooth) {
        if (this.mSyncDataHelper.getSyncButtonUpdateCounter() != 0 || wasNavigationFromSettingsOrHelpSections()) {
            this.mSyncDataHelper.updateBluetoothIcon(item_bluetooth, false);
        } else {
            this.mSyncDataHelper.updateBluetoothIcon(item_bluetooth, true);
        }
        resetNavigationFromSettingsOrHelpSectionis();
    }

    private void resetNavigationFromSettingsOrHelpSectionis() {
        this.mSettings.edit().putBoolean("NAVIGATION_FROM_SETTINGS", false).commit();
        this.mSettings.edit().putBoolean("NAVIGATION_FROM_SETTINGS", false).commit();
    }

    private boolean wasNavigationFromSettingsOrHelpSections() {
        if (this.mSettings.getBoolean("NAVIGATION_FROM_SETTINGS", false) || this.mSettings.getBoolean("NAVIGATION_FROM_SETTINGS", false)) {
            return true;
        }
        return false;
    }

    private void initMenuForJournalScreen(MenuItem item_share, MenuItem item_settings, MenuItem item_bluetooth, MenuItem item_devices, MenuItem item_users, MenuItem item_award_types) {
        item_share.setVisible(true);
        item_devices.setVisible(true);
        item_settings.setVisible(true);
        item_bluetooth.setVisible(false);
        item_users.setVisible(false);
        item_award_types.setVisible(false);
    }

    private void initMenuForGoalsScreen(MenuItem item_share, MenuItem item_users, MenuItem item_settings, MenuItem item_devices, MenuItem item_bluetooth, MenuItem item_award_types) {
        item_settings.setVisible(true);
        item_devices.setVisible(true);
        item_share.setVisible(false);
        item_users.setVisible(false);
        item_bluetooth.setVisible(false);
        item_award_types.setVisible(false);
    }

    private void initMenuForAwardsScreen(MenuItem item_share, MenuItem item_users, MenuItem item_settings, MenuItem item_devices, MenuItem item_bluetooth, MenuItem item_award_types) {
        item_settings.setVisible(true);
        item_devices.setVisible(true);
        item_share.setVisible(true);
        item_award_types.setVisible(true);
        item_users.setVisible(false);
        item_bluetooth.setVisible(false);
    }

    private void initMenuForAllRemainingScreens(MenuItem item_share, MenuItem item_users, MenuItem item_settings, MenuItem item_devices, MenuItem item_bluetooth, MenuItem item_award_types) {
        if (this.mMainActivity.mHasDefaultDevice) {
            setCurrentUserIcon(item_users);
        } else {
            item_users.setVisible(false);
            Preferences.resetPreferences(this.mSettings);
        }
        item_share.setVisible(false);
        item_award_types.setVisible(false);
        item_bluetooth.setVisible(true);
        item_settings.setVisible(true);
        item_devices.setVisible(true);
    }

    private void initSyncMenu(MenuItem item_sync) {
        if (this.mMainActivity.mHasDefaultDevice) {
            item_sync.setVisible(true);
        } else {
            item_sync.setVisible(false);
            Preferences.resetPreferences(this.mSettings);
        }
        if (this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, true)) {
            disableSyncNowMenuOptionWhileSyncInProgress();
        } else {
            enableSyncNowMenuOptionWhenDoneSyncing();
        }
    }

    private void setCurrentUserIcon(MenuItem item_users) {
        switch (this.mSettings.getInt(Preferences.USER_INDEX, -1)) {
            case 1:
                item_users.setIcon(R.drawable.ic_user_number_1_ab);
                break;
            case 2:
                item_users.setIcon(R.drawable.ic_user_number_2_ab);
                break;
            case 3:
                item_users.setIcon(R.drawable.ic_user_number_3_ab);
                break;
            case 4:
                item_users.setIcon(R.drawable.ic_user_number_4_ab);
                break;
        }
        item_users.setVisible(true);
    }

    public void disableSyncNowMenuOptionWhileSyncInProgress() {
        if (this.mActionbarMenu != null) {
            this.mActionbarMenu.findItem(R.id.action_sync_now).setEnabled(false);
        }
    }

    public void enableSyncNowMenuOptionWhenDoneSyncing() {
        if (this.mActionbarMenu != null) {
            this.mActionbarMenu.findItem(R.id.action_sync_now).setEnabled(true);
        }
    }
}
