package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.nautilus.omni.util.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private Cache.Entry mCacheEntry;
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private final Response.ErrorListener mErrorListener;
    /* access modifiers changed from: private */
    public final VolleyLog.MarkerLog mEventLog;
    private final int mMethod;
    private RequestQueue mRequestQueue;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private Object mTag;
    private final String mUrl;

    public interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 7;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int TRACE = 6;
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    /* access modifiers changed from: protected */
    public abstract void deliverResponse(T t);

    /* access modifiers changed from: protected */
    public abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    @Deprecated
    public Request(String url, Response.ErrorListener listener) {
        this(-1, url, listener);
    }

    public Request(int method, String url, Response.ErrorListener listener) {
        VolleyLog.MarkerLog markerLog;
        if (VolleyLog.MarkerLog.ENABLED) {
            markerLog = new VolleyLog.MarkerLog();
        } else {
            markerLog = null;
        }
        this.mEventLog = markerLog;
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mCacheEntry = null;
        this.mMethod = method;
        this.mUrl = url;
        this.mErrorListener = listener;
        setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
    }

    public int getMethod() {
        return this.mMethod;
    }

    public Request<?> setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public Object getTag() {
        return this.mTag;
    }

    public Response.ErrorListener getErrorListener() {
        return this.mErrorListener;
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    private static int findDefaultTrafficStatsTag(String url) {
        Uri uri;
        String host;
        if (TextUtils.isEmpty(url) || (uri = Uri.parse(url)) == null || (host = uri.getHost()) == null) {
            return 0;
        }
        return host.hashCode();
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public void addMarker(String tag) {
        if (VolleyLog.MarkerLog.ENABLED) {
            this.mEventLog.add(tag, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: package-private */
    public void finish(final String tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.finish(this);
        }
        if (VolleyLog.MarkerLog.ENABLED) {
            final long threadId = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        Request.this.mEventLog.add(tag, threadId);
                        Request.this.mEventLog.finish(toString());
                    }
                });
                return;
            }
            this.mEventLog.add(tag, threadId);
            this.mEventLog.finish(toString());
        }
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
        return this;
    }

    public final Request<?> setSequence(int sequence) {
        this.mSequence = Integer.valueOf(sequence);
        return this;
    }

    public final int getSequence() {
        if (this.mSequence != null) {
            return this.mSequence.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getCacheKey() {
        return getUrl();
    }

    public Request<?> setCacheEntry(Cache.Entry entry) {
        this.mCacheEntry = entry;
        return this;
    }

    public Cache.Entry getCacheEntry() {
        return this.mCacheEntry;
    }

    public void cancel() {
        this.mCanceled = true;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return Collections.emptyMap();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Map<String, String> getPostParams() throws AuthFailureError {
        return getParams();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public byte[] getPostBody() throws AuthFailureError {
        Map<String, String> postParams = getPostParams();
        if (postParams == null || postParams.size() <= 0) {
            return null;
        }
        return encodeParameters(postParams, getPostParamsEncoding());
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getParamsEncoding() {
        return "UTF-8";
    }

    public String getBodyContentType() {
        String valueOf = String.valueOf(getParamsEncoding());
        return valueOf.length() != 0 ? "application/x-www-form-urlencoded; charset=".concat(valueOf) : new String("application/x-www-form-urlencoded; charset=");
    }

    public byte[] getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params == null || params.size() <= 0) {
            return null;
        }
        return encodeParameters(params, getParamsEncoding());
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            String valueOf = String.valueOf(paramsEncoding);
            throw new RuntimeException(valueOf.length() != 0 ? "Encoding not supported: ".concat(valueOf) : new String("Encoding not supported: "), uee);
        }
    }

    public final Request<?> setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
        return this;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public final int getTimeoutMs() {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public void markDelivered() {
        this.mResponseDelivered = true;
    }

    public boolean hasHadResponseDelivered() {
        return this.mResponseDelivered;
    }

    /* access modifiers changed from: protected */
    public VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    public void deliverError(VolleyError error) {
        if (this.mErrorListener != null) {
            this.mErrorListener.onErrorResponse(error);
        }
    }

    public int compareTo(Request<T> other) {
        Priority left = getPriority();
        Priority right = other.getPriority();
        return left == right ? this.mSequence.intValue() - other.mSequence.intValue() : right.ordinal() - left.ordinal();
    }

    public String toString() {
        String valueOf = String.valueOf(Integer.toHexString(getTrafficStatsTag()));
        String trafficStatsTag = valueOf.length() != 0 ? "0x".concat(valueOf) : new String("0x");
        String valueOf2 = String.valueOf(String.valueOf(this.mCanceled ? "[X] " : "[ ] "));
        String valueOf3 = String.valueOf(String.valueOf(getUrl()));
        String valueOf4 = String.valueOf(String.valueOf(trafficStatsTag));
        String valueOf5 = String.valueOf(String.valueOf(getPriority()));
        String valueOf6 = String.valueOf(String.valueOf(this.mSequence));
        return new StringBuilder(valueOf2.length() + 3 + valueOf3.length() + valueOf4.length() + valueOf5.length() + valueOf6.length()).append(valueOf2).append(valueOf3).append(Constants.EMPTY_SPACE).append(valueOf4).append(Constants.EMPTY_SPACE).append(valueOf5).append(Constants.EMPTY_SPACE).append(valueOf6).toString();
    }
}
