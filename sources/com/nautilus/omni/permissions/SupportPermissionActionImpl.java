package com.nautilus.omni.permissions;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class SupportPermissionActionImpl implements PermissionAction {
    private Activity activity;

    public SupportPermissionActionImpl(Activity activity2) {
        this.activity = activity2;
    }

    public boolean hasSelfPermission(String permission) {
        return ContextCompat.checkSelfPermission(this.activity, permission) == 0;
    }

    public void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this.activity, new String[]{permission}, requestCode);
    }

    public void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this.activity, permissions, requestCode);
    }

    public boolean shouldShowRequestPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission);
    }
}
