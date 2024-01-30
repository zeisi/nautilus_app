package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.internal.zzasm;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingEvent {
    private final int zzPY;
    private final int zzbjP;
    private final List<Geofence> zzbjQ;
    private final Location zzbjR;

    private GeofencingEvent(int i, int i2, List<Geofence> list, Location location) {
        this.zzPY = i;
        this.zzbjP = i2;
        this.zzbjQ = list;
        this.zzbjR = location;
    }

    public static GeofencingEvent fromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return new GeofencingEvent(intent.getIntExtra("gms_error_code", -1), zzA(intent), zzB(intent), (Location) intent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
    }

    private static int zzA(Intent intent) {
        int intExtra = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        if (intExtra == -1) {
            return -1;
        }
        if (intExtra == 1 || intExtra == 2 || intExtra == 4) {
            return intExtra;
        }
        return -1;
    }

    private static List<Geofence> zzB(Intent intent) {
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(zzasm.zzw((byte[]) it.next()));
        }
        return arrayList2;
    }

    public int getErrorCode() {
        return this.zzPY;
    }

    public int getGeofenceTransition() {
        return this.zzbjP;
    }

    public List<Geofence> getTriggeringGeofences() {
        return this.zzbjQ;
    }

    public Location getTriggeringLocation() {
        return this.zzbjR;
    }

    public boolean hasError() {
        return this.zzPY != -1;
    }
}
