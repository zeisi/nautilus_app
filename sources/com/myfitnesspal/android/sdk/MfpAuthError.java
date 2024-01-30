package com.myfitnesspal.android.sdk;

public class MfpAuthError extends RuntimeException {
    private static final long serialVersionUID = 1;
    private int mErrorCode = 0;
    private String mErrorType;

    public MfpAuthError(String message) {
        super(message);
    }

    public MfpAuthError(String message, String type, int code) {
        super(message);
        this.mErrorType = type;
        this.mErrorCode = code;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorType() {
        return this.mErrorType;
    }
}
