package org.acra.collector;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;
import org.acra.model.StringElement;

final class StacktraceCollector extends Collector {
    StacktraceCollector() {
        super(ReportField.STACK_TRACE, ReportField.STACK_TRACE_HASH);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return collect == ReportField.STACK_TRACE || super.shouldCollect(crashReportFields, collect, reportBuilder);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case STACK_TRACE:
                return new StringElement(getStackTrace(reportBuilder.getMessage(), reportBuilder.getException()));
            case STACK_TRACE_HASH:
                return new StringElement(getStackTraceHash(reportBuilder.getException()));
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    private String getStackTrace(@Nullable String msg, @Nullable Throwable th) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        if (msg != null && !TextUtils.isEmpty(msg)) {
            printWriter.println(msg);
        }
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String stacktraceAsString = result.toString();
        printWriter.close();
        return stacktraceAsString;
    }

    @NonNull
    private String getStackTraceHash(@Nullable Throwable th) {
        StringBuilder res = new StringBuilder();
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            for (StackTraceElement e : cause.getStackTrace()) {
                res.append(e.getClassName());
                res.append(e.getMethodName());
            }
        }
        return Integer.toHexString(res.toString().hashCode());
    }
}
