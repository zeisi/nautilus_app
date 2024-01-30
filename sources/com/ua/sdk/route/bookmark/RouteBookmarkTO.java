package com.ua.sdk.route.bookmark;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.route.RouteBookmark;

public class RouteBookmarkTO extends ApiTransferObject {
    @SerializedName("from_user")
    public String fromUserHref;

    public static RouteBookmarkImpl fromTransferObject(RouteBookmarkTO from) {
        if (from == null) {
            return null;
        }
        RouteBookmarkImpl answer = new RouteBookmarkImpl();
        answer.setFromUserHref(from.fromUserHref);
        for (String key : from.getLinkKeys()) {
            answer.setLinksForRelation(key, from.getLinks(key));
        }
        return answer;
    }

    public static RouteBookmarkTO toTransferObject(RouteBookmark entity) {
        if (entity == null) {
            return null;
        }
        RouteBookmarkImpl route = (RouteBookmarkImpl) entity;
        RouteBookmarkTO to = new RouteBookmarkTO();
        to.fromUserHref = route.getFromUserHref();
        to.setLinkMap(route.getLinkMap());
        return to;
    }
}
