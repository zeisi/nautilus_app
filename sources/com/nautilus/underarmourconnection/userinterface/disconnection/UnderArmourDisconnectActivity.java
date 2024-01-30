package com.nautilus.underarmourconnection.userinterface.disconnection;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nautilus.underarmourconnection.R;
import com.nautilus.underarmourconnection.errorhandling.UnderArmourError;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import com.nautilus.underarmourconnection.services.authentication.LogoutCallback;
import com.nautilus.underarmourconnection.userinterface.BaseActivity;

public abstract class UnderArmourDisconnectActivity extends BaseActivity implements DisconnectionContract, LogoutCallback {
    /* access modifiers changed from: private */
    public AuthenticationService mAuthenticationService;
    private Button mButtonDisconnect;
    private LinearLayout mContainer;
    private ImageView mImageViewUnderArmour;
    private TextView mTextViewDisconnectTitle;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_armour_disconnect);
        initViews();
    }

    private void initViews() {
        this.mContainer = (LinearLayout) findViewById(R.id.container);
        this.mButtonDisconnect = (Button) findViewById(R.id.button_disconnect);
        this.mTextViewDisconnectTitle = (TextView) findViewById(R.id.text_view_disconnect_title);
        this.mImageViewUnderArmour = (ImageView) findViewById(R.id.image_view_underarmour);
        setupToolbar();
    }

    private void setupToolbar() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        this.mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    public void setBackgroundColor(int color) {
        this.mContainer.setBackgroundColor(color);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mContainer.setBackground(drawable);
    }

    public void setToolbarStyle(String title, int textColor, Typeface typeface) {
        this.mToolbarTitle.setText(title);
        this.mToolbarTitle.setTextColor(textColor);
        this.mToolbarTitle.setTypeface(typeface);
    }

    public void setDisconnectButtonStyle(int backgroundColor, Typeface typeface, int textColor) {
        this.mButtonDisconnect.setBackgroundColor(backgroundColor);
        this.mButtonDisconnect.setTypeface(typeface);
        this.mButtonDisconnect.setTextColor(textColor);
    }

    public void setDisconnectTitleStyle(int textColor, Typeface typeface) {
        this.mTextViewDisconnectTitle.setTextColor(textColor);
        this.mTextViewDisconnectTitle.setTypeface(typeface);
    }

    public void setUnderArmourImage(Drawable drawable) {
        this.mImageViewUnderArmour.setImageDrawable(drawable);
    }

    public void initAuthenticationService(String clientKey, String clientSecretKey) {
        if (this.mAuthenticationService == null) {
            this.mAuthenticationService = new AuthenticationService(getApplicationContext(), clientKey, clientSecretKey);
        }
    }

    public void disconnectFromUnderArmour(View view) {
        if (this.mAuthenticationService != null) {
            showDisconnectDialog();
        }
    }

    private void showDisconnectDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.disconnect_dialog_title));
        alertDialogBuilder.setMessage((CharSequence) getString(R.string.disconnect_dialog_message));
        alertDialogBuilder.setPositiveButton((CharSequence) getString(R.string.ok_button), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                UnderArmourDisconnectActivity.this.mAuthenticationService.logoutFromUnderArmour(UnderArmourDisconnectActivity.this);
            }
        });
        alertDialogBuilder.setNegativeButton((CharSequence) getString(R.string.cancel_button), (DialogInterface.OnClickListener) null);
        alertDialogBuilder.create().show();
    }

    public void onLogoutSuccess() {
        Toast.makeText(this, getString(R.string.logout_successful), 1).show();
        finish();
    }

    public void onLogoutError(UnderArmourError underArmourError) {
        showErrorAlert(underArmourError.getErrorRecommendation(), R.string.ok_button);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return true;
        }
        finish();
        return true;
    }
}
