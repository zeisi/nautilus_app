package com.buddybuild.sdk.events;

import android.util.Log;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.properties.BuddyBuildProperties;
import com.buddybuild.sdk.utils.HttpMakeRequestAsyncTask;

public class OpenAppEvent implements SDKEvent {
    public void trigger() {
        String email = BuddyBuildProperties.BUDDYBUILD_EMAIL;
        if (email == null || !email.contains("@")) {
            Log.d(Constants.BUDDYBUILD_TAG, "Skipping triggering OpenAppEvent due to invalid email");
            return;
        }
        new HttpMakeRequestAsyncTask().execute(new String[]{Constants.LAUNCHED_PARTIAL_URL});
    }
}
