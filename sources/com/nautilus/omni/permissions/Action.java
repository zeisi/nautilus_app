package com.nautilus.omni.permissions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Action {
    public static final Action ACCESS_COARSE_LOCATION = new Action(1, "android.permission.ACCESS_COARSE_LOCATION");
    public static final Action ACCESS_FINE_LOCATION = new Action(0, "android.permission.ACCESS_FINE_LOCATION");
    public static final Action ACCESS_TO_ACCOUNTS = new Action(4, MULTIPLE_REQUEST_NAME);
    public static final int ACTION_CODE_ACCESS_COARSE_LOCATION = 1;
    public static final int ACTION_CODE_ACCESS_FINE_LOCATION = 0;
    public static final int ACTION_CODE_ACCESS_TO_ACCOUNTS = 4;
    public static final int ACTION_CODE_ACCOUNT_MANAGER = 4;
    public static final int ACTION_CODE_GET_ACCOUNTS = 5;
    public static final int ACTION_CODE_MULTIPLE_REQUEST = 3;
    public static final int ACTION_CODE_SAVE_IMAGE = 2;
    public static final Action GET_ACCOUNTS = new Action(5, "android.permission.GET_ACCOUNTS");
    public static final Action MULTIPLE_REQUEST = new Action(3, MULTIPLE_REQUEST_NAME);
    public static final String MULTIPLE_REQUEST_NAME = "MULTIPLE_REQUEST";
    public static final Action SAVE_IMAGE = new Action(2, "android.permission.WRITE_EXTERNAL_STORAGE");
    public static final Action USE_CREDENTIAL = new Action(4, "android.permission.ACCOUNT_MANAGER");
    private int code;
    private String permission;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionCode {
    }

    private Action(int value, String name) {
        this.code = value;
        this.permission = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getPermission() {
        return this.permission;
    }
}
