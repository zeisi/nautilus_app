package org.acra.file;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.io.File;

public enum Directory {
    FILES_LEGACY {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return (fileName.startsWith("/") ? Directory.ROOT : Directory.FILES).getFile(context, fileName);
        }
    },
    FILES {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(context.getFilesDir(), fileName);
        }
    },
    EXTERNAL_FILES {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(context.getExternalFilesDir((String) null), fileName);
        }
    },
    CACHE {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(context.getCacheDir(), fileName);
        }
    },
    EXTERNAL_CACHE {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(context.getExternalCacheDir(), fileName);
        }
    },
    NO_BACKUP_FILES {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(ContextCompat.getNoBackupFilesDir(context), fileName);
        }
    },
    EXTERNAL_STORAGE {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File(Environment.getExternalStorageDirectory(), fileName);
        }
    },
    ROOT {
        public File getFile(@NonNull Context context, @NonNull String fileName) {
            return new File("/", fileName);
        }
    };

    public abstract File getFile(@NonNull Context context, @NonNull String str);
}
