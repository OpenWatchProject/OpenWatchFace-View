package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class TwentyFourHoursRotatableItem extends RotatableItem {
    public TwentyFourHoursRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float twentyFourHoursAngle;
        float analogTwentyFourHoursAngle = calendar.get(Calendar.HOUR_OF_DAY) + ((float) calendar.get(Calendar.MINUTE) / (float) 60) + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            twentyFourHoursAngle = angle + (((float) analogTwentyFourHoursAngle / 24.0f) * 360.0f);
        } else {
            twentyFourHoursAngle = angle - (((float) analogTwentyFourHoursAngle / 24.0f) * 360.0f);
        }

        return twentyFourHoursAngle;
    }
}
