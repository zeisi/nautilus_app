package com.nautilus.omni.appmodules.settings.underarmour.connect;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Toast;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourConstants;
import com.nautilus.omni.syncservices.UnderArmourSyncService;
import com.nautilus.omni.util.ResourceHelper;
import com.nautilus.underarmourconnection.userinterface.connection.UnderArmourConnectActivity;

public class ConnectUnderArmourActivity extends UnderArmourConnectActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityStyle();
        buildRedirectUri(getString(R.string.intent_scheme), getString(R.string.intent_host), getString(R.string.intent_path));
        initAuthenticationService(UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET);
        getUnderArmourAuthorizationCode();
    }

    private void setActivityStyle() {
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.under_armour_background_color, (Resources.Theme) null));
        setToolbarStyle(getString(R.string.under_armour_settings_title), ResourcesCompat.getColor(getResources(), R.color.white, (Resources.Theme) null), ResourceHelper.getLatoBoldItalicTypeface(this));
        setConnectButtonStyle(ResourcesCompat.getColor(getResources(), R.color.under_armour_button_background_color, (Resources.Theme) null), ResourceHelper.getLatoRegularTypeface(getApplicationContext()), ResourcesCompat.getColor(getResources(), R.color.under_armour_button_text_color, (Resources.Theme) null));
        setSyncTitleStyle(ResourcesCompat.getColor(getResources(), R.color.under_armour_sync_title_text_color, (Resources.Theme) null), ResourceHelper.getLatoLightTypeface(getApplicationContext()));
        setDescriptionTextStyle(ResourcesCompat.getColor(getResources(), R.color.under_armour_description_text_color, (Resources.Theme) null), ResourceHelper.getLatoLightTypeface(getApplicationContext()));
        setUnderArmourImage(ResourcesCompat.getDrawable(getResources(), R.drawable.im_omni_ua_connect, (Resources.Theme) null));
    }

    public void startWorkoutSyncService() {
        Toast.makeText(this, getString(R.string.sync_workouts_with_under_armour_message), 1).show();
        UnderArmourSyncService.startSyncWithUnderArmourAction(getApplicationContext());
    }
}
