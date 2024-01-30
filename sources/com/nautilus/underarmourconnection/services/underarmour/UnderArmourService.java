package com.nautilus.underarmourconnection.services.underarmour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.ua.sdk.Ua;
import com.ua.sdk.internal.UaProviderImpl;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;

public class UnderArmourService {
    protected String mAuthenticationCode;
    /* access modifiers changed from: protected */
    public Context mContext;
    protected Ua mUnderArmourInterface;

    public UnderArmourService(Context context, String clientKey, String clientSecret) {
        this.mContext = context;
        this.mUnderArmourInterface = Ua.getBuilder().setClientId(clientKey).setClientSecret(clientSecret).setContext(context).setProvider(new UaProviderImpl(clientKey, clientSecret, context, false) {
            public UrlBuilderImpl getUrlBuilder() {
                return new UrlBuilderImpl();
            }
        }).setDebug(true).build();
    }

    public boolean isConnectedToUnderArmour() {
        return this.mUnderArmourInterface.isAuthenticated();
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
