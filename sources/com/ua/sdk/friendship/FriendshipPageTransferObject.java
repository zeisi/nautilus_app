package com.ua.sdk.friendship;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class FriendshipPageTransferObject extends ApiTransferObject {
    public static final String KEY_FRIENDSHIPS = "friendships";
    @SerializedName("_embedded")
    public Map<String, ArrayList<FriendshipTransferObject>> friendships;
    @SerializedName("total_count")
    public Integer totalFriendsCount;

    private ArrayList<FriendshipTransferObject> getFriendshipList() {
        if (this.friendships == null) {
            return null;
        }
        return this.friendships.get(KEY_FRIENDSHIPS);
    }

    public static FriendshipPageTransferObject toTransferObject(FriendshipListImpl friendshipPage) {
        if (friendshipPage == null) {
            return null;
        }
        FriendshipPageTransferObject friendshipPageTransferObject = new FriendshipPageTransferObject();
        Iterator i$ = friendshipPage.getElements().iterator();
        while (i$.hasNext()) {
            friendshipPageTransferObject.friendships.get(KEY_FRIENDSHIPS).add(FriendshipTransferObject.fromFriendship((FriendshipImpl) ((Friendship) i$.next())));
        }
        friendshipPageTransferObject.setLinkMap(friendshipPage.getLinkMap());
        friendshipPageTransferObject.totalFriendsCount = Integer.valueOf(friendshipPage.getTotalCount());
        return friendshipPageTransferObject;
    }

    public static FriendshipListImpl fromTransferObject(FriendshipPageTransferObject to) {
        FriendshipListImpl page = new FriendshipListImpl();
        Iterator i$ = to.getFriendshipList().iterator();
        while (i$.hasNext()) {
            page.add(FriendshipTransferObject.toFriendship(i$.next()));
        }
        page.setLinkMap(to.getLinkMap());
        page.setTotalCount(to.totalFriendsCount.intValue());
        return page;
    }
}
