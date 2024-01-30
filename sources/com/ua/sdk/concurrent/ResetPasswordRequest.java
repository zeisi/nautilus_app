package com.ua.sdk.concurrent;

import com.ua.sdk.ResetPasswordCallback;
import com.ua.sdk.UaException;

public class ResetPasswordRequest extends AsyncRequest<Void> {
    private final ResetPasswordCallback callback;

    public ResetPasswordRequest(ResetPasswordCallback callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(Void ignore, UaException e) {
        EntityEventHandler.callOnResetPassword(e, this.callback);
    }
}
