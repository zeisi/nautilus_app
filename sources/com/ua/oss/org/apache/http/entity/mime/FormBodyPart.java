package com.ua.oss.org.apache.http.entity.mime;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.content.AbstractContentBody;
import com.ua.oss.org.apache.http.entity.mime.content.ContentBody;
import com.ua.sdk.internal.Precondition;

public class FormBodyPart {
    private final ContentBody body;
    private final Header header = new Header();
    private final String name;

    public FormBodyPart(String name2, ContentBody body2) {
        Precondition.isNotNull(name2, "Name");
        Precondition.isNotNull(body2, "Body");
        this.name = name2;
        this.body = body2;
        generateContentDisp(body2);
        generateContentType(body2);
        generateTransferEncoding(body2);
    }

    public String getName() {
        return this.name;
    }

    public ContentBody getBody() {
        return this.body;
    }

    public Header getHeader() {
        return this.header;
    }

    public void addField(String name2, String value) {
        Precondition.isNotNull(name2, "Field name");
        this.header.addField(new MinimalField(name2, value));
    }

    /* access modifiers changed from: protected */
    public void generateContentDisp(ContentBody body2) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("form-data; name=\"");
        buffer.append(getName());
        buffer.append("\"");
        if (body2.getFilename() != null) {
            buffer.append("; filename=\"");
            buffer.append(body2.getFilename());
            buffer.append("\"");
        }
        addField(MIME.CONTENT_DISPOSITION, buffer.toString());
    }

    /* access modifiers changed from: protected */
    public void generateContentType(ContentBody body2) {
        ContentType contentType;
        if (body2 instanceof AbstractContentBody) {
            contentType = ((AbstractContentBody) body2).getContentType();
        } else {
            contentType = null;
        }
        if (contentType != null) {
            addField(MIME.CONTENT_TYPE, contentType.toString());
            return;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(body2.getMimeType());
        if (body2.getCharset() != null) {
            buffer.append("; charset=");
            buffer.append(body2.getCharset());
        }
        addField(MIME.CONTENT_TYPE, buffer.toString());
    }

    /* access modifiers changed from: protected */
    public void generateTransferEncoding(ContentBody body2) {
        addField(MIME.CONTENT_TRANSFER_ENC, body2.getTransferEncoding());
    }
}
