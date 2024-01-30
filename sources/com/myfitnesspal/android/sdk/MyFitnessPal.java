package com.myfitnesspal.android.sdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import com.myfitnesspal.shared.api.AppGalleryApi;
import com.myfitnesspal.shared.api.AppGalleryApiImpl;
import com.myfitnesspal.shared.constants.Constants;
import com.myfitnesspal.shared.utils.Ln;
import com.myfitnesspal.shared.utils.Strings;
import com.myfitnesspal.shared.utils.UriUtils;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;

public class MyFitnessPal {
    private static final int DEFAULT_AUTH_ACTIVITY_CODE = 349233733;
    private static final String TAG = "MFP-authorize";
    private String accessToken;
    private String authCode;
    private MfpAuthListener authListener;
    private String clientId;
    private Activity parentActivity;
    private int parentRequestCode;
    private String refreshToken;
    private AuthorizeRequestData requestData;
    private String suffix;

    public MyFitnessPal(String clientId2) {
        this(clientId2, (String) null);
    }

    public MyFitnessPal(String _clientId, String _suffix) {
        if (_clientId == null) {
            throw new IllegalArgumentException("You must specify your application ID when instantiating a MyFitnessPal object. See README for details.");
        }
        this.clientId = _clientId;
        this.suffix = _suffix;
    }

    public void authorize(Activity activity, Scope scope, MfpAuthListener authListener2) {
        authorize(activity, (int) DEFAULT_AUTH_ACTIVITY_CODE, scope, authListener2);
    }

    public void authorize(Activity activity, int activityCode, Scope scope, MfpAuthListener authListener2) {
        authorize(activity, activityCode, scope, ResponseType.Token, authListener2);
    }

    public void authorize(Activity activity, Scope scope, ResponseType responseType, MfpAuthListener authListener2) {
        authorize(activity, DEFAULT_AUTH_ACTIVITY_CODE, scope, responseType, authListener2);
    }

    public void authorize(Activity activity, int activityCode, Scope scope, ResponseType responseType, MfpAuthListener authListener2) {
        Ln.d(Constants.LaunchActions.AUTHORIZE, new Object[0]);
        setAccessToken((String) null);
        setRefreshToken((String) null);
        setAuthCode((String) null);
        this.parentActivity = activity;
        this.parentRequestCode = activityCode;
        this.requestData = new AuthorizeRequestData(this.clientId, this.suffix, scope, responseType);
        this.authListener = authListener2;
        if (!startSingleSignOn(activity, activityCode)) {
            new DownloadManager().redirectToDownloadPage(activity, this.clientId, this.suffix, this.requestData.getRedirectUri());
        }
    }

    private boolean startSingleSignOn(Activity activity, int activityCode) {
        boolean didSucceed = true;
        Intent intent = createActivityIntent();
        if (intent == null) {
            return false;
        }
        try {
            activity.startActivityForResult(intent, activityCode);
        } catch (ActivityNotFoundException e) {
            didSucceed = false;
        }
        return didSucceed;
    }

    private Intent createActivityIntent() {
        Intent intent = new Intent().setPackage(MyFitnessPalConstants.MFP_PACKAGE).setAction("com.myfitnesspal.android.login.AUTHORIZE").putExtras(this.requestData.asBundle());
        intent.setFlags(intent.getFlags() & -268435457);
        Ln.d("SDK connect intent action = %s, flags = 0x%x", intent.getAction(), Integer.valueOf(intent.getFlags()));
        if (Util.validateActivityIntent(this.parentActivity, intent, true)) {
            return intent;
        }
        return null;
    }

    private Intent createTestActivityIntent() {
        Intent intent = this.parentActivity.getPackageManager().getLaunchIntentForPackage("com.myfitnesspal.marketplacetest");
        if (intent == null) {
            return null;
        }
        intent.putExtra(Constants.LaunchParams.OPERATION, "mfpconnect").putExtra("action", Constants.LaunchActions.AUTHORIZE).putExtras(this.requestData.asBundle());
        intent.setFlags(intent.getFlags() & -268435457);
        Ln.d("SDK connect intent action = %s, flags = 0x%x", intent.getAction(), Integer.valueOf(intent.getFlags()));
        if (!Util.validateActivityIntent(this.parentActivity, intent, false)) {
            intent = null;
        }
        return intent;
    }

    private void startDialogAuth(Activity activity) {
        CookieSyncManager.createInstance(activity);
        dialog(activity, Constants.LaunchActions.AUTHORIZE, new MfpAuthListener() {
            public void onComplete(Bundle params) {
                CookieSyncManager.getInstance().sync();
                MyFitnessPal.this.persistDataAndInvokeCallbacks(params);
            }

            public void onError(MfpWebError error) {
                MyFitnessPal.this.onMfpWebError(error);
            }

            public void onMfpError(MfpAuthError authError) {
                MyFitnessPal.this.onGenericMfpError(authError);
            }

            public void onCancel(Bundle params) {
                MyFitnessPal.this.onUserCanceled(params);
            }
        });
    }

    public void authorizeCallback(int requestCode, int resultCode, Intent data) {
        Ln.d("auth callback: req = %s, result = %s", Integer.valueOf(requestCode), Integer.valueOf(resultCode));
        if (requestCode == this.parentRequestCode) {
            switch (resultCode) {
                case -1:
                    onResultOK(data);
                    return;
                case 0:
                    onResultCanceled(data);
                    return;
                default:
                    return;
            }
        }
    }

