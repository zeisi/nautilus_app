package org.acra.config;

import java.util.List;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

public interface RetryPolicy {
    boolean shouldRetrySend(List<ReportSender> list, List<FailedSender> list2);

    public static class FailedSender {
        private final ReportSenderException exception;
        private final ReportSender sender;

        public FailedSender(ReportSender sender2, ReportSenderException exception2) {
            this.sender = sender2;
            this.exception = exception2;
        }

        public ReportSender getSender() {
            return this.sender;
        }

        public ReportSenderException getException() {
            return this.exception;
        }
    }
}
