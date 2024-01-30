package com.ua.sdk.util;

public class SystemTimeSource implements TimeSource {
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
