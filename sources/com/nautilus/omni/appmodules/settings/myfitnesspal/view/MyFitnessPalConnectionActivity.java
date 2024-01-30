package com.nautilus.omni.appmodules.settings.myfitnesspal.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.myfitnesspal.android.sdk.MfpAuthError;
import com.myfitnesspal.android.sdk.MfpAuthListener;
import com.myfitnesspal.android.sdk.MfpWebError;
import com.myfitnesspal.android.sdk.MyFitnessPal;
import com.myfitnesspal.android.sdk.ResponseType;
import com.myfitnesspal.android.sdk.Scope;
import com.myfitnesspal.shared.constants.Constants;
import com.myfitnesspal.shared.utils.Ln;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import com.nautilus.omni.dependencyinjection.components.DaggerMFPComponent;
import com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal.MFPModule;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.nautilus.omni.syncservices.FitAppsSyncDataService;
import javax.inject.Inject;

public class MyFitnessPalConnectionActivity extends BaseActivity implements MyFitnessPalConnectionViewContract {
    private String TAG = MyFitnessPalConnectionActivity.class.getName();
    final MfpAuthListener authListener = new MfpAuthListener() {
        public void onComplete(Bundle params) {
            Ln.d("MFP AUTH onComplete!", new Object[0]);
            Log.e("onComplete", "Access Token:" + params.getString("access_token"));
            Log.e("onComplete", "Refresh Token:" + params.getString(Constants.Params.REFRESH_TOKEN));
            MyFitnessPalConnectionActivity.this.connectApp(params.getString("access_token"), params.getString(Constants.Params.REFRESH_TOKEN), params.getString(MyFitnessPalConstants.MFP_USER_ID_KEY));
        }

        public void onMfpError(MfpAuthError e) {
            Ln.d(e);
            MyFitnessPalConnectionActivity.this.showErrorConnectingToMFPDialog();
        }

        public void onError(MfpWebError e) {
            Ln.d(e);
            e.printStackTrace();
        }

        public void onCancel(Bundle params) {
            Ln.d("AUTH onCancel!", new Object[0]);
        }
    };
    private MyFitnessPal mMyFitnessPal;
    @Inject
    MyFitnessPalPresenterContract myFitnessPalPresenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_my_fitness_pal);
        setupToolbar();
        setActivityGraph();
    }

    private void setActivityGraph() {
        DaggerMFPComponent.builder().appComponent(getAppComponent()).mFPModule(new MFPModule()).build().inject(this);
        this.myFitnessPalPresenter.setiMyFitnessPalConnectionActivity(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.myFitnessPalPresenter.checkMFPStatus();
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        ((TextView) findViewById(R.id.toolbar_title)).setText(getString(R.string.mfp_connect_title));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_fitness_pal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                if (isFinishing()) {
                    return true;
                }
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    public void connectToMyFitnessPal(View view) {
        if (isAppInstalled(MyFitnessPalConstants.MFP_PACKAGE)) {
            connectWithMyFitnessPal();
        } else {
            showGetMyFitnessPalApp();
        }
    }

    private void showGetMyFitnessPalApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.mfp_app_required_title));
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.mfp_app_required_msg));
        alertDialogBuilder.setNegativeButton((int) R.string.cancel, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MyFitnessPalConnectionActivity.this.getMyFitnessPalAppFromGooglePlay();
            }
        });
        alertDialogBuilder.create().show();
    }

    private void connectWithMyFitnessPal() {
        Log.d(this.TAG, "mMyFitnessPal setted");
        this.mMyFitnessPal = new MyFitnessPal(MyFitnessPalConstants.MFP_CLIENT_ID);
        this.mMyFitnessPal.authorize(this, 2, Scope.Diary, ResponseType.Token, this.authListener);
    }

    public void connectApp(String accessToken, String refreshToken, String clientId) {
        this.myFitnessPalPresenter.saveMyFitnessPalCredentials(accessToken, refreshToken, clientId);
        this.myFitnessPalPresenter.startSync();
        finish();
    }

    /* access modifiers changed from: private */
    public void getMyFitnessPalAppFromGooglePlay() {
        this.myFitnessPalPresenter.updateMyFitnessPalDownloadPreference(true);
        Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.myfitnesspal.android");
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.setData(uri);
        startActivity(intent);
    }

    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getInstallerPackageName(packageName);
            return Boolean.TRUE.booleanValue();
        } catch (Exception e) {
            return Boolean.FALSE.booleanValue();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (2 == requestCode) {
            Log.d(this.TAG, "mMyFitnessPal " + this.mMyFitnessPal);
            handleMFPResult(requestCode, resultCode, data);
        }
    }

    private void handleMFPResult(int requestCode, int resultCode, Intent data) {
        if (this.mMyFitnessPal != null) {
            this.mMyFitnessPal.authorizeCallback(requestCode, resultCode, data);
        } else {
            showErrorConnectingToMFPDialog();
        }
    }

    /* access modifiers changed from: private */
    public void showErrorConnectingToMFPDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.mfp_connection_error_title));
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.mfp_connection_error_msg));
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }

    public void startSync() {
        FitAppsSyncDataService.startSyncWithMyFitnessPalAction(this);
    }

    public void showDialogRemember() {
        new AlertDialog.Builder(this).setTitle((int) R.string.mfp_dialog_remember_title).setMessage((int) R.string.mfp_dialog_remember_copy).setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MyFitnessPalConnectionActivity.this.myFitnessPalPresenter.updateMyFitnessPalDownloadPreference(false);
            }
        }).create().show();
    }
}
