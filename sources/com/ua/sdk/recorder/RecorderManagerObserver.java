package com.ua.sdk.recorder;

public interface RecorderManagerObserver {
    void onRecorderCreated(String str);

    void onRecorderDestroyed(String str);

    void onRecorderRecovered(String str);
}
