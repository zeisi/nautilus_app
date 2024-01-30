package com.nautilus.omni.util;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationSettingsUtil {
    public static final int REQUEST_CHECK_SETTINGS = 1001;
    /* access modifiers changed from: private */
    public static final String TAG = LocationSettingsUtil.class.getSimpleName();

    public interface LocationSettingsResponse {
        void OnLocationStatusSuccess();

        void OnLocationStatusUnavailable();
    }

    public void checkGpsStatus(final Activity activity, final LocationSettingsResponse locationSettingsResponse) {
        LocationServices.SettingsApi.checkLocationSettings(getGoogleApiClient(activity), getLocationSettingsRequest(getLocationRequest())).setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                LocationSettingsStates locationSettingsStates = locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case 0:
                        locationSettingsResponse.OnLocationStatusSuccess();
                        return;
                    case 6:
                        try {
                            status.startResolutionForResult(activity, 1001);
                            return;
                        } catch (IntentSender.SendIntentException e) {
                            return;
                        }
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        locationSettingsResponse.OnLocationStatusUnavailable();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(100);
        return locationRequest;
    }

    private LocationSettingsRequest getLocationSettingsRequest(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.setNeedBle(true);
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        return builder.build();
    }

    private GoogleApiClient getGoogleApiClient(Activity activity) {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(activity).addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            public void onConnected(Bundle bundle) {
                Log.d(LocationSettingsUtil.TAG, "DEBUG - GOOGLE LOCATION API CONNECTED");
            }

            public void onConnectionSuspended(int i) {
                Log.d(LocationSettingsUtil.TAG, "DEBUG - GOOGLE LOCATION API CONNECTION SUSPENDED");
            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d(LocationSettingsUtil.TAG, "DEBUG - GOOGLE LOCATION API CONNECTION FAILED");
            }
        }).build();
        mGoogleApiClient.connect();
        return mGoogleApiClient;
    }
}
