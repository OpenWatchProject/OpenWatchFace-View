package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;
import com.openwatchproject.watchface.SystemUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class Battery180AngleRotatableItem extends RotatableItem {
    public Battery180AngleRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float batteryAngle;
        int analogBattery = SystemUtils.getBatteryPercentage();

        batteryAngle = (((float) analogBattery) / 100.0f) * 180.0f;
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            batteryAngle = -batteryAngle;
        }

        if (rotationFactor < 0) {
            batteryAngle = angle + (batteryAngle / ((float) -rotationFactor));
        } else {
            batteryAngle = angle + (batteryAngle * ((float) rotationFactor));
        }

        return batteryAngle;
    }
}
