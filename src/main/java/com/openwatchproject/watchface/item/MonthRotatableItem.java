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
        float monthAngle;
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH starts at 0

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            monthAngle = angle + ((((float) month) / 12.0f) * 360.0f * ((float) rotationFactor));
        } else {
            monthAngle = angle - ((((float) month) / 12.0f) * 360.0f * ((float) rotationFactor));
        }

        return monthAngle;
    }
}
