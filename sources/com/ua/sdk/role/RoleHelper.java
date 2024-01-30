package com.ua.sdk.role;

import com.ua.sdk.role.Role;
import java.util.ArrayList;
import java.util.List;

public class RoleHelper {
    public static final List<Role.Permission> ADMINISTRATOR_PERMISSIONS = new ArrayList<Role.Permission>() {
        {
            add(Role.Permission.ADMINISTER);
            add(Role.Permission.EDIT);
            add(Role.Permission.MODERATE);
        }
    };
    public static final List<Role.Permission> CREATOR_PERMISSIONS = new ArrayList<Role.Permission>() {
        {
            add(Role.Permission.CREATE);
        }
    };
    public static final String DESC_ADMINISTRATOR = "The ADMINISTRATOR role has permission to Manage object authorization, Edit object settings, Approve content";
    public static final String DESC_CREATOR = "The CREATOR role has permission to Create new objects";
    public static final String DESC_EDITOR = "The EDITOR role has permission to Edit object settings, Approve content";
    public static final String DESC_MODERATOR = "The MODERATOR role has permission to Approve content";
    public static final List<Role.Permission> EDITOR_PERMISSIONS = new ArrayList<Role.Permission>() {
        {
            add(Role.Permission.EDIT);
            add(Role.Permission.MODERATE);
        }
    };
    public static final List<Role.Permission> MODERATOR_PERMISSIONS = new ArrayList<Role.Permission>() {
        {
            add(Role.Permission.MODERATE);
        }
    };

    public static RoleImpl getRole(Role.Type name) throws IllegalArgumentException {
        switch (name) {
            case CREATOR:
                return new RoleImpl(Role.Type.CREATOR, CREATOR_PERMISSIONS, DESC_CREATOR);
            case MODERATOR:
                return new RoleImpl(Role.Type.MODERATOR, MODERATOR_PERMISSIONS, DESC_MODERATOR);
            case EDITOR:
                return new RoleImpl(Role.Type.EDITOR, EDITOR_PERMISSIONS, DESC_EDITOR);
            case ADMINISTRATOR:
                return new RoleImpl(Role.Type.ADMINISTRATOR, ADMINISTRATOR_PERMISSIONS, DESC_ADMINISTRATOR);
            default:
                throw new IllegalArgumentException("Role passed is not known!");
        }
    }

    public static RoleImpl getRole(String id) throws IllegalArgumentException {
        return getRole(Role.Type.parse(id));
    }
}
