package com.nautilus.omni.networking;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.nautilus.omni.R;

public class VolleyErrorHelper {
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.time_out_error);
        }
        if (isServerProblem(error)) {
            return context.getResources().getString(R.string.server_error);
        }
        if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.network_error);
        }
        return context.getResources().getString(R.string.generic_error);
    }

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }
}
