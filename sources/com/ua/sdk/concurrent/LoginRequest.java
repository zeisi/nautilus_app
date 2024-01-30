package com.ua.sdk.concurrent;

import com.ua.sdk.Ua;
import com.ua.sdk.UaException;
import com.ua.sdk.user.User;

public class LoginRequest extends AsyncRequest<User> {
    private final Ua.LoginCallback callback;

    public LoginRequest(Ua.LoginCallback callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: package-private */
    public void onDone(User user, UaException e) {
        EntityEventHandler.callOnLogin(user, e, this.callback);
    }
}
