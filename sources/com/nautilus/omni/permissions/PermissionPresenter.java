package com.nautilus.omni.permissions;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class PermissionPresenter {
    private PermissionAction permissionAction;
    private PermissionCallbacks permissionCallbacks;
    private List<String> permissionsDenied;

    @Inject
    public PermissionPresenter(PermissionAction permissionAction2, PermissionCallbacks permissionCallbacks2) {
        this.permissionAction = permissionAction2;
        this.permissionCallbacks = permissionCallbacks2;
    }

    public void setPermissionCallbacks(PermissionCallbacks permissionCallbacks2) {
        this.permissionCallbacks = permissionCallbacks2;
    }

    public void requestAccessFineCoarseLocationPermission() {
        checkAndRequestPermission(new Action[]{Action.ACCESS_COARSE_LOCATION, Action.ACCESS_FINE_LOCATION}, Action.MULTIPLE_REQUEST.getCode());
    }

    public void requestAccessToAccountsPermission() {
        checkAndRequestPermission(new Action[]{Action.GET_ACCOUNTS}, Action.ACCESS_TO_ACCOUNTS.getCode());
    }

    public void requestWriteExternalStorangePermission() {
        checkAndRequestPermission(Action.SAVE_IMAGE);
    }

    private void checkAndRequestPermission(Action action) {
        if (this.permissionAction.hasSelfPermission(action.getPermission())) {
            this.permissionCallbacks.permissionAccepted(action.getCode());
        } else if (this.permissionAction.shouldShowRequestPermissionRationale(action.getPermission())) {
            this.permissionCallbacks.showRationale(action.getCode(), (String[]) null, action);
        } else {
            this.permissionAction.requestPermission(action.getPermission(), action.getCode());
        }
    }

    private void checkAndRequestPermission(Action[] actions, int codeRequest) {
        int requestRequiredCont = 0;
        boolean neverAsk = false;
        String[] permissions = new String[actions.length];
        for (int i = 0; i < actions.length; i++) {
            if (!this.permissionAction.hasSelfPermission(actions[i].getPermission())) {
                requestRequiredCont++;
                permissions[i] = actions[i].getPermission();
                if (!this.permissionAction.shouldShowRequestPermissionRationale(permissions[i])) {
                    neverAsk = true;
                }
            }
        }
        if (requestRequiredCont <= 0) {
            this.permissionCallbacks.permissionAccepted(codeRequest);
        } else if (neverAsk) {
            this.permissionAction.requestPermission(permissions, codeRequest);
        } else {
            this.permissionCallbacks.showRationale(3, permissions, (Action) null);
        }
    }

    public void requestPermission(Action action) {
        this.permissionAction.requestPermission(action.getPermission(), action.getCode());
    }

    public void checkGrantedPermission(int[] grantResults, String[] permissions, int requestCode) {
        if (verifyGrantedPermission(grantResults, permissions)) {
            this.permissionCallbacks.permissionAccepted(requestCode);
        } else {
            this.permissionCallbacks.permissionDenied(requestCode, this.permissionsDenied);
        }
    }

    private boolean verifyGrantedPermission(int[] grantResults, String[] permissions) {
        this.permissionsDenied = new ArrayList();
        int len = permissions.length;
        for (int i = 0; i < len; i++) {
            String permission = permissions[i];
            if (grantResults[i] == -1 && !this.permissionAction.shouldShowRequestPermissionRationale(permission)) {
                this.permissionsDenied.add(permission);
            }
        }
        for (int result : grantResults) {
            if (result != 0) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(String[] permissions, int codeRequest) {
        this.permissionAction.requestPermission(permissions, codeRequest);
    }
}
