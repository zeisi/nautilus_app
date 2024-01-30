package com.ua.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class ProgressHttpEntity implements HttpEntity {
    private final HttpEntity httpEntity;

    public ProgressHttpEntity(HttpEntity httpEntity2) {
        this.httpEntity = httpEntity2;
    }

    public boolean isRepeatable() {
        return this.httpEntity.isRepeatable();
    }

    public boolean isChunked() {
        return this.httpEntity.isChunked();
    }

    public long getContentLength() {
        return this.httpEntity.getContentLength();
    }

    public Header getContentType() {
        return this.httpEntity.getContentType();
    }

    public Header getContentEncoding() {
        return this.httpEntity.getContentEncoding();
    }

    public InputStream getContent() throws IOException, IllegalStateException {
        return this.httpEntity.getContent();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.httpEntity.writeTo(outputStream);
    }

    public boolean isStreaming() {
        return this.httpEntity.isStreaming();
    }

    public void consumeContent() throws IOException {
        this.httpEntity.consumeContent();
    }
}
