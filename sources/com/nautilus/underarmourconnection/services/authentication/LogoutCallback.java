package com.nautilus.underarmourconnection.services.authentication;

import com.nautilus.underarmourconnection.errorhandling.UnderArmourError;

public interface LogoutCallback {
    void onLogoutError(UnderArmourError underArmourError);

    void onLogoutSuccess();
}
