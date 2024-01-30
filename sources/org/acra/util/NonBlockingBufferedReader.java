package org.acra.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

final class NonBlockingBufferedReader {
    private Thread backgroundReaderThread = null;
    /* access modifiers changed from: private */
    public volatile IOException exception = null;
    /* access modifiers changed from: private */
    public final BlockingQueue<String> lines = new LinkedBlockingQueue();

    NonBlockingBufferedReader(final BufferedReader bufferedReader) {
        this.backgroundReaderThread = new Thread(new Runnable() {
            public void run() {
                String line;
                while (!Thread.interrupted() && (line = bufferedReader.readLine()) != null) {
                    try {
                        NonBlockingBufferedReader.this.lines.add(line);
                    } catch (IOException e) {
                        IOException unused = NonBlockingBufferedReader.this.exception = e;
                        return;
                    } finally {
                        IOUtils.safeClose(bufferedReader);
                    }
                }
            }
        });
        this.backgroundReaderThread.setDaemon(true);
        this.backgroundReaderThread.start();
    }

    /* access modifiers changed from: package-private */
    public String readLine() throws InterruptedException, IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.lines.isEmpty()) {
            return null;
        } else {
            return this.lines.poll(500, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: package-private */
    public void close() {
        if (this.backgroundReaderThread != null) {
            this.backgroundReaderThread.interrupt();
            this.backgroundReaderThread = null;
        }
    }
}
