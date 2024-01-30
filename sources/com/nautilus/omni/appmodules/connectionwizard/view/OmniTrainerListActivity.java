package com.nautilus.omni.appmodules.connectionwizard.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.connectionwizard.adapters.OmniTrainerListAdapter;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.dependencyinjection.components.wizard.DaggerDevicesListComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.DevicesListModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import java.util.ArrayList;
import javax.inject.Inject;

public class OmniTrainerListActivity extends BaseActivity implements OmniTrainerListActivityContract {
    public static final int ANIMATION_DURATION = 2000;
    public static final String BACK_WITH_ERROR = "BACK_WITH_ERROR";
    public static final String TAG = OmniTrainerListActivity.class.getSimpleName();
    @Inject
    DevicesListPresenterContract devicesListPresenter;
    private OmniTrainerListAdapter mOmniTrainerListAdapter;
    private View mToastLayout;
    private TextView mTxtViewSkip;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityGraph();
        setContentView((int) R.layout.activity_omni_trainer_list);
        ArrayList<OmniData> mOmniDataList = getIntent().getExtras().getParcelableArrayList(Constants.OMNI_TRAINER_LIST);
        this.devicesListPresenter.setmOmniDataList(mOmniDataList);
        this.mTxtViewSkip = (TextView) findViewById(R.id.text_view_skip);
        initConnectingDeviceToast();
        initOmniTrainerRecyclerView(mOmniDataList);
    }

    public void setActivityGraph() {
        DaggerDevicesListComponent.builder().appComponent(getAppComponent()).wizardDataModule(new WizardDataModule()).devicesListModule(new DevicesListModule(this)).build().inject(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.devicesListPresenter.resume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.devicesListPresenter.pause();
        super.onPause();
    }

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getString(R.string.connection_back_navigation_not_allowed), 0).show();
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
                OmniTrainerListActivity.this.executeSkipAction();
            }
        });
        alertDialogBuilder.create().show();
    }

    /* access modifiers changed from: private */
    public void executeSkipAction() {
        sendStopOmniTrainerScanBroadcast();
        sendDisconnectOmniTrainerBroadcast();
        this.devicesListPresenter.updateSyncInProgressPreference(false);
        this.devicesListPresenter.resetCurrentUserPreference();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(268468224);
        intent.putExtra(Constants.CONNECTION_WIZARD_SKIP_EXECUTED, true);
        startActivity(intent);
    }

    private void sendStopOmniTrainerScanBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.STOP_OMNI_SCAN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendDisconnectOmniTrainerBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.DISCONNECT_OMNI_TRAINER);
        intent.putExtra(Constants.HAVE_BLE, false);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void initOmniTrainerRecyclerView(ArrayList<OmniData> mOmniDataList) {
        RecyclerView mOmniTrainerRecyclerView = (RecyclerView) findViewById(R.id.omni_trainer_recycler_view);
        mOmniTrainerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.mOmniTrainerListAdapter = new OmniTrainerListAdapter(getApplicationContext(), this.devicesListPresenter, this.mToastLayout, this.mTxtViewSkip);
        this.mOmniTrainerListAdapter.addAll(mOmniDataList);
        mOmniTrainerRecyclerView.setAdapter(this.mOmniTrainerListAdapter);
    }

    private void initConnectingDeviceToast() {
        this.mToastLayout = getLayoutInflater().inflate(R.layout.custom_animated_toast, (ViewGroup) findViewById(R.id.custom_animated_toast_layout_root));
        ((TextView) this.mToastLayout.findViewById(R.id.custom_toast_text_view)).setText(getString(R.string.connection_connecting_to_omni_trainer));
        ((ImageView) this.mToastLayout.findViewById(R.id.custom_toast_image_view)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_toast_bluetooth, (Resources.Theme) null));
    }

    public void showConnectingDeviceToastOutAnimation() {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_out);
        fadeOutAnimation.setDuration(2000);
        this.mToastLayout.setVisibility(8);
        this.mToastLayout.startAnimation(fadeOutAnimation);
    }

    public void changeTxtViewSkipEnableDisable(boolean value) {
        this.mTxtViewSkip.setEnabled(value);
    }

    public void showUnableToConnectDialog() {
        Log.d(TAG, "DEBUG - UNABLE TO CONNECT WITH THE MACHINE...");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.connection_unable_to_connect_with_omni_trainer));
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OmniTrainerListActivity.this.restartConnectionWizard(false);
            }
        });
        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public void restartConnectionWizard(boolean error) {
        Intent restartConnectionWizardIntent = new Intent(getApplicationContext(), ConnectionWizardActivity.class);
        restartConnectionWizardIntent.setFlags(268468224);
        if (error) {
            restartConnectionWizardIntent.putExtra(BACK_WITH_ERROR, true);
        }
        startActivity(restartConnectionWizardIntent);
    }

    public void startSelectUserNumberActivity() {
        startActivity(new Intent(this, SelectUserNumberActivity.class));
    }

    public void updateRecycleViewItems(OmniData omniData) {
        this.mOmniTrainerListAdapter.updateList(omniData);
    }
}
