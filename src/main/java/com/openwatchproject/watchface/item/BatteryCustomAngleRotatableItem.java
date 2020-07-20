package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class BatteryCustomAngleRotatableItem extends RotatableItem {
    private int maxAngle;

    public BatteryCustomAngleRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        // TODO: Not implemented. Might be able to implement with custom angle in the normal battery item
        return 0.0f;
    }
}
