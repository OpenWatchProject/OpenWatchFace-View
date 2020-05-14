package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Calendar;

public class MinuteRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public MinuteRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float minuteAngle;
        float analogMinute = (float) calendar.get(Calendar.MINUTE) + ((float) calendar.get(Calendar.SECOND) / (float) 60);

        minuteAngle = angle + (rotationFactor * ((float) analogMinute / (float) 60 * 360));

        return minuteAngle;
    }
}
