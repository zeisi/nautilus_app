package com.nautilus.omni.permissions;

import java.util.List;

public interface PermissionCallbacks {
    void permissionAccepted(int i);

    void permissionDenied(int i, List<String> list);

    void showRationale(int i, String[] strArr, Action action);
}
