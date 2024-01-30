package com.ua.sdk.group.invite;

import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.user.User;

public interface GroupInvite extends Entity<EntityRef<GroupInvite>> {
    String getEmail();

    EntityRef<Group> getGroupRef();

    EntityRef<User> getUserRef();
}
