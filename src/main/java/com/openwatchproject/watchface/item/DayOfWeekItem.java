package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayOfWeekItem extends AbstractItem {

    public DayOfWeekItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        List<Drawable> drawables = getFrames();
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Drawable drawable = drawables.get(weekDay);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        drawable.setBounds(centerX - halfWidth, top, centerX + halfWidth, bottom);
        drawable.draw(canvas);
    }
}
