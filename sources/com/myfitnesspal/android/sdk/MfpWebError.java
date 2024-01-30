package com.myfitnesspal.android.sdk;

public class MfpWebError extends Throwable {
    private int mErrorCode;
    private String mFailingUrl;

    public MfpWebError(String message, int errorCode, String failingUrl) {
        super(message);
        this.mErrorCode = errorCode;
        this.mFailingUrl = failingUrl;
    }

    /* access modifiers changed from: package-private */
    public int getErrorCode() {
        return this.mErrorCode;
    }

    /* access modifiers changed from: package-private */
    public String getFailingUrl() {
        return this.mFailingUrl;
    }
}
