package com.buddybuild.sdk.utils;

import android.util.Log;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.properties.BuddyBuildProperties;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.acra.ACRAConstants;
import org.json.JSONException;
import org.json.JSONObject;

public final class HttpUtils {
    public static void setupSSLTrust() throws Exception {
        TrustManager[] trustAllCerts = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init((KeyManager[]) null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public static String makeRequest(String partialUrl, Map params) {
        JSONObject requestJSON;
        HttpURLConnection urlConnection = null;
        if (params == null) {
            params = new HashMap();
        }
        try {
            setupSSLTrust();
            URL endpoint = new URL(new URL(BuddyBuildProperties.BUDDYBUILD_ENDPOINT), partialUrl);
            Log.d(Constants.BUDDYBUILD_TAG, "Make request endpoint is: " + endpoint.toString());
            if (params == null) {
                requestJSON = new JSONObject();
            } else {
                requestJSON = new JSONObject(params);
            }
            requestJSON.put("build_id", BuddyBuildProperties.BUDDYBUILD_BUILD_ID);
            requestJSON.put("app_id", BuddyBuildProperties.BUDDYBUILD_APP_ID);
            requestJSON.put("email", BuddyBuildProperties.BUDDYBUILD_EMAIL);
            requestJSON.put("android_application_variant_name", BuddyBuildProperties.BUDDYBUILD_APPLICATION_VARIANT_NAME);
            HttpURLConnection urlConnection2 = (HttpURLConnection) endpoint.openConnection();
            urlConnection2.setDoOutput(true);
            urlConnection2.setUseCaches(false);
            urlConnection2.setRequestProperty("User-Agent", Constants.BUDDYBUILD_USER_AGENT);
            urlConnection2.setRequestProperty(MIME.CONTENT_TYPE, "application/json");
            urlConnection2.setRequestProperty("Accept", "application/json");
            urlConnection2.setChunkedStreamingMode(1024);
            urlConnection2.setRequestMethod("POST");
            urlConnection2.connect();
            Log.d(Constants.BUDDYBUILD_TAG, "Sending json: " + requestJSON.toString());
            OutputStream outputStream = urlConnection2.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, ACRAConstants.UTF8));
            writer.write(requestJSON.toString());
            writer.close();
            outputStream.close();
            String response = getResponseFromUrlConnection(urlConnection2);
            if (urlConnection2.getResponseCode() != 200 && urlConnection2.getResponseCode() != 204) {
                throw new RuntimeException("Failed : HTTP error code : " + urlConnection2.getResponseCode() + ", " + response);
            } else if (urlConnection2 == null) {
                return response;
            } else {
                urlConnection2.disconnect();
                return response;
            }
        } catch (JSONException ex) {
            throw new RuntimeException("Failed to generate feedback JSON Object", ex);
        } catch (Exception e) {
            Log.d(Constants.BUDDYBUILD_TAG, "Failed to make request: ");
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return null;
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            throw th;
        }
    }

    public static String getResponseFromUrlConnection(HttpURLConnection urlConnection) {
        StringBuilder responseBuilder = new StringBuilder();
        try {
            if (urlConnection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), ACRAConstants.UTF8));
                while (true) {
                    String responseLine = bufferedReader.readLine();
                    if (responseLine == null) {
                        break;
                    }
                    responseBuilder.append(responseLine);
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!(urlConnection.getResponseCode() == 200 || urlConnection.getErrorStream() == null)) {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), ACRAConstants.UTF8));
                while (true) {
                    String responseLine2 = bufferedReader2.readLine();
                    if (responseLine2 == null) {
                        break;
                    }
                    responseBuilder.append(responseLine2);
                }
                bufferedReader2.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return responseBuilder.toString();
    }
}
