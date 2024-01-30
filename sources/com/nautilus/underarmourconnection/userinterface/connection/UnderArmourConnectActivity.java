package com.nautilus.underarmourconnection.userinterface.connection;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.nautilus.underarmourconnection.services.authentication.LoginCallback;
import com.nautilus.underarmourconnection.userinterface.BaseActivity;

public abstract class UnderArmourConnectActivity extends BaseActivity implements ConnectionContract, LoginCallback {
    private AuthenticationService mAuthenticationService;
    private Button mButtonConnect;
    private LinearLayout mContainer;
    private ImageView mImageViewUnderArmour;
    private String mRedirectURI;
    private TextView mTextViewDescription;
    private TextView mTextViewSyncTitle;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    public abstract void startWorkoutSyncService();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_armour_connect);
        initViews();
    }

    private void initViews() {
        this.mContainer = (LinearLayout) findViewById(R.id.container);
        this.mButtonConnect = (Button) findViewById(R.id.button_connect);
        this.mTextViewSyncTitle = (TextView) findViewById(R.id.text_view_sync_title);
        this.mTextViewDescription = (TextView) findViewById(R.id.text_view_connect_description);
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

    public void setSyncTitleStyle(int textColor, Typeface typeface) {
        this.mTextViewSyncTitle.setTextColor(textColor);
        this.mTextViewSyncTitle.setTypeface(typeface);
    }

    public void setDescriptionTextStyle(int textColor, Typeface typeface) {
        this.mTextViewDescription.setTextColor(textColor);
        this.mTextViewDescription.setTypeface(typeface);
    }

    public void setConnectButtonStyle(int backgroundColor, Typeface typeface, int textColor) {
        this.mButtonConnect.setBackgroundColor(backgroundColor);
        this.mButtonConnect.setTypeface(typeface);
        this.mButtonConnect.setTextColor(textColor);
    }

    public void setUnderArmourImage(Drawable drawable) {
        this.mImageViewUnderArmour.setImageDrawable(drawable);
    }

    public void initAuthenticationService(String clientKey, String clientSecretKey) {
        if (this.mAuthenticationService == null) {
            this.mAuthenticationService = new AuthenticationService(getApplicationContext(), clientKey, clientSecretKey);
        }
    }

    public void getUnderArmourAuthorizationCode() {
        Uri data = getIntent().getData();
        if (data != null) {
            this.mAuthenticationService.loginToUnderArmour(data.getQueryParameter("code"), this);
        }
    }

    public void buildRedirectUri(String intentScheme, String intentHost, String intentPath) {
        this.mRedirectURI = intentScheme + "://" + intentHost + intentPath;
    }

    public void connectWithUnderArmour(View view) {
        if (this.mRedirectURI != null) {
            executeConnection();
        } else {
            showErrorAlert(getString(R.string.redirect_uri_error), R.string.ok_button);
        }
    }

    private void executeConnection() {
        if (this.mAuthenticationService.isNetworkAvailable()) {
            this.mAuthenticationService.requestUnderArmourAuthorization(this.mRedirectURI, this);
            finish();
            return;
        }
        this.mAuthenticationService.getNetWorkError(this);
    }

    public void onLoginSuccess() {
        Toast.makeText(this, getString(R.string.login_successful), 1).show();
        startWorkoutSyncService();
        finish();
    }

    public void onLoginError(UnderArmourError underArmourError) {
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
