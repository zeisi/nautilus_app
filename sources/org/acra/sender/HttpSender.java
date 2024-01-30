package org.acra.sender;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.attachment.DefaultAttachmentProvider;
import org.acra.collections.ImmutableSet;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.http.BinaryHttpRequest;
import org.acra.http.DefaultHttpRequest;
import org.acra.http.HttpUtils;
import org.acra.http.MultipartHttpRequest;
import org.acra.model.Element;
import org.acra.util.InstanceCreator;
import org.json.JSONObject;

public class HttpSender implements ReportSender {
    private final ACRAConfiguration config;
    @Nullable
    private final Uri mFormUri;
    private final Map<ReportField, String> mMapping;
    private final Method mMethod;
    @Nullable
    private String mPassword;
    private final Type mType;
    @Nullable
    private String mUsername;

    public enum Method {
        POST {
            /* access modifiers changed from: package-private */
            public URL createURL(String baseUrl, CrashReportData report) throws MalformedURLException {
                return new URL(baseUrl);
            }
        },
        PUT {
            /* access modifiers changed from: package-private */
            public URL createURL(String baseUrl, CrashReportData report) throws MalformedURLException {
                return new URL(baseUrl + '/' + report.getProperty(ReportField.REPORT_ID));
            }
        };

        /* access modifiers changed from: package-private */
        public abstract URL createURL(String str, CrashReportData crashReportData) throws MalformedURLException;
    }

    public enum Type {
        FORM("application/x-www-form-urlencoded") {
            /* access modifiers changed from: package-private */
            public String convertReport(HttpSender sender, CrashReportData report) throws IOException {
                return HttpUtils.getParamsAsFormString(sender.convertToForm(report));
            }
        },
        JSON("application/json") {
            /* access modifiers changed from: package-private */
            public String convertReport(HttpSender sender, CrashReportData report) throws IOException {
                return sender.convertToJson(report).toString();
            }
        };
        
        private final String contentType;

        /* access modifiers changed from: package-private */
        public abstract String convertReport(HttpSender httpSender, CrashReportData crashReportData) throws IOException;

        private Type(String contentType2) {
            this.contentType = contentType2;
        }

        @NonNull
        public String getContentType() {
            return this.contentType;
        }
    }

    public HttpSender(@NonNull ACRAConfiguration config2, @NonNull Method method, @NonNull Type type) {
        this(config2, method, type, (Map<ReportField, String>) null);
    }

    public HttpSender(@NonNull ACRAConfiguration config2, @NonNull Method method, @NonNull Type type, @Nullable Map<ReportField, String> mapping) {
        this(config2, method, type, (String) null, mapping);
    }

    public HttpSender(@NonNull ACRAConfiguration config2, @NonNull Method method, @NonNull Type type, @Nullable String formUri, @Nullable Map<ReportField, String> mapping) {
        this.config = config2;
        this.mMethod = method;
        this.mFormUri = formUri == null ? null : Uri.parse(formUri);
        this.mMapping = mapping;
        this.mType = type;
        this.mUsername = null;
        this.mPassword = null;
    }

    public void setBasicAuth(@Nullable String username, @Nullable String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

    public void send(@NonNull Context context, @NonNull CrashReportData report) throws ReportSenderException {
        String login;
        String password;
        try {
            String baseUrl = this.mFormUri == null ? this.config.formUri() : this.mFormUri.toString();
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Connect to " + baseUrl);
            }
            if (this.mUsername != null) {
                login = this.mUsername;
            } else {
                login = isNull(this.config.formUriBasicAuthLogin()) ? null : this.config.formUriBasicAuthLogin();
            }
            if (this.mPassword != null) {
                password = this.mPassword;
            } else {
                password = isNull(this.config.formUriBasicAuthPassword()) ? null : this.config.formUriBasicAuthPassword();
            }
            sendHttpRequests(this.config, context, this.mMethod, this.mType, login, password, this.config.connectionTimeout(), this.config.socketTimeout(), this.config.httpHeaders(), this.mType.convertReport(this, report), this.mMethod.createURL(baseUrl, report), ((AttachmentUriProvider) new InstanceCreator().create(this.config.attachmentUriProvider(), new DefaultAttachmentProvider())).getAttachments(context, this.config));
        } catch (IOException e) {
            throw new ReportSenderException("Error while sending " + this.config.reportType() + " report via Http " + this.mMethod.name(), e);
        }
    }

