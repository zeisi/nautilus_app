package com.ua.sdk.group.invite;

import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.List;

public class GroupInviteBuilder {
    String email;
    List<GroupInvite> groupInvites;
    EntityRef<Group> groupRef;
    EntityRef<User> userRef;

    public GroupInviteBuilder setGroup(EntityRef<Group> groupRef2) {
        this.groupRef = groupRef2;
        return this;
    }

    public GroupInviteBuilder setUser(EntityRef<User> userRef2) {
        this.userRef = userRef2;
        return this;
    }

    public GroupInviteBuilder setEmail(String email2) {
        this.email = email2;
        return this;
    }

    public GroupInviteBuilder addGroupInvite(EntityRef<User> user, EntityRef<Group> group) {
        return addGroupInvite(new GroupInviteBuilder().setUser(user).setGroup(group).build());
    }

    public GroupInviteBuilder addGroupInvite(String email2, EntityRef<Group> group) {
        return addGroupInvite(new GroupInviteBuilder().setEmail(email2).setGroup(group).build());
    }

    public GroupInvite build() {
        if (this.groupInvites == null || this.groupInvites.isEmpty()) {
            Precondition.isNotNull(this.groupRef);
            if (this.email == null) {
                Precondition.isNotNull(this.userRef);
            }
            if (this.userRef == null) {
                Precondition.isNotNull(this.email);
            }
            GroupInviteImpl impl = new GroupInviteImpl();
            impl.setUserRef(this.userRef);
            impl.setGroupRef(this.groupRef);
            impl.setEmail(this.email);
            return impl;
        }
        GroupInviteBatch impl2 = new GroupInviteBatch();
        impl2.setGroupInvites(this.groupInvites);
        return impl2;
    }

    private GroupInviteBuilder addGroupInvite(GroupInvite groupInvite) {
        if (this.groupInvites == null) {
            this.groupInvites = new ArrayList();
        }
        this.groupInvites.add(groupInvite);
        return this;
    }
}
