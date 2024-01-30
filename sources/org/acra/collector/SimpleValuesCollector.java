package org.acra.collector;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.BooleanElement;
import org.acra.model.Element;
import org.acra.model.StringElement;
import org.acra.util.Installation;

final class SimpleValuesCollector extends Collector {
    private final Context context;

    SimpleValuesCollector(Context context2) {
        super(ReportField.IS_SILENT, ReportField.REPORT_ID, ReportField.INSTALLATION_ID, ReportField.PACKAGE_NAME, ReportField.PHONE_MODEL, ReportField.ANDROID_VERSION, ReportField.BRAND, ReportField.PRODUCT, ReportField.FILE_PATH, ReportField.USER_IP);
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return collect == ReportField.IS_SILENT || collect == ReportField.REPORT_ID || super.shouldCollect(crashReportFields, collect, reportBuilder);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case IS_SILENT:
                return new BooleanElement(reportBuilder.isSendSilently());
            case REPORT_ID:
                return new StringElement(UUID.randomUUID().toString());
            case INSTALLATION_ID:
                return new StringElement(Installation.id(this.context));
            case PACKAGE_NAME:
                return new StringElement(this.context.getPackageName());
            case PHONE_MODEL:
                return new StringElement(Build.MODEL);
            case ANDROID_VERSION:
                return new StringElement(Build.VERSION.RELEASE);
            case BRAND:
                return new StringElement(Build.BRAND);
            case PRODUCT:
                return new StringElement(Build.PRODUCT);
            case FILE_PATH:
                return new StringElement(getApplicationFilePath());
            case USER_IP:
                return new StringElement(getLocalIpAddress());
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    private String getApplicationFilePath() {
        File filesDir = this.context.getFilesDir();
        if (filesDir != null) {
            return filesDir.getAbsolutePath();
        }
        ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve ApplicationFilePath for : " + this.context.getPackageName());
        return "Couldn't retrieve ApplicationFilePath";
    }

    @NonNull
    private static String getLocalIpAddress() {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (!first) {
                            result.append(10);
                        }
                        result.append(inetAddress.getHostAddress());
                        first = false;
                    }
                }
            }
        } catch (SocketException ex) {
            ACRA.log.w(ACRA.LOG_TAG, ex.toString());
        }
        return result.toString();
    }
}
