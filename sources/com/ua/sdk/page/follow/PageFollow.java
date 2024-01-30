package com.ua.sdk.page.follow;

import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;
import com.ua.sdk.page.Page;
import com.ua.sdk.user.User;

public interface PageFollow extends Entity {
    EntityRef<Page> getPageReference();

    EntityRef<PageFollow> getRef();

    EntityRef<User> getUserReference();
}
