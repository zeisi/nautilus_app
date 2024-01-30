package org.acra.sender;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.attachment.AcraContentProvider;
import org.acra.attachment.AttachmentUriProvider;
import org.acra.attachment.DefaultAttachmentProvider;
import org.acra.collections.ImmutableSet;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.model.Element;
import org.acra.util.IOUtils;
import org.acra.util.InstanceCreator;

public class EmailIntentSender implements ReportSender {
    private final ACRAConfiguration config;

    public EmailIntentSender(@NonNull ACRAConfiguration config2) {
        this.config = config2;
    }

    public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {
        PackageManager pm = context.getPackageManager();
        String subject = buildSubject(context);
        String body = buildBody(errorContent);
        ArrayList<Uri> attachments = new ArrayList<>();
        boolean contentAttached = fillAttachmentList(context, errorContent, attachments);
        Intent resolveIntent = buildResolveIntent(subject, body);
        ComponentName resolveActivity = resolveIntent.resolveActivity(pm);
        if (resolveActivity == null) {
            throw new ReportSenderException("No email client found");
        } else if (attachments.size() == 0) {
            context.startActivity(resolveIntent);
        } else {
            Intent attachmentIntent = buildAttachmentIntent(subject, body, attachments, contentAttached);
            List<Intent> initialIntents = buildInitialIntents(pm, resolveIntent, attachmentIntent);
            String packageName = getPackageName(resolveActivity, initialIntents);
            attachmentIntent.setPackage(packageName);
            if (packageName == null) {
                for (Intent intent : initialIntents) {
                    grantPermission(context, intent, intent.getPackage(), attachments);
                }
                showChooser(context, initialIntents);
            } else if (attachmentIntent.resolveActivity(pm) != null) {
                grantPermission(context, attachmentIntent, packageName, attachments);
                context.startActivity(attachmentIntent);
            } else {
                ACRA.log.w(ACRA.LOG_TAG, "No email client supporting attachments found. Attachments will be ignored");
                context.startActivity(resolveIntent);
            }
        }
    }

    @Nullable
    private String getPackageName(@NonNull ComponentName resolveActivity, @NonNull List<Intent> initialIntents) {
        String packageName = resolveActivity.getPackageName();
        if (!packageName.equals("android")) {
            return packageName;
        }
        if (initialIntents.size() > 1) {
            return null;
        }
        if (initialIntents.size() == 1) {
            return initialIntents.get(0).getPackage();
        }
        return packageName;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Intent buildAttachmentIntent(@NonNull String subject, @NonNull String body, @NonNull ArrayList<Uri> attachments, boolean contentAttached) {
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{this.config.mailTo()});
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.setType("message/rfc822");
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", attachments);
        if (!contentAttached) {
            intent.putExtra("android.intent.extra.TEXT", body);
        }
        return intent;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Intent buildResolveIntent(@NonNull String subject, @NonNull String body) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.fromParts("mailto", this.config.mailTo(), (String) null));
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.TEXT", body);
        return intent;
    }

    @NonNull
    private List<Intent> buildInitialIntents(@NonNull PackageManager pm, @NonNull Intent resolveIntent, @NonNull Intent emailIntent) {
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(resolveIntent, 65536);
        List<Intent> initialIntents = new ArrayList<>();
        for (ResolveInfo info : resolveInfoList) {
            Intent packageSpecificIntent = new Intent(emailIntent);
            packageSpecificIntent.setPackage(info.activityInfo.packageName);
            if (packageSpecificIntent.resolveActivity(pm) != null) {
                initialIntents.add(packageSpecificIntent);
            }
        }
        return initialIntents;
    }

    private void showChooser(@NonNull Context context, @NonNull List<Intent> initialIntents) {
        Intent chooser = new Intent("android.intent.action.CHOOSER");
        chooser.putExtra("android.intent.extra.INTENT", initialIntents.remove(0));
        chooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) initialIntents.toArray(new Intent[initialIntents.size()]));
        chooser.addFlags(268435456);
        context.startActivity(chooser);
    }

    private void grantPermission(@NonNull Context context, Intent intent, String packageName, List<Uri> attachments) {
        if (Build.VERSION.SDK_INT >= 21) {
            intent.addFlags(1);
            return;
        }
        for (Uri uri : attachments) {
            context.grantUriPermission(packageName, uri, 1);
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String buildSubject(@NonNull Context context) {
        return context.getPackageName() + " Crash Report";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String buildBody(@NonNull CrashReportData errorContent) {
        Set<ReportField> fields = this.config.reportContent();
        if (fields.isEmpty()) {
            fields = new ImmutableSet<>((E[]) ACRAConstants.DEFAULT_MAIL_REPORT_FIELDS);
        }
        StringBuilder builder = new StringBuilder();
        for (ReportField field : fields) {
            builder.append(field.toString()).append('=');
            Element value = (Element) errorContent.get(field);
            if (value != null) {
                builder.append(TextUtils.join("\n\t", value.flatten()));
            }
            builder.append(10);
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public boolean fillAttachmentList(@NonNull Context context, @NonNull CrashReportData errorContent, @NonNull List<Uri> attachments) {
        Uri report;
        attachments.addAll(((AttachmentUriProvider) new InstanceCreator().create(this.config.attachmentUriProvider(), new DefaultAttachmentProvider())).getAttachments(context, this.config));
        if (!this.config.reportAsFile() || (report = createAttachmentFromString(context, "ACRA-report.stacktrace", errorContent.toJSON().toString())) == null) {
            return false;
        }
        attachments.add(report);
        return true;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Uri createAttachmentFromString(@NonNull Context context, @NonNull String name, @NonNull String content) {
        File cache = new File(context.getCacheDir(), name);
        try {
            IOUtils.writeStringToFile(cache, content);
            return AcraContentProvider.getUriForFile(context, cache);
        } catch (IOException e) {
            return null;
        }
    }
}
