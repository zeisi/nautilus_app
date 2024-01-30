package org.acra.config;

import java.util.List;
import org.acra.config.RetryPolicy;
import org.acra.sender.ReportSender;

public class DefaultRetryPolicy implements RetryPolicy {
    public boolean shouldRetrySend(List<ReportSender> senders, List<RetryPolicy.FailedSender> failedSenders) {
        return senders.size() == failedSenders.size() && !senders.isEmpty();
    }
}
