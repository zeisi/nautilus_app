package org.acra.util;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;
import org.acra.ACRA;

public final class Installation {
    private static final String INSTALLATION = "ACRA-INSTALLATION";
    private static String sID;

    private Installation() {
    }

    @NonNull
    public static synchronized String id(@NonNull Context context) {
        String str;
        synchronized (Installation.class) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) {
                        writeInstallationFile(installation);
                    }
                    sID = readInstallationFile(installation);
                } catch (IOException e) {
                    ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
                    str = "Couldn't retrieve InstallationId";
                } catch (RuntimeException e2) {
                    ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e2);
                    str = "Couldn't retrieve InstallationId";
                }
            }
            str = sID;
        }
        return str;
    }

    /* JADX INFO: finally extract failed */
    @NonNull
    private static String readInstallationFile(@NonNull File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[((int) f.length())];
        try {
            f.readFully(bytes);
            IOUtils.safeClose(f);
            return new String(bytes);
        } catch (Throwable th) {
            IOUtils.safeClose(f);
            throw th;
        }
    }

    private static void writeInstallationFile(@NonNull File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        try {
            out.write(UUID.randomUUID().toString().getBytes());
        } finally {
            IOUtils.safeClose(out);
        }
    }
}
