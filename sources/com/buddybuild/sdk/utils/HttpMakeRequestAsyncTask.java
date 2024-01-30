package com.buddybuild.sdk.utils;

import android.os.AsyncTask;
import java.util.Map;

public class HttpMakeRequestAsyncTask extends AsyncTask<String, Integer, String> {
    private AsyncTaskResponse mResponder;

    public HttpMakeRequestAsyncTask(AsyncTaskResponse responder) {
        this.mResponder = responder;
    }

    public HttpMakeRequestAsyncTask() {
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... partialUrls) {
        StringBuilder responseBuilder = new StringBuilder();
        for (String partialUrl : partialUrls) {
            String response = HttpUtils.makeRequest(partialUrl, (Map) null);
            if (response != null) {
                responseBuilder.append(response);
            }
        }
        return responseBuilder.toString();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String result) {
        if (this.mResponder != null) {
            this.mResponder.onResponse(result);
        }
    }
}
