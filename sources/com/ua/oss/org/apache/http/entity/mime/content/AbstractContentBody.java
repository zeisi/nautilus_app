package com.ua.oss.org.apache.http.entity.mime.content;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.sdk.internal.Precondition;
import java.nio.charset.Charset;

public abstract class AbstractContentBody implements ContentBody {
    private final ContentType contentType;

    public AbstractContentBody(ContentType contentType2) {
        Precondition.isNotNull(contentType2, "Content type");
        this.contentType = contentType2;
    }

    @Deprecated
    public AbstractContentBody(String mimeType) {
        this(ContentType.parse(mimeType));
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public String getMimeType() {
        return this.contentType.getMimeType();
    }

    public String getMediaType() {
        String mimeType = this.contentType.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(0, i);
        }
        return mimeType;
    }

    public String getSubType() {
        String mimeType = this.contentType.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(i + 1);
        }
        return null;
    }

    public String getCharset() {
        Charset charset = this.contentType.getCharset();
        if (charset != null) {
            return charset.name();
        }
        return null;
    }
}
