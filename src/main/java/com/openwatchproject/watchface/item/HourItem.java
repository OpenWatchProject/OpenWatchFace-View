package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HourItem extends AbstractItem {
    public HourItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        List<Drawable> drawables = getFrames();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        /*if (!DateFormat.is24HourFormat(context)) { // TODO: Fix this
            hour = hour % 12;
            if (hour == 0) {
                hour = 12;
            }
        }*/
        Drawable drawable1 = drawables.get(hour / 10);
        Drawable drawable2 = drawables.get(hour % 10);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int width = drawable1.getIntrinsicWidth();
        int halfHeight = drawable1.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        drawable1.setBounds(centerX - width, top, centerX, bottom);
        drawable1.draw(canvas);
        drawable2.setBounds(centerX, top, centerX + width, bottom);
        drawable2.draw(canvas);
    }
}
