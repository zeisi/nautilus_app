package com.ua.oss.org.apache.http.entity.mime;

import com.ua.sdk.internal.Precondition;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.util.ByteArrayBuffer;

abstract class AbstractMultipartForm {
    private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
    private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
    private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");
    private final String boundary;
    protected final Charset charset;
    private final String subType;

    /* access modifiers changed from: protected */
    public abstract void formatMultipartHeader(FormBodyPart formBodyPart, OutputStream outputStream) throws IOException;

    public abstract List<FormBodyPart> getBodyParts();

    private static ByteArrayBuffer encode(Charset charset2, String string) {
        ByteBuffer encoded = charset2.encode(CharBuffer.wrap(string));
        ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
        bab.append(encoded.array(), encoded.position(), encoded.remaining());
        return bab;
    }

    private static void writeBytes(ByteArrayBuffer b, OutputStream out) throws IOException {
        out.write(b.buffer(), 0, b.length());
    }

    private static void writeBytes(String s, Charset charset2, OutputStream out) throws IOException {
        writeBytes(encode(charset2, s), out);
    }

    private static void writeBytes(String s, OutputStream out) throws IOException {
        writeBytes(encode(MIME.DEFAULT_CHARSET, s), out);
    }

    protected static void writeField(MinimalField field, OutputStream out) throws IOException {
        writeBytes(field.getName(), out);
        writeBytes(FIELD_SEP, out);
        writeBytes(field.getBody(), out);
        writeBytes(CR_LF, out);
    }

    protected static void writeField(MinimalField field, Charset charset2, OutputStream out) throws IOException {
        writeBytes(field.getName(), charset2, out);
        writeBytes(FIELD_SEP, out);
        writeBytes(field.getBody(), charset2, out);
        writeBytes(CR_LF, out);
    }

    public AbstractMultipartForm(String subType2, Charset charset2, String boundary2) {
        Precondition.isNotNull(subType2, "Multipart subtype");
        Precondition.isNotNull(boundary2, "Multipart boundary");
        this.subType = subType2;
        this.charset = charset2 == null ? MIME.DEFAULT_CHARSET : charset2;
        this.boundary = boundary2;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getBoundary() {
        return this.boundary;
    }

    /* access modifiers changed from: package-private */
    public void doWriteTo(OutputStream out, boolean writeContent) throws IOException {
        ByteArrayBuffer boundary2 = encode(this.charset, getBoundary());
        for (FormBodyPart part : getBodyParts()) {
            writeBytes(TWO_DASHES, out);
            writeBytes(boundary2, out);
            writeBytes(CR_LF, out);
            formatMultipartHeader(part, out);
            writeBytes(CR_LF, out);
            if (writeContent) {
                part.getBody().writeTo(out);
            }
            writeBytes(CR_LF, out);
        }
        writeBytes(TWO_DASHES, out);
        writeBytes(boundary2, out);
        writeBytes(TWO_DASHES, out);
        writeBytes(CR_LF, out);
    }

    public void writeTo(OutputStream out) throws IOException {
        doWriteTo(out, true);
    }

    public long getTotalLength() {
        long contentLen = 0;
        for (FormBodyPart part : getBodyParts()) {
            long len = part.getBody().getContentLength();
            if (len < 0) {
                return -1;
            }
            contentLen += len;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            doWriteTo(out, false);
            return ((long) out.toByteArray().length) + contentLen;
        } catch (IOException e) {
            return -1;
        }
    }
}
