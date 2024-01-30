package com.buddybuild.sdk.feedback;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.buddybuild.sdk.Constants;
import com.buddybuild.sdk.utils.HttpUtils;
import com.buddybuild.sdk.utils.ImageUtils;
import com.nautilus.omni.model.dto.AwardType;
import java.util.HashMap;
import java.util.Map;

final class SendFeedbackAsyncTask extends AsyncTask<FeedbackInfo, Integer, Void> {
    SendFeedbackAsyncTask() {
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(FeedbackInfo... feedbackInfos) {
        for (FeedbackInfo feedbackInfo : feedbackInfos) {
            feedbackInfo.upload();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Integer... progress) {
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Void result) {
    }

    static final class FeedbackInfo {
        public final String description;
        public final Bitmap image;
        public final Map<String, Object> mSystemInfo;

        public FeedbackInfo(String description2, Bitmap image2, Map<String, Object> systemInfo) {
            this.description = description2;
            this.image = image2;
            this.mSystemInfo = systemInfo;
        }

        public void upload() {
            String imageData = ImageUtils.convertToBase64String(this.image);
            Map<String, Object> params = new HashMap<>();
            params.put(AwardType.DESCRIPTION, this.description);
            params.put("screenshot_data", imageData);
            params.put("system", this.mSystemInfo);
            HttpUtils.makeRequest(Constants.FEEDBACK_PARTIAL_URL, params);
        }
    }
}
