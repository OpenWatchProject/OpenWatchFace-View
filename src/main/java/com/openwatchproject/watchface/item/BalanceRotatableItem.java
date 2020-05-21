package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class BalanceRotatableItem extends RotatableItem {
    public BalanceRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float balanceAngle;
        float analogBalance = System.currentTimeMillis() % 2000;

        if (analogBalance < 1000) {
            balanceAngle = ((analogBalance * 6.0f) * ((float) rotationFactor)) / 1000.0f;
        } else {
            balanceAngle = ((((float) rotationFactor) * 6000.0f) / 1000.0f) - (((((float) (analogBalance - 1000)) * 6.0f) * ((float) rotationFactor)) / 1000.0f);
        }

        return balanceAngle;
    }
}
