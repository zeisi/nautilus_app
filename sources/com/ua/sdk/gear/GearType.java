package com.ua.sdk.gear;

public enum GearType {
    FOOTWEAR(1);
    
    int value;

    private GearType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }
}
