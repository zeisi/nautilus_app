package com.nautilus.omni.syncservices.syncserviceshelpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.R;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.util.AlertDialogUtil;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.ToastUtil;
import com.nautilus.omni.util.Util;
import java.sql.SQLException;

public class MainActivitySyncHelper {
    public static final int ANIMATION_DURATION = 2000;
    public static final int BLUETOOTH_DELAY = 1000;
    public static final int OUT_ANIMATION_DELAY = 5000;
    public static final int SYNC_ICON_DELAY = 2000;
    /* access modifiers changed from: private */
    public static final String TAG = MainActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public Menu mActionbarMenu;
    private DataBaseHelper mDataBaseHelper;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public MainActivity mMainActivityInstance;
    private Dao<Product, Integer> mProductDao;
    /* access modifiers changed from: private */
    public SharedPreferences mSettings;
    /* access modifiers changed from: private */
    public int mSyncButtonUpdateCounter;
    public BroadcastReceiver startManualSyncProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            MainActivitySyncHelper.this.mMainActivityInstance.startScanProcess();
        }
    };
    public BroadcastReceiver updateStatusWhenFailed = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean isConsoleNotResponsive = intent.getBooleanExtra(Constants.CONSOLE_NOT_RESPONSIVE, false);
            MainActivitySyncHelper.this.updateSyncStatusWhenFailedSync(intent.getBooleanExtra(Constants.CONSOLE_BUSY, false));
            if (isConsoleNotResponsive) {
                MainActivitySyncHelper.this.showConsoleNotResponsiveDialog();
            }
        }
    };
    public BroadcastReceiver updateStatusWhenFinished = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            MainActivitySyncHelper.this.updateSyncStatusWhenFinishedSync();
        }
    };

    public MainActivitySyncHelper(MainActivity context, Handler handler, SharedPreferences settings, DataBaseHelper dataBaseHelper) {
        this.mMainActivityInstance = context;
        this.mDataBaseHelper = dataBaseHelper;
        this.mSettings = settings;
        initDatabaseComponents();
        this.mSyncButtonUpdateCounter = 0;
        this.mHandler = handler;
    }

    private void initDatabaseComponents() {
        try {
            this.mProductDao = this.mDataBaseHelper.getProductDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startAutomaticSyncProcess() {
        Log.d(TAG, "DEBUG - STARTING AUTO SYNC");
        setSyncDataState();
        this.mSettings.edit().putBoolean("SYNC_FROM_CONNECTION_WIZARD", false).commit();
        startSyncProcessService(SyncProcessService.AUTO_SYNC);
        sendDisableSyncNowButtonInDeviceScreenBroadcast();
    }

    public void startManualSyncProcess() {
        Log.d(TAG, "DEBUG - STARTING MANUAL SYNC");
        startSyncProcessService(SyncProcessService.MANUAL_SYNC);
        setSyncDataState();
        this.mSettings.edit().putBoolean("SYNC_FROM_CONNECTION_WIZARD", false).commit();
        sendDisableSyncNowButtonInDeviceScreenBroadcast();
    }

    private void startSyncProcessService(String syncType) {
        Intent syncProcessService = new Intent(this.mMainActivityInstance.getApplicationContext(), SyncProcessService.class);
        syncProcessService.putExtra(SyncProcessService.SYNC_TYPE, syncType);
        this.mMainActivityInstance.startService(syncProcessService);
    }

    private void sendDisableSyncNowButtonInDeviceScreenBroadcast() {
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.DISABLE_SYNC_NOW_BUTTON);
        LocalBroadcastManager.getInstance(this.mMainActivityInstance.getApplicationContext()).sendBroadcast(syncStartedIntent);
    }

    public void setSyncDataState() {
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, true).commit();
        this.mMainActivityInstance.mToolbarMenuHelper.disableSyncNowMenuOptionWhileSyncInProgress();
        this.mSyncButtonUpdateCounter = 0;
        this.mMainActivityInstance.mToolbarMenuHelper.updateToolbarMenu();
    }

    public void setSyncErrorState() {
        if (this.mActionbarMenu != null) {
            updateBluetoothIconToErrorState(this.mActionbarMenu.findItem(R.id.action_sync_now));
        }
    }

    public void updateSyncStatusWhenFinishedSync() {
        if (!this.mSettings.getBoolean(Preferences.SYNC_TOASTS_SHOWED, false)) {
            showSuccessfulSyncToast();
        }
        this.mSettings.edit().putBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, false).commit();
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, false).commit();
        this.mMainActivityInstance.mToolbarMenuHelper.enableSyncNowMenuOptionWhenDoneSyncing();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                if (MainActivitySyncHelper.this.mActionbarMenu != null) {
                    MainActivitySyncHelper.this.updateBluetoothIcon(MainActivitySyncHelper.this.mActionbarMenu.findItem(R.id.action_bluetooth), false);
                    int unused = MainActivitySyncHelper.this.mSyncButtonUpdateCounter = 0;
                }
            }
        }, 1000);
        checkIfUserWonAwards();
    }

    private void showSuccessfulSyncToast() {
        this.mSettings.edit().putBoolean(Preferences.SYNC_TOASTS_SHOWED, true).commit();
        if (this.mSettings.getBoolean("SYNC_FROM_CONNECTION_WIZARD", false)) {
            ToastUtil.createSuccessToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_sync_finished), 1).show();
        }
        showWorkoutSyncSuccessfulToast();
    }

    private void showWorkoutSyncSuccessfulToast() {
        try {
            Product product = this.mProductDao.queryBuilder().queryForFirst();
            if (product == null) {
                ToastUtil.createSyncIssueToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_sync_failed), 1).show();
            } else if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL_NO_WORKOUTS)) {
                ToastUtil.createSuccessToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_no_new_workouts_found), 0).show();
            } else if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL) && !this.mSettings.getBoolean("SYNC_FROM_CONNECTION_WIZARD", false)) {
                ToastUtil.createSuccessToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_workouts_synchronized), 0).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSyncStatusWhenFailedSync(boolean isConsoleBusy) {
        showSyncErrorToast(isConsoleBusy);
        this.mSettings.edit().putBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, true).commit();
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, false).commit();
        this.mMainActivityInstance.mToolbarMenuHelper.enableSyncNowMenuOptionWhenDoneSyncing();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                MainActivitySyncHelper.this.updateBluetoothIconToErrorState(MainActivitySyncHelper.this.mActionbarMenu.findItem(R.id.action_bluetooth));
                int unused = MainActivitySyncHelper.this.mSyncButtonUpdateCounter = 0;
            }
        }, 1000);
        sendEnableSyncNowButtonInDeviceScreenBroadcast();
        Log.d(TAG, "DEBUG - SYNC FAILED...");
    }

    private void showSyncErrorToast(boolean isConsoleBusy) {
        if (!this.mSettings.getBoolean(Preferences.SYNC_TOASTS_SHOWED, false)) {
            this.mSettings.edit().putBoolean(Preferences.SYNC_TOASTS_SHOWED, true).commit();
            if (isConsoleBusy) {
                ToastUtil.createSyncIssueToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_sync_failed_by_busy_state), 1).show();
            } else {
                ToastUtil.createSyncIssueToast(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_sync_failed), 1).show();
            }
        }
    }

    /* access modifiers changed from: private */
    public void showConsoleNotResponsiveDialog() {
        if (this.mMainActivityInstance.mCurrentUser == null) {
            AlertDialogUtil.buildRestartConnectionDialog(this.mMainActivityInstance, this.mMainActivityInstance.getString(R.string.connection_error_console_not_responsive_restart_wizard));
        } else {
            AlertDialogUtil.buildSimpleAlertDialog(this.mMainActivityInstance.getString(R.string.connection_error_console_not_responsive), this.mMainActivityInstance);
        }
    }

    public void sendEnableSyncNowButtonInDeviceScreenBroadcast() {
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.ENABLE_SYNC_NOW_BUTTON);
        LocalBroadcastManager.getInstance(this.mMainActivityInstance.getApplicationContext()).sendBroadcast(syncStartedIntent);
    }

    public void updateBluetoothIconToErrorState(final MenuItem item_bluetooth) {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                item_bluetooth.setActionView((View) null);
                item_bluetooth.setIcon(R.drawable.ic_bluetooth_sync_issue);
                Log.d(MainActivitySyncHelper.TAG, " ---------------------- DEBUG - Updated bluetooth icon to ERROR state...");
            }
        }, 1000);
    }

    public void updateBluetoothIcon(final MenuItem item_bluetooth, boolean isFirstIconUpdate) {
        this.mSyncButtonUpdateCounter++;
        Runnable changeBluetoothToActive = new Runnable() {
            public void run() {
                if (MainActivitySyncHelper.this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, true)) {
                    item_bluetooth.setIcon(R.drawable.ic_bluetooth_active);
                    Log.d(MainActivitySyncHelper.TAG, " ---------------------- DEBUG - Updated bluetooth icon to ACTIVE...");
                } else if (MainActivitySyncHelper.this.mSettings.getBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, false)) {
                    item_bluetooth.setIcon(R.drawable.ic_bluetooth_sync_issue);
                    Log.d(MainActivitySyncHelper.TAG, " ---------------------- DEBUG - Updated bluetooth icon to ERROR...");
                } else {
                    item_bluetooth.setIcon(R.drawable.ic_bluetooth_inactive);
                    Log.d(MainActivitySyncHelper.TAG, " ---------------------- DEBUG - Updated bluetooth icon to INACTIVE...");
                }
            }
        };
        Runnable changeBluetoothToSyncing = new Runnable() {
            public void run() {
                if (MainActivitySyncHelper.this.mSettings.getBoolean(Preferences.SYNC_IN_PROGRESS, true)) {
                    MainActivitySyncHelper.this.changeSyncState(item_bluetooth);
                    return;
                }
                item_bluetooth.setActionView((View) null);
                Log.d(MainActivitySyncHelper.TAG, " ---------------------- DEBUG - Bluetooth icon to NULL...");
            }
        };
        if (isFirstIconUpdate) {
            this.mHandler.postDelayed(changeBluetoothToActive, 1000);
            this.mHandler.postDelayed(changeBluetoothToSyncing, 2000);
            return;
        }
        this.mHandler.postDelayed(changeBluetoothToActive, 0);
        this.mHandler.postDelayed(changeBluetoothToSyncing, 0);
    }

    /* access modifiers changed from: private */
    public void changeSyncState(MenuItem item) {
        ProgressBar progressBar = (ProgressBar) ((LayoutInflater) this.mMainActivityInstance.getSystemService("layout_inflater")).inflate(R.layout.refresh_view, (ViewGroup) null);
        progressBar.getIndeterminateDrawable().setColorFilter(this.mMainActivityInstance.getResources().getColor(R.color.blue_nautilus), PorterDuff.Mode.SRC_ATOP);
        item.setActionView(progressBar);
        Log.d(TAG, " ---------------------- DEBUG - Updated bluetooth icon to SYNCING state...");
    }

    private void checkIfUserWonAwards() {
        if (this.mSettings.getBoolean(Preferences.WON_AWARDS, false)) {
            showAwardsToast();
        }
    }

    private void showAwardsToast() {
        View toastLayout = this.mMainActivityInstance.getLayoutInflater().inflate(R.layout.custom_animated_toast, (ViewGroup) this.mMainActivityInstance.findViewById(R.id.custom_animated_toast_layout_root));
        toastLayout.setBackgroundColor(this.mMainActivityInstance.getResources().getColor(R.color.custom_toast_color));
        ((ImageView) toastLayout.findViewById(R.id.custom_toast_image_view)).setImageDrawable(ResourcesCompat.getDrawable(this.mMainActivityInstance.getResources(), R.drawable.ic_toast_trophy_icon, (Resources.Theme) null));
        TextView toastTextView = (TextView) toastLayout.findViewById(R.id.custom_toast_text_view);
        TextView toastSecondTextView = (TextView) toastLayout.findViewById(R.id.custom_toast_second_text_view);
        toastTextView.setTypeface(Typeface.createFromAsset(this.mMainActivityInstance.getAssets(), "fonts/Lato-Regular.ttf"));
        toastSecondTextView.setVisibility(0);
        toastTextView.setText(R.string.won_awards);
        toastSecondTextView.setText(R.string.check_awards);
        Util.executeViewFadeInAnimation(this.mMainActivityInstance, 2000, toastLayout);
        makeGoalsReminderOutAnimation(toastLayout);
        updateWonAwardsState();
        toastLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivitySyncHelper.this.mMainActivityInstance.changeFragmentWithTitle(MainActivitySyncHelper.this.mMainActivityInstance.getString(R.string.title_awards), AwardsFragment.TAG, false);
                MainActivitySyncHelper.this.mMainActivityInstance.mNavigationDrawerHelper.updateSelectedMenu(AwardsFragment.TAG, MainActivitySyncHelper.this.mMainActivityInstance.mNavigationDrawerHelper.mNavigationDrawerList.getChildAt(2));
            }
        });
    }

    private void makeGoalsReminderOutAnimation(final View toastLayout) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Util.executeViewFadeOutAnimation(MainActivitySyncHelper.this.mMainActivityInstance.getApplicationContext(), 2000, toastLayout);
            }
        }, 5000);
    }

    private void updateWonAwardsState() {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(Preferences.WON_AWARDS, false);
        editor.commit();
    }

    public void releaseDatabaseHelper() {
        if (this.mDataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            this.mDataBaseHelper = null;
        }
    }

    public int getSyncButtonUpdateCounter() {
        return this.mSyncButtonUpdateCounter;
    }

    public void setActionbarMenu(Menu mActionbarMenu2) {
        this.mActionbarMenu = mActionbarMenu2;
    }
}
