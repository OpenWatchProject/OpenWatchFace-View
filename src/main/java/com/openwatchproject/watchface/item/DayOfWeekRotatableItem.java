package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class DayOfWeekRotatableItem extends RotatableItem {
    public DayOfWeekRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2; // Sunday = -2, Monday = -1, Tuesday = 0, etc
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            dayOfWeek = -dayOfWeek;
        }
        float dayOfWeekAngle = angle + (rotationFactor * ((float) dayOfWeek / (float) 7 * 360));
        return dayOfWeekAngle;
    }
}
