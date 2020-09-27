package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthRotatableItem extends RotatableItem {
    public MonthRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogMonth = (float) (calendar.get(Calendar.MONTH) + 1); // Calendar.MONTH starts at 0

        return analogMonth / 12.0f;
    }
}
