package com.nautilus.underarmourconnection.services.authentication;

public interface AuthenticationServiceInterface {
    void getNetWorkError(LoginCallback loginCallback);

    void loginToUnderArmour(String str, LoginCallback loginCallback);

    void logoutFromUnderArmour(LogoutCallback logoutCallback);

    void requestUnderArmourAuthorization(String str, LoginCallback loginCallback);
}
