package com.ua.sdk.user;

import com.ua.sdk.internal.LinkEntityRef;

public class CurrentUserRef extends LinkEntityRef<User> {
    public CurrentUserRef() {
        super("self", (String) null);
    }

    public boolean checkCache() {
        return false;
    }
}
