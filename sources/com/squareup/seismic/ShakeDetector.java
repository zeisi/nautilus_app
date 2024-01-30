package com.squareup.seismic;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.ArrayList;
import java.util.List;

public class ShakeDetector implements SensorEventListener {
    private static final int DEFAULT_ACCELERATION_THRESHOLD = 13;
    public static final int SENSITIVITY_HARD = 15;
    public static final int SENSITIVITY_LIGHT = 11;
    public static final int SENSITIVITY_MEDIUM = 13;
    private int accelerationThreshold = 13;
    private Sensor accelerometer;
    private final Listener listener;
    private final SampleQueue queue = new SampleQueue();
    private SensorManager sensorManager;

    public interface Listener {
        void hearShake();
    }

    public ShakeDetector(Listener listener2) {
        this.listener = listener2;
    }

    public boolean start(SensorManager sensorManager2) {
        if (this.accelerometer != null) {
            return true;
        }
        this.accelerometer = sensorManager2.getDefaultSensor(1);
        if (this.accelerometer != null) {
            this.sensorManager = sensorManager2;
            sensorManager2.registerListener(this, this.accelerometer, 0);
        }
        if (this.accelerometer == null) {
            return false;
        }
        return true;
    }

    public void stop() {
        if (this.accelerometer != null) {
            this.sensorManager.unregisterListener(this, this.accelerometer);
            this.sensorManager = null;
            this.accelerometer = null;
        }
    }

    public void onSensorChanged(SensorEvent event) {
        boolean accelerating = isAccelerating(event);
        this.queue.add(event.timestamp, accelerating);
        if (this.queue.isShaking()) {
            this.queue.clear();
            this.listener.hearShake();
        }
    }

    private boolean isAccelerating(SensorEvent event) {
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        if (((double) ((ax * ax) + (ay * ay) + (az * az))) > ((double) (this.accelerationThreshold * this.accelerationThreshold))) {
            return true;
        }
        return false;
    }

    public void setSensitivity(int accelerationThreshold2) {
        this.accelerationThreshold = accelerationThreshold2;
    }

    static class SampleQueue {
        private static final long MAX_WINDOW_SIZE = 500000000;
        private static final int MIN_QUEUE_SIZE = 4;
        private static final long MIN_WINDOW_SIZE = 250000000;
        private int acceleratingCount;
        private Sample newest;
        private Sample oldest;
        private final SamplePool pool = new SamplePool();
        private int sampleCount;

        SampleQueue() {
        }

        /* access modifiers changed from: package-private */
        public void add(long timestamp, boolean accelerating) {
            purge(timestamp - MAX_WINDOW_SIZE);
            Sample added = this.pool.acquire();
            added.timestamp = timestamp;
            added.accelerating = accelerating;
            added.next = null;
            if (this.newest != null) {
                this.newest.next = added;
            }
            this.newest = added;
            if (this.oldest == null) {
                this.oldest = added;
            }
            this.sampleCount++;
            if (accelerating) {
                this.acceleratingCount++;
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            while (this.oldest != null) {
                Sample removed = this.oldest;
                this.oldest = removed.next;
                this.pool.release(removed);
            }
            this.newest = null;
            this.sampleCount = 0;
            this.acceleratingCount = 0;
        }

        /* access modifiers changed from: package-private */
        public void purge(long cutoff) {
            while (this.sampleCount >= 4 && this.oldest != null && cutoff - this.oldest.timestamp > 0) {
                Sample removed = this.oldest;
                if (removed.accelerating) {
                    this.acceleratingCount--;
                }
                this.sampleCount--;
                this.oldest = removed.next;
                if (this.oldest == null) {
                    this.newest = null;
                }
                this.pool.release(removed);
            }
        }

        /* access modifiers changed from: package-private */
        public List<Sample> asList() {
            List<Sample> list = new ArrayList<>();
            for (Sample s = this.oldest; s != null; s = s.next) {
                list.add(s);
            }
            return list;
        }

        /* access modifiers changed from: package-private */
        public boolean isShaking() {
            return this.newest != null && this.oldest != null && this.newest.timestamp - this.oldest.timestamp >= MIN_WINDOW_SIZE && this.acceleratingCount >= (this.sampleCount >> 1) + (this.sampleCount >> 2);
        }
    }

    static class Sample {
        boolean accelerating;
        Sample next;
        long timestamp;

        Sample() {
        }
    }

    static class SamplePool {
        private Sample head;

        SamplePool() {
        }

        /* access modifiers changed from: package-private */
        public Sample acquire() {
            Sample acquired = this.head;
            if (acquired == null) {
                return new Sample();
            }
            this.head = acquired.next;
            return acquired;
        }

        /* access modifiers changed from: package-private */
        public void release(Sample sample) {
            sample.next = this.head;
            this.head = sample;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
