package com.nautilus.underarmourconnection.userinterface.disconnection;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface DisconnectionContract {
    void initAuthenticationService(String str, String str2);

    void setBackgroundColor(int i);

    void setBackgroundDrawable(Drawable drawable);

    void setDisconnectButtonStyle(int i, Typeface typeface, int i2);

    void setDisconnectTitleStyle(int i, Typeface typeface);

    void setToolbarStyle(String str, int i, Typeface typeface);

    void setUnderArmourImage(Drawable drawable);
}
