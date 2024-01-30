package org.acra.file;

import android.support.annotation.NonNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.acra.collector.CrashReportData;
import org.acra.util.IOUtils;
import org.acra.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class CrashReportPersister {
    @NonNull
    public CrashReportData load(@NonNull File file) throws IOException, JSONException {
        InputStream in = new BufferedInputStream(new FileInputStream(file), 8192);
        try {
            return JsonUtils.toCrashReportData(new JSONObject(IOUtils.streamToString(in)));
        } finally {
            IOUtils.safeClose(in);
        }
    }

    public void store(@NonNull CrashReportData crashData, @NonNull File file) throws IOException {
        IOUtils.writeStringToFile(file, crashData.toJSON().toString());
    }
}
