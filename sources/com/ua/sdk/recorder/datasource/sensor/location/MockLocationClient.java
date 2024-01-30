package com.ua.sdk.recorder.datasource.sensor.location;

import android.content.ContentResolver;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.ua.sdk.UaLog;
import com.ua.sdk.recorder.datasource.RollingAverage;
import com.ua.sdk.recorder.datasource.sensor.location.LocationClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MockLocationClient implements LocationClient {
    public static final int CONNECTION_TIMEOUT = 30000;
    public static final String MOCK_GPS_ENABLED_KEY = "mockGpsEnabled";
    public static final String MOCK_GPS_FAST_KEY = "mockGpsFast";
    public static final String MOCK_GPS_URL_KEY = "mockGpsUrl";
    private RollingAverage<Float> accuracyAccumulator = new RollingAverage<>(3);
    private Handler backgroundHandler;
    /* access modifiers changed from: private */
    public Context context;
    private Location currentLocation = null;
    private long currentOriginalTime = 0;
    /* access modifiers changed from: private */
    public List<Location> data;
    /* access modifiers changed from: private */
    public MockGpsLoader dataLoader = null;
    private boolean fastForward;
    private HandlerThread handlerThread;
    private boolean isRunning = false;
    private int lineIndex = 0;
    private LocationClient.LocationClientListener locationClientListener;
    private boolean mockLocationRunning = false;
    private boolean stopped = false;
    private String url;

    public MockLocationClient(Context context2, String url2, boolean fastForward2) {
        this.context = context2;
        this.url = url2;
        this.fastForward = fastForward2;
        this.handlerThread = new HandlerThread("MockLocationClientTimer");
    }

    public void connect(LocationClient.LocationClientListener locationClientListener2) {
        this.locationClientListener = locationClientListener2;
        if (!this.isRunning) {
            this.isRunning = true;
            this.dataLoader = new MockGpsLoader(this.context, this.url);
            this.dataLoader.execute(new Void[0]);
        }
        this.handlerThread.start();
        this.backgroundHandler = new MyHandler(this.handlerThread.getLooper());
    }

    public void disconnect() {
        if (this.dataLoader != null) {
            this.dataLoader.cancel(true);
            this.dataLoader = null;
        }
        stopLocationUpdates();
        this.handlerThread.quit();
        this.isRunning = false;
    }

    public void beginLocationUpdates() {
        if (this.data != null && this.data.size() > 0) {
            this.locationClientListener.onStatus(true, true, 100.0f);
            updateLocation();
            dispatchCurrentLocation();
        }
    }

    public void stopLocationUpdates() {
        if (this.backgroundHandler != null) {
            this.backgroundHandler.removeMessages(0);
        }
        this.stopped = true;
    }

    public void dispatchCurrentLocation() {
        if (!this.stopped) {
            UaLog.debug("MockLocationClient next location " + this.lineIndex + " of " + this.data.size());
            this.accuracyAccumulator.addValue(Float.valueOf(this.currentLocation.getAccuracy()));
            this.locationClientListener.onStatus(true, true, Double.valueOf(this.accuracyAccumulator.getAverage()).floatValue());
            this.locationClientListener.onLocation(this.currentLocation);
            if (this.lineIndex < this.data.size()) {
                updateLocation();
                long delay = this.currentLocation.getTime() - System.currentTimeMillis();
                if (delay < 0) {
                    delay = 0;
                }
                this.backgroundHandler.sendEmptyMessageDelayed(0, delay);
                return;
            }
            this.mockLocationRunning = false;
        }
    }

    private void updateLocation() {
        long now = System.currentTimeMillis();
        List<Location> list = this.data;
        int i = this.lineIndex;
        this.lineIndex = i + 1;
        Location location = list.get(i);
        long nextOriginalTime = location.getTime();
        if (this.currentOriginalTime != 0) {
            long adjustment = nextOriginalTime - this.currentOriginalTime;
            if (this.fastForward) {
                adjustment /= 2;
            }
            location.setTime(now + adjustment);
        } else {
            location.setTime(now);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Method locationJellyBeanFixMethod = Location.class.getMethod("makeComplete", new Class[0]);
                if (locationJellyBeanFixMethod != null) {
                    locationJellyBeanFixMethod.invoke(location, new Object[0]);
                }
            } catch (Exception e) {
                UaLog.warn("failed Location.makeComplete", (Throwable) e);
            }
        }
        this.currentLocation = location;
        this.currentOriginalTime = nextOriginalTime;
    }

    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            MockLocationClient.this.dispatchCurrentLocation();
        }
    }

    protected class MockGpsLoader extends AsyncTask<Void, Void, List<Location>> {
        private ContentResolver cr;
        private String dataUrl;

        public MockGpsLoader(Context c, String dataUrl2) {
            this.cr = c.getContentResolver();
            this.dataUrl = dataUrl2;
        }

        /* access modifiers changed from: protected */
        public List<Location> doInBackground(Void... params) {
            InputStream is;
            int timeColumn;
            int longColumn;
            int latColumn;
            int accuracyColumn;
            List<Location> result = new ArrayList<>();
            InputStream is2 = null;
            try {
                if (this.dataUrl == null) {
                    UaLog.error("Mock GPS URL is empty, aborting data mock data load.");
                    List<Location> access$000 = MockLocationClient.this.data;
                    if (is2 == null) {
                        return access$000;
                    }
                    try {
                        is2.close();
                        return access$000;
                    } catch (IOException e) {
                        return access$000;
                    }
                } else {
                    UaLog.info("Importing mock data from URI " + this.dataUrl);
                    if (this.dataUrl.startsWith("http")) {
                        is = new URL(this.dataUrl).openConnection().getInputStream();
                    } else {
                        is = this.cr.openInputStream(Uri.parse(this.dataUrl));
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = reader.readLine();
                    if (line.startsWith("#Android")) {
                        latColumn = 0;
                        longColumn = 1;
                        timeColumn = 2;
                        accuracyColumn = 3;
                        line = reader.readLine();
                    } else {
                        longColumn = 0;
                        latColumn = 1;
                        accuracyColumn = 2;
                        timeColumn = 3;
                    }
                    while (line != null) {
                        String[] parts = line.split(",");
                        Double longitude = Double.valueOf(parts[longColumn].trim());
                        Double latitude = Double.valueOf(parts[latColumn].trim());
                        float acc = Float.valueOf(parts[accuracyColumn].trim()).floatValue();
                        long time = Long.valueOf(parts[timeColumn].trim()).longValue();
                        Location location = new Location("mock");
                        location.setLatitude(latitude.doubleValue());
                        location.setLongitude(longitude.doubleValue());
                        location.setAccuracy(acc);
                        location.setTime(time);
                        result.add(location);
                        line = reader.readLine();
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e2) {
                        }
                    }
                    return result;
                }
            } catch (Exception e3) {
                UaLog.error("Exception during Mock GPS URL load.", (Throwable) e3);
                result = null;
                if (is2 != null) {
                    try {
                        is2.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (Throwable th) {
                if (is2 != null) {
                    try {
                        is2.close();
                    } catch (IOException e5) {
                    }
                }
                throw th;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<Location> result) {
            if (result != null) {
                List unused = MockLocationClient.this.data = result;
                MockLocationClient.this.beginLocationUpdates();
            } else {
                Toast.makeText(MockLocationClient.this.context, "Mock GPS load failed.", 1).show();
            }
            MockGpsLoader unused2 = MockLocationClient.this.dataLoader = null;
        }
    }
}
