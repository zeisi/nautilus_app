package org.acra.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.acra.ACRA;

public final class InstanceCreator {
    public <T> T create(@NonNull Class<? extends T> clazz, @Nullable T fallback) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to create instance of class " + clazz.getName(), e);
            return fallback;
        } catch (IllegalAccessException e2) {
            ACRA.log.e(ACRA.LOG_TAG, "Failed to create instance of class " + clazz.getName(), e2);
            return fallback;
        }
    }

    @NonNull
    public <T> List<T> create(@NonNull Collection<Class<? extends T>> classes) {
        List<T> result = new ArrayList<>();
        for (Class<? extends T> clazz : classes) {
            T instance = create(clazz, (Object) null);
            if (instance != null) {
                result.add(instance);
            }
        }
        return result;
    }
}
