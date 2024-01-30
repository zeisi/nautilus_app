package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.zzasm;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private String zzOV = null;
        private int zzbjH = 0;
        private long zzbjI = Long.MIN_VALUE;
        private short zzbjJ = -1;
        private double zzbjK;
        private double zzbjL;
        private float zzbjM;
        private int zzbjN = 0;
        private int zzbjO = -1;

        public Geofence build() {
            if (this.zzOV == null) {
                throw new IllegalArgumentException("Request ID not set.");
            } else if (this.zzbjH == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            } else if ((this.zzbjH & 4) != 0 && this.zzbjO < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            } else if (this.zzbjI == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            } else if (this.zzbjJ == -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            } else if (this.zzbjN >= 0) {
                return new zzasm(this.zzOV, this.zzbjH, 1, this.zzbjK, this.zzbjL, this.zzbjM, this.zzbjI, this.zzbjN, this.zzbjO);
            } else {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
        }

        public Builder setCircularRegion(double d, double d2, float f) {
            this.zzbjJ = 1;
            this.zzbjK = d;
            this.zzbjL = d2;
            this.zzbjM = f;
            return this;
        }

        public Builder setExpirationDuration(long j) {
            if (j < 0) {
                this.zzbjI = -1;
            } else {
                this.zzbjI = SystemClock.elapsedRealtime() + j;
            }
            return this;
        }

        public Builder setLoiteringDelay(int i) {
            this.zzbjO = i;
            return this;
        }

        public Builder setNotificationResponsiveness(int i) {
            this.zzbjN = i;
            return this;
        }

        public Builder setRequestId(String str) {
            this.zzOV = str;
            return this;
        }

        public Builder setTransitionTypes(int i) {
            this.zzbjH = i;
            return this;
        }
    }

    String getRequestId();
}
