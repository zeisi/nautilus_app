package com.ua.sdk.route;

import com.ua.sdk.Entity;
import com.ua.sdk.internal.Link;

public interface RouteBookmark extends Entity {
    String getFromUserHref();

    Link getRoute();

    Link getUser();

    void setFromUserHref(String str);

    void setRoute(Link link);

    void setUser(Link link);
}
