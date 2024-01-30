package com.ua.sdk.role;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RolePagedTO extends ApiTransferObject {
    private static final String KEY_ROLES = "roles";
    @SerializedName("_embedded")
    public Map<String, ArrayList<RoleTO>> roles;
    @SerializedName("total_count")
    public Integer totalUserCount;

    private ArrayList<RoleTO> getRolesList() {
        if (this.roles == null) {
            return null;
        }
        return this.roles.get(KEY_ROLES);
    }

    public static RoleList toPage(RolePagedTO to) {
        RoleList list = new RoleList();
        Iterator i$ = to.getRolesList().iterator();
        while (i$.hasNext()) {
            list.add(RoleTO.fromTransferObject(i$.next()));
        }
        list.setLinkMap(to.getLinkMap());
        list.setTotalCount(to.totalUserCount.intValue());
        return list;
    }
}
