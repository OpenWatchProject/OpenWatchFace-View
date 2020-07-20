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
                + ((float) calendar.get(Calendar.MINUTE) / 60.0f)
                + ((float) calendar.get(Calendar.SECOND) / 60.0f / 60.0f);

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            hourAngle = this.angle + ((analogHour / 12.0f) * 360.0f * ((float) rotationFactor));
        } else {
            hourAngle = this.angle - ((analogHour / 12.0f) * 360.0f * ((float) rotationFactor));
        }

        return hourAngle;
    }
}
