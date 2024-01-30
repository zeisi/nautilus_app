package com.ua.sdk.route.bookmark;

import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.route.RouteBookmark;

public class RouteBookmarkImpl extends ApiTransferObject implements RouteBookmark {
    private String fromUserHref;
    private transient Link route;
    private transient Link user;

    public RouteBookmarkImpl() {
    }

    private RouteBookmarkImpl(Builder in) {
        this.route = in.route;
        this.user = in.user;
    }

    public EntityRef<RouteBookmark> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public Link getRoute() {
        if (this.route != null) {
            return this.route;
        }
        return getLink("route");
    }

    public Link getUser() {
        if (this.user != null) {
            return this.user;
        }
        return getLink("user");
    }

    public String getFromUserHref() {
        return this.fromUserHref;
    }

    public void setRoute(Link route2) {
        setLink("route", route2);
    }

    public void setUser(Link user2) {
        setLink("user", user2);
    }

    public void setFromUserHref(String fromUserHref2) {
        this.fromUserHref = fromUserHref2;
    }

    public int describeContents() {
        return 0;
    }

    public static class Builder {
        Link route;
        Link user;

        public Builder setRoute(String route2) {
            this.route = new Link((String) null, route2);
            return this;
        }

        public Builder setUser(String user2) {
            this.user = new Link((String) null, user2);
            return this;
        }

        public RouteBookmarkImpl build() {
            return new RouteBookmarkImpl(this);
        }
    }
}
