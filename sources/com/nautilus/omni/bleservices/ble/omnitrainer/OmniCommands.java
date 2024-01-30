package com.nautilus.omni.bleservices.ble.omnitrainer;

public enum OmniCommands {
    REQUEST_BLE_ACCESS(11),
    GIVEUP_BLE_ACCESS(12),
    GET_STATUS(13),
    CLEAR_NVM_FLAGS(18),
    GET_PRODUCT_DATA(21),
    GET_SYSTEM_DATA(23),
    GET_USER_DATA(24),
    GET_WORKOUT_DATA(25);
    
    private int cmdVal;

    private OmniCommands(int cmd) {
        this.cmdVal = cmd;
    }

    public int getCmdVal() {
        return this.cmdVal;
    }
}
