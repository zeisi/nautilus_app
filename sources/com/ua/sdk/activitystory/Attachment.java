package com.ua.sdk.activitystory;

import android.os.Parcelable;
import java.util.Date;

public interface Attachment extends Parcelable {

    public enum Status {
        UNKNOWN,
        PENDING,
        PROCESSING,
        READY
    }

    public enum Type {
        PHOTO,
        VIDEO
    }

    Date getPublished();

    Status getStatus();

    Type getType();

    String getUri();
}
