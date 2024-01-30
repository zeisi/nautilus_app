package com.ua.oss.org.apache.http.entity.mime.content;

import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.sdk.internal.Precondition;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBody extends AbstractContentBody {
    private final File file;
    private final String filename;

    @Deprecated
    public FileBody(File file2, String filename2, String mimeType, String charset) {
        this(file2, ContentType.create(mimeType, charset), filename2);
    }

    public FileBody(File file2, ContentType contentType, String filename2) {
        super(contentType);
        Precondition.isNotNull(file2, "File");
        this.file = file2;
        this.filename = filename2;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public void writeTo(OutputStream out) throws IOException {
        Precondition.isNotNull(out, "Output stream");
        InputStream in = new FileInputStream(this.file);
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int l = in.read(tmp);
                if (l != -1) {
                    out.write(tmp, 0, l);
                } else {
                    out.flush();
                    return;
                }
            }
        } finally {
            in.close();
        }
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public long getContentLength() {
        return this.file.length();
    }

    public String getFilename() {
        return this.filename;
    }

    public File getFile() {
        return this.file;
    }
}
