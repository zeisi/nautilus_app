package com.dd;

class StateManager {
    private boolean mIsEnabled;
    private int mProgress;

    public StateManager(CircularProgressButton progressButton) {
        this.mIsEnabled = progressButton.isEnabled();
        this.mProgress = progressButton.getProgress();
    }

    public void saveProgress(CircularProgressButton progressButton) {
        this.mProgress = progressButton.getProgress();
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void checkState(CircularProgressButton progressButton) {
        if (progressButton.getProgress() != getProgress()) {
            progressButton.setProgress(progressButton.getProgress());
        } else if (progressButton.isEnabled() != isEnabled()) {
            progressButton.setEnabled(progressButton.isEnabled());
        }
    }
}
