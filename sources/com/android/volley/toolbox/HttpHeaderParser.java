package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class HttpHeaderParser {
    public static Cache.Entry parseCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        long lastModified = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long finalExpire = 0;
        long maxAge = 0;
        long staleWhileRevalidate = 0;
        boolean hasCacheControl = false;
        boolean mustRevalidate = false;
        String headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = parseDateAsEpoch(headerValue);
        }
        String headerValue2 = headers.get("Cache-Control");
        if (headerValue2 != null) {
            hasCacheControl = true;
            String[] tokens = headerValue2.split(",");
            for (String trim : tokens) {
                String token = trim.trim();
                if (token.equals("no-cache") || token.equals("no-store")) {
                    return null;
                }
                if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception e) {
                    }
                } else if (token.startsWith("stale-while-revalidate=")) {
                    try {
                        staleWhileRevalidate = Long.parseLong(token.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    mustRevalidate = true;
                }
            }
        }
        String headerValue3 = headers.get("Expires");
        if (headerValue3 != null) {
            serverExpires = parseDateAsEpoch(headerValue3);
        }
        String headerValue4 = headers.get("Last-Modified");
        if (headerValue4 != null) {
            lastModified = parseDateAsEpoch(headerValue4);
        }
        String serverEtag = headers.get("ETag");
        if (hasCacheControl) {
            softExpire = now + (1000 * maxAge);
            finalExpire = mustRevalidate ? softExpire : softExpire + (1000 * staleWhileRevalidate);
        } else if (serverDate > 0 && serverExpires >= serverDate) {
            softExpire = now + (serverExpires - serverDate);
            finalExpire = softExpire;
        }
        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = finalExpire;
        entry.serverDate = serverDate;
        entry.lastModified = lastModified;
        entry.responseHeaders = headers;
        return entry;
    }

    public static long parseDateAsEpoch(String dateStr) {
        try {
            return DateUtils.parseDate(dateStr).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }

    public static String parseCharset(Map<String, String> headers, String defaultCharset) {
        String contentType = headers.get(MIME.CONTENT_TYPE);
        if (contentType == null) {
            return defaultCharset;
        }
        String[] params = contentType.split(";");
        for (int i = 1; i < params.length; i++) {
            String[] pair = params[i].trim().split(SimpleComparison.EQUAL_TO_OPERATION);
            if (pair.length == 2 && pair[0].equals("charset")) {
                return pair[1];
            }
        }
        return defaultCharset;
    }

    public static String parseCharset(Map<String, String> headers) {
        return parseCharset(headers, "ISO-8859-1");
    }
}
