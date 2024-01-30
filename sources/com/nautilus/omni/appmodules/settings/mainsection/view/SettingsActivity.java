package com.nautilus.omni.appmodules.settings.mainsection.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitActivity;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenterContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.about.AboutActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalDisconnectionActivity;
import com.nautilus.omni.appmodules.settings.underarmour.connect.ConnectUnderArmourActivity;
import com.nautilus.omni.appmodules.settings.underarmour.disconnect.DisconnectUnderArmourActivity;
import com.nautilus.omni.dependencyinjection.components.DaggerSettingsComponent;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.nautilus.omni.permissions.Action;
import com.nautilus.omni.permissions.PermissionCallbacks;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.syncservices.syncserviceshelpers.SyncProcessService;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.LocationSettingsUtil;
import com.nautilus.omni.util.ToastUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements SettingsViewContract, PermissionCallbacks, LocationSettingsUtil.LocationSettingsResponse {
    public static final String TAG = SettingsActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public Menu mActionbarMenu;
    /* access modifiers changed from: private */
    public boolean mIsFirstInit;
    MenuItem mMenuItemBluetooth;
    MenuItem mMenuItemSyncNow;
    @Inject
    PermissionPresenter mPermissionPresenter;
    @Inject
    SettingsPresenterContract mSettingsPresenter;
    @BindView(2131755181)
    Spinner mSpinnerUnits;
    @BindView(2131755140)
    Toolbar mToolbar;
    @BindView(2131755183)
    TextView mTxtViewGoogleFitStatus;
    @BindView(2131755185)
    TextView mTxtViewMFPStatus;
    @BindView(2131755162)
    TextView mTxtViewToolbarTitle;
    @BindView(2131755187)
    TextView mTxtViewUnderArmourStatus;
    @BindView(2131755189)
    TextView mTxtViewVersion;
    public BroadcastReceiver stopSyncWhenNoMachineFound = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SettingsActivity.this.mSettingsPresenter.updateIsSyncingErrorStatePreference(true);
            if (SettingsActivity.this.mActionbarMenu != null) {
                SettingsActivity.this.mSettingsPresenter.updateBluetoothIconToErrorState();
            }
            SettingsActivity.this.enableSyncNowMenuOptionWhenDoneSyncing();
            SettingsActivity.this.mSettingsPresenter.showSyncErrorToast();
            Log.d(SettingsActivity.TAG, "DEBUG - MACHINE NOT FOUND DURING SCAN..");
        }
    };
    public BroadcastReceiver updateStatusWhenFailed = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SettingsActivity.this.mSettingsPresenter.updateIsSyncingErrorStatePreference(true);
            if (SettingsActivity.this.mActionbarMenu != null) {
                SettingsActivity.this.mSettingsPresenter.updateBluetoothIconToErrorState();
            }
            SettingsActivity.this.enableSyncNowMenuOptionWhenDoneSyncing();
            SettingsActivity.this.mSettingsPresenter.showSyncErrorToast();
            Log.d(SettingsActivity.TAG, "DEBUG - SYNC DATA FAILED");
        }
    };
    public BroadcastReceiver updateStatusWhenFinished = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SettingsActivity.this.mSettingsPresenter.updateIsSyncingErrorStatePreference(false);
            if (SettingsActivity.this.mActionbarMenu != null) {
                SettingsActivity.this.mSettingsPresenter.updateBluetoothIcon(false);
            }
            SettingsActivity.this.enableSyncNowMenuOptionWhenDoneSyncing();
            SettingsActivity.this.mSettingsPresenter.showSuccessfulSyncToast();
            Log.d(SettingsActivity.TAG, "DEBUG - SYNC FINISHED");
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_settings);
        ButterKnife.bind((Activity) this);
        setActivityGraph();
        this.mIsFirstInit = true;
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        setSupportActionBar(this.mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        this.mTxtViewToolbarTitle.setText(getString(R.string.title_activity_settings));
    }

    private void setActivityGraph() {
        DaggerSettingsComponent.builder().appComponent(getAppComponent()).settingsModule(new SettingsModule(this)).build().inject(this);
    }

    private void initViews() {
        this.mSettingsPresenter.loadAppVersion();
        this.mSettingsPresenter.loadUnitsForSpinner();
    }

    @OnClick({2131755190})
    public void settingsButtonPrivacyPolicyClick() {
        displayPrivatePolicyInfo();
    }

    @OnClick({2131755191})
    public void settingsButtonEulaClick() {
        displayUserLicenceAgreement();
    }

    @OnClick({2131755192})
    public void settingsButtonAttributions() {
        displayAttributions();
    }

    @OnClick({2131755182})
    public void settingsButtonGoogleFitClick() {
        this.mSettingsPresenter.callGoogleFitActivity();
    }

    @OnClick({2131755184})
    public void settingsButtonMFPClick() {
        this.mSettingsPresenter.callMFPActivity();
    }

    @OnClick({2131755188})
    public void settingsEmailUsClick() {
        this.mSettingsPresenter.sendEmail(this);
    }

    @OnClick({2131755186})
    public void settingsButtonUnderArmourClick() {
        this.mSettingsPresenter.callUnderArmourActivity();
    }

    public void initUnitsSpinner(int unit) {
        ArrayAdapter<CharSequence> mSpinnerUnitsAdapter = ArrayAdapter.createFromResource(this, R.array.units, R.layout.custom_settings_spinner_item);
        mSpinnerUnitsAdapter.setDropDownViewResource(R.layout.custom_settings_dropdown_item);
        this.mSpinnerUnits.setAdapter(mSpinnerUnitsAdapter);
        this.mSpinnerUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!SettingsActivity.this.mIsFirstInit) {
                    SettingsActivity.this.mSettingsPresenter.updateUnits(position);
                }
                boolean unused = SettingsActivity.this.mIsFirstInit = false;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpinnerUnits.setSelection(unit);
    }

    public void displayMenuItemSync(boolean display) {
        if (display) {
            this.mMenuItemSyncNow.setVisible(display);
        } else {
            this.mMenuItemSyncNow.setVisible(display);
        }
    }

    public void enableMenuItemSync(boolean enable) {
        if (enable) {
            disableSyncNowMenuOptionWhileSyncInProgress();
        } else {
            enableSyncNowMenuOptionWhenDoneSyncing();
        }
    }

    public void disableSyncNowMenuOptionWhileSyncInProgress() {
        if (this.mActionbarMenu != null) {
            this.mMenuItemSyncNow.setEnabled(false);
        }
    }

    public void enableSyncNowMenuOptionWhenDoneSyncing() {
        if (this.mActionbarMenu != null) {
            this.mMenuItemSyncNow.setEnabled(true);
        }
    }

    public void showWorkoutSyncSuccessfulToast(String message) {
        ToastUtil.createSuccessToast(this, message, 0).show();
    }

    public void displayToastIfFromWizardConnection() {
        ToastUtil.createSuccessToast(this, getString(R.string.connection_sync_finished), 1).show();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mSettingsPresenter.updatePreferenceNavigationFromSettings(true);
        this.mSettingsPresenter.checkActivityCalled();
    }

    public void executeNormalSettingsResume() {
        registerBroadcastReceivers();
        updateBluetoothIconWhenComingBackFromHelpScreen();
        this.mSettingsPresenter.checkMFPStatus();
        this.mSettingsPresenter.checkGoogleFitStatus();
        this.mSettingsPresenter.checkMFPDownloadStatus();
        this.mSettingsPresenter.checkUnderArmourStatus();
    }

    private void updateBluetoothIconWhenComingBackFromHelpScreen() {
        if (this.mActionbarMenu != null) {
            this.mSettingsPresenter.updateBluetoothIcon(false);
        }
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.updateStatusWhenFailed, new IntentFilter(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FAILED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.updateStatusWhenFinished, new IntentFilter(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FINISHED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.stopSyncWhenNoMachineFound, new IntentFilter(BroadcastKeys.STOP_SYNC_STATE_ON_OTHER_SCREENS));
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.updateStatusWhenFailed);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.updateStatusWhenFinished);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.stopSyncWhenNoMachineFound);
    }

    public void setGoogleFitStatus(String status) {
        this.mTxtViewGoogleFitStatus.setText(status);
    }

    public void showMFPStatus(String status) {
        this.mTxtViewMFPStatus.setText(status);
    }

    public void showUnderArmourStatus(String status) {
        this.mTxtViewUnderArmourStatus.setText(status);
    }

    public void showSyncErrorToast() {
        ToastUtil.createSyncIssueToast(this, getString(R.string.connection_sync_failed), 1).show();
    }

    public void showSuccessfulSyncToast() {
        this.mSettingsPresenter.updatePreferenceSyncToastShowed();
        this.mSettingsPresenter.getToastMessageIfFromWizard();
        this.mSettingsPresenter.getSuccessSyncMessage();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        this.mActionbarMenu = menu;
        initMenu();
        return true;
    }

    private void initMenu() {
        this.mMenuItemBluetooth = this.mActionbarMenu.findItem(R.id.settings_action_bluetooth);
        this.mMenuItemSyncNow = this.mActionbarMenu.findItem(R.id.settings_action_sync_now);
        this.mSettingsPresenter.hasNautilusDeviceSet();
        this.mSettingsPresenter.updateBluetoothIcon(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                navigateBackToMainActivity();
                return true;
            case R.id.settings_action_bluetooth:
                showDeviceScreen();
                return true;
            case R.id.settings_action_devices:
                showDeviceScreen();
                return true;
            case R.id.settings_action_sync_now:
                checkLocationSettingStatus();
                return true;
            default:
                return true;
        }
    }

    private void navigateBackToMainActivity() {
        if (!isFinishing()) {
            this.mSettingsPresenter.updatePreferenceShowDeviceView(false);
            onBackPressed();
        }
    }

    private void executeSyncProcess() {
        startSyncProcessService(SyncProcessService.MANUAL_SYNC);
        this.mSettingsPresenter.updateBluetoothIcon(true);
        disableSyncNowMenuOptionWhileSyncInProgress();
    }

    private void startSyncProcessService(String syncType) {
        Intent syncProcessService = new Intent(getApplicationContext(), SyncProcessService.class);
        syncProcessService.putExtra(SyncProcessService.SYNC_TYPE, syncType);
        startService(syncProcessService);
    }

    public void showDeviceScreen() {
        if (!isFinishing()) {
            this.mSettingsPresenter.updatePreferenceShowDeviceView(true);
            onBackPressed();
        }
    }

    public void showHomeScreen() {
        if (!isFinishing()) {
            this.mSettingsPresenter.updatePreferenceShowHomeScreen(true);
            onBackPressed();
        }
    }

    public void refreshBluetoothMenuItem() {
        this.mMenuItemBluetooth = this.mActionbarMenu.findItem(R.id.settings_action_bluetooth);
    }

    public void changeItemBluetoothIcon(int drawableId) {
        this.mMenuItemBluetooth.setIcon(drawableId);
    }

    public void setBluetoothItemNullAction() {
        this.mMenuItemBluetooth.setActionView((View) null);
    }

    public void bluetoothItemChangeSyncState() {
        changeSyncState(this.mMenuItemBluetooth);
    }

    private void changeSyncState(MenuItem item) {
        ProgressBar progressBar = (ProgressBar) ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.refresh_view, (ViewGroup) null);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue_nautilus), PorterDuff.Mode.SRC_ATOP);
        item.setActionView(progressBar);
    }

    public void updateBluetoothIconToErrorState() {
        this.mMenuItemBluetooth.setActionView((View) null);
        this.mMenuItemBluetooth.setIcon(R.drawable.ic_bluetooth_sync_issue);
    }

    public void setMFPDownloadStatus(boolean isDownloaded) {
        if (!isDownloaded) {
            return;
        }
        if (isAppInstalled(MyFitnessPalConstants.MFP_PACKAGE)) {
            new AlertDialog.Builder(this).setTitle(R.string.mfp_dialog_remember_title).setMessage(R.string.mfp_dialog_remember_copy).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SettingsActivity.this.mSettingsPresenter.updateMyFitnessPalDownloadPreference(false);
                }
            }).create().show();
        } else {
            this.mSettingsPresenter.updateMyFitnessPalDownloadPreference(false);
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

    private void displayPrivatePolicyInfo() {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("TITLE", getString(R.string.settings_title_privacy_policy));
        intent.putExtra(AboutActivity.CONTENT, Constants.PRIVATE_POLICY_PATH);
        startActivity(intent);
    }

    private void displayUserLicenceAgreement() {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("TITLE", getString(R.string.settings_title_eula));
        intent.putExtra(AboutActivity.CONTENT, Constants.USER_LICENCE_AGREEMENT_PATH);
        startActivity(intent);
    }

    private void displayAttributions() {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("TITLE", getString(R.string.settings_title_attributions));
        intent.putExtra(AboutActivity.CONTENT, Constants.ATTRIBUTIONS_PATH);
        startActivity(intent);
    }

    public void startConnectionGoogleFitActivity() {
        Intent intent = new Intent(this, ConnectionGoogleFitActivity.class);
        intent.putExtra("TITLE", getString(R.string.settings_title_google_fit));
        startActivity(intent);
    }

    public void startConfigGoogleFitActivity() {
        Intent intent = new Intent(this, ConfigGoogleFitActivity.class);
        intent.putExtra("TITLE", getString(R.string.google_fit_title));
        intent.putExtra(Preferences.GOOGLE_FIT_SYNC, false);
        startActivity(intent);
    }

    public void openMyFitnessPalDisconnectionActivity() {
        startActivity(new Intent(this, MyFitnessPalDisconnectionActivity.class));
    }

    public void openMyFitnessPalConnectionActivity() {
        startActivity(new Intent(this, MyFitnessPalConnectionActivity.class));
    }

    public void openUnderArmourDisconnectionActivity() {
        startActivity(new Intent(this, DisconnectUnderArmourActivity.class));
    }

    public void openUnderArmourConnectionActivity() {
        startActivity(new Intent(this, ConnectUnderArmourActivity.class));
    }

    private void checkFolder() {
        File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");
        if (direct.exists() || direct.mkdir()) {
        }
    }

    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                File backupDB = new File(data, "//data//com.nautilus.omni//databases//omniDB.db");
                FileChannel src = new FileInputStream(new File(sd, "/BackupFolder/omniDB.db")).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(), 1).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), 1).show();
        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                File currentDB = new File(data, "//data//com.nautilus.omni//databases//omniDB.db");
                File backupDB = new File(sd, "/BackupFolder/omniDB.db");
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(), 1).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), 1).show();
        }
    }

    public void setAppVersion(String version) {
        this.mTxtViewVersion.setText(version);
    }

    public void permissionAccepted(int actionCode) {
        executeSyncProcess();
    }

    public void permissionDenied(int actionCode, List<String> permissionsDenied) {
        if (permissionsDenied.size() > 0) {
            showPermissionsDialog(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", SettingsActivity.this.getPackageName(), (String) null));
                    SettingsActivity.this.startActivity(intent);
                }
            }, R.string.app_name, R.string.location_permission_required_denied, this, R.string.settings_text, R.string.permi_dialog_negative_button_text);
        }
    }

    public void showRationale(final int actionCode, final String[] permissions, Action action) {
        showPermissionsDialog(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.this.mPermissionPresenter.requestPermission(permissions, actionCode);
            }
        }, R.string.app_name, R.string.location_permission_required_rationale, this, R.string.permi_dialog_ok_button_text, R.string.permi_dialog_negative_button_text);
    }

    public void OnLocationStatusSuccess() {
        this.mPermissionPresenter.requestAccessFineCoarseLocationPermission();
    }

    public void OnLocationStatusUnavailable() {
    }

    public void checkLocationSettingStatus() {
        checkLocationSettingsStatus(this, this);
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

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                switch (resultCode) {
                    case -1:
                        this.mPermissionPresenter.requestAccessFineCoarseLocationPermission();
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }
}
