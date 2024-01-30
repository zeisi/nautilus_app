package org.acra.attachment;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRA;
import org.acra.config.ACRAConfiguration;

public class DefaultAttachmentProvider implements AttachmentUriProvider {
    @NonNull
    public List<Uri> getAttachments(Context context, ACRAConfiguration configuration) {
        ArrayList<Uri> result = new ArrayList<>();
        Iterator<String> it = configuration.attachmentUris().iterator();
        while (it.hasNext()) {
            String s = it.next();
            try {
                result.add(Uri.parse(s));
            } catch (Exception e) {
                ACRA.log.e(ACRA.LOG_TAG, "Failed to parse Uri " + s, e);
            }
        }
        return result;
    }
}
