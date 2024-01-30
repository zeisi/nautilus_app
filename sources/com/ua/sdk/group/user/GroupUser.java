package com.ua.sdk.group.user;

import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;
import com.ua.sdk.group.Group;
import com.ua.sdk.user.User;

public interface GroupUser extends Entity<EntityRef<GroupUser>> {
    EntityRef<Group> getGroupRef();

    EntityRef<User> getUserRef();
}
