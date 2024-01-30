package org.acra.http;

import android.support.annotation.NonNull;
import java.io.IOException;
import java.net.URL;

public interface HttpRequest<T> {
    void send(@NonNull URL url, @NonNull T t) throws IOException;
}
