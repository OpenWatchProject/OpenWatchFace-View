package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;
import com.openwatchproject.watchface.SystemUtils;

public class BatteryRotatableItem extends RotatableItem {
    @Override
    float getAngle() {
        int battery = SystemUtils.getBatteryPercentage();
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            battery = -battery;
        }
        float batteryAngle;
        if (rotationFactor < 0) {
            batteryAngle = ((((float) battery) / 100.0f) * 180.0f / ((float) - rotationFactor)) + offsetAngle;
        } else {
            batteryAngle = ((((float) battery) / 100.0f) * 180.0f * ((float) rotationFactor)) + offsetAngle;
        }
        return batteryAngle;
    }
}
