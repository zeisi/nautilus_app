package com.nautilus.omni.appmodules.settings.underarmour.disconnect;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourConstants;
import com.nautilus.omni.util.ResourceHelper;
import com.nautilus.underarmourconnection.userinterface.disconnection.UnderArmourDisconnectActivity;

public class DisconnectUnderArmourActivity extends UnderArmourDisconnectActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityStyle();
        initAuthenticationService(UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET);
    }

    private void setActivityStyle() {
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.under_armour_background_color, (Resources.Theme) null));
        setToolbarStyle(getString(R.string.under_armour_settings_title), ResourcesCompat.getColor(getResources(), R.color.white, (Resources.Theme) null), ResourceHelper.getLatoBoldItalicTypeface(this));
        setDisconnectTitleStyle(ResourcesCompat.getColor(getResources(), R.color.under_armour_disconnect_title_text_color, (Resources.Theme) null), ResourceHelper.getLatoLightTypeface(this));
        setDisconnectButtonStyle(ResourcesCompat.getColor(getResources(), R.color.under_armour_button_background_color, (Resources.Theme) null), ResourceHelper.getLatoRegularTypeface(this), ResourcesCompat.getColor(getResources(), R.color.under_armour_button_text_color, (Resources.Theme) null));
        setUnderArmourImage(ResourcesCompat.getDrawable(getResources(), R.drawable.im_omni_ua_connect, (Resources.Theme) null));
    }
}
