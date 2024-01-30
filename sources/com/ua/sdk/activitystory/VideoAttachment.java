package com.ua.sdk.activitystory;

import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.Attachment;

public interface VideoAttachment extends Attachment {

    public enum Provider {
        OOYALA,
        UNKNOWN
    }

    Provider getProvider();

    String getProviderId();

    String getProviderString();

    ImageUrl getThumbnailUrl();

    Attachment.Type getType();
}
