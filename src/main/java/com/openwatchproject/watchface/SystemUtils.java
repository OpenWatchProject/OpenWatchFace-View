package com.openwatchproject.watchface;

import android.content.Intent;
import android.os.BatteryManager;

public class SystemUtils {
    private static boolean batteryCharging;
    private static int batteryPercentage;

    private static void updateBatteryStatus(Intent batteryStatus) {
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        batteryCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        batteryPercentage =  Math.round((float) (level * 100) / (float) scale);
    }

    public static boolean isBatteryCharging() {
        return batteryCharging;
    }

    public static int getBatteryPercentage() {
        return batteryPercentage;
    }
}
