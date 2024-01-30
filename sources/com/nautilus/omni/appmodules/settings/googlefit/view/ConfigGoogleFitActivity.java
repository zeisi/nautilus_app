package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dd.CircularProgressButton;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.dependencyinjection.components.DaggerGoogleFitComponent;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import java.util.List;
import javax.inject.Inject;

public class ConfigGoogleFitActivity extends BaseActivity implements ConfigGoogleFitContract {
    public static final String TAG = ConfigGoogleFitActivity.class.getSimpleName();
    public static final String TITLE = "TITLE";
    @BindView(2131755160)
    Button mBtnDisconnect;
    @BindView(2131755161)
    CircularProgressButton mBtnSyncProgress;
    @Inject
    FitServicesSyncDataHelper mFitServicesSyncDataHelper;
    @Inject
    GoogleFitPresenterContract mGoogleFitPresenter;
    @BindView(2131755157)
    SwitchCompat mSwitchAvgHeartRate;
    @BindView(2131755158)
    SwitchCompat mSwitchAvgRPM;
    @BindView(2131755156)
    SwitchCompat mSwitchCalories;
    @BindView(2131755159)
    SwitchCompat mSwitchDistance;
    @BindView(2131755155)
    SwitchCompat mSwitchTime;
    private boolean mSync;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_google_fit_conf);
        ButterKnife.bind((Activity) this);
        onGraphActivity();
        this.mSync = getIntent().getExtras().getBoolean(Preferences.GOOGLE_FIT_SYNC, false);
        setupToolbar();
        initButtons();
        this.mGoogleFitPresenter.loadSwitches();
        this.mGoogleFitPresenter.saveCurrentSwitchesStateInPreferences(this.mSwitchTime.isChecked(), this.mSwitchCalories.isChecked(), this.mSwitchAvgHeartRate.isChecked(), this.mSwitchAvgRPM.isChecked(), this.mSwitchDistance.isChecked());
    }

    private void onGraphActivity() {
        DaggerGoogleFitComponent.builder().appComponent(getAppComponent()).googleFitModule(new GoogleFitModule((ConnectionGoogleFitActivity) null)).build().inject(this);
        this.mGoogleFitPresenter.setConfigurationViewView(this);
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ((TextView) findViewById(R.id.toolbar_title)).setText(getIntent().getStringExtra("TITLE"));
    }

    private void initButtons() {
        this.mBtnSyncProgress.setIndeterminateProgressMode(true);
        if (this.mSync) {
            this.mBtnSyncProgress.setVisibility(0);
            this.mBtnDisconnect.setVisibility(4);
            return;
        }
        this.mBtnSyncProgress.setVisibility(4);
        this.mBtnDisconnect.setVisibility(0);
    }

    @OnClick({2131755160})
    public void disconnect() {
        new AlertDialog.Builder(this).setTitle((int) R.string.google_fit_button_disconnect).setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ConfigGoogleFitActivity.this.mGoogleFitPresenter.disconnectFitnessService(ConfigGoogleFitActivity.this.getApplicationContext());
            }
        }).setNegativeButton((int) R.string.no, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setMessage((int) R.string.google_fit_disconnect_message).show();
    }

    @OnClick({2131755161})
    public void sync() {
        this.mGoogleFitPresenter.startSync();
    }

    public void initSwitches(boolean time, boolean calories, boolean avgHeartRate, boolean avgRPM, boolean distance) {
        this.mSwitchTime.setChecked(time);
        this.mSwitchCalories.setChecked(calories);
        this.mSwitchAvgHeartRate.setChecked(avgHeartRate);
        this.mSwitchAvgRPM.setChecked(avgRPM);
        this.mSwitchDistance.setChecked(distance);
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigGoogleFitActivity.this.mGoogleFitPresenter.saveCurrentSwitchesStateInPreferences(ConfigGoogleFitActivity.this.mSwitchTime.isChecked(), ConfigGoogleFitActivity.this.mSwitchCalories.isChecked(), ConfigGoogleFitActivity.this.mSwitchAvgHeartRate.isChecked(), ConfigGoogleFitActivity.this.mSwitchAvgRPM.isChecked(), ConfigGoogleFitActivity.this.mSwitchDistance.isChecked());
            }
        };
        this.mSwitchTime.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mSwitchCalories.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mSwitchAvgHeartRate.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mSwitchAvgRPM.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mSwitchDistance.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return true;
        }
        finish();
        return true;
    }

    public void startSync(List<FitServicesWorkout> workoutsToSyncList, OmniData omniData) {
        if (workoutsToSyncList == null || workoutsToSyncList.size() <= 0) {
            this.mBtnSyncProgress.setProgress(0);
            showNoNewWorkoutsToSyncDialog();
            return;
        }
        Log.d(TAG, "DEBUG - SENDING NEW WORKOUTS TO GOOGLE FIT");
        this.mBtnSyncProgress.setProgress(50);
        this.mGoogleFitPresenter.syncFitnessData(this, this.mFitServicesSyncDataHelper, omniData, workoutsToSyncList);
        finish();
    }

    private void showNoNewWorkoutsToSyncDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.google_fit_no_workouts_to_sync));
        alertDialogBuilder.setNegativeButton((int) R.string.cancel, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ConfigGoogleFitActivity.this.finish();
            }
        });
        alertDialogBuilder.create().show();
    }

    public void close() {
        finish();
    }

    public void showDisconnectionError(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle((CharSequence) title);
        alertDialogBuilder.setMessage((CharSequence) message);
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }
}
