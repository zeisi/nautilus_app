package com.myfitnesspal.android.sdk;

public final class MarketConstants {

    public static final class Campaigns {
        public static final String MFPCONNECT = "mfpconnect";
    }

    public static final class Params {
        public static final String UTM_CAMPAIGN = "utm_campaign";
        public static final String UTM_CONTENT = "utm_content";
        public static final String UTM_MEDIUM = "utm_medium";
        public static final String UTM_SOURCE = "utm_source";
        public static final String UTM_TERM = "utm_term";
    }

    public static final class Uris {
        public static final String AMAZON_MARKETPLACE = "http://www.amazon.com/Calorie-Counter-Diet-Tracker-MyFitnessPal/dp/B004H6WTJI";
        public static final String GOOGLE_PLAY_MARKETPLACE = "market://details?id=com.myfitnesspal.android&referrer=%s";
        public static final String GOOGLE_PLAY_WEB = "https://play.google.com/store/apps/details?id=com.myfitnesspal.android&referrer=%s";
        public static final String TEST_APP = "market://details?id=com.myfitnesspal.marketplacetest&referrer=%s";
        public static final String TEST_APP_WEB = "https://play.google.com/store/apps/details?id=com.myfitnesspal.marketplacetest&referrer=%s";
    }
}
