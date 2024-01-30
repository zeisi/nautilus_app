package org.acra.collector;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import com.ua.sdk.datapoint.BaseDataTypes;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class DisplayManagerCollector extends Collector {
    private final Context context;
    private final SparseArray<String> flagNames = new SparseArray<>();

    DisplayManagerCollector(Context context2) {
        super(ReportField.DISPLAY);
        this.context = context2;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        ComplexElement result = new ComplexElement();
        for (Display display : DisplayManagerCompat.getInstance(this.context).getDisplays()) {
            try {
                result.put(String.valueOf(display.getDisplayId()), collectDisplayData(display));
            } catch (JSONException e) {
                ACRA.log.w(ACRA.LOG_TAG, "Failed to collect data for display " + display.getDisplayId(), e);
            }
        }
        return result;
    }

    @NonNull
    private JSONObject collectDisplayData(@NonNull Display display) throws JSONException {
        display.getMetrics(new DisplayMetrics());
        JSONObject result = new JSONObject();
        collectCurrentSizeRange(display, result);
        collectFlags(display, result);
        collectMetrics(display, result);
        collectRealMetrics(display, result);
        collectName(display, result);
        collectRealSize(display, result);
        collectRectSize(display, result);
        collectSize(display, result);
        collectRotation(display, result);
        collectIsValid(display, result);
        result.put("orientation", display.getRotation()).put("refreshRate", (double) display.getRefreshRate());
        result.put(BaseDataTypes.ID_HEIGHT, display.getHeight()).put("width", display.getWidth()).put("pixelFormat", display.getPixelFormat());
        return result;
    }

    private static void collectIsValid(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            container.put("isValid", display.isValid());
        }
    }

    private static void collectRotation(@NonNull Display display, JSONObject container) throws JSONException {
        container.put("rotation", rotationToString(display.getRotation()));
    }

    @NonNull
    private static String rotationToString(int rotation) {
        switch (rotation) {
            case 0:
                return "ROTATION_0";
            case 1:
                return "ROTATION_90";
            case 2:
                return "ROTATION_180";
            case 3:
                return "ROTATION_270";
            default:
                return String.valueOf(rotation);
        }
    }

    private static void collectRectSize(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 13) {
            Rect size = new Rect();
            display.getRectSize(size);
            container.put("rectSize", new JSONArray(Arrays.asList(new Integer[]{Integer.valueOf(size.top), Integer.valueOf(size.left), Integer.valueOf(size.width()), Integer.valueOf(size.height())})));
        }
    }

    private static void collectSize(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            container.put("size", new JSONArray(Arrays.asList(new Integer[]{Integer.valueOf(size.x), Integer.valueOf(size.y)})));
        }
    }

    private static void collectRealSize(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            Point size = new Point();
            display.getRealSize(size);
            container.put("realSize", new JSONArray(Arrays.asList(new Integer[]{Integer.valueOf(size.x), Integer.valueOf(size.y)})));
        }
    }

    private static void collectCurrentSizeRange(@NonNull Display display, @NonNull JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 16) {
            Point smallest = new Point();
            Point largest = new Point();
            display.getCurrentSizeRange(smallest, largest);
            JSONObject result = new JSONObject();
            result.put("smallest", new JSONArray(Arrays.asList(new Integer[]{Integer.valueOf(smallest.x), Integer.valueOf(smallest.y)})));
            result.put("largest", new JSONArray(Arrays.asList(new Integer[]{Integer.valueOf(largest.x), Integer.valueOf(largest.y)})));
            container.put("currentSizeRange", result);
        }
    }

    private void collectFlags(@NonNull Display display, @NonNull JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            int flags = display.getFlags();
            for (Field field : display.getClass().getFields()) {
                if (field.getName().startsWith("FLAG_")) {
                    try {
                        this.flagNames.put(field.getInt((Object) null), field.getName());
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            container.put("flags", activeFlags(flags));
        }
    }

    private static void collectName(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            container.put("name", display.getName());
        }
    }

    private static void collectMetrics(@NonNull Display display, JSONObject container) throws JSONException {
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        JSONObject result = new JSONObject();
        collectMetrics(metrics, result);
        container.put("metrics", result);
    }

    private static void collectRealMetrics(@NonNull Display display, JSONObject container) throws JSONException {
        if (Build.VERSION.SDK_INT >= 17) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getRealMetrics(metrics);
            JSONObject result = new JSONObject();
            collectMetrics(metrics, result);
            container.put("realMetrics", result);
        }
    }

    private static void collectMetrics(@NonNull DisplayMetrics metrics, JSONObject container) throws JSONException {
        container.put("density", (double) metrics.density).put("densityDpi", metrics.densityDpi).put("scaledDensity", "x" + metrics.scaledDensity).put("widthPixels", metrics.widthPixels).put("heightPixels", metrics.heightPixels).put("xdpi", (double) metrics.xdpi).put("ydpi", (double) metrics.ydpi);
    }

    @NonNull
    private String activeFlags(int bitfield) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.flagNames.size(); i++) {
            int value = bitfield & this.flagNames.keyAt(i);
            if (value > 0) {
                if (result.length() > 0) {
                    result.append('+');
                }
                result.append(this.flagNames.get(value));
            }
        }
        return result.toString();
    }
}
