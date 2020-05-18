package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;
import com.openwatchproject.watchface.SystemUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class BatteryRotatableItem extends RotatableItem {
    private float offsetAngle;

    public BatteryRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    public void setOffsetAngle(float offsetAngle) {
        this.offsetAngle = offsetAngle;
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
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
