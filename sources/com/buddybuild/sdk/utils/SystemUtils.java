package com.buddybuild.sdk.utils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.DisplayMetrics;
import java.util.HashMap;
import java.util.Map;

public final class SystemUtils {
    public static Map<String, Object> getSystemInfo(Context context) {
        Map<String, Object> systemInfo = new HashMap<>();
        DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        systemInfo.put("hardware_screen_width", Integer.valueOf(metrics.widthPixels));
        systemInfo.put("hardware_screen_height", Integer.valueOf(metrics.heightPixels));
        Intent batteryIntent = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        systemInfo.put("battery_level", Integer.valueOf((int) ((((float) batteryIntent.getIntExtra("level", -1)) / ((float) batteryIntent.getIntExtra("scale", -1))) * 100.0f)));
        int status = batteryIntent.getIntExtra("status", -1);
        String statusMsg = "Unplugged";
        if (status == 2) {
            statusMsg = "Charging";
        } else if (status == 5) {
            statusMsg = "Full";
        }
        systemInfo.put("battery_state_friendly", statusMsg);
        ActivityManager.MemoryInfo ram = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(ram);
        systemInfo.put("memory_free", Long.valueOf(ram.availMem / 1048576));
        if (Build.VERSION.SDK_INT >= 16) {
            systemInfo.put("memory_total", Long.valueOf(ram.totalMem / 1048576));
        }
        systemInfo.put("hardware_device_type_friendly", Build.MODEL);
        systemInfo.put("hardware_system_version", Build.VERSION.RELEASE);
        return systemInfo;
    }
}
