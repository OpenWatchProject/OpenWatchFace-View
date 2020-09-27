package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class BatteryRotatableItem extends RotatableItem {
    public BatteryRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogBattery = (float) dataRepository.getBatteryPercentage();

        return analogBattery / 100.0f;
    }
}
