package com.nautilus.omni.appmodules.connectionwizard.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenter;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.dependencyinjection.components.wizard.DaggerWizardConnectionComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.permissions.Action;
import com.nautilus.omni.permissions.PermissionCallbacks;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.syncservices.BLECallbacksHandlerService;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.LocationSettingsUtil;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ConnectionWizardActivity extends BaseActivity implements ConnectionWizardViewContract, PermissionCallbacks, LocationSettingsUtil.LocationSettingsResponse {
    private static final int ANIMATION_DURATION = 1000;
    public static final int SCANNING_DURATION = 6000;
    public static final String TAG = ConnectionWizardActivity.class.getSimpleName();
    @BindView(2131755148)
    RelativeLayout mConnectNowButtonContainer;
    @Inject
    PermissionPresenter mPermissionPresenter;
    @BindView(2131755150)
    ProgressBar mProgressBarConnectNow;
    private Toast mSyncFailedDataToast;
    private View mToastLayout;
    @BindView(2131755149)
    TextView mTxtViewConnectNow;
    @BindView(2131755144)
    TextView mTxtViewSkip;
    @Inject
    WizardPresenter mWizardPresenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_connection);
        ButterKnife.bind((Activity) this);
        DaggerWizardConnectionComponent.builder().appComponent(getAppComponent()).wizardDataModule(new WizardDataModule()).connectionModule(new ConnectionModule(this)).build().inject(this);
        if (getIntent().getBooleanExtra(OmniTrainerListActivity.BACK_WITH_ERROR, false)) {
            showNoOmniTrainerFoundState();
        }
        this.mProgressBarConnectNow.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.connection_progress_bar_color), PorterDuff.Mode.SRC_ATOP);
        initSyncFailedDataToast();
        getSharedPreferences();
        initConnectingDeviceToast();
        startService(new Intent(getApplicationContext(), BLECallbacksHandlerService.class));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mWizardPresenter.resume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.mWizardPresenter.pause();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mWizardPresenter.updateSettingFirstTimeAppExecutes();
    }

    private void initSyncFailedDataToast() {
        View toastLayout = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.custom_regular_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_root));
        ((TextView) toastLayout.findViewById(R.id.custom_toast_text_view)).setText(getString(R.string.connection_sync_failed));
        ((ImageView) toastLayout.findViewById(R.id.custom_toast_image_view)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_toast_bluetooth_grey, (Resources.Theme) null));
        toastLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.toast_sync_failed_color));
        this.mSyncFailedDataToast = new Toast(getApplicationContext());
        this.mSyncFailedDataToast.setGravity(55, 0, 0);
        this.mSyncFailedDataToast.setDuration(1);
        this.mSyncFailedDataToast.setView(toastLayout);
    }

    private void getSharedPreferences() {
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    }

    public void skipToHomeScreen(View view) {
        showConnectFromDeviceScreenDialog();
    }

    private void showConnectFromDeviceScreenDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.connection_connect_using_device_screen));
        alertDialogBuilder.setNegativeButton((int) R.string.cancel, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ConnectionWizardActivity.this.mWizardPresenter.executeSkipAction();
            }
        });
        alertDialogBuilder.create().show();
    }

    public void goToMainWithSkipAction() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(268468224);
        intent.putExtra(Constants.CONNECTION_WIZARD_SKIP_EXECUTED, true);
        startActivity(intent);
    }

    private void sendDisconnectOmniTrainerBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.DISCONNECT_OMNI_TRAINER);
        intent.putExtra(Constants.HAVE_BLE, false);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void initConnectingDeviceToast() {
        this.mToastLayout = getLayoutInflater().inflate(R.layout.custom_animated_toast, (ViewGroup) findViewById(R.id.custom_animated_toast_layout_root));
        ((TextView) this.mToastLayout.findViewById(R.id.custom_toast_text_view)).setText(getString(R.string.connection_omni_trainer_found));
        ((ImageView) this.mToastLayout.findViewById(R.id.custom_toast_image_view)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_toast_bluetooth, (Resources.Theme) null));
    }

    public void connectToOmniTrainer(View view) {
        Util.enableBluetooth(this);
    }

    private void sendScanForOmniTrainerBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.START_OMNI_SCAN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void callOmniTrainerListActivity(ArrayList<OmniData> omniDatas) {
        Intent intent = new Intent(this, OmniTrainerListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.OMNI_TRAINER_LIST, omniDatas);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showOmniTrainerBusyDialog() {
        sendStopOmniTrainerScanBroadcast();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.connection_omni_trainer_busy));
        alertDialogBuilder.setNegativeButton((int) R.string.cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ConnectionWizardActivity.this.showConnectButton();
                ConnectionWizardActivity.this.mTxtViewSkip.setEnabled(true);
            }
        });
        alertDialogBuilder.setPositiveButton((int) R.string.retry, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Util.enableBluetooth(ConnectionWizardActivity.this);
            }
        });
        alertDialogBuilder.create().show();
    }

    public void showConnectingDeviceToast() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_in);
        fadeInAnimation.setDuration(1000);
        this.mToastLayout.setVisibility(0);
        this.mToastLayout.startAnimation(fadeInAnimation);
    }

    public void showNoOmniTrainerFoundState() {
        sendStopOmniTrainerScanBroadcast();
        showConnectButton();
        TextView headerTitle1 = (TextView) findViewById(R.id.connection_header_title1);
        TextView headerTitle2 = (TextView) findViewById(R.id.connection_header_title2);
        headerTitle1.setText(getString(R.string.connection_no_omni_trainer_found_1));
        headerTitle2.setText(R.string.connection_no_omni_trainer_found_2);
        headerTitle1.setTextColor(ContextCompat.getColor(this, R.color.connection_no_devices_found_text_color));
        headerTitle2.setTextColor(ContextCompat.getColor(this, R.color.connection_no_devices_found_text_color));
        showConnectButton();
        this.mTxtViewSkip.setEnabled(true);
        this.mConnectNowButtonContainer.setEnabled(true);
        this.mTxtViewConnectNow.setText(getString(R.string.connection_try_again));
    }

    public void stopScanServices() {
        sendStopOmniTrainerScanBroadcast();
        sendDisconnectOmniTrainerBroadcast();
    }

    private void executeConnectionProcess() {
        this.mTxtViewSkip.setEnabled(false);
        hideConnectButton();
        this.mWizardPresenter.startScan();
        sendScanForOmniTrainerBroadcast();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ConnectionWizardActivity.this.mWizardPresenter.validateScanningProcess();
            }
        }, 6000);
    }

    public void sendConnectToOmniTrainerBroadcast(OmniData omniData) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.OMNI_TRAINER, omniData);
        bundle.putBoolean(Constants.CONNECTION_FROM_OMNI_TRAINER_LIST, false);
        intent.setAction(BroadcastKeys.CONNECT_TO_OMNI_DEVICE);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendStopOmniTrainerScanBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.STOP_OMNI_SCAN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void showErrorConnectingDialog() {
        sendStopOmniTrainerScanBroadcast();
        showConnectingDeviceToastOutAnimation();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.connection_omni_trainer_error));
        alertDialogBuilder.setNegativeButton((int) R.string.cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ConnectionWizardActivity.this.mTxtViewSkip.setEnabled(true);
                ConnectionWizardActivity.this.showConnectButton();
            }
        });
        alertDialogBuilder.setPositiveButton((int) R.string.retry, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Util.enableBluetooth(ConnectionWizardActivity.this);
            }
        });
        if (!isFinishing()) {
            alertDialogBuilder.create().show();
        }
    }

    public void showConnectingDeviceToastOutAnimation() {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_out);
        fadeOutAnimation.setDuration(1000);
        this.mToastLayout.setVisibility(8);
        this.mToastLayout.startAnimation(fadeOutAnimation);
    }

    private void hideConnectButton() {
        this.mConnectNowButtonContainer.setEnabled(false);
        this.mTxtViewConnectNow.setEnabled(false);
        Util.executeViewFadeOutAnimation(getApplicationContext(), 1000, this.mTxtViewConnectNow);
        this.mProgressBarConnectNow.setVisibility(0);
    }

    public void showConnectButton() {
        this.mConnectNowButtonContainer.setEnabled(true);
        this.mTxtViewConnectNow.setEnabled(true);
        Util.executeViewFadeInAnimation(getApplicationContext(), 1000, this.mTxtViewConnectNow);
        this.mProgressBarConnectNow.setVisibility(8);
    }

    public void enableTxtViewSkip() {
        this.mTxtViewSkip.setEnabled(true);
    }

    public void goToSelectUserNumberActivity() {
        startActivity(new Intent(this, SelectUserNumberActivity.class));
    }

    public void showSyncFailedDataToast() {
        this.mSyncFailedDataToast.show();
    }

    public void permissionAccepted(int actionCode) {
        executeConnectionProcess();
    }

    public void permissionDenied(int actionCode, List<String> permissionsDenied) {
        if (permissionsDenied.size() > 0) {
            showPermissionsDialog(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", ConnectionWizardActivity.this.getPackageName(), (String) null));
                    ConnectionWizardActivity.this.startActivity(intent);
                }
            }, R.string.app_name, R.string.location_permission_required_denied, this, R.string.settings_text, R.string.permi_dialog_negative_button_text);
        }
    }

    public void showRationale(final int actionCode, final String[] permissions, Action action) {
        showPermissionsDialog(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ConnectionWizardActivity.this.mPermissionPresenter.requestPermission(permissions, actionCode);
            }
        }, R.string.app_name, R.string.location_permission_required_rationale, this, R.string.permi_dialog_ok_button_text, R.string.permi_dialog_negative_button_text);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    public void checkLocationSettingStatus() {
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
                return;
        }
    }
}
