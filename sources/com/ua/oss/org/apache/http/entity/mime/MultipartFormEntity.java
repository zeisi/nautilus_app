package com.ua.oss.org.apache.http.entity.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

class MultipartFormEntity implements HttpEntity {
    private final long contentLength;
    private final Header contentType;
    private final AbstractMultipartForm multipart;

    MultipartFormEntity(AbstractMultipartForm multipart2, String contentType2, long contentLength2) {
        this.multipart = multipart2;
        this.contentType = new BasicHeader(MIME.CONTENT_TYPE, contentType2);
        this.contentLength = contentLength2;
    }

    public boolean isRepeatable() {
        return this.contentLength != -1;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    public void writeTo(OutputStream outstream) throws IOException {
        this.multipart.writeTo(outstream);
    }
}
