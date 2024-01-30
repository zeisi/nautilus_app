package com.ua.sdk.page.follow;

import com.ua.sdk.UaException;
import com.ua.sdk.internal.ApiTransferObject;

public class PageFollowTransferObject extends ApiTransferObject {
    public static PageFollowTransferObject fromPageFollow(PageFollow pageFollow) {
        if (pageFollow == null) {
            return null;
        }
        PageFollowTransferObject to = new PageFollowTransferObject();
        if (!(pageFollow instanceof PageFollowImpl)) {
            return to;
        }
        to.setLinkMap(((PageFollowImpl) pageFollow).getLinkMap());
        return to;
    }

    public static PageFollowImpl toPageFollowImpl(PageFollowTransferObject obj) throws UaException {
        if (obj == null) {
            return null;
        }
        PageFollowImpl pageFollow = new PageFollowImpl();
        for (String key : obj.getLinkKeys()) {
            pageFollow.setLinksForRelation(key, obj.getLinks(key));
        }
        return pageFollow;
    }
}
