package com.nautilus.omni.appmodules.mainactivity.view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Callbacks;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.home.view.HomeFragment;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainMenuHelper;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainPresenter;
import com.nautilus.omni.appmodules.navigationdrawer.NavigationDrawerHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.HasComponent;
import com.nautilus.omni.dependencyinjection.components.DaggerMainOmniComponent;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.nautilus.omni.permissions.Action;
import com.nautilus.omni.permissions.PermissionCallbacks;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.syncservices.syncserviceshelpers.MainActivitySyncHelper;
import com.nautilus.omni.util.AlertDialogUtil;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.LocationSettingsUtil;
import com.nautilus.omni.util.Util;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainViewContract, Callbacks.NavDrawerCallback, AwardsFragment.OnAwardBackNavigationListener, HasComponent<MainOmniComponent>, PermissionCallbacks, LocationSettingsUtil.LocationSettingsResponse {
    private static final String CURRENT_NAV_DRAWER_ITEM = "CURRENT_NAV_DRAWER_ITEM";
    private static final String CURRENT_SCREEN = "CURRENT_SCREEN";
    private static final String CURRENT_SCREEN_TITLE = "CURRENT_SCREEN_TITLE";
    private static final String IS_SYNC_FINISHED_KEY = "IS_SYNC_FINISHED_KEY";
    private static final String SHOULD_START_AUTO_SYNC = "SHOULD_START_AUTO_SYNC";
    public static final String TAG = MainActivity.class.getSimpleName();
    public int mCurrentNavDrawerItem;
    private String mCurrentScreen;
    public User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    public boolean mGoalsEnabled;
    private Handler mHandler;
    public boolean mHasDefaultDevice;
    private ImageView mImgViewToolbarLogo;
    private boolean mIsBackNavigationFromAwardsScreen;
    MainOmniComponent mMainOmniComponent;
    @Inject
    MainPresenter mMainPresenter;
    public NavigationDrawerHelper mNavigationDrawerHelper;
    @Inject
    PermissionPresenter mPermissionPresenter;
    private boolean mSavedInstanceCalled;
    public boolean mShouldShowTimeGoalUpdatedDialog;
    private boolean mShouldStartAutoSync;
    private boolean mSkipExecutedOnConnectionWizard;
    private boolean mStartedFromConnectionWizard;
    private MainActivitySyncHelper mSyncDataHelper;
    public Toolbar mToolbar;
    public MainMenuHelper mToolbarMenuHelper;
    private TextView mTxtViewToolbarTitle;
    private Dao<User, Integer> mUserDao;
    public int mUserIndex;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        createMainOmniComponent();
        this.mMainOmniComponent.inject(this);
        this.mSavedInstanceCalled = false;
        getSharedPreferences();
        initDatabaseComponents();
        initSyncDataHelper();
        initToolbar();
        initNavigationDrawer();
        registerBroadcastReceivers();
        if (savedInstanceState == null) {
            showFirstFragment();
        }
        getIntentExtras();
        askIfSyncDataProcessIsFinished();
        initGoalsHelper();
    }

    private void createMainOmniComponent() {
        this.mMainOmniComponent = DaggerMainOmniComponent.builder().appComponent(getAppComponent()).mainModule(new MainModule(this)).goalsModule(new GoalsModule(new GoalsFragment())).awardsModule(new AwardsModule(new AwardsFragment())).deviceModule(new DeviceModule(new DeviceFragment())).homeModule(new HomeModule(new HomeFragment())).journalModule(new JournalModule(new JournalFragment())).build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.mToolbarMenuHelper.initMenu(menu);
        return true;
    }

    private void getSharedPreferences() {
        this.mHasDefaultDevice = this.mSettings.getBoolean(Preferences.HAS_OMNI_TRAINER_DEVICE_SET, false);
        this.mUserIndex = this.mSettings.getInt(Preferences.USER_INDEX, -1);
        this.mGoalsEnabled = this.mSettings.getBoolean(Preferences.GOALS_ENABLED, false);
        this.mShouldShowTimeGoalUpdatedDialog = this.mSettings.getBoolean(Preferences.SHOW_TIME_GOAL_UPDATED_DIALOG, false);
    }

    private void initDatabaseComponents() {
        this.mDataBaseHelper = ((OmniApplication) getApplication()).getAppComponent().getDatabaseHelper();
        try {
            getCurrentUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentUser() throws SQLException {
        this.mUserDao = this.mDataBaseHelper.getUserDao();
        List<User> usersList = this.mUserDao.queryBuilder().where().eq(User.USER_INDEX, Integer.valueOf(this.mUserIndex)).query();
        if (usersList.size() > 0) {
            this.mCurrentUser = usersList.get(0);
        }
    }

    private void initToolbar() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mTxtViewToolbarTitle = (TextView) this.mToolbar.findViewById(R.id.toolbar_title);
        this.mImgViewToolbarLogo = (ImageView) this.mToolbar.findViewById(R.id.img_view_max_trainer_logo);
        setSupportActionBar(this.mToolbar);
        this.mToolbarMenuHelper = new MainMenuHelper(this, this.mSettings, this.mSyncDataHelper, this.mPermissionPresenter);
    }

    private void initNavigationDrawer() {
        this.mNavigationDrawerHelper = new NavigationDrawerHelper(this);
        this.mNavigationDrawerHelper.initNavigationDrawer();
    }

    private void initSyncDataHelper() {
        this.mHandler = new Handler();
        this.mSyncDataHelper = new MainActivitySyncHelper(this, this.mHandler, this.mSettings, this.mDataBaseHelper);
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mSyncDataHelper.updateStatusWhenFailed, new IntentFilter(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FAILED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mSyncDataHelper.updateStatusWhenFinished, new IntentFilter(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FINISHED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mSyncDataHelper.startManualSyncProcessReceiver, new IntentFilter(BroadcastKeys.START_SYNC_PROCESS));
    }

    private void showFirstFragment() {
        if (this.mSettings.getBoolean(Preferences.IS_CONNECTION_WIZARD_STARTED_FROM_DEVICE_SCREEN, false)) {
            changeFragmentWithTitle(getString(R.string.title_device), DeviceFragment.TAG, false);
        } else {
            changeFragmentWithTitle(getString(R.string.home_screen_title), HomeFragment.TAG, false);
        }
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(Preferences.IS_CONNECTION_WIZARD_STARTED_FROM_DEVICE_SCREEN, false);
        editor.commit();
    }

    private void getIntentExtras() {
        this.mShouldStartAutoSync = getIntent().getBooleanExtra("SHOULD_START_AUTO_SYNC", false);
        this.mSkipExecutedOnConnectionWizard = getIntent().getBooleanExtra(Constants.CONNECTION_WIZARD_SKIP_EXECUTED, false);
        this.mStartedFromConnectionWizard = getIntent().getBooleanExtra(Constants.STARTED_FROM_CONNECTION_WIZARD, false);
    }

    private void askIfSyncDataProcessIsFinished() {
        if (this.mStartedFromConnectionWizard && !this.mSkipExecutedOnConnectionWizard && !this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, false)) {
            this.mSyncDataHelper.updateSyncStatusWhenFinishedSync();
        }
    }

    private void initGoalsHelper() {
        this.mMainPresenter.setGoalsEnabled(this.mGoalsEnabled);
        this.mMainPresenter.setShouldShowTimeGoalUpdatedDialog(this.mShouldShowTimeGoalUpdatedDialog);
        this.mMainPresenter.checkCurrentGoals();
    }

    public void onDrawerItemClicked(View view, int position) {
        this.mNavigationDrawerHelper.onDrawerItemClicked(view, position);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mSavedInstanceCalled = false;
        checkMFPStatus();
        updateSyncState();
        checkIfShouldShowADifferentScreen();
        checkIfShouldStartAutoSync();
    }

    private void checkIfShouldStartAutoSync() {
        if (this.mShouldStartAutoSync) {
            startScanProcess();
        }
    }

    private void updateSyncState() {
        if (this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, false)) {
            this.mSyncDataHelper.setSyncDataState();
        } else if (this.mSettings.getBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, false)) {
            this.mSyncDataHelper.setSyncErrorState();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        this.mToolbarMenuHelper.executeMenuTransition(item);
        return super.onOptionsItemSelected(item);
    }

    private void checkIfShouldShowADifferentScreen() {
        if (this.mSettings.getBoolean(Preferences.SHOW_DEVICE_VIEW, false)) {
            changeFragmentWithTitle(getString(R.string.title_device), DeviceFragment.TAG, true);
        } else if (this.mSettings.getBoolean(Preferences.SHOW_HOME_SCREEN, false)) {
            changeFragmentWithTitle(getString(R.string.home_screen_title), HomeFragment.TAG, true);
        }
        this.mSettings.edit().putBoolean(Preferences.SHOW_DEVICE_VIEW, false).commit();
        this.mSettings.edit().putBoolean(Preferences.SHOW_DEVICE_SCREEN_FROM_HELP, false).commit();
        this.mSettings.edit().putBoolean(Preferences.SHOW_HOME_SCREEN, false).commit();
        this.mSettings.edit().putBoolean(Preferences.SHOW_HOME_SCREEN_FROM_HELP, false).commit();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mSavedInstanceCalled = true;
        outState.putBoolean(IS_SYNC_FINISHED_KEY, this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, false));
        outState.putBoolean("SHOULD_START_AUTO_SYNC", this.mShouldStartAutoSync);
        outState.putString(CURRENT_SCREEN_TITLE, this.mTxtViewToolbarTitle.getText().toString());
        outState.putString(CURRENT_SCREEN, this.mCurrentScreen);
        outState.putInt(CURRENT_NAV_DRAWER_ITEM, this.mCurrentNavDrawerItem);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mSavedInstanceCalled = false;
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, savedInstanceState.getBoolean(IS_SYNC_FINISHED_KEY));
        this.mShouldStartAutoSync = savedInstanceState.getBoolean("SHOULD_START_AUTO_SYNC");
        this.mTxtViewToolbarTitle.setText(savedInstanceState.getString(CURRENT_SCREEN_TITLE));
        this.mCurrentScreen = savedInstanceState.getString(CURRENT_SCREEN);
        this.mCurrentNavDrawerItem = savedInstanceState.getInt(CURRENT_NAV_DRAWER_ITEM);
        updateToolbarTitle(this.mTxtViewToolbarTitle.getText().toString(), this.mCurrentScreen);
    }

    public void onBackPressed() {
        if (this.mCurrentScreen.equalsIgnoreCase(HomeFragment.TAG)) {
            HomeFragment myFragment = (HomeFragment) getFragmentManager().findFragmentByTag(HomeFragment.TAG);
            if (myFragment == null || !myFragment.isVisible() || !myFragment.isOpenYourAchievementsFragment()) {
                finish();
                return;
            }
            myFragment.closeDetailView();
            myFragment.setOpenYourAchievementsFragment(false);
        } else if (this.mCurrentScreen.equals(AwardsFragment.TAG)) {
            handleBackNavigationFromAwards();
        } else {
            changeFragmentWithTitle(getString(R.string.home_screen_title), HomeFragment.TAG, false);
            this.mNavigationDrawerHelper.updateSelectedMenu(HomeFragment.TAG, this.mNavigationDrawerHelper.mNavigationDrawerList.getChildAt(0));
        }
    }

    public void checkMFPStatus() {
        if (!this.mSettings.getBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, false)) {
            return;
        }
        if (isAppInstalled(MyFitnessPalConstants.MFP_PACKAGE)) {
            new AlertDialog.Builder(this).setTitle(R.string.mfp_dialog_remember_title).setMessage(R.string.mfp_dialog_remember_copy).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.mSettings.edit().putBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, false).commit();
                }
            }).create().show();
        } else {
            this.mSettings.edit().putBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, false).commit();
        }
    }

    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getInstallerPackageName(packageName);
            return Boolean.TRUE.booleanValue();
        } catch (Exception e) {
            return Boolean.FALSE.booleanValue();
        }
    }

    public void changeFragmentWithTitle(String screenTitle, String tag, boolean isNavigationFromSettings) {
        updateToolbarTitle(screenTitle, tag);
        changeFragment(tag, (Bundle) null, isNavigationFromSettings);
        this.mNavigationDrawerHelper.closeDrawer();
    }

    private void updateToolbarTitle(String screenTitle, String tag) {
        if (tag.equals(HomeFragment.TAG)) {
            this.mTxtViewToolbarTitle.setVisibility(8);
            this.mImgViewToolbarLogo.setVisibility(0);
            return;
        }
        this.mTxtViewToolbarTitle.setVisibility(0);
        this.mImgViewToolbarLogo.setVisibility(8);
        this.mTxtViewToolbarTitle.setText(screenTitle);
    }

    private void handleBackNavigationFromAwards() {
        if (this.mIsBackNavigationFromAwardsScreen) {
            changeFragmentWithTitle(getString(R.string.home_screen_title), HomeFragment.TAG, false);
            this.mNavigationDrawerHelper.updateSelectedMenu(HomeFragment.TAG, this.mNavigationDrawerHelper.mNavigationDrawerList.getChildAt(0));
            return;
        }
        closeDetailView();
    }

    private void closeDetailView() {
        AwardsFragment awardsFragment = (AwardsFragment) getFragmentManager().findFragmentByTag(AwardsFragment.TAG);
        if (awardsFragment != null) {
            awardsFragment.closeDetailView();
        }
    }

    public void executeBackNavigation() {
        this.mIsBackNavigationFromAwardsScreen = true;
        onBackPressed();
        this.mIsBackNavigationFromAwardsScreen = false;
    }

    public void changeFragment(String fragmentTag, Bundle args, boolean isNavigationFromSettings) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragments_main_content, getFragmentToShow(fragmentTag), fragmentTag);
        if (isActivityActive()) {
            fragmentTransaction.commit();
        }
        this.mCurrentScreen = fragmentTag;
    }

    private Fragment getFragmentToShow(String fragmentTag) {
        if (fragmentTag.equals(HomeFragment.TAG)) {
            return this.mMainOmniComponent.getHomeFragment();
        }
        if (fragmentTag.equals(GoalsFragment.TAG)) {
            return this.mMainOmniComponent.getGoalFragment();
        }
        if (fragmentTag.equals(JournalFragment.TAG)) {
            return this.mMainOmniComponent.getJournalFragment();
        }
        if (fragmentTag.equals(AwardsFragment.TAG)) {
            return this.mMainOmniComponent.getAwardsFragment();
        }
        if (fragmentTag.equals(DeviceFragment.TAG)) {
            return this.mMainOmniComponent.getDeviceFragment();
        }
        return null;
    }

    private boolean isActivityActive() {
        return !isFinishing() && !this.mSavedInstanceCalled;
    }

    public void onDestroy() {
        unregisterBroadcastReceivers();
        this.mSyncDataHelper.releaseDatabaseHelper();
        if (this.mMainPresenter != null) {
            this.mMainPresenter.onDestroy();
        }
        super.onDestroy();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mSyncDataHelper.updateStatusWhenFailed);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mSyncDataHelper.updateStatusWhenFinished);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mSyncDataHelper.startManualSyncProcessReceiver);
    }

    public MainOmniComponent getComponent() {
        return this.mMainOmniComponent;
    }

    public void permissionAccepted(int actionCode) {
        switch (actionCode) {
            case 2:
                this.mToolbarMenuHelper.showShareComponent();
                return;
            case 3:
                if (this.mShouldStartAutoSync) {
                    startAutomaticSyncProcess();
                    return;
                } else {
                    this.mToolbarMenuHelper.startSyncManual();
                    return;
                }
            default:
                return;
        }
    }

    private void startAutomaticSyncProcess() {
        this.mSyncDataHelper.startAutomaticSyncProcess();
        this.mShouldStartAutoSync = false;
    }

    public void permissionDenied(int actionCode, List<String> list) {
        this.mShouldStartAutoSync = false;
        DialogInterface.OnClickListener negativeButtonListener = null;
        int message = 0;
        switch (actionCode) {
            case 2:
                negativeButtonListener = null;
                message = R.string.storage_permission_required_denied;
                break;
            case 3:
                message = R.string.location_permission_required_denied;
                negativeButtonListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.handlePermissionsDialogNegativeAction();
                    }
                };
                break;
        }
        showPermissionsDialog(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), (String) null));
                MainActivity.this.startActivity(intent);
            }
        }, negativeButtonListener, R.string.app_name, message, this, R.string.settings_text, R.string.permi_dialog_negative_button_text);
    }

    public void showRationale(final int actionCode, final String[] permissions, final Action action) {
        DialogInterface.OnClickListener positiveButtonListener = null;
        DialogInterface.OnClickListener negativeButtonListener = null;
        int message = 0;
        switch (actionCode) {
            case 2:
                message = R.string.storage_permission_required_rationale;
                negativeButtonListener = null;
                positiveButtonListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.mPermissionPresenter.requestPermission(action);
                    }
                };
                break;
            case 3:
                message = R.string.location_permission_required_rationale;
                positiveButtonListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int buttonId) {
                        MainActivity.this.mPermissionPresenter.requestPermission(permissions, actionCode);
                    }
                };
                negativeButtonListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.handlePermissionsDialogNegativeAction();
                    }
                };
                break;
        }
        showPermissionsDialog(positiveButtonListener, negativeButtonListener, R.string.app_name, message, this, R.string.permi_dialog_ok_button_text, R.string.permi_dialog_negative_button_text);
    }

    /* access modifiers changed from: private */
    public void handlePermissionsDialogNegativeAction() {
        if (this.mSettings.getBoolean(Preferences.SYNC_FROM_DEVICE_VIEW, false)) {
            this.mSyncDataHelper.sendEnableSyncNowButtonInDeviceScreenBroadcast();
        }
        this.mSettings.edit().putBoolean(Preferences.SYNC_FROM_DEVICE_VIEW, false).commit();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
            case 1:
            case 2:
            case 3:
                this.mPermissionPresenter.checkGrantedPermission(grantResults, permissions, requestCode);
                return;
            default:
                return;
        }
    }

    public void OnLocationStatusSuccess() {
        this.mPermissionPresenter.requestAccessFineCoarseLocationPermission();
    }

    public void OnLocationStatusUnavailable() {
    }

    public void startScanProcess() {
        this.mShouldStartAutoSync = false;
        Util.enableBluetooth(this);
    }

    private void checkLocationSettingStatus() {
        this.mShouldStartAutoSync = false;
        checkLocationSettingsStatus(this, this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                checkBluetoothRequest(resultCode);
                return;
            case 1001:
                checkLocationRequest(resultCode);
                return;
            default:
                return;
        }
    }

    private void checkLocationRequest(int resultCode) {
        switch (resultCode) {
            case -1:
                this.mPermissionPresenter.requestAccessFineCoarseLocationPermission();
                return;
            default:
                return;
        }
    }

    private void checkBluetoothRequest(int resultCode) {
        switch (resultCode) {
            case -1:
                checkLocationSettingStatus();
                return;
            default:
                checkLocationSettingStatus();
                return;
        }
    }

    public void showTimeGoalUpdatedDialog() {
        AlertDialogUtil.buildAlertDialog(getString(R.string.goals_time_goal_updated_dialog_title), getString(R.string.goals_time_goal_updated_dialog_message), R.string.goals_time_goal_updated_dialog_check_goals_button, R.string.goals_time_goal_updated_dialog_close_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.changeFragmentWithTitle(MainActivity.this.getString(R.string.title_goals), GoalsFragment.TAG, false);
                MainActivity.this.mNavigationDrawerHelper.updateSelectedMenu(GoalsFragment.TAG, MainActivity.this.mNavigationDrawerHelper.mNavigationDrawerList.getChildAt(1));
            }
        }, (DialogInterface.OnClickListener) null, this);
        this.mSettings.edit().putBoolean(Preferences.SHOW_TIME_GOAL_UPDATED_DIALOG, false).commit();
    }
}
