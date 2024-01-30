package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;
import org.acra.ACRA;

final class AssetKeyStoreFactory extends BaseKeyStoreFactory {
    private final String assetName;

    AssetKeyStoreFactory(String certificateType, String assetName2) {
        super(certificateType);
        this.assetName = assetName2;
    }

    public InputStream getInputStream(@NonNull Context context) {
        try {
            return context.getAssets().open(this.assetName);
        } catch (IOException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Could not open certificate in asset://" + this.assetName, e);
            return null;
        }
    }
}
