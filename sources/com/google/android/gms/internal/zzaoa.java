package com.google.android.gms.internal;

import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.ua.sdk.datapoint.BaseDataTypes;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class zzaoa {
    public static final Set<String> zzaUw = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"altitude", MyFitnessPalConstants.MFP_DURATION_KEY, "food_item", "meal_type", "repetitions", "resistance", "resistance_type", "debug_session", "google.android.fitness.SessionV2"})));
    private static final zzaoa zzaUz = new zzaoa();
    private final Map<String, Map<String, zza>> zzaUx;
    private final Map<String, zza> zzaUy;

    public static class zza {
        private final double zzaUA;
        private final double zzaUB;

        private zza(double d, double d2) {
            this.zzaUA = d;
            this.zzaUB = d2;
        }

        public boolean zzg(double d) {
            return d >= this.zzaUA && d <= this.zzaUB;
        }
    }

    private zzaoa() {
        HashMap hashMap = new HashMap();
        hashMap.put("latitude", new zza(-90.0d, 90.0d));
        hashMap.put("longitude", new zza(-180.0d, 180.0d));
        hashMap.put("accuracy", new zza(0.0d, 10000.0d));
        hashMap.put("bpm", new zza(0.0d, 1000.0d));
        hashMap.put("altitude", new zza(-100000.0d, 100000.0d));
        hashMap.put("percentage", new zza(0.0d, 100.0d));
        hashMap.put("confidence", new zza(0.0d, 100.0d));
        hashMap.put(MyFitnessPalConstants.MFP_DURATION_KEY, new zza(0.0d, 9.223372036854776E18d));
        hashMap.put(BaseDataTypes.ID_HEIGHT, new zza(0.0d, 3.0d));
        hashMap.put(User.WEIGHT, new zza(0.0d, 1000.0d));
        hashMap.put(BaseDataTypes.ID_SPEED, new zza(0.0d, 11000.0d));
        this.zzaUy = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("com.google.step_count.delta", zzf(BaseDataTypes.ID_STEPS, new zza(0.0d, 1.0E-8d)));
        hashMap2.put("com.google.calories.consumed", zzf("calories", new zza(0.0d, 1.0E-6d)));
        hashMap2.put("com.google.calories.expended", zzf("calories", new zza(0.0d, 5.555555555555555E-10d)));
        hashMap2.put("com.google.distance.delta", zzf("distance", new zza(0.0d, 1.0E-7d)));
        this.zzaUx = Collections.unmodifiableMap(hashMap2);
    }

    public static zzaoa zzCH() {
        return zzaUz;
    }

    private static <K, V> Map<K, V> zzf(K k, V v) {
        HashMap hashMap = new HashMap();
        hashMap.put(k, v);
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public zza zzK(String str, String str2) {
        Map map = this.zzaUx.get(str);
        if (map != null) {
            return (zza) map.get(str2);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public zza zzej(String str) {
        return this.zzaUy.get(str);
    }
}
