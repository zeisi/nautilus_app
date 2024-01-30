package com.nautilus.omni.app;

import android.view.View;

public class Callbacks {

    public interface ChangeFragmentsCallback {
        void onChangeFragment(String str, String str2, boolean z);
    }

    public interface NavDrawerCallback {
        void onDrawerItemClicked(View view, int i);
    }
}
