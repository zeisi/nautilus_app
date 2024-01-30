package com.ua.sdk.authentication;

import android.os.Parcelable;
import com.ua.sdk.Resource;

public interface OAuth2Credentials extends Resource, Parcelable {
    String getAccessToken();

    Long getExpiresAt();

    String getRefreshToken();

    void setAccessToken(String str);

    void setExpiresAt(Long l);

    void setRefreshToken(String str);
}
