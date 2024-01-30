package com.myfitnesspal.android.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.myfitnesspal.android.sdk.MarketConstants;
import com.myfitnesspal.shared.utils.Ln;
import com.myfitnesspal.shared.utils.UriUtils;
import java.net.URLEncoder;

public class DownloadManager {
    private static final String TAG = "MFP-download";

    public void redirectToDownloadPage(Context context, String clientId, String suffix, String redirectUrl) {
        String uri = getMarketplaceUri(context, clientId, suffix, redirectUrl);
        Ln.d("app not on device, using download URL %s", uri);
        context.startActivity(new Intent("android.intent.action.VIEW").addFlags(268435456).setData(Uri.parse(uri)));
    }

    private String getMarketplaceUri(Context context, String clientId, String suffix, String redirectUrl) {
        String baseUrlFormat = isAmazonDevice() ? MarketConstants.Uris.AMAZON_MARKETPLACE : Util.isUriAvailable(context, MarketConstants.Uris.GOOGLE_PLAY_MARKETPLACE) ? MarketConstants.Uris.GOOGLE_PLAY_MARKETPLACE : MarketConstants.Uris.GOOGLE_PLAY_WEB;
        Bundle utmParams = new Bundle();
        utmParams.putString(MarketConstants.Params.UTM_CAMPAIGN, "mfpconnect");
        utmParams.putString(MarketConstants.Params.UTM_SOURCE, clientId);
        if (suffix == null) {
            suffix = "";
        }
        utmParams.putString(MarketConstants.Params.UTM_MEDIUM, suffix);
        utmParams.putString(MarketConstants.Params.UTM_CONTENT, redirectUrl);
        return String.format(baseUrlFormat, new Object[]{URLEncoder.encode(UriUtils.urlEncode(utmParams))});
    }

    private boolean isAmazonDevice() {
        return Build.MANUFACTURER.contains("Amazon");
    }
}
