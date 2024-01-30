package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.security.KeyStore;

public interface KeyStoreFactory {
    @Nullable
    KeyStore create(@NonNull Context context);
}
