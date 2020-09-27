package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class DayRotatableItem extends RotatableItem {
    public DayRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogDay = (float) calendar.get(Calendar.DAY_OF_MONTH);

        return analogDay / 31.0f;
    }
}
