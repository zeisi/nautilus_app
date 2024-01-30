package com.nautilus.omni.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.LocationSettingsUtil;
import java.sql.SQLException;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {
    private static final int PERIOD_OF_MINUTES_TO_SYNC_AGAIN = 15;
    private static final String TAG = BaseActivity.class.getSimpleName();
    private User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    @Inject
    LocationSettingsUtil mLocationSettingsUtil;
    protected OmniApplication mMaxTrainer;
    private Dao<Product, Integer> mProductDao;
    @Inject
    public SharedPreferences mSettings;
    private Dao<User, Integer> mUserDao;

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((OmniApplication) getApplication()).getAppComponent().inject(this);
        this.mMaxTrainer = (OmniApplication) getApplication();
        initDatabaseComponents();
    }

    /* access modifiers changed from: protected */
    public AppComponent getAppComponent() {
        return ((OmniApplication) getApplication()).getAppComponent();
    }

    private void initDatabaseComponents() {
        this.mDataBaseHelper = ((OmniApplication) getApplication()).getAppComponent().getDatabaseHelper();
        try {
            this.mProductDao = this.mDataBaseHelper.getProductDao();
            this.mUserDao = this.mDataBaseHelper.getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mMaxTrainer.onForeground();
        OmniApplication myApp = (OmniApplication) getApplication();
        if (myApp.wasInBackground && appHasNotSyncDuringLastFifteenMinutes() && appIsPairedWithOmniTrainer()) {
            sendStartSyncProcessBroadcast();
        }
        myApp.stopActivityTransitionTimer();
    }

    private boolean appHasNotSyncDuringLastFifteenMinutes() {
        try {
            Product product = this.mProductDao.queryBuilder().queryForFirst();
            if (product == null) {
                return false;
            }
            DateTime lastSyncDate = product.getmLastSync();
            return lastSyncDate.plusMinutes(15).isBefore((ReadableInstant) new DateTime());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean appIsPairedWithOmniTrainer() {
        try {
            this.mCurrentUser = this.mUserDao.queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.mCurrentUser != null;
    }

    private void sendStartSyncProcessBroadcast() {
        Log.d(TAG, "DEBUG - SEND START SYNC PROCESS WHEN RETURNING FROM BACKGROUND");
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.START_SYNC_PROCESS);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(syncStartedIntent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        ((OmniApplication) getApplication()).startActivityTransitionTimer();
    }

    /* access modifiers changed from: protected */
    public void checkLocationSettingsStatus(Activity activity, LocationSettingsUtil.LocationSettingsResponse response) {
        if (Build.VERSION.SDK_INT < 23) {
            response.OnLocationStatusSuccess();
        } else {
            this.mLocationSettingsUtil.checkGpsStatus(activity, response);
        }
    }

    /* access modifiers changed from: protected */
    public void showPermissionsDialog(DialogInterface.OnClickListener positiveButtonListener, int title, int message, Context context, int okButtonText, int negativeButtonText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(okButtonText, positiveButtonListener);
        alertDialog.setNegativeButton(negativeButtonText, (DialogInterface.OnClickListener) null);
        alertDialog.show();
    }

    /* access modifiers changed from: protected */
    public void showPermissionsDialog(DialogInterface.OnClickListener positiveButtonListener, DialogInterface.OnClickListener negativeButtonListener, int title, int message, Context context, int okButtonText, int negativeButtonText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(okButtonText, positiveButtonListener);
        alertDialog.setNegativeButton(negativeButtonText, negativeButtonListener);
        alertDialog.show();
    }
}
