package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import java.io.InputStream;

final class ResourceKeyStoreFactory extends BaseKeyStoreFactory {
    @RawRes
    private final int rawRes;

    ResourceKeyStoreFactory(String certificateType, @RawRes int rawRes2) {
        super(certificateType);
        this.rawRes = rawRes2;
    }

    public InputStream getInputStream(@NonNull Context context) {
        return context.getResources().openRawResource(this.rawRes);
    }
}
