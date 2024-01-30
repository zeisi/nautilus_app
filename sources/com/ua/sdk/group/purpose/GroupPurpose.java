package com.ua.sdk.group.purpose;

import com.ua.sdk.Entity;
import com.ua.sdk.EntityRef;

public interface GroupPurpose extends Entity<EntityRef<GroupPurpose>> {
    String getPurpose();

    Boolean isRestricted();
}
