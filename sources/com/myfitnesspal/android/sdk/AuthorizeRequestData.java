package com.myfitnesspal.android.sdk;

import android.os.Bundle;
import com.myfitnesspal.shared.constants.Constants;
import com.myfitnesspal.shared.utils.Strings;

class AuthorizeRequestData {
    private String clientId;
    private String redirectUri;
    private ResponseType responseType;
    private Scope scope;
    private String suffix;

    public AuthorizeRequestData(String clientId2, Scope scope2, ResponseType responseType2) {
        this(clientId2, (String) null, scope2, responseType2);
    }

    public AuthorizeRequestData(String clientId2, String suffix2, Scope scope2, ResponseType responseType2) {
        this.clientId = clientId2;
        this.suffix = suffix2;
        this.scope = scope2;
        this.responseType = responseType2;
        Object[] objArr = new Object[3];
        objArr[0] = this.clientId;
        objArr[1] = Strings.notEmpty(suffix2) ? "-" + suffix2 : "";
        objArr[2] = "://mfp/authorize/response";
        this.redirectUri = String.format("mfp-%s%s%s", objArr);
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public ResponseType getResponseType() {
        return this.responseType;
    }

    public Bundle asBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LaunchParams.CLIENT_ID, this.clientId);
        bundle.putString(Constants.LaunchParams.SUFFIX, this.suffix);
        bundle.putString(Constants.LaunchParams.REDIRECT_URI, this.redirectUri);
        bundle.putString(Constants.Params.DISPLAY, "mobile");
        bundle.putString(Constants.Params.ACCESS_TYPE, AccessType.Offline.toString());
        bundle.putString(Constants.Params.SCOPE, this.scope.toString());
        bundle.putString(Constants.Params.RESPONSE_TYPE, this.responseType.toString());
        return bundle;
    }

    public boolean isRequestForCode() {
        return this.responseType == ResponseType.Code || this.responseType == ResponseType.Both;
    }

    public boolean isRequestForTokens() {
        return this.responseType == ResponseType.Token || this.responseType == ResponseType.Both;
    }
}
