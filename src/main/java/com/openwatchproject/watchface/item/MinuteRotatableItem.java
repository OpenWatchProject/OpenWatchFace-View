package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.List;
import java.util.Calendar;

public class MinuteRotatableItem extends RotatableItem {
    public MinuteRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogMinute = (float) calendar.get(Calendar.MINUTE)
                + ((float) calendar.get(Calendar.SECOND) / 60.0f);

        return analogMinute / 60.0f;
    }
}
