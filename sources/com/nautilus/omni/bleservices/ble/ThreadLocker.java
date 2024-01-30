package com.nautilus.omni.bleservices.ble;

public class ThreadLocker {
    private boolean isLocked = false;
    private boolean isThreadUnlockByNotify = false;

    public synchronized boolean lockThread(long millis) throws InterruptedException {
        this.isThreadUnlockByNotify = false;
        this.isLocked = true;
        wait(millis);
        this.isLocked = false;
        return this.isThreadUnlockByNotify;
    }

    public synchronized void unlockThread() {
        if (this.isLocked) {
            this.isLocked = false;
            this.isThreadUnlockByNotify = true;
            notify();
        }
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked(boolean isWaiting) {
        this.isLocked = isWaiting;
    }
}
