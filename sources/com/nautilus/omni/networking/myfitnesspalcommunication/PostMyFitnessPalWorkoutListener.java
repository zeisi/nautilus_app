package com.nautilus.omni.networking.myfitnesspalcommunication;

import com.android.volley.VolleyError;

public interface PostMyFitnessPalWorkoutListener {
    void onFailure(VolleyError volleyError);

    void onParsingObjectFailure(String str);

    void onSuccess(String str);
}
