package com.nautilus.omni.permissions;

public interface PermissionAction {
    boolean hasSelfPermission(String str);

    void requestPermission(String str, int i);

    void requestPermission(String[] strArr, int i);

    boolean shouldShowRequestPermissionRationale(String str);
}
