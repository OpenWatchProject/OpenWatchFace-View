package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class DayRotatableItem extends RotatableItem {
    public DayRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            day = -day;
        }
        float dayAngle = angle + (rotationFactor * ((float) day / (float) 31 * 360));
        return dayAngle;
    }
}
