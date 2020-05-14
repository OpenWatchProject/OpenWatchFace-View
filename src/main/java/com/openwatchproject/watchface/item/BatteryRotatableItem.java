package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;
import com.openwatchproject.watchface.SystemUtils;

import java.util.ArrayList;

public class BatteryRotatableItem extends RotatableItem {
    public BatteryRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

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
