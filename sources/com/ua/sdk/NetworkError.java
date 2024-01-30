package com.ua.sdk;

import com.nautilus.omni.util.Constants;
import com.ua.sdk.UaException;
import com.ua.sdk.util.Streams;
import java.net.HttpURLConnection;

public class NetworkError extends UaException {
    String message;
    final int responseCode;

    public NetworkError(int responseCode2, HttpURLConnection conn) {
        super(getErrorCode(responseCode2));
        this.message = "";
        this.responseCode = responseCode2;
        try {
            this.message = Streams.readFully(conn.getErrorStream());
        } catch (Throwable th) {
            this.message = "";
        }
    }

    public NetworkError(int responseCode2) {
        this(responseCode2, (Throwable) null);
    }

    public NetworkError(int responseCode2, Throwable cause) {
        super(getErrorCode(responseCode2), cause);
        this.message = "";
        this.responseCode = responseCode2;
    }

    public String toString() {
        return "Response Code " + this.responseCode + Constants.EMPTY_SPACE + this.message;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getHttpResponse() {
        return this.message;
    }

    public static final UaException.Code getErrorCode(int responseCode2) {
        return UaException.Code.NETWORK;
    }
}
