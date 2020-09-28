package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.List;
import java.util.Calendar;

public class SpecialSecondItem extends AbstractItem {
    public SpecialSecondItem(int centerX, int centerY, List<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {

    }
}
