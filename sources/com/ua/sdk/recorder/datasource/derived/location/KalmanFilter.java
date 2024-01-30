package com.ua.sdk.recorder.datasource.derived.location;

import com.ua.sdk.UaLog;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPointImpl;

public class KalmanFilter {
    public static final double KALMAN_NOISE_VALUE = 500.0d;
    private static boolean jniLoaded;
    private long kalmanFilterPtr = 0;
    private double previousTime = 0.0d;

    /* access modifiers changed from: protected */
    public native long allocNative(double d);

    /* access modifiers changed from: protected */
    public native double getLatNative(long j);

    /* access modifiers changed from: protected */
    public native double getLngNative(long j);

    /* access modifiers changed from: protected */
    public native void releaseNative(long j);

    /* access modifiers changed from: protected */
    public native void updateNative(long j, double d, double d2, double d3);

    static {
        try {
            System.loadLibrary("KalmanFilterJni");
            jniLoaded = true;
        } catch (Throwable throwable) {
            jniLoaded = false;
            UaLog.error("KalmanFilterJni was NOT loaded. Entering graceful fail mode.", throwable);
        }
    }

    public KalmanFilter() {
        init(500.0d);
    }

    public DataPointImpl update(DataPointImpl dataPoint) {
        if (!jniLoaded) {
            return new DataPointImpl(dataPoint);
        }
        double durationSec = 0.0d;
        double currentTime = ((double) dataPoint.getDatetime().getTime()) / 1000.0d;
        if (this.previousTime != 0.0d) {
            durationSec = currentTime - this.previousTime;
        }
        this.previousTime = currentTime;
        updateNative(this.kalmanFilterPtr, dataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE).doubleValue(), dataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE).doubleValue(), durationSec);
        DataPointImpl answer = new DataPointImpl(dataPoint);
        answer.setValue(BaseDataTypes.FIELD_LATITUDE, Double.valueOf(getLatNative(this.kalmanFilterPtr)));
        answer.setValue(BaseDataTypes.FIELD_LONGITUDE, Double.valueOf(getLngNative(this.kalmanFilterPtr)));
        return answer;
    }

    private void init(double noiseValue) {
        this.kalmanFilterPtr = allocNative(noiseValue);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        releaseNative(this.kalmanFilterPtr);
        super.finalize();
    }
}
