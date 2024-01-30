package com.myfitnesspal.shared.constants;

public final class Constants {

    public static final class Errors {
        public static final String ACCESS_DENIED = "access_denied";
        public static final String SINGLE_SIGN_ON_DISABLED = "service_disabled";
    }

    public static final class LaunchActions {
        public static final String AUTHORIZE = "authorize";
        public static final String MFP_CONNECT = "mfpconnect";
        public static final String REVOKE = "revoke";
    }

    public static final class LaunchParams {
        public static final String ACTION = "action";
        public static final String CLIENT_ID = "client_id";
        public static final String OPERATION = "operation";
        public static final String REDIRECT_URI = "redirect_uri";
        public static final String SUFFIX = "suffix";
        public static final String USE_REDIRECT_URI_INSTEAD_OF_ACTIVITY_RESULT = "useUriInsteadOfActivityResult";
    }

    public static final class Params {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ACCESS_TYPE = "access_type";
        public static final String CODE = "code";
        public static final String DISPLAY = "display";
        public static final String ERROR = "error";
        public static final String ERROR_DESCRIPTION = "error_description";
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String RESPONSE_TYPE = "response_type";
        public static final String SCOPE = "scope";
        public static final String USER_CANCELED = "userCanceled";
    }

    public static final class Uris {
        public static final String MFP_APPGALLERY_API_BASE_URL = "http://www.myfitnesspal.com";
        public static final String OAUTH2 = "/oauth2";
        public static final String REVOKE = "/oauth2/revoke";
    }

    public static final class Values {
        public static final int FORCE_DIALOG_AUTH = -1;
    }
}
