package org.acra.collector;

import android.support.annotation.NonNull;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONException;

final class ThreadCollector extends Collector {
    ThreadCollector() {
        super(ReportField.THREAD_DETAILS);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        Thread t = reportBuilder.getUncaughtExceptionThread();
        ComplexElement result = new ComplexElement();
        if (t == null) {
            return ACRAConstants.NOT_AVAILABLE;
        }
        try {
            result.put("id", t.getId());
            result.put("name", t.getName());
            result.put("priority", t.getPriority());
            if (t.getThreadGroup() == null) {
                return result;
            }
            result.put("groupName", t.getThreadGroup().getName());
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
    }
}
