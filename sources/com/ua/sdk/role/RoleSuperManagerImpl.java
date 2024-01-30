package com.ua.sdk.role;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityList;
import com.ua.sdk.EntityRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;
import com.ua.sdk.role.Role;
import com.ua.sdk.user.permission.UserPermission;
import com.ua.sdk.user.permission.UserPermissionManager;
import com.ua.sdk.user.role.UserRole;
import com.ua.sdk.user.role.UserRoleManager;

public class RoleSuperManagerImpl implements RoleSuperManager {
    private final RoleManager roleManager;
    private final UserPermissionManager userPermissionManager;
    private final UserRoleManager userRoleManager;

    public RoleSuperManagerImpl(RoleManager roleManager2, UserPermissionManager userPermissionManager2, UserRoleManager userRoleManager2) {
        this.roleManager = roleManager2;
        this.userPermissionManager = userPermissionManager2;
        this.userRoleManager = userRoleManager2;
    }

    public Role fetchRole(Role.Type name) {
        return this.roleManager.fetchRole(name);
    }

    public EntityList<Role> fetchRoleList() throws UaException {
        return this.roleManager.fetchRoleList();
    }

    public Request fetchRoleList(FetchCallback<EntityList<Role>> callback) {
        return this.roleManager.fetchRoleList(callback);
    }

    public EntityList<UserPermission> fetchUserPermission(EntityRef ref) throws UaException {
        return this.userPermissionManager.fetchUserPermission(ref);
    }

    public Request fetchUserPermission(EntityRef ref, FetchCallback<EntityList<UserPermission>> callback) {
        return this.userPermissionManager.fetchUserPermission(ref, callback);
    }

    public EntityList<UserPermission> fetchUserPermissionList() throws UaException {
        return this.userPermissionManager.fetchUserPermissionList();
    }

    public Request fetchUserPermissionList(FetchCallback<EntityList<UserPermission>> callback) {
        return this.userPermissionManager.fetchUserPermissionList(callback);
    }

    public UserRole fetchUserRole(EntityRef ref) throws UaException {
        return this.userRoleManager.fetchUserRole(ref);
    }

    public Request fetchUserRole(EntityRef ref, FetchCallback<UserRole> callback) {
        return this.userRoleManager.fetchUserRole(ref, callback);
    }

    public UserRole createUserRole(UserRole entity) throws UaException {
        return this.userRoleManager.createUserRole(entity);
    }

    public Request createUserRole(UserRole entity, CreateCallback<UserRole> callback) {
        return this.userRoleManager.createUserRole(entity, callback);
    }
}
