package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.List;
import java.util.Calendar;

public class BalanceRotatableItem extends RotatableItem {
    public BalanceRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        long millis = System.currentTimeMillis() % 2000;
        float analogBalance;

        if (millis < 1000) {
            analogBalance = millis;
        } else {
            analogBalance = 2000 - millis;
        }

        return analogBalance / 1000.0f;
    }
}
