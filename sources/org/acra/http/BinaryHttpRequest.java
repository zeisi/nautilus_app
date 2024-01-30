package org.acra.http;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import org.acra.config.ACRAConfiguration;
import org.acra.sender.HttpSender;

public class BinaryHttpRequest extends BaseHttpRequest<Uri> {
    @NonNull
    private final Context context;

    public BinaryHttpRequest(@NonNull ACRAConfiguration config, @NonNull Context context2, @NonNull HttpSender.Method method, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers) {
        super(config, context2, method, login, password, connectionTimeOut, socketTimeOut, headers);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public String getContentType(@NonNull Context context2, @NonNull Uri uri) {
        return HttpUtils.getMimeType(context2, uri);
    }

    /* access modifiers changed from: protected */
    public byte[] asBytes(Uri content) throws IOException {
        return HttpUtils.uriToByteArray(this.context, content);
    }
}
