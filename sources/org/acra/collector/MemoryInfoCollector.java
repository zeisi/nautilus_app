package org.acra.collector;

import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.Element;
import org.acra.model.NumberElement;
import org.acra.model.StringElement;
import org.acra.util.IOUtils;

final class MemoryInfoCollector extends Collector {
    MemoryInfoCollector() {
        super(ReportField.DUMPSYS_MEMINFO, ReportField.TOTAL_MEM_SIZE, ReportField.AVAILABLE_MEM_SIZE);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder) && !(reportBuilder.getException() instanceof OutOfMemoryError);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        switch (reportField) {
            case DUMPSYS_MEMINFO:
                return collectMemInfo();
            case TOTAL_MEM_SIZE:
                return new NumberElement(Long.valueOf(getTotalInternalMemorySize()));
            case AVAILABLE_MEM_SIZE:
                return new NumberElement(Long.valueOf(getAvailableInternalMemorySize()));
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    private static Element collectMemInfo() {
        try {
            List<String> commandLine = new ArrayList<>();
            commandLine.add("dumpsys");
            commandLine.add("meminfo");
            commandLine.add(Integer.toString(Process.myPid()));
            return new StringElement(IOUtils.streamToString(Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()])).getInputStream()));
        } catch (IOException e) {
            ACRA.log.e(ACRA.LOG_TAG, "MemoryInfoCollector.meminfo could not retrieve data", e);
            return ACRAConstants.NOT_AVAILABLE;
        }
    }

    private static long getAvailableInternalMemorySize() {
        long blockSize;
        long availableBlocks;
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = (long) stat.getBlockSize();
            availableBlocks = (long) stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    private static long getTotalInternalMemorySize() {
        long blockSize;
        long totalBlocks;
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = (long) stat.getBlockSize();
            totalBlocks = (long) stat.getBlockCount();
        }
        return totalBlocks * blockSize;
    }
}
