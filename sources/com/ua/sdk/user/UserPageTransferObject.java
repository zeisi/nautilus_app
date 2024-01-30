package com.ua.sdk.user;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class UserPageTransferObject extends ApiTransferObject {
    public static final String KEY_USERS = "user";
    @SerializedName("total_count")
    public Integer totalUserCount;
    @SerializedName("_embedded")
    public Map<String, ArrayList<UserTO>> users;

    private ArrayList<UserTO> getUserList() {
        if (this.users == null) {
            return null;
        }
        return this.users.get("user");
    }

    public static UserListImpl toPage(UserPageTransferObject to) throws UaException {
        UserListImpl list = new UserListImpl();
        Iterator i$ = to.getUserList().iterator();
        while (i$.hasNext()) {
            list.add(UserTO.fromTransferObject(i$.next()));
        }
        list.setLinkMap(to.getLinkMap());
        list.setTotalCount(to.totalUserCount.intValue());
        return list;
    }
}
