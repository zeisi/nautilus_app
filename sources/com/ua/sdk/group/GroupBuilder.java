package com.ua.sdk.group;

import com.ua.sdk.EntityRef;
import com.ua.sdk.group.objective.GroupObjective;
import com.ua.sdk.group.purpose.GroupPurpose;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class GroupBuilder {
    String description;
    GroupObjective groupObjective;
    EntityRef<GroupPurpose> groupPurposeRef;
    String invitationCode;
    Boolean invitationRequired;
    Boolean isPublic;
    String name;

    public GroupBuilder setIsPublic(Boolean isPublic2) {
        this.isPublic = isPublic2;
        return this;
    }

    public GroupBuilder setInvitationRequired(Boolean invitationRequired2) {
        this.invitationRequired = invitationRequired2;
        return this;
    }

    public GroupBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public GroupBuilder setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public GroupBuilder setInvitationCode(String invitationCode2) {
        this.invitationCode = invitationCode2;
        return this;
    }

    public GroupBuilder setGroupObjective(GroupObjective groupObjective2) {
        this.groupObjective = groupObjective2;
        return this;
    }

    public GroupBuilder setGroupPurpose(GroupPurpose purpose) {
        Precondition.isNotNull(purpose);
        this.groupPurposeRef = purpose.getRef();
        return this;
    }

    public Group build() {
        Precondition.isNotNull(this.name, "name");
        Precondition.isNotNull(this.groupObjective, "groupObjective");
        if (this.groupPurposeRef == null) {
            this.groupPurposeRef = new LinkEntityRef(UrlBuilderImpl.GET_GROUP_PURPOSE_CHALLENGE_URL);
        }
        GroupImpl impl = new GroupImpl();
        impl.setPublicGroup(this.isPublic);
        impl.setInvitationRequired(this.invitationRequired);
        impl.setName(this.name);
        impl.setDescription(this.description);
        impl.setInvitationCode(this.invitationCode);
        impl.setGroupObjective(this.groupObjective);
        impl.setGroupPurposeRef(this.groupPurposeRef);
        return impl;
    }
}
