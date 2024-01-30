package com.nautilus.omni.appmodules.connectionwizard.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenterContract;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.dependencyinjection.components.wizard.DaggerUserSelectComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.UserSelectModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.syncservices.syncserviceshelpers.SyncProcessService;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.Util;
import javax.inject.Inject;

public class SelectUserNumberActivity extends BaseActivity implements SelectUserNumberActivityContract {
    private static final int ANIMATION_DURATION = 1000;
    private static final int DEFAULT_USER_NUMBER = 1;
    private static final int FIRST_USER = 1;
    private static final int FOURTH_USER = 4;
    private static final int SECOND_USER = 2;
    private static final int THIRD_USER = 3;
    @BindView(2131755177)
    RelativeLayout mBtnConfirmUser;
    @BindView(2131755173)
    Button mButtonUser1;
    @BindView(2131755175)
    Button mButtonUser2;
    @BindView(2131755174)
    Button mButtonUser3;
    @BindView(2131755176)
    Button mButtonUser4;
    @BindView(2131755179)
    ProgressBar mProgressBarConnectNow;
    private Toast mSyncDataToast;
    @BindView(2131755178)
    TextView mTxtViewConfirmUser;
    @BindView(2131755144)
    TextView mTxtViewSkip;
    private int mUserNumberSelected;
    @Inject
    UserSelectPresenterContract userSelectPresenter;
    @BindView(2131755171)
    ImageView userSelectionImageView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_select_user_number);
        setActivityGraph();
        ButterKnife.bind((Activity) this);
        initViews();
        this.mUserNumberSelected = 1;
    }

    private void initViews() {
        this.mProgressBarConnectNow.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.connection_progress_bar_color), PorterDuff.Mode.SRC_ATOP);
    }

    public void setActivityGraph() {
        DaggerUserSelectComponent.builder().appComponent(getAppComponent()).wizardDataModule(new WizardDataModule()).userSelectModule(new UserSelectModule(this)).build().inject(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.userSelectPresenter.resume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.userSelectPresenter.pause();
    }

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getString(R.string.connection_back_navigation_not_allowed), 0).show();
    }

    public void confirmUserNumber(View view) {
        this.mTxtViewSkip.setEnabled(false);
        disableUserButtons();
        hideConfirmTextButton();
        executeSyncDataProcess();
    }

    private void disableUserButtons() {
        this.mButtonUser1.setEnabled(false);
        this.mButtonUser2.setEnabled(false);
        this.mButtonUser3.setEnabled(false);
        this.mButtonUser4.setEnabled(false);
    }

    private void hideConfirmTextButton() {
        this.mBtnConfirmUser.setEnabled(false);
        this.mTxtViewConfirmUser.setEnabled(false);
        Util.executeViewFadeOutAnimation(getApplicationContext(), 1000, this.mTxtViewConfirmUser);
        this.mProgressBarConnectNow.setVisibility(0);
    }

    public void executeSyncDataProcess() {
        this.userSelectPresenter.saveCurrentUserIndexInSharedPreferences(this.mUserNumberSelected);
        startSyncDataService();
        initSyncDataToast();
        this.userSelectPresenter.showSyncingToast();
    }

    private void startSyncDataService() {
        Intent syncDataServiceIntent = new Intent(getApplicationContext(), SyncProcessService.class);
        syncDataServiceIntent.putExtra(SyncProcessService.SYNC_TYPE, "SYNC_FROM_CONNECTION_WIZARD");
        startService(syncDataServiceIntent);
    }

    private void initSyncDataToast() {
        View toastLayout = getLayoutInflater().inflate(R.layout.custom_regular_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_root));
        ((TextView) toastLayout.findViewById(R.id.custom_toast_text_view)).setText(getString(R.string.connection_sync_data, new Object[]{Integer.valueOf(this.mUserNumberSelected)}));
        ((ImageView) toastLayout.findViewById(R.id.custom_toast_image_view)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_toast_sync, (Resources.Theme) null));
        this.mSyncDataToast = new Toast(getApplicationContext());
        this.mSyncDataToast.setGravity(55, 0, 0);
        this.mSyncDataToast.setDuration(1);
        this.mSyncDataToast.setView(toastLayout);
    }

    public void showSyncingToast() {
        this.mSyncDataToast.show();
    }

    public void startMainActivity() {
        this.userSelectPresenter.saveDefaultDevicePreference();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(268468224);
        intent.putExtra(Constants.STARTED_FROM_CONNECTION_WIZARD, true);
        startActivity(intent);
        this.mTxtViewSkip.setEnabled(true);
    }

    public void firstUserSelected(View view) {
        this.userSelectionImageView.setImageResource(R.drawable.ic_user_number_1);
        this.mUserNumberSelected = 1;
        if (!this.mBtnConfirmUser.isShown()) {
            showConfirmUserButton();
        }
    }

    public void secondUserSelected(View view) {
        this.userSelectionImageView.setImageResource(R.drawable.ic_user_number_2);
        this.mUserNumberSelected = 2;
        if (!this.mBtnConfirmUser.isShown()) {
            showConfirmUserButton();
        }
    }

    public void thirdUserSelected(View view) {
        this.userSelectionImageView.setImageResource(R.drawable.ic_user_number_3);
        this.mUserNumberSelected = 3;
        if (!this.mBtnConfirmUser.isShown()) {
            showConfirmUserButton();
        }
    }

    public void fourthUserSelected(View view) {
        this.userSelectionImageView.setImageResource(R.drawable.ic_user_number_4);
        this.mUserNumberSelected = 4;
        if (!this.mBtnConfirmUser.isShown()) {
            showConfirmUserButton();
        }
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
                SelectUserNumberActivity.this.executeSkipAction();
            }
        });
        alertDialogBuilder.create().show();
    }

    /* access modifiers changed from: private */
    public void executeSkipAction() {
        this.userSelectPresenter.deleteOmnidata();
        this.userSelectPresenter.resetCurrentUserPreference();
        this.userSelectPresenter.updateSyncPreference(false);
        sendDisconnectOmniTrainerBroadcast();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    private void showConfirmUserButton() {
        Util.executeViewFadeInAnimation(getApplicationContext(), 1000, this.mBtnConfirmUser);
    }

    public void showUnableToConnectDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.connection_unable_to_connect_with_omni_trainer));
        alertDialogBuilder.setPositiveButton((int) R.string.restart_pairing, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SelectUserNumberActivity.this.restartConnectionWizard();
            }
        });
        alertDialogBuilder.setCancelable(false);
        if (!isFinishing()) {
            alertDialogBuilder.create().show();
        }
    }

    /* access modifiers changed from: private */
    public void restartConnectionWizard() {
        Intent restartConnectionWizardIntent = new Intent(getApplicationContext(), ConnectionWizardActivity.class);
        restartConnectionWizardIntent.setFlags(268468224);
        startActivity(restartConnectionWizardIntent);
    }
}
