package com.ua.oss.org.apache.http.entity.mime.content;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.sdk.internal.Precondition;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamBody extends AbstractContentBody {
    private final String filename;
    private final InputStream in;

    public InputStreamBody(InputStream in2, ContentType contentType, String filename2) {
        super(contentType);
        Precondition.isNotNull(in2, "Input stream");
        this.in = in2;
        this.filename = filename2;
    }

    public InputStream getInputStream() {
        return this.in;
    }

    public void writeTo(OutputStream out) throws IOException {
        Precondition.isNotNull(out, "Output stream");
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int l = this.in.read(tmp);
                if (l != -1) {
                    out.write(tmp, 0, l);
                } else {
                    out.flush();
                    return;
                }
            }
        } finally {
            this.in.close();
        }
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public long getContentLength() {
        return -1;
    }

    public String getFilename() {
        return this.filename;
    }
}
