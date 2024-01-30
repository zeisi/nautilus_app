package com.ua.sdk.activitystory;

import android.os.Parcelable;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.Attachment;

public interface PhotoAttachment extends Attachment, Parcelable {
    ImageUrl getImageUrl();

    Attachment.Type getType();
}
