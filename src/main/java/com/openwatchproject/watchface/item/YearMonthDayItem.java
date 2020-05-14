package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.List;

public class YearMonthDayItem extends AbstractItem {
    private final Calendar calendar;

    public YearMonthDayItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        List<Drawable> drawables = getFrames();

        int year = calendar.get(Calendar.YEAR);
        Drawable y1000 = drawables.get(year / 1000);
        Drawable y100 = drawables.get((year % 1000) / 1000);
        Drawable y10 = drawables.get(((year % 1000) % 100) / 10);
        Drawable y1 = drawables.get(((year % 1000) % 100) % 10);

        int month = calendar.get(Calendar.MONTH) + 1;
        Drawable m10 = drawables.get(month / 10);
        Drawable m1 = drawables.get(month % 10);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Drawable d10 = drawables.get(day / 10);
        Drawable d1 = drawables.get(day % 10);

        Drawable separator = drawables.get(10);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int width = y1000.getIntrinsicWidth();
        int halfHeight = y1000.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        y1000.setBounds(centerX - (width * 5), top, centerX - (width * 4), bottom);
        y1000.draw(canvas);
        y100.setBounds(centerX - (width * 4), top, centerX - (width * 3), bottom);
        y100.draw(canvas);
        y10.setBounds(centerX - (width * 3), top, centerX - (width * 2), bottom);
        y10.draw(canvas);
        y1.setBounds(centerX - (width * 2), top, centerX - width, bottom);
        y1.draw(canvas);
        separator.setBounds(centerX - width, top, centerX, bottom);
        separator.draw(canvas);
        m10.setBounds(centerX, top, centerX + width, bottom);
        m10.draw(canvas);
        m1.setBounds(centerX + width, top, centerX + (width * 2), bottom);
        m1.draw(canvas);
        separator.setBounds(centerX + (width * 2), top, centerX + (width * 3), bottom);
        separator.draw(canvas);
        d10.setBounds(centerX + (width * 3), top, centerX + (width * 4), bottom);
        d10.draw(canvas);
        d1.setBounds(centerX + (width * 4), top, centerX + (width * 5), bottom);
        d1.draw(canvas);
    }
}
