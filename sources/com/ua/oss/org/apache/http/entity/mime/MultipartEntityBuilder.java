package com.ua.oss.org.apache.http.entity.mime;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.content.ByteArrayBody;
import com.ua.oss.org.apache.http.entity.mime.content.ContentBody;
import com.ua.oss.org.apache.http.entity.mime.content.FileBody;
import com.ua.oss.org.apache.http.entity.mime.content.InputStreamBody;
import com.ua.oss.org.apache.http.entity.mime.content.StringBody;
import com.ua.sdk.internal.Precondition;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpEntity;

public class MultipartEntityBuilder {
    private static final String DEFAULT_SUBTYPE = "form-data";
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private List<FormBodyPart> bodyParts = null;
    private String boundary = null;
    private Charset charset = null;
    private HttpMultipartMode mode = HttpMultipartMode.BROWSER_COMPATIBLE;
    private String subType = DEFAULT_SUBTYPE;

    public static MultipartEntityBuilder create() {
        return new MultipartEntityBuilder();
    }

    MultipartEntityBuilder() {
    }

    public MultipartEntityBuilder setMode(HttpMultipartMode mode2) {
        this.mode = mode2;
        return this;
    }

    public MultipartEntityBuilder setCharset(Charset charset2) {
        this.charset = charset2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public MultipartEntityBuilder addPart(FormBodyPart bodyPart) {
        if (bodyPart != null) {
            if (this.bodyParts == null) {
                this.bodyParts = new ArrayList();
            }
            this.bodyParts.add(bodyPart);
        }
        return this;
    }

    public MultipartEntityBuilder addPart(String name, ContentBody contentBody) {
        Precondition.isNotNull(name, "Name");
        Precondition.isNotNull(contentBody, "Content body");
        return addPart(new FormBodyPart(name, contentBody));
    }

    public MultipartEntityBuilder addTextBody(String name, String text, ContentType contentType) {
        return addPart(name, new StringBody(text, contentType));
    }

    public MultipartEntityBuilder addTextBody(String name, String text) {
        return addTextBody(name, text, ContentType.DEFAULT_TEXT);
    }

    public MultipartEntityBuilder addBinaryBody(String name, byte[] b, ContentType contentType, String filename) {
        return addPart(name, new ByteArrayBody(b, contentType, filename));
    }

    public MultipartEntityBuilder addBinaryBody(String name, File file, ContentType contentType, String filename) {
        return addPart(name, new FileBody(file, contentType, filename));
    }

    public MultipartEntityBuilder addBinaryBody(String name, InputStream stream, ContentType contentType, String filename) {
        return addPart(name, new InputStreamBody(stream, contentType, filename));
    }

    private String generateContentType(String boundary2, Charset charset2) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/form-data; boundary=");
        buffer.append(boundary2);
        if (charset2 != null) {
            buffer.append("; charset=");
            buffer.append(charset2.name());
        }
        return buffer.toString();
    }

    private String generateBoundary() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30;
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    /* access modifiers changed from: package-private */
    public MultipartFormEntity buildEntity() {
        AbstractMultipartForm form;
        String st = this.subType != null ? this.subType : DEFAULT_SUBTYPE;
        Charset cs = this.charset;
        String b = this.boundary != null ? this.boundary : generateBoundary();
        List<FormBodyPart> bps = this.bodyParts != null ? new ArrayList<>(this.bodyParts) : Collections.emptyList();
        switch (this.mode != null ? this.mode : HttpMultipartMode.BROWSER_COMPATIBLE) {
            case BROWSER_COMPATIBLE:
                form = new HttpBrowserCompatibleMultipart(st, cs, b, bps);
                break;
            default:
                form = new HttpBrowserCompatibleMultipart(st, cs, b, bps);
                break;
        }
        return new MultipartFormEntity(form, generateContentType(b, cs), form.getTotalLength());
    }

    public HttpEntity build() {
        return buildEntity();
    }
}
