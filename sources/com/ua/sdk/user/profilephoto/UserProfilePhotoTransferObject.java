package com.ua.sdk.user.profilephoto;

import com.ua.sdk.internal.ApiTransferObject;

public class UserProfilePhotoTransferObject extends ApiTransferObject {
    public static UserProfilePhotoImpl toUserProfilePhotoImpl(UserProfilePhotoTransferObject obj) {
        UserProfilePhotoImpl profilePhoto = new UserProfilePhotoImpl();
        for (String key : obj.getLinkKeys()) {
            profilePhoto.setLinksForRelation(key, obj.getLinks(key));
        }
        return profilePhoto;
    }
}
