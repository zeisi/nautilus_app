package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.dependencyinjection.components.DaggerGoogleFitComponent;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule;
import javax.inject.Inject;

public class ConnectionGoogleFitActivity extends BaseActivity implements ConnectionGoogleFitViewContract {
    public static final String TITLE = "TITLE";
    public static final int UNKNOWN_ERROR_WHEN_TRYING_TO_GET_OAUTH_TOKEN = 5005;
    @Inject
    GoogleFitPresenterContract mGoogleFitPresenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_google_fit_connection);
        ButterKnife.bind((Activity) this);
        onGraphActivity();
        setupToolbar();
        if (savedInstanceState != null) {
            this.mGoogleFitPresenter.updateAuthProgressValue(savedInstanceState.getBoolean(GoogleFitHelper.AUTH_PENDING));
        }
    }

    private void onGraphActivity() {
        DaggerGoogleFitComponent.builder().appComponent(getAppComponent()).googleFitModule(new GoogleFitModule(this)).build().inject(this);
        this.mGoogleFitPresenter.setConnectionView(this);
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ((TextView) findViewById(R.id.toolbar_title)).setText(getIntent().getStringExtra("TITLE"));
    }

    @OnClick({2131755154})
    public void connectToGoogleFitOnClick() {
        this.mGoogleFitPresenter.connectToFitnessService(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            this.mGoogleFitPresenter.updateAuthProgressValue(false);
            retryConnect(resultCode);
        }
    }

    private void retryConnect(int resultCode) {
        if (resultCode == -1) {
            this.mGoogleFitPresenter.retryConnection(this);
        } else {
            showInternetError();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return true;
        }
        finish();
        return true;
    }

    public void onConnectionSuccess() {
        Intent intent = new Intent();
        intent.setClass(this, ConfigGoogleFitActivity.class);
        intent.putExtra(Preferences.GOOGLE_FIT_SYNC, true);
        startActivity(intent);
        finish();
    }

    public void onConnectionFailed(int errorCode, int requestCode) {
        if (errorCode == 5005 || errorCode == 7) {
            showInternetError();
        } else {
            showGeneralGoogleFitError(errorCode, requestCode);
        }
    }

    private void showInternetError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.google_fit_connection_error_title));
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.google_fit_connection_error_message));
        alertDialogBuilder.setPositiveButton((int) R.string.ok, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }

    private void showGeneralGoogleFitError(int errorCode, int requestCode) {
        GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode, requestCode).show();
    }
}
