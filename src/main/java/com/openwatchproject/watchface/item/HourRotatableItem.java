package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class HourRotatableItem extends RotatableItem {
    public HourRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float hourAngle;

        float analogHour = (float) calendar.get(Calendar.HOUR)
                + ((float) calendar.get(Calendar.MINUTE) / (float) 60)
                + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            analogHour = -analogHour;
        }

        hourAngle = angle + (rotationFactor * ((float) analogHour * (float) 360 / (float) 12));
        return hourAngle;
    }
}
