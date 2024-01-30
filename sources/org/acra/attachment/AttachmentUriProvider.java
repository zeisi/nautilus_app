package org.acra.attachment;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.List;
import org.acra.config.ACRAConfiguration;

public interface AttachmentUriProvider {
    @NonNull
    List<Uri> getAttachments(Context context, ACRAConfiguration aCRAConfiguration);
}
