package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YearItem extends AbstractItem {
    public YearItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        List<Drawable> drawables = getFrames();
        int year = calendar.get(Calendar.YEAR);
        Drawable y1000 = drawables.get(year / 1000);
        Drawable y100 = drawables.get((year % 1000) / 100);
        Drawable y10 = drawables.get(((year % 1000) % 100) / 10);
        Drawable y1 = drawables.get(((year % 1000) % 100) % 10);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int width = y1000.getIntrinsicWidth();
        int halfHeight = y1000.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        y1000.setBounds(centerX - (width * 2), top, centerX - width, bottom);
        y1000.draw(canvas);
        y100.setBounds(centerX - width, top, centerX, bottom);
        y100.draw(canvas);
        y10.setBounds(centerX, top, centerX + width, bottom);
        y10.draw(canvas);
        y1.setBounds(centerX + width, top, centerX + (width * 2), bottom);
        y1.draw(canvas);
    }
}
