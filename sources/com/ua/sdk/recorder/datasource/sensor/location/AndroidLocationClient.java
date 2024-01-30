package com.ua.sdk.recorder.datasource.sensor.location;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.nautilus.omni.util.Constants;
import com.ua.sdk.UaLog;
import com.ua.sdk.recorder.datasource.RollingAverage;
import com.ua.sdk.recorder.datasource.sensor.location.LocationClient;

public class AndroidLocationClient implements LocationClient {
    private static final int ACCURACY_MOVING_AVG_COUNT = 3;
    private static final long DURATION_TO_FIX_LOST_MS = 10000;
    public static final float MINIMUM_DISTANCECHANGE_FOR_UPDATE_GPS = 0.0f;
    public static final long MINIMUM_TIME_BETWEEN_UPDATE_GPS = 1000;
    /* access modifiers changed from: private */
    public RollingAverage<Float> accuracyAccumulator = new RollingAverage<>(3);
    private MyLocationListener androidLocationListener;
    private LocationManager androidLocationManager;
    private double curAccuracy = 0.0d;
    private boolean curGpsEnabled = false;
    private boolean curGpsFix = false;
    private HandlerThread handlerThread;
    private boolean hasDispatched = false;
    private boolean isRunning = false;
    /* access modifiers changed from: private */
    public LocationClient.LocationClientListener locationClientListener;
    /* access modifiers changed from: private */
    public double nextAccuracy = 0.0d;
    /* access modifiers changed from: private */
    public boolean nextGpsEnabled = false;
    /* access modifiers changed from: private */
    public boolean nextGpsFix = false;
    /* access modifiers changed from: private */
    public long previousLocationRealtime = 0;
    private boolean serviceSeenFirstFix = false;
    private long serviceStartRealtime = 0;

    public AndroidLocationClient(LocationManager androidLocationManager2) {
        this.androidLocationManager = androidLocationManager2;
    }

    public void connect(LocationClient.LocationClientListener listener) {
        this.locationClientListener = listener;
        this.serviceStartRealtime = SystemClock.elapsedRealtime();
        this.serviceSeenFirstFix = false;
        this.androidLocationListener = new MyLocationListener();
        this.androidLocationManager.requestLocationUpdates("gps", 1000, 0.0f, this.androidLocationListener);
        this.androidLocationManager.addGpsStatusListener(this.androidLocationListener);
        this.isRunning = true;
    }

    public void disconnect() {
        if (this.androidLocationListener != null) {
            this.androidLocationManager.removeUpdates(this.androidLocationListener);
        }
        this.isRunning = false;
    }

    /* access modifiers changed from: private */
    public void dispatchOnStatus() {
        if (!this.hasDispatched || this.curGpsEnabled != this.nextGpsEnabled || this.curGpsFix != this.nextGpsFix || this.curAccuracy != this.nextAccuracy) {
            this.hasDispatched = true;
            this.curGpsEnabled = this.nextGpsEnabled;
            this.curGpsFix = this.nextGpsFix;
            this.curAccuracy = this.nextAccuracy;
            this.locationClientListener.onStatus(this.curGpsEnabled, this.curGpsFix, Double.valueOf(this.curAccuracy).floatValue());
        }
    }

    protected class MyLocationListener implements LocationListener, GpsStatus.Listener {
        protected MyLocationListener() {
        }

        public void onLocationChanged(Location location) {
            if (location == null) {
                UaLog.warn("AndroidLocationClient bad location. location==null");
            } else if (location.hasAccuracy() && ((double) location.getAccuracy()) <= 0.0d) {
                UaLog.warn("AndroidLocationClient bad location. accuracy zero. " + location.toString());
            } else if (location.getLatitude() == 0.0d && location.getLongitude() == 0.0d) {
                UaLog.warn("AndroidLocationClient bad location. lat/lng zero. " + location.toString());
            } else if (location.getTime() == 0) {
                UaLog.warn("AndroidLocationClient bad location. time zero. " + location.toString());
            } else {
                AndroidLocationClient.this.locationClientListener.onLocation(location);
                long unused = AndroidLocationClient.this.previousLocationRealtime = SystemClock.elapsedRealtime();
                if (location.hasAccuracy()) {
                    AndroidLocationClient.this.accuracyAccumulator.addValue(Float.valueOf(location.getAccuracy()));
                    double unused2 = AndroidLocationClient.this.nextAccuracy = AndroidLocationClient.this.accuracyAccumulator.getAverage();
                }
                AndroidLocationClient.this.dispatchOnStatus();
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            UaLog.info("AndroidLocationClient onStatusChanged " + provider + Constants.EMPTY_SPACE + status);
        }

        public void onProviderEnabled(String provider) {
            UaLog.warn("AndroidLocationClient onProviderEnabled");
        }

        public void onProviderDisabled(String provider) {
            UaLog.warn("AndroidLocationClient onProviderDisabled");
        }

        public void onGpsStatusChanged(int changeType) {
            boolean z = true;
            switch (changeType) {
                case 1:
                    boolean unused = AndroidLocationClient.this.nextGpsEnabled = true;
                    boolean unused2 = AndroidLocationClient.this.nextGpsFix = false;
                    break;
                case 2:
                    boolean unused3 = AndroidLocationClient.this.nextGpsEnabled = false;
                    boolean unused4 = AndroidLocationClient.this.nextGpsFix = false;
                    break;
                case 3:
                    boolean unused5 = AndroidLocationClient.this.nextGpsEnabled = true;
                    boolean unused6 = AndroidLocationClient.this.nextGpsFix = true;
                    break;
                case 4:
                    boolean unused7 = AndroidLocationClient.this.nextGpsEnabled = true;
                    AndroidLocationClient androidLocationClient = AndroidLocationClient.this;
                    if (SystemClock.elapsedRealtime() - AndroidLocationClient.this.previousLocationRealtime >= AndroidLocationClient.DURATION_TO_FIX_LOST_MS) {
                        z = false;
                    }
                    boolean unused8 = androidLocationClient.nextGpsFix = z;
                    break;
                default:
                    UaLog.warn("unknown GpsStatus event type. " + changeType);
                    return;
            }
            double unused9 = AndroidLocationClient.this.nextAccuracy = AndroidLocationClient.this.accuracyAccumulator.getAverage();
            AndroidLocationClient.this.dispatchOnStatus();
        }
    }
}
