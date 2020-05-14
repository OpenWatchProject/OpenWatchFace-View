package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Calendar;

public class MinuteShadowRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public MinuteShadowRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float analogMinuteShadow = (float) calendar.get(Calendar.MINUTE) + ((float) calendar.get(Calendar.SECOND) / (float) 60);
        float minuteShadowAngle = angle + (rotationFactor * ((float) analogMinuteShadow / (float) 60 * 360));
        return minuteShadowAngle;
    }
}
