package com.ua.sdk.suggestedfriends;

import com.ua.sdk.EntityList;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;

public interface SuggestedFriendsManager {
    EntityList<SuggestedFriends> fetchSuggestedFriendList(EntityListRef<SuggestedFriends> entityListRef) throws UaException;

    Request fetchSuggestedFriendList(EntityListRef<SuggestedFriends> entityListRef, FetchCallback<EntityList<SuggestedFriends>> fetchCallback);
}
