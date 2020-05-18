package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthRotatableItem extends RotatableItem {
    public MonthRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH starts at 0
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            month = -month;
        }
        float monthAngle = angle + (rotationFactor * ((float) month / (float) 12 * 360));
        return monthAngle;
    }
}
