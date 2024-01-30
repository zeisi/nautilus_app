package com.buddybuild.sdk.buildinfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.R;
import com.buddybuild.sdk.properties.BuddyBuildProperties;

public class BuildInfoActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bb_build_info_activity);
        setUpBuildInfo();
        ((ImageButton) findViewById(R.id.bb_back_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BuildInfoActivity.this.finish();
            }
        });
    }

    private void setUpBuildInfo() {
        String buildNumber = BuddyBuildProperties.BUDDYBUILD_BUILD_NUMBER;
        String appVariant = BuddyBuildProperties.BUDDYBUILD_APPLICATION_VARIANT_NAME;
        String versionName = Constants.APP_VERSION_NAME;
        String versionCode = Constants.APP_VERSION;
        ((TextView) findViewById(R.id.bb_build_number)).setText(buildNumber);
        ((TextView) findViewById(R.id.bb_app_variant)).setText(appVariant);
        ((TextView) findViewById(R.id.bb_version_name)).setText(versionName);
        ((TextView) findViewById(R.id.bb_version_code)).setText(versionCode);
    }
}
