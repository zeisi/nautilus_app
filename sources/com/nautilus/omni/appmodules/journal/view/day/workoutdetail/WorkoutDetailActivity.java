package com.nautilus.omni.appmodules.journal.view.day.workoutdetail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.sharelibrary.OnSelectPackageListener;
import com.example.sharelibrary.ShareDialogComponent;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail.WorkoutDetailPresenter;
import com.nautilus.omni.dependencyinjection.components.DaggerWorkoutDetailComponent;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.permissions.Action;
import com.nautilus.omni.permissions.PermissionCallbacks;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.Util;
import java.util.List;
import javax.inject.Inject;

public class WorkoutDetailActivity extends BaseActivity implements WorkoutDetailViewContract, PermissionCallbacks {
    public static final String TAG = WorkoutDetailActivity.class.getSimpleName();
    @Inject
    PermissionPresenter mPermissionPresenter;
    @BindView(2131755215)
    TextView mPowerDivider;
    @BindView(2131755216)
    RelativeLayout mPowerLabelLayout;
    @BindView(2131755217)
    RelativeLayout mPowerValueLayout;
    @BindView(2131755140)
    Toolbar mToolbar;
    @BindView(2131755213)
    TextView mTxtViewAvgCaloriesBurnedValue;
    @BindView(2131755219)
    TextView mTxtViewAvgSpeed;
    @BindView(2131755220)
    TextView mTxtViewAvgSpeedUnit;
    @BindView(2131755218)
    TextView mTxtViewAvgWattsValue;
    @BindView(2131755212)
    TextView mTxtViewCaloriesBurnedValue;
    @BindView(2131755221)
    TextView mTxtViewDistance;
    @BindView(2131755222)
    TextView mTxtViewDistanceUnit;
    @BindView(2131755211)
    TextView mTxtViewElapsedTimeValue;
    @BindView(2131755214)
    TextView mTxtViewHeartRateValue;
    @BindView(2131755162)
    TextView mTxtViewToolbarTitle;
    @BindView(2131755209)
    TextView mTxtViewWorkoutDate;
    private Workout mWorkout;
    @Inject
    WorkoutDetailPresenter mWorkoutDetailPresenter;
    /* access modifiers changed from: private */
    public ShareDialogComponent shareDialogComponent;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_workout_detail);
        DaggerWorkoutDetailComponent.builder().appComponent(getAppComponent()).workoutDetailModule(new WorkoutDetailModule(this)).build().inject(this);
        ButterKnife.bind((Activity) this);
        setupToolbar();
        this.mWorkout = (Workout) getIntent().getExtras().getParcelable("workout");
        this.mWorkoutDetailPresenter.prepareWorkoutInfo(this.mWorkout);
        this.shareDialogComponent = new ShareDialogComponent(this);
        this.shareDialogComponent.setOnPackageSelectListener(new OnSelectPackageListener() {
            public void onPackageSelect(String pPackage, ResolveInfo resolveInfo) {
                Util.updateShareMessageAccordingWithAppSelected(WorkoutDetailActivity.this, Util.buildShareIntent(WorkoutDetailActivity.this, pPackage, resolveInfo), WorkoutDetailActivity.this, R.id.workout_detail_content);
                WorkoutDetailActivity.this.shareDialogComponent.hide();
            }
        });
        if (Util.hasWatts(this)) {
            this.mPowerDivider.setVisibility(0);
            this.mPowerLabelLayout.setVisibility(0);
            this.mPowerValueLayout.setVisibility(0);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.mTxtViewToolbarTitle.setText(getString(R.string.title_workout_details));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_workout_detail, menu);
        buildShareActionProvider(menu);
        shareWorkoutDetail();
        return true;
    }

    private void buildShareActionProvider(Menu menu) {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                if (isFinishing()) {
                    return true;
                }
                onBackPressed();
                return true;
            case R.id.workout_detail_action_share:
                this.mPermissionPresenter.requestWriteExternalStorangePermission();
                return true;
            default:
                return true;
        }
    }

    private void shareWorkoutDetail() {
        if (this.mWorkout != null) {
            shareInformation(this);
        }
    }

    public void shareInformation(Context context) {
    }

    public void showWorkoutDate(String workoutDate) {
        this.mTxtViewWorkoutDate.setText(workoutDate);
    }

    public void showWorkoutElapsedTime(String workoutTime) {
        this.mTxtViewElapsedTimeValue.setText(workoutTime);
    }

    public void showWorkoutTotalCalories(String workoutCalories) {
        this.mTxtViewCaloriesBurnedValue.setText(workoutCalories);
    }

    public void showWorkoutAvgCaloriesPerMinute(String workoutAvgCaloriesPerMinute) {
        this.mTxtViewAvgCaloriesBurnedValue.setText(workoutAvgCaloriesPerMinute);
    }

    public void showWorkoutHeartRate(String workoutHeartRate) {
        this.mTxtViewHeartRateValue.setText(workoutHeartRate);
    }

    public void showWorkoutSpeed(String workoutSpeed, String unit) {
        this.mTxtViewAvgSpeed.setText(workoutSpeed);
        this.mTxtViewAvgSpeedUnit.setText(unit);
    }

    public void showWorkoutAvgWatts(String workoutAvgWatts) {
        this.mTxtViewAvgWattsValue.setText(workoutAvgWatts);
    }

    public void showWorkoutDistance(String workoutDistance, String unit) {
        this.mTxtViewDistance.setText(workoutDistance);
        this.mTxtViewDistanceUnit.setText(unit);
    }

    public void permissionAccepted(int actionCode) {
        if (!isFinishing()) {
            this.shareDialogComponent.show();
        }
    }

    public void permissionDenied(int actionCode, List<String> permissionsDenied) {
        if (permissionsDenied.size() > 0) {
            showPermissionsDialog(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", WorkoutDetailActivity.this.getPackageName(), (String) null));
                    WorkoutDetailActivity.this.startActivity(intent);
                }
            }, R.string.app_name, R.string.storage_permission_required_denied, this, R.string.settings_text, R.string.permi_dialog_negative_button_text);
        }
    }

    public void showRationale(int actionCode, String[] permissions, final Action action) {
        showPermissionsDialog(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                WorkoutDetailActivity.this.mPermissionPresenter.requestPermission(action);
            }
        }, R.string.app_name, R.string.location_permission_required_rationale, this, R.string.permi_dialog_ok_button_text, R.string.permi_dialog_negative_button_text);
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
}
