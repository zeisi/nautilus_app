package com.myfitnesspal.android.sdk;

import android.os.Bundle;

public interface MfpAuthListener {
    void onCancel(Bundle bundle);

    void onComplete(Bundle bundle);

    void onError(MfpWebError mfpWebError);

    void onMfpError(MfpAuthError mfpAuthError);
}
