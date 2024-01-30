package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.security.KeyStore;
import org.acra.config.ACRAConfiguration;
import org.acra.util.InstanceCreator;

public final class KeyStoreHelper {
    private static final String ASSET_PREFIX = "asset://";

    private KeyStoreHelper() {
    }

    @Nullable
    public static KeyStore getKeyStore(@NonNull Context context, @NonNull ACRAConfiguration config) {
        KeyStore keyStore = ((KeyStoreFactory) new InstanceCreator().create(config.keyStoreFactoryClass(), new NoKeyStoreFactory())).create(context);
        if (keyStore != null) {
            return keyStore;
        }
        int certificateRes = config.resCertificate();
        String certificatePath = config.certificatePath();
        String certificateType = config.certificateType();
        if (certificateRes != 0) {
            return new ResourceKeyStoreFactory(certificateType, certificateRes).create(context);
        }
        if (certificatePath.equals("")) {
            return keyStore;
        }
        if (certificatePath.startsWith(ASSET_PREFIX)) {
            return new AssetKeyStoreFactory(certificateType, certificatePath.substring(ASSET_PREFIX.length())).create(context);
        }
        return new FileKeyStoreFactory(certificateType, certificatePath).create(context);
    }
}
