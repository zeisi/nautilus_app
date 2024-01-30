package org.acra.http;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.acra.ACRAConstants;
import org.acra.config.ACRAConfiguration;
import org.acra.sender.HttpSender;

public class MultipartHttpRequest extends BaseHttpRequest<Pair<String, List<Uri>>> {
    private static final String BOUNDARY = "%&ACRA_REPORT_DIVIDER&%";
    private static final String BOUNDARY_FIX = "--";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String NEW_LINE = "\r\n";
    @NonNull
    private final Context context;
    @NonNull
    private final HttpSender.Type type;

    public MultipartHttpRequest(@NonNull ACRAConfiguration config, @NonNull Context context2, @NonNull HttpSender.Type type2, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers) {
        super(config, context2, HttpSender.Method.POST, login, password, connectionTimeOut, socketTimeOut, headers);
        this.context = context2;
        this.type = type2;
    }

    /* access modifiers changed from: protected */
    public String getContentType(@NonNull Context context2, @NonNull Pair<String, List<Uri>> pair) {
        return "multipart/mixed; boundary=%&ACRA_REPORT_DIVIDER&%";
    }

    /* access modifiers changed from: protected */
    public byte[] asBytes(Pair<String, List<Uri>> content) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, ACRAConstants.UTF8);
        try {
            writer.append(NEW_LINE).append(BOUNDARY_FIX).append(BOUNDARY).append(NEW_LINE);
            writer.append(CONTENT_TYPE).append(this.type.getContentType()).append(NEW_LINE).append(NEW_LINE);
            writer.append((CharSequence) content.first);
            for (Uri uri : (List) content.second) {
                writer.append(NEW_LINE).append(BOUNDARY_FIX).append(BOUNDARY).append(NEW_LINE);
                writer.append("Content-Disposition: attachment; filename=\"").append(HttpUtils.getFileNameFromUri(this.context, uri)).append('\"').append(NEW_LINE);
                writer.append(CONTENT_TYPE).append(HttpUtils.getMimeType(this.context, uri)).append(NEW_LINE).append(NEW_LINE);
                writer.flush();
                outputStream.write(HttpUtils.uriToByteArray(this.context, uri));
            }
            writer.append(NEW_LINE).append(BOUNDARY_FIX).append(BOUNDARY).append(BOUNDARY_FIX).append(NEW_LINE);
            writer.flush();
            return outputStream.toByteArray();
        } finally {
            writer.close();
        }
    }
}
