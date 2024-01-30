package com.myfitnesspal.shared.api;

import com.myfitnesspal.shared.constants.Constants;
import com.myfitnesspal.shared.utils.HttpUtils;
import com.myfitnesspal.shared.utils.Ln;
import com.myfitnesspal.shared.utils.UriUtils;

public class AppGalleryApiImpl implements AppGalleryApi {
    public void revokeAccess(String refreshToken) {
        String url = UriUtils.createUrl(Constants.Uris.MFP_APPGALLERY_API_BASE_URL, Constants.Uris.REVOKE, Constants.Params.REFRESH_TOKEN, refreshToken);
        Ln.d("revokeAccess: %s", url);
        HttpUtils.makeGetRequest(url);
    }
}
