package com.ua.sdk.user.role;

import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.role.Role;

public class UserRoleImpl extends ApiTransferObject implements UserRole {
    private transient Link resource;
    private transient Link role;
    private transient Link user;

    public UserRoleImpl() {
    }

    private UserRoleImpl(Builder in) {
        this.role = in.role;
        this.user = in.user;
        this.resource = in.resource;
    }

    public Link getRole() {
        if (this.role != null) {
            return this.role;
        }
        return getLink("role");
    }

    public Link getUser() {
        if (this.user != null) {
            return this.user;
        }
        return getLink("user");
    }

    public Link getResource() {
        if (this.resource != null) {
            return this.resource;
        }
        return getLink("resource");
    }

    public void setRole(Link role2) {
        this.role = role2;
    }

    public void setUser(Link user2) {
        this.user = user2;
    }

    public void setResource(Link resource2) {
        this.resource = resource2;
    }

    public EntityRef<UserRole> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public static class Builder {
        Link resource;
        Link role;
        Link user;

        public Builder setRole(Role.Type role2) {
            this.role = new Link((String) null, role2.getValue());
            return this;
        }

        public Builder setUser(String user2) {
            this.user = new Link((String) null, user2);
            return this;
        }

        public Builder setResource(String resource2) {
            this.resource = new Link(resource2);
            return this;
        }

        public UserRoleImpl build() {
            return new UserRoleImpl(this);
        }
    }
}
