package com.nautilus.omni.appmodules.awardtypes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.sharelibrary.OnSelectPackageListener;
import com.example.sharelibrary.ShareDialogComponent;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awardtypes.adapters.AwardTypesAdapter;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenterContract;
import com.nautilus.omni.dependencyinjection.components.DaggerAwardTypesComponent;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule;
import com.nautilus.omni.permissions.Action;
import com.nautilus.omni.permissions.PermissionCallbacks;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.Util;
import java.util.List;
import javax.inject.Inject;

public class AwardTypesActivity extends BaseActivity implements AwardTypesActivityContract, PermissionCallbacks {
    public static final String TAG = AwardTypesActivity.class.getSimpleName();
    @Inject
    AwardTypesPresenterContract awardTypesPresenter;
    private AwardTypesAdapter mAwardTypesAdapter;
    @Inject
    AwardValueBuilder mAwardValueBuilder;
    @Inject
    PermissionPresenter mPermissionPresenter;
    /* access modifiers changed from: private */
    public ShareDialogComponent shareDialogComponent;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_award_types);
        setActivityGraph();
        setupToolbar();
        initRecyclerViewAwardTypes();
        this.awardTypesPresenter.loadAvailableAwards();
        this.shareDialogComponent = new ShareDialogComponent(this);
        this.shareDialogComponent.setOnPackageSelectListener(new OnSelectPackageListener() {
            public void onPackageSelect(String pPackage, ResolveInfo resolveInfo) {
                Util.updateShareMessageAccordingWithAppSelected(AwardTypesActivity.this, Util.buildShareIntent(AwardTypesActivity.this, pPackage, resolveInfo), AwardTypesActivity.this, R.id.award_types_content);
                AwardTypesActivity.this.shareDialogComponent.hide();
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ((TextView) findViewById(R.id.toolbar_title)).setText(getString(R.string.title_award_types));
    }

    private void initRecyclerViewAwardTypes() {
        RecyclerView mRecyclerViewAwardTypes = (RecyclerView) findViewById(R.id.recycler_view_award_types);
        mRecyclerViewAwardTypes.setLayoutManager(new LinearLayoutManager(this));
        this.mAwardTypesAdapter = new AwardTypesAdapter(this.mAwardValueBuilder);
        mRecyclerViewAwardTypes.setAdapter(this.mAwardTypesAdapter);
    }

    private void setActivityGraph() {
        DaggerAwardTypesComponent.builder().appComponent(getAppComponent()).awardTypesModule(new AwardTypesModule(this)).build().inject(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_award_types, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_award_types_share:
                this.mPermissionPresenter.requestWriteExternalStorangePermission();
                return true;
            default:
                return true;
        }
    }

    public void setAvailableAwardsAndUnit(List<Object> availableAwards, int unit) {
        this.mAwardTypesAdapter.setmAwardsList(availableAwards, unit);
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
                    intent.setData(Uri.fromParts("package", AwardTypesActivity.this.getPackageName(), (String) null));
                    AwardTypesActivity.this.startActivity(intent);
                }
            }, R.string.app_name, R.string.storage_permission_required_denied, this, R.string.settings_text, R.string.permi_dialog_negative_button_text);
        }
    }

    public void showRationale(int actionCode, String[] permissions, final Action action) {
        showPermissionsDialog(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                AwardTypesActivity.this.mPermissionPresenter.requestPermission(action);
            }
        }, R.string.app_name, R.string.storage_permission_required_rationale, this, R.string.permi_dialog_ok_button_text, R.string.permi_dialog_negative_button_text);
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
