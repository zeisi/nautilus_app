package com.ua.sdk.activitystory;

import com.google.gson.annotations.SerializedName;

public class AttachmentDest {
    @SerializedName("href")
    String href;
    @SerializedName("index")
    int index;
    @SerializedName("rel")
    String rel;
    transient String userId;

    public AttachmentDest(String href2, String rel2, int index2) {
        this(href2, rel2, index2, (String) null);
    }

    public AttachmentDest(String href2, String rel2, int index2, String userId2) {
        this.href = href2;
        this.rel = rel2;
        this.index = index2;
        this.userId = userId2;
    }

    public String getHref() {
        return this.href;
    }

    public String getRel() {
        return this.rel;
    }

    public int getIndex() {
        return this.index;
    }

    public String getUserId() {
        return this.userId;
    }

    public String toString() {
        return '{' + "\"href\":\"" + this.href + "\",\"rel\":\"" + this.rel + "\",\"index\":" + this.index + '}';
    }
}