    public void dialog(Context context, String action, MfpAuthListener authListener2) {
        dialog(context, action, new Bundle(), authListener2);
    }

    public void dialog(Context context, String action, Bundle params, MfpAuthListener authListener2) {
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Util.showAlert(context, "Error", "Application requires permission to access the Internet");
            return;
        }
        new MfpDialog(context, Constants.LaunchActions.AUTHORIZE, this.requestData, params, authListener2).show();
    }

    public boolean hasValidData() {
        Ln.d("hasValidData: wantsCode = %s, code = %s, wantsTokens = %s, accessToken = %s, refreshToken = %s", Boolean.valueOf(wantsCode()), getAuthCode(), Boolean.valueOf(wantsTokens()), getAccessToken(), getRefreshToken());
        if ((!wantsTokens() || (getAccessToken() != null && getRefreshToken() != null)) && (!wantsCode() || getAuthCode() != null)) {
            return true;
        }
        return false;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    private void setAccessToken(String token) {
        this.accessToken = token;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    private void setRefreshToken(String token) {
        this.refreshToken = token;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    private void setAuthCode(String token) {
        this.authCode = token;
    }

    private void onResultCanceled(Intent data) {
        if (data != null) {
            onAndroidError(data);
        } else {
            onUserCanceled((Bundle) null);
        }
    }

    private void onResultOK(Intent data) {
        Bundle query = UriUtils.getQueryParams(data.getData());
        String error = query.getString(Constants.Params.ERROR);
        if (error == null) {
            persistDataAndInvokeCallbacks(query);
        } else if (error.equals(Constants.Errors.SINGLE_SIGN_ON_DISABLED)) {
            onSingleSignOnDisabled();
        } else if (error.equals(Constants.Errors.ACCESS_DENIED)) {
            onUserCanceled(query);
        } else {
            onGenericMfpError(query, error);
        }
    }

    private boolean wantsCode() {
        ResponseType responseType = this.requestData.getResponseType();
        return responseType == ResponseType.Code || responseType == ResponseType.Both;
    }

    private boolean wantsTokens() {
        ResponseType responseType = this.requestData.getResponseType();
        return responseType == ResponseType.Token || responseType == ResponseType.Both;
    }

    /* access modifiers changed from: private */
    public void persistDataAndInvokeCallbacks(Bundle params) {
        Ln.d(params.toString(), new Object[0]);
        String redirectUri = params.getString(Constants.LaunchParams.REDIRECT_URI);
        if (redirectUri == null || Strings.equals(redirectUri, this.requestData.getRedirectUri())) {
            setDataFromBundle(params);
        } else {
            Ln.d("Got redirectUrl = %s but doesn't match %s", redirectUri, this.requestData.getRedirectUri());
        }
        if (hasValidData()) {
            onAuthComplete(params);
            return;
        }
        onGenericMfpError(String.format("Failed to receive %s.", new Object[]{this.requestData.getResponseType().toString()}));
    }

    private void onAuthComplete(Bundle extras) {
        this.authListener.onComplete(extras);
    }

    /* access modifiers changed from: private */
    public void onUserCanceled(Bundle params) {
        Bundle data = params != null ? params : new Bundle();
        data.putBoolean(Constants.Params.USER_CANCELED, true);
        this.authListener.onCancel(data);
    }

    /* access modifiers changed from: private */
    public void onMfpWebError(MfpWebError error) {
        Ln.d("Login failed: " + error, new Object[0]);
        this.authListener.onError(error);
    }

    private void onGenericMfpError(Bundle query, String error) {
        String description = query.getString(Constants.Params.ERROR_DESCRIPTION);
        if (description != null) {
            error = error + com.nautilus.omni.util.Constants.COLON_SEPARATOR + description;
        }
        onGenericMfpError(error);
    }

    private void onGenericMfpError(String error) {
        onGenericMfpError(new MfpAuthError(error));
    }

    /* access modifiers changed from: private */
    public void onGenericMfpError(MfpAuthError authError) {
        Ln.d(authError.toString(), new Object[0]);
        this.authListener.onMfpError(authError);
    }

    private void onSingleSignOnDisabled() {
        Ln.d("Hosted auth currently disabled. Retrying dialog auth...", new Object[0]);
        startDialogAuth(this.parentActivity);
    }

    private void onAndroidError(Intent data) {
        Ln.d("Login failed: " + data.getStringExtra(Constants.Params.ERROR), new Object[0]);
        this.authListener.onError(new MfpWebError(data.getStringExtra(Constants.Params.ERROR), data.getIntExtra("error_code", -1), data.getStringExtra("failing_url")));
    }

    private void setDataFromBundle(Bundle params) {
        Ln.d("setDataFromBundle: Response type = %s, code = %s, accessToken = %s, refreshToken = %s", this.requestData.getResponseType().toString(), getAuthCode(), getAccessToken(), getRefreshToken());
        setAuthCode(params.getString("code"));
        setAccessToken(params.getString("access_token"));
        setRefreshToken(params.getString(Constants.Params.REFRESH_TOKEN));
    }

    private AppGalleryApi createApi() {
        return new AppGalleryApiImpl();
    }
}
