package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class Hour24RotatableItem extends RotatableItem {
    public Hour24RotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogTwentyFourHours = calendar.get(Calendar.HOUR_OF_DAY)
                + ((float) calendar.get(Calendar.MINUTE) / 60.0f)
                + ((float) calendar.get(Calendar.SECOND) / 60.0f / 60.0f);

        return analogTwentyFourHours / 24.0f;
    }
}
