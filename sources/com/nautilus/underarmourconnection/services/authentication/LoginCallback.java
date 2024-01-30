package com.nautilus.underarmourconnection.services.authentication;

import com.nautilus.underarmourconnection.errorhandling.UnderArmourError;

public interface LoginCallback {
    void onLoginError(UnderArmourError underArmourError);

    void onLoginSuccess();
}
