package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.acra.ACRA;

final class FileKeyStoreFactory extends BaseKeyStoreFactory {
    private final String filePath;

    FileKeyStoreFactory(String certificateType, String filePath2) {
        super(certificateType);
        this.filePath = filePath2;
    }

    public InputStream getInputStream(@NonNull Context context) {
        try {
            return new FileInputStream(this.filePath);
        } catch (FileNotFoundException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Could not find File " + this.filePath, e);
            return null;
        }
    }
}
