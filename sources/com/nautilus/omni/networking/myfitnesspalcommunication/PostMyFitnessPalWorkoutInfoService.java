package com.nautilus.omni.networking.myfitnesspalcommunication;

import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nautilus.omni.networking.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;

public class PostMyFitnessPalWorkoutInfoService {
    public static final String TAG = PostMyFitnessPalWorkoutInfoService.class.getSimpleName();
    private final String endPoint = "https://api.myfitnesspal.com/client_api/json/1.0.0?client_id=@myFitnessPalClientId@";
    /* access modifiers changed from: private */
    public PostMyFitnessPalWorkoutListener mCallbackListener;
    private Context mContext;
    private String mServiceUrl;

    public PostMyFitnessPalWorkoutInfoService(Context context, PostMyFitnessPalWorkoutListener callbackListener) {
        this.mContext = context;
        this.mServiceUrl = "https://api.myfitnesspal.com/client_api/json/1.0.0?client_id=@myFitnessPalClientId@".replace("@myFitnessPalClientId@", MyFitnessPalConstants.MFP_CLIENT_ID);
        this.mCallbackListener = callbackListener;
    }

    public void postWorkoutInfoToMyFitnessPal(JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, this.mServiceUrl, jsonObject, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                PostMyFitnessPalWorkoutInfoService.this.mCallbackListener.onSuccess(PostMyFitnessPalWorkoutInfoService.this.getMyFitnessPalWorkoutId(response));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                PostMyFitnessPalWorkoutInfoService.this.mCallbackListener.onFailure(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(250000, 0, 0.0f));
        VolleySingleton.getInstance(this.mContext).getRequestQueue().add(jsonObjectRequest);
    }

    /* access modifiers changed from: private */
    public String getMyFitnessPalWorkoutId(JSONObject response) {
        try {
            return String.valueOf(response.getInt("exercise_entry_id"));
        } catch (JSONException e) {
            Log.d(TAG, "DEBUG - " + response);
            e.printStackTrace();
            return "";
        }
    }
}
