package com.ua.sdk.internal;

import com.ua.sdk.UploadCallback;
import com.ua.sdk.concurrent.EntityEventHandler;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;

public class ProgressOutputStream extends FilterOutputStream {
    private final UploadCallback callback;
    private final long totalLength;
    private volatile long transferred = 0;

    public ProgressOutputStream(OutputStream out, long totalLength2, UploadCallback callback2) {
        super(out);
        this.callback = callback2;
        this.totalLength = totalLength2;
    }

    public void write(int oneByte) throws InterruptedIOException {
        try {
            this.out.write(oneByte);
            this.transferred++;
            if (this.transferred < this.totalLength) {
                EntityEventHandler.callOnUploadProgress(this.transferred, this.callback);
            }
        } catch (IOException e) {
            throw new InterruptedIOException();
        }
    }

    public void write(byte[] buffer, int offset, int length) throws InterruptedIOException {
        try {
            this.out.write(buffer, offset, length);
            this.transferred += (long) length;
            if (this.transferred < this.totalLength) {
                EntityEventHandler.callOnUploadProgress(this.transferred, this.callback);
            }
        } catch (IOException e) {
            throw new InterruptedIOException();
        }
    }
}
