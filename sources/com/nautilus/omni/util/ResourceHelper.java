package com.nautilus.omni.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import com.nautilus.omni.R;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {
    public static final String STRING = "string";

    public static String getStringByKey(String key, Context context) {
        try {
            return context.getString(context.getResources().getIdentifier(key, STRING, context.getPackageName()));
        } catch (Exception e) {
            return "";
        }
    }

    public static List<TypedArray> getMultiTypedArray(Context context, String key) {
        Field field;
        List<TypedArray> array = new ArrayList<>();
        Class<R.array> res = R.array.class;
        int counter = 0;
        do {
            try {
                field = res.getField(key + "_" + counter);
                array.add(context.getResources().obtainTypedArray(field.getInt((Object) null)));
                counter++;
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
            }
        } while (field != null);
        return array;
    }

    public static Typeface getLatoLightTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), Constants.LATO_LIGHT);
    }

    public static Typeface getLatoRegularTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
    }

    public static Typeface getLatoBoldTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
    }

    public static Typeface getLatoBoldItalicTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), Constants.LATO_BOLD_ITALIC);
    }
}
