package org.acra.collector;

import android.support.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;
import org.acra.model.StringElement;

final class TimeCollector extends Collector {
    private final Calendar appStartDate;

    TimeCollector(Calendar appStartDate2) {
        super(ReportField.USER_APP_START_DATE, ReportField.USER_CRASH_DATE);
        this.appStartDate = appStartDate2;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> set, ReportField collect, ReportBuilder reportBuilder) {
        return true;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        Calendar time;
        switch (reportField) {
            case USER_APP_START_DATE:
                time = this.appStartDate;
                break;
            case USER_CRASH_DATE:
                time = new GregorianCalendar();
                break;
            default:
                throw new IllegalArgumentException();
        }
        return new StringElement(getTimeString(time));
    }

    @NonNull
    private static String getTimeString(@NonNull Calendar time) {
        return new SimpleDateFormat(ACRAConstants.DATE_TIME_FORMAT_STRING, Locale.ENGLISH).format(Long.valueOf(time.getTimeInMillis()));
    }
}
