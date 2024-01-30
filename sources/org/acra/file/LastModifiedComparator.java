package org.acra.file;

import android.support.annotation.NonNull;
import java.io.File;
import java.util.Comparator;

final class LastModifiedComparator implements Comparator<File> {
    LastModifiedComparator() {
    }

    public int compare(@NonNull File lhs, @NonNull File rhs) {
        long l = lhs.lastModified();
        long r = rhs.lastModified();
        if (l < r) {
            return -1;
        }
        return l == r ? 0 : 1;
    }
}
