package com.nautilus.omni.appmodules.device.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.appmodules.device.presenter.DevicePresenter;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Util;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DeviceFragment extends BaseFragment implements IDeviceView {
    private static final int ANIMATION_DURATION = 1000;
    private static final float DISABLED_ALPHA = 0.5f;
    private static final float ENABLED_ALPHA = 1.0f;
    public static final String TAG = DeviceFragment.class.getSimpleName();
    public BroadcastReceiver connectionErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.updateSyncStatusTextView(false);
            DeviceFragment.this.setDeviceInfo();
            Log.d(DeviceFragment.TAG, "DEBUG - DEVICE SCREEN - CONNECTION ERROR");
        }
    };
    public BroadcastReceiver disableSyncNowButtonReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.disableSyncNowButton();
            DeviceFragment.this.disableForgetDeviceButton();
        }
    };
    public BroadcastReceiver enableSyncNowButtonReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.enableSyncNowButton();
            DeviceFragment.this.enableForgetDeviceButton();
            DeviceFragment.this.changeSyncButtonStateToVisible(false);
        }
    };
    private Product mCurrentProduct;
    private DateTimeFormatter mDateFormatter;
    @Inject
    DevicePresenter mDevicePresenter;
    @BindView(2131755270)
    TextView mFirmwareVersionTextView;
    @BindView(2131755271)
    Button mForgetDeviceButton;
    @BindView(2131755267)
    TextView mHardwareVariantTextView;
    private boolean mIsMaxTrainerPaired;
    private boolean mIsSyncInProgress;
    @BindView(2131755264)
    TextView mLastSyncStatusTextView;
    @BindView(2131755265)
    TextView mLastSyncTextView;
    @BindView(2131755268)
    TextView mManufacturerTextView;
    @Inject
    PermissionPresenter mPermissionPresenter;
    @BindView(2131755269)
    TextView mProductModelTextView;
    @BindView(2131755266)
    TextView mStatusTextView;
    @BindView(2131755272)
    LinearLayout mSyncNowButtonContainer;
    @BindView(2131755274)
    ProgressBar mSyncNowProgressBar;
    @BindView(2131755273)
    TextView mTxtViewSyncNow;
    public BroadcastReceiver syncDataFailedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.updateSyncStatusTextView(false);
            DeviceFragment.this.mDevicePresenter.loadDeviceInfo();
            DeviceFragment.this.enableForgetDeviceButton();
            Log.d(DeviceFragment.TAG, "DEBUG - DEVICE SCREEN - SYNC DATA FAILED");
        }
    };
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.updateSyncStatusTextView(false);
            DeviceFragment.this.mDevicePresenter.loadDeviceInfo();
            DeviceFragment.this.enableSyncNowButton();
            DeviceFragment.this.enableForgetDeviceButton();
            Log.d(DeviceFragment.TAG, "DEBUG - DEVICE SCREEN - SYNC FINISHED");
        }
    };
    public BroadcastReceiver unableToConnectReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.updateSyncStatusTextView(false);
            DeviceFragment.this.mDevicePresenter.loadDeviceInfo();
            DeviceFragment.this.enableSyncNowButton();
            DeviceFragment.this.enableForgetDeviceButton();
            Log.d(DeviceFragment.TAG, "DEBUG - UNABLE TO CONNECT TO MAX TRAINER...");
        }
    };
    public BroadcastReceiver unexpectedDisconnectionProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            DeviceFragment.this.updateSyncStatusTextView(false);
            DeviceFragment.this.mDevicePresenter.loadDeviceInfo();
            DeviceFragment.this.enableSyncNowButton();
            DeviceFragment.this.enableForgetDeviceButton();
            Log.d(DeviceFragment.TAG, "DEBUG - UNEXPECTED DISCONNECTION FROM MAX TRAINER...");
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDateFormatter();
        getSharedPreferences();
    }

    private void initDateFormatter() {
        if (DateFormat.is24HourFormat(getActivity().getApplicationContext())) {
            this.mDateFormatter = DateTimeFormat.forPattern(getString(R.string.device_info_date_format24));
        } else {
            this.mDateFormatter = DateTimeFormat.forPattern(getString(R.string.device_info_date_format));
        }
    }

    private void getSharedPreferences() {
        this.mIsSyncInProgress = this.mPreferences.getBoolean(Preferences.SYNC_IN_PROGRESS, false);
    }

    public void onResume() {
        super.onResume();
        registerBroadcastReceivers();
        this.mIsSyncInProgress = this.mPreferences.getBoolean(Preferences.SYNC_IN_PROGRESS, false);
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFailedReceiver, new IntentFilter(BroadcastKeys.SYNC_FAILED));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.unexpectedDisconnectionProcessReceiver, new IntentFilter(BroadcastKeys.UNEXPECTED_DISCONNECTION));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.disableSyncNowButtonReceiver, new IntentFilter(BroadcastKeys.DISABLE_SYNC_NOW_BUTTON));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.unableToConnectReceiver, new IntentFilter(BroadcastKeys.UNABLE_TO_CONNECT));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.enableSyncNowButtonReceiver, new IntentFilter(BroadcastKeys.ENABLE_SYNC_NOW_BUTTON));
    }

    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.connectionErrorReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFailedReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.unexpectedDisconnectionProcessReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.disableSyncNowButtonReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.enableSyncNowButtonReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.unableToConnectReceiver);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind((Object) this, rootView);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mDevicePresenter.setIDeviceView(this);
        setSyncProgressBarColor();
        this.mDevicePresenter.loadDeviceInfo();
    }

    private void setSyncProgressBarColor() {
        this.mSyncNowProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.connection_progress_bar_color), PorterDuff.Mode.SRC_ATOP);
    }

    public void showDeviceInfo(Product product) {
        this.mCurrentProduct = product;
        fillDeviceData();
    }

    private void fillDeviceData() {
        if (this.mDevicePresenter.getCurrentUser() == null && this.mCurrentProduct == null) {
            setDeviceNotPairedInfo();
        } else {
            setDevicePairedInfo();
        }
    }

    private void setDevicePairedInfo() {
        this.mIsMaxTrainerPaired = true;
        setDeviceInfo();
        updateSyncStatusTextView(true);
        checkIfSyncIsInProgress();
    }

    private void checkIfSyncIsInProgress() {
        if (this.mIsSyncInProgress) {
            disableSyncNowButton();
            disableForgetDeviceButton();
            return;
        }
        enableSyncNowButton();
        enableForgetDeviceButton();
    }

    /* access modifiers changed from: private */
    public void setDeviceInfo() {
        if (this.mCurrentProduct != null) {
            this.mStatusTextView.setText(getString(R.string.device_info_status_value));
            this.mHardwareVariantTextView.setText(this.mCurrentProduct.getmHardwareVariant());
            this.mManufacturerTextView.setText(this.mCurrentProduct.getmManufacturer());
            this.mProductModelTextView.setText(this.mCurrentProduct.getmProductModelName());
            this.mFirmwareVersionTextView.setText(this.mCurrentProduct.getmFirmwareVersion());
            this.mLastSyncTextView.setText(this.mDateFormatter.print((ReadableInstant) this.mCurrentProduct.getmLastSync()));
        }
    }

    private void setDeviceNotPairedInfo() {
        this.mIsMaxTrainerPaired = false;
        this.mStatusTextView.setText(getString(R.string.device_info_not_paired_omni_trainer));
        this.mHardwareVariantTextView.setText("");
        this.mManufacturerTextView.setText("");
        this.mProductModelTextView.setText("");
        this.mFirmwareVersionTextView.setText("");
        this.mLastSyncTextView.setText("");
        this.mLastSyncStatusTextView.setText("");
        this.mForgetDeviceButton.setText(getString(R.string.device_info_setup_pairing));
        disableSyncNowButton();
    }

    @OnClick({2131755271})
    public void forgetDevice() {
        if (this.mIsMaxTrainerPaired) {
            showForgetDeviceDialog();
        } else {
            startConnectionWizard();
        }
    }

    @OnClick({2131755272})
    public void syncNowContainerAction() {
        startSyncProcess();
    }

    @OnClick({2131755273})
    public void syncNowTextViewAction() {
        startSyncProcess();
    }

    private void startSyncProcess() {
        if (getActivity() instanceof MainActivity) {
            this.mPreferences.edit().putBoolean(Preferences.SYNC_FROM_DEVICE_VIEW, true).commit();
            startManualSyncProcess();
        }
    }

    private void startManualSyncProcess() {
        disableViewsForSyncProcess();
        this.mDevicePresenter.startSyncProcess();
    }

    private void disableViewsForSyncProcess() {
        disableForgetDeviceButton();
        disableSyncNowButton();
        Util.executeViewFadeOutAnimation(getActivity().getApplicationContext(), 1000, this.mTxtViewSyncNow);
        this.mSyncNowProgressBar.setVisibility(0);
    }

    private void showForgetDeviceDialog() {
        disableSyncNowButton();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(getString(R.string.device_info_forget_omni_trainer_msg));
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DeviceFragment.this.enableSyncNowButton();
            }
        });
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DeviceFragment.this.executeForgetDeviceAction();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();
    }

    /* access modifiers changed from: private */
    public void executeForgetDeviceAction() {
        this.mDevicePresenter.forgetDeviceInfo();
        Preferences.resetPreferences(this.mPreferences);
        startConnectionWizard();
    }

    private void startConnectionWizard() {
        Intent intent = new Intent(getActivity(), ConnectionWizardActivity.class);
        intent.setFlags(268468224);
        this.mPreferences.edit().putBoolean(Preferences.IS_CONNECTION_WIZARD_STARTED_FROM_DEVICE_SCREEN, true).apply();
        startActivity(intent);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /* access modifiers changed from: private */
    public void updateSyncStatusTextView(boolean isScreenFirstCreation) {
        changeSyncButtonStateToVisible(isScreenFirstCreation);
        if (this.mCurrentProduct == null) {
            setSyncStatusWhenProductIsNull();
        } else {
            setSyncStatusWhenProductIsNotNull(this.mCurrentProduct);
        }
    }

    private void setSyncStatusWhenProductIsNull() {
        this.mLastSyncTextView.setText(this.mDateFormatter.print((ReadableInstant) new DateTime()));
        this.mLastSyncStatusTextView.setText(getString(R.string.device_info_failed_pairing));
        disableSyncNowButton();
    }

    private void setSyncStatusWhenProductIsNotNull(Product product) {
        this.mLastSyncTextView.setText(this.mDateFormatter.print((ReadableInstant) product.getmLastSync()));
        if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL)) {
            this.mLastSyncStatusTextView.setText(getString(R.string.device_info_successful_pairing));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL_NO_WORKOUTS)) {
            this.mLastSyncStatusTextView.setText(getString(R.string.device_info_successful_no_new_workouts));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.FAILED)) {
            this.mLastSyncStatusTextView.setText(getString(R.string.device_info_failed_pairing));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.UNABLE_TO_CONNECT)) {
            this.mLastSyncStatusTextView.setText(getString(R.string.device_info_unabled_to_connect));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.BUSY_STATE)) {
            this.mLastSyncStatusTextView.setText(getString(R.string.device_info_console_busy_state));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.EMPTY)) {
            this.mLastSyncStatusTextView.setText("");
        }
    }

    /* access modifiers changed from: private */
    public void changeSyncButtonStateToVisible(boolean isScreenFirstCreation) {
        if (isScreenFirstCreation) {
            this.mTxtViewSyncNow.setVisibility(0);
        } else {
            Util.executeViewFadeInAnimation(getActivity().getApplicationContext(), 1000, this.mTxtViewSyncNow);
        }
        this.mSyncNowProgressBar.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void enableSyncNowButton() {
        if (this.mDevicePresenter.getCurrentUser() != null) {
            this.mTxtViewSyncNow.setEnabled(true);
            this.mSyncNowButtonContainer.setEnabled(true);
            this.mSyncNowButtonContainer.setAlpha(1.0f);
            return;
        }
        disableSyncNowButton();
        Preferences.resetPreferences(this.mPreferences);
    }

    /* access modifiers changed from: private */
    public void disableSyncNowButton() {
        this.mTxtViewSyncNow.setEnabled(false);
        this.mSyncNowButtonContainer.setEnabled(false);
        this.mSyncNowButtonContainer.setAlpha(DISABLED_ALPHA);
    }

    /* access modifiers changed from: private */
    public void enableForgetDeviceButton() {
        this.mForgetDeviceButton.setEnabled(true);
        this.mForgetDeviceButton.setAlpha(1.0f);
    }

    /* access modifiers changed from: private */
    public void disableForgetDeviceButton() {
        this.mForgetDeviceButton.setEnabled(false);
        this.mForgetDeviceButton.setAlpha(DISABLED_ALPHA);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
