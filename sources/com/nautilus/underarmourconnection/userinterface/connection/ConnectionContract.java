package com.nautilus.underarmourconnection.userinterface.connection;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface ConnectionContract {
    void buildRedirectUri(String str, String str2, String str3);

    void getUnderArmourAuthorizationCode();

    void initAuthenticationService(String str, String str2);

    void setBackgroundColor(int i);

    void setBackgroundDrawable(Drawable drawable);

    void setConnectButtonStyle(int i, Typeface typeface, int i2);

    void setDescriptionTextStyle(int i, Typeface typeface);

    void setSyncTitleStyle(int i, Typeface typeface);

    void setToolbarStyle(String str, int i, Typeface typeface);

    void setUnderArmourImage(Drawable drawable);
}
