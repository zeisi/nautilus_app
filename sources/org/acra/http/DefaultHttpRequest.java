package org.acra.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import org.acra.ACRAConstants;
import org.acra.config.ACRAConfiguration;
import org.acra.sender.HttpSender;

public class DefaultHttpRequest extends BaseHttpRequest<String> {
    @NonNull
    private final HttpSender.Type type;

    public DefaultHttpRequest(@NonNull ACRAConfiguration config, @NonNull Context context, @NonNull HttpSender.Method method, @NonNull HttpSender.Type type2, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers) {
        super(config, context, method, login, password, connectionTimeOut, socketTimeOut, headers);
        this.type = type2;
    }

    /* access modifiers changed from: protected */
    public String getContentType(@NonNull Context context, @NonNull String s) {
        return this.type.getContentType();
    }

    /* access modifiers changed from: protected */
    public byte[] asBytes(String content) throws IOException {
        return content.getBytes(ACRAConstants.UTF8);
    }
}
