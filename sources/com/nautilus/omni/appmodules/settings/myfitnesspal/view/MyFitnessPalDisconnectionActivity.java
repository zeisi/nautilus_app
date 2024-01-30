package com.nautilus.omni.appmodules.settings.myfitnesspal.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import com.nautilus.omni.dependencyinjection.components.DaggerMFPComponent;
import com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal.MFPModule;
import javax.inject.Inject;

public class MyFitnessPalDisconnectionActivity extends BaseActivity {
    @Inject
    MyFitnessPalPresenterContract myFitnessPalPresenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_my_fitness_pal_disconnection);
        setupToolbar();
        setActivityGraph();
    }

    private void setActivityGraph() {
        DaggerMFPComponent.builder().appComponent(getAppComponent()).mFPModule(new MFPModule()).build().inject(this);
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        ((TextView) findViewById(R.id.toolbar_title)).setText(getString(R.string.mfp_connected_title));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_fitness_pal_disconnection, menu);
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

    public void disconnectFromMyFitnessPal(View view) {
        showDisconnectMyFitnessPalAppDialog();
    }

    private void showDisconnectMyFitnessPalAppDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.mfp_app_disconnect_title));
        alertDialogBuilder.setMessage(getString(R.string.mfp_app_disconnect_msg));
        alertDialogBuilder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MyFitnessPalDisconnectionActivity.this.myFitnessPalPresenter.disconnectMyFitnessPalApp();
                MyFitnessPalDisconnectionActivity.this.finish();
            }
        });
        alertDialogBuilder.create().show();
    }
}
