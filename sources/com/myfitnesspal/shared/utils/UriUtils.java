package com.myfitnesspal.shared.utils;

import android.net.Uri;
import android.os.Bundle;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class UriUtils {
    public static final Bundle getQueryParams(Uri uri) {
        if (uri == null) {
            return new Bundle();
        }
        Bundle b = urlDecode(uri.getQuery());
        b.putAll(urlDecode(uri.getFragment()));
        return b;
    }

    public static final Bundle getQueryParams(String query) {
        return getQueryParams(Uri.parse(query.replaceFirst(".*:\\/\\/", "http://")));
    }

    public static String urlEncode(Bundle parameters) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            if (parameters.get(key) instanceof String) {
                if (first) {
                    first = false;
                } else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key) + SimpleComparison.EQUAL_TO_OPERATION + URLEncoder.encode(parameters.getString(key)));
            }
        }
        return sb.toString();
    }

    public static Bundle urlDecode(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            for (String parameter : s.split("&")) {
                String[] v = parameter.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (v.length == 2) {
                    params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
                }
            }
        }
        return params;
    }

    public static String createUrl(String host, String path, Object... params) {
        Uri.Builder builder = Uri.parse(host).buildUpon().path(path);
        if (params.length > 0) {
            for (int i = 0; i < params.length; i += 2) {
                builder.appendQueryParameter(params[i].toString(), params.length > i + 1 ? params[i + 1].toString() : "");
            }
        }
        return builder.build().toString();
    }
}
