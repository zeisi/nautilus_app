package com.ua.oss.org.apache.http.entity.mime.content;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.sdk.internal.Precondition;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayBody extends AbstractContentBody {
    private final byte[] data;
    private final String filename;

    @Deprecated
    public ByteArrayBody(byte[] data2, String mimeType, String filename2) {
        this(data2, ContentType.create(mimeType), filename2);
    }

    public ByteArrayBody(byte[] data2, ContentType contentType, String filename2) {
        super(contentType);
        Precondition.isNotNull(data2, "byte[]");
        this.data = data2;
        this.filename = filename2;
    }

    public String getFilename() {
        return this.filename;
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.data);
    }

    public String getCharset() {
        return null;
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public long getContentLength() {
        return (long) this.data.length;
    }
}