    /* access modifiers changed from: protected */
    public void sendHttpRequests(@NonNull ACRAConfiguration configuration, @NonNull Context context, @NonNull Method method, @NonNull Type type, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers, @NonNull String content, @NonNull URL url, @NonNull List<Uri> attachments) throws IOException {
        switch (method) {
            case POST:
                if (attachments.isEmpty()) {
                    sendWithoutAttachments(configuration, context, method, type, login, password, connectionTimeOut, socketTimeOut, headers, content, url);
                    return;
                } else {
                    postMultipart(configuration, context, type, login, password, connectionTimeOut, socketTimeOut, headers, content, url, attachments);
                    return;
                }
            case PUT:
                sendWithoutAttachments(configuration, context, method, type, login, password, connectionTimeOut, socketTimeOut, headers, content, url);
                for (Uri uri : attachments) {
                    putAttachment(configuration, context, login, password, connectionTimeOut, socketTimeOut, headers, url, uri);
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void sendWithoutAttachments(@NonNull ACRAConfiguration configuration, @NonNull Context context, @NonNull Method method, @NonNull Type type, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers, @NonNull String content, @NonNull URL url) throws IOException {
        new DefaultHttpRequest(configuration, context, method, type, login, password, connectionTimeOut, socketTimeOut, headers).send(url, content);
    }

    /* access modifiers changed from: protected */
    public void postMultipart(@NonNull ACRAConfiguration configuration, @NonNull Context context, @NonNull Type type, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers, @NonNull String content, @NonNull URL url, @NonNull List<Uri> attachments) throws IOException {
        new MultipartHttpRequest(configuration, context, type, login, password, connectionTimeOut, socketTimeOut, headers).send(url, Pair.create(content, attachments));
    }

    /* access modifiers changed from: protected */
    public void putAttachment(@NonNull ACRAConfiguration configuration, @NonNull Context context, @Nullable String login, @Nullable String password, int connectionTimeOut, int socketTimeOut, @Nullable Map<String, String> headers, @NonNull URL url, @NonNull Uri attachment) throws IOException {
        new BinaryHttpRequest(configuration, context, Method.PUT, login, password, connectionTimeOut, socketTimeOut, headers).send(new URL(url.toString() + "-" + HttpUtils.getFileNameFromUri(context, attachment)), attachment);
    }

    /* access modifiers changed from: protected */
    public JSONObject convertToJson(CrashReportData report) {
        return report.toJSON();
    }

    /* access modifiers changed from: protected */
    public Map<String, String> convertToForm(CrashReportData report) {
        return remap(report);
    }

    @NonNull
    private Map<String, String> remap(@NonNull Map<ReportField, Element> report) {
        Set<ReportField> fields = this.config.reportContent();
        if (fields.isEmpty()) {
            fields = new ImmutableSet<>((E[]) ACRAConstants.DEFAULT_REPORT_FIELDS);
        }
        Map<String, String> finalReport = new HashMap<>(report.size());
        for (ReportField field : fields) {
            Element element = report.get(field);
            String value = element != null ? TextUtils.join("\n", element.flatten()) : null;
            if (this.mMapping == null || this.mMapping.get(field) == null) {
                finalReport.put(field.toString(), value);
            } else {
                finalReport.put(this.mMapping.get(field), value);
            }
        }
        return finalReport;
    }

    private boolean isNull(@Nullable String aString) {
        return aString == null || ACRAConstants.NULL_VALUE.equals(aString);
    }
}
