package com.ua.sdk.user.role;

import com.ua.sdk.Entity;
import com.ua.sdk.internal.Link;

public interface UserRole extends Entity {
    Link getResource();

    Link getRole();

    Link getUser();

    void setResource(Link link);

    void setRole(Link link);

    void setUser(Link link);
}
