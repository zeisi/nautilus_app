package com.nautilus.underarmourconnection.services.authentication;

import android.content.Context;
import com.nautilus.underarmourconnection.errorhandling.UnderArmourErrorHandler;
import com.nautilus.underarmourconnection.services.underarmour.UnderArmourService;
import com.ua.sdk.Ua;
import com.ua.sdk.UaException;
import com.ua.sdk.user.User;

public class AuthenticationService extends UnderArmourService implements AuthenticationServiceInterface {
    public AuthenticationService(Context context, String clientKey, String clientSecret) {
        super(context, clientKey, clientSecret);
    }

    public void requestUnderArmourAuthorization(String redirectUri, LoginCallback loginCallback) {
        this.mUnderArmourInterface.requestUserAuthorization(redirectUri);
    }

    public void getNetWorkError(LoginCallback loginCallback) {
        loginCallback.onLoginError(UnderArmourErrorHandler.getUnderArmourError(this.mContext, new UaException(UaException.Code.NETWORK)));
    }

    public void loginToUnderArmour(String authenticationCode, final LoginCallback loginCallback) {
        this.mAuthenticationCode = authenticationCode;
        this.mUnderArmourInterface.login(this.mAuthenticationCode, new Ua.LoginCallback() {
            public void onLogin(User user, UaException exception) {
                if (user != null) {
                    loginCallback.onLoginSuccess();
                } else {
                    loginCallback.onLoginError(UnderArmourErrorHandler.getUnderArmourError(AuthenticationService.this.mContext, exception));
                }
            }
        });
    }

    public void logoutFromUnderArmour(final LogoutCallback logoutCallback) {
        this.mUnderArmourInterface.logout(new Ua.LogoutCallback() {
            public void onLogout(UaException e) {
                logoutCallback.onLogoutSuccess();
            }
        });
    }
}
