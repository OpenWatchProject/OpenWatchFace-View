package com.openwatchproject.watchface;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OpenWatchWatchFaceView extends View {
    private static final String TAG = "ClockSkinView";

    private DataRepository dataRepository;

    private float lastTouchX;
    private float lastTouchY;

    private Context context;
    private Calendar calendar;
    private ClockSkin clockSkin;
    private List<ClockSkinItem> touchItems;

    private boolean registered;

    private boolean isBatteryCharging;
    private int batteryPercentage;
    private int viewCenterX;
    private int viewCenterY;

    private boolean shouldRunTicker;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_BATTERY_CHANGED:
                    updateBatteryStatus(intent);
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    updateTimeZone();
                    break;
            }
        }
    };

    public OpenWatchWatchFaceView(Context context) {
        super(context);
        constructView(context);
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        constructView(context);
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        constructView(context);
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        constructView(context);
    }

    private void constructView(Context context) {
        this.context = context;
        this.calendar = Calendar.getInstance();


    }

    public void setClockSkin(ClockSkin clockSkin) {
        this.touchItems = new ArrayList<>();
        //this.clockSkin = parseClockSkin(clockSkin);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!registered) {
            registered = true;

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            Intent batteryStatus = context.registerReceiver(receiver, intentFilter);
            updateBatteryStatus(batteryStatus);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (registered) {
            registered = false;

            context.unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.viewCenterX = w / 2;
        this.viewCenterY = h / 2;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            lastTouchX = event.getX();
            lastTouchY = event.getY();
        }

        return super.onTouchEvent(event);
    }

    public final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clockSkin != null) {
                List<ClockSkinItem> touchClockSkinItems = clockSkin.getTouchClockSkinItems();
                if (touchClockSkinItems != null) {
                    for (ClockSkinItem item : touchClockSkinItems) {
                        int centerX = viewCenterX + item.getCenterX();
                        int centerY = viewCenterY + item.getCenterY();
                        int range = item.getRange();

                        if (distance(lastTouchX, lastTouchY, centerX, centerY) <= range) {
                            String packageName = item.getPackageName();
                            String className = item.getClassName();

                            if (packageName != null && className != null) {
                                Intent i = new Intent();
                                i.setComponent(new ComponentName(packageName, className));
                                try {
                                    context.startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    Log.d(TAG, "onTouch: tried to open a non-existent activity: packageName = " + packageName + ", className = " + className);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    };

    public ClockSkin getClockSkin() {
        return clockSkin;
    }

    private int distance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;

        return (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }

    private void updateBatteryStatus(Intent batteryStatus) {
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        this.isBatteryCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        this.batteryPercentage =  Math.round((float) (level * 100) / (float) scale);
    }

    private void updateTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        calendar.setTimeZone(timeZone);
        for (ClockSkinItem clockSkinItem : clockSkin.getClockSkinItems()) {
            clockSkinItem.setTimeZone(timeZone);
        }
    }

    private final Runnable ticker = new Runnable() {
        public void run() {
            onTimeChanged();

            long now = SystemClock.uptimeMillis();
            long next = now + ((1000 / 60) - now % (1000 / 60));

            getHandler().postAtTime(ticker, next);
        }
    };

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);

        if (!shouldRunTicker && isVisible) {
            shouldRunTicker = true;
            ticker.run();
        } else if (shouldRunTicker && !isVisible) {
            shouldRunTicker = false;
            getHandler().removeCallbacks(ticker);
        }
    }

    private void onTimeChanged() {
        postInvalidateOnAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (clockSkin != null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            for (ClockSkinItem item : clockSkin.getClockSkinItems()) {
                switch (item.getArrayType()) {
                    case OpenWatchWatchFaceConstants.TYPE_NONE:
                        drawNoArrayType(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_YEAR_MONTH_DAY:
                        drawArrayYearMonthDay(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_MONTH_DAY:
                        drawArrayMonthDay(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_MONTH:
                        int month = calendar.get(Calendar.MONTH);
                        drawArraySingle(canvas, item, month);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_DAY:
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        drawArrayDouble(canvas, item, day / 10, day % 10);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_WEEKDAY:
                        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                        drawArraySingle(canvas, item, weekDay);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_HOUR_MINUTE:
                        drawArrayHourMinute(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_HOUR:
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        if (!DateFormat.is24HourFormat(context)) {
                            hour = hour % 12;
                            if (hour == 0) {
                                hour = 12;
                            }
                        }
                        drawArrayDouble(canvas, item, hour / 10, hour % 10);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_MINUTE: {
                        int minute = calendar.get(Calendar.MINUTE);
                        drawArrayDouble(canvas, item, minute / 10, minute % 10);
                        break;
                    }
                    case OpenWatchWatchFaceConstants.TYPE_SECOND: {
                        int second = calendar.get(Calendar.SECOND);
                        drawArrayDouble(canvas, item, second / 10, second % 10);
                        break;
                    }
                    case OpenWatchWatchFaceConstants.TYPE_WEATHER:
                        if (dataRepository == null) break;
                        int weatherIcon = dataRepository.getWeatherIcon();
                        int weatherIconValue = 0;
                        switch (weatherIcon) {
                            case 6:
                            case 7:
                            case 8:
                            case 38:
                                weatherIconValue = 1;
                                break;
                            case 11:
                                weatherIconValue = 3;
                                break;
                            case 18:
                                weatherIconValue = 4;
                                break;
                            case 12:
                            case 13:
                            case 14:
                            case 39:
                            case 40:
                                weatherIconValue = 5;
                                break;
                            case 26:
                            case 29:
                                weatherIconValue = 6;
                                break;
                            case 15:
                            case 16:
                            case 17:
                            case 41:
                            case 42:
                                weatherIconValue = 7;
                                break;
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 43:
                            case 44:
                                weatherIconValue = 8;
                                break;
                            case 30:
                                weatherIconValue = 10;
                                break;
                            case 31:
                                weatherIconValue = 11;
                                break;
                            case 32:
                                weatherIconValue = 12;
                                break;
                        }
                        drawArraySingle(canvas, item, weatherIconValue);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_TEMPERATURE:
                        drawArrayTemperature(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_STEPS:
                        drawArraySteps(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_HEART_RATE:
                        drawArrayHeartRate(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_BATTERY:
                        drawArrayBattery(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_SPECIAL_SECOND: {
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);
                        drawArraySpecialSecond(canvas, item, minute, second);
                        break;
                    }
                    case OpenWatchWatchFaceConstants.TYPE_YEAR:
                        drawArrayYear(canvas, item);
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_STEPS_CIRCLE:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_MOON_PHASE:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE_PIC:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_KCAL:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_DISTANCE:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_TEXT_PEDOMETER:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_TEXT_HEARTRATE:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_CHARGING:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_TAP_ACTION:
                        // Handled on parse
                        break;
                    case OpenWatchWatchFaceConstants.TYPE_DISTANCE_2:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_DISTANCE_UNIT:

                        break;
                    case OpenWatchWatchFaceConstants.TYPE_TEMP_UNIT:

                        break;
                }
            }
        }
    }

    private void drawNoArrayType(Canvas canvas, ClockSkinItem item) {

    }

    private void drawArrayYearMonthDay(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        if (drawables != null) {
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

            int centerX = viewCenterX + item.getCenterX();
            int centerY = viewCenterY + item.getCenterY();
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

    private void drawArrayMonthDay(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        int month = calendar.get(Calendar.MONTH) + 1;
        Drawable m10 = drawables.get(month / 10);
        Drawable m1 = drawables.get(month % 10);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Drawable d10 = drawables.get(day / 10);
        Drawable d1 = drawables.get(day % 10);
        Drawable separator = drawables.get(10);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int numberWidth = m10.getIntrinsicWidth();
        int numberHalfHeight = m10.getIntrinsicHeight() / 2;
        int top = centerY - numberHalfHeight;
        int bottom = centerY + numberHalfHeight;
        int separatorHalfWidth = separator.getIntrinsicWidth() / 2;
        m10.setBounds((centerX - separatorHalfWidth) - (numberWidth * 2), top, (centerX - separatorHalfWidth) - numberWidth, bottom);
        m10.draw(canvas);
        m1.setBounds((centerX - separatorHalfWidth) - numberWidth, top, (centerX - separatorHalfWidth), bottom);
        m1.draw(canvas);
        separator.setBounds(centerX - separatorHalfWidth, top, centerX + separatorHalfWidth, bottom);
        separator.draw(canvas);
        d10.setBounds((centerX + separatorHalfWidth), top, (centerX + separatorHalfWidth) + numberWidth, bottom);
        d10.draw(canvas);
        d1.setBounds((centerX + separatorHalfWidth) + numberWidth, top, (centerX + separatorHalfWidth) + (numberWidth * 2), bottom);
        d1.draw(canvas);
    }

    private void drawArraySingle(Canvas canvas, ClockSkinItem item, int value) {
        List<Drawable> drawables = item.getDrawables();
        Drawable drawable = drawables.get(value);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        drawable.setBounds(centerX - halfWidth, top, centerX + halfWidth, bottom);
        drawable.draw(canvas);
    }

    private void drawArrayDouble(Canvas canvas, ClockSkinItem item, int value1, int value2) {
        List<Drawable> drawables = item.getDrawables();
        Drawable drawable1 = drawables.get(value1);
        Drawable drawable2 = drawables.get(value2);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int width = drawable1.getIntrinsicWidth();
        int halfHeight = drawable1.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        drawable1.setBounds(centerX - width, top, centerX, bottom);
        drawable1.draw(canvas);
        drawable2.setBounds(centerX, top, centerX + width, bottom);
        drawable2.draw(canvas);
    }

    private void drawArrayHourMinute(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Drawable periodIndicator = null;
        if (!DateFormat.is24HourFormat(context)) {
            if (drawables.size() > 11) {
                if (hour < 12) {
                    periodIndicator = drawables.get(11);
                } else {
                    periodIndicator = drawables.get(12);
                }
            }

            hour = hour % 12;
            if (hour == 0) {
                hour = 12;
            }
        }
        Drawable h10 = drawables.get(hour / 10);
        Drawable h1 = drawables.get(hour % 10);
        Drawable separator = drawables.get(10);
        Drawable m10 = drawables.get(minute / 10);
        Drawable m1 = drawables.get(minute % 10);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int numberWidth = h10.getIntrinsicWidth();
        int numberHalfHeight = h10.getIntrinsicHeight() / 2;
        int separatorWidth = separator.getIntrinsicWidth();
        int periodWidth = 0;
        if (periodIndicator != null) {
            periodWidth = periodIndicator.getIntrinsicWidth();
        }
        int startX = centerX - ((((numberWidth * 4) + separatorWidth) + periodWidth) / 2);

        int top = centerY - numberHalfHeight;
        int bottom = centerY + numberHalfHeight;

        h10.setBounds(startX, top, startX + numberWidth, bottom);
        h10.draw(canvas);
        h1.setBounds(startX + numberWidth, top, (numberWidth * 2) + startX, bottom);
        h1.draw(canvas);
        if (calendar.get(Calendar.SECOND) % 2 == 0) {
            separator.setBounds((numberWidth * 2) + startX, top, (numberWidth * 2) + startX + separatorWidth, bottom);
            separator.draw(canvas);
        }
        m10.setBounds((numberWidth * 2) + startX + separatorWidth, top, (numberWidth * 3) + startX + separatorWidth, bottom);
        m10.draw(canvas);
        m1.setBounds((numberWidth * 3) + startX + separatorWidth, top, (numberWidth * 4) + startX + separatorWidth, bottom);
        m1.draw(canvas);
        if (periodIndicator != null) {
            periodIndicator.setBounds((numberWidth * 4) + startX + separatorWidth, top, (numberWidth * 4) + startX + separatorWidth + periodWidth, bottom);
            periodIndicator.draw(canvas);
        }
    }

    private void drawArrayBattery(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        int battery = batteryPercentage;
        Drawable b100;
        if ((battery / 100) == 0) {
            b100 = drawables.get(10);
        } else {
            b100 = drawables.get(battery / 100);
        }
        Drawable b10 = drawables.get((battery % 100) / 10);
        Drawable b1 = drawables.get((battery % 100) % 10);
        Drawable symbol = null;
        if (drawables.size() == 12) {
            symbol = drawables.get(11);
        }

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int width = b1.getIntrinsicWidth();
        int halfHeight = b1.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        b100.setBounds(centerX - (width * 2), top, centerX - width, bottom);
        b100.draw(canvas);
        b10.setBounds(centerX - width, top, centerX, bottom);
        b10.draw(canvas);
        b1.setBounds(centerX, top, centerX + width, bottom);
        b1.draw(canvas);
        if (symbol != null) {
            symbol.setBounds(centerX + width, top, centerX + (width * 2), bottom);
            symbol.draw(canvas);
        }
    }

    private void drawArrayYear(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        int year = calendar.get(Calendar.YEAR);
        Drawable y1000 = drawables.get(year / 1000);
        Drawable y100 = drawables.get((year % 1000) / 100);
        Drawable y10 = drawables.get(((year % 1000) % 100) / 10);
        Drawable y1 = drawables.get(((year % 1000) % 100) % 10);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
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

    private void drawArrayTemperature(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        if (dataRepository == null) return;
        int weatherTemp = dataRepository.getWeatherTemp();
        boolean negative = false;
        if (weatherTemp < 0) {
            weatherTemp = -weatherTemp;
            negative = true;
        }

        Drawable sign = drawables.get(10);
        Drawable t10 = drawables.get(weatherTemp / 10);
        Drawable t1 = drawables.get(weatherTemp % 10);
        Drawable unit = drawables.get(11);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int numberWidth = t10.getIntrinsicWidth();
        int halfHeight = t10.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        if (negative) {
            int signWidth = sign.getIntrinsicWidth();
            sign.setBounds(centerX - numberWidth - signWidth, top, centerX - numberWidth, bottom);
            sign.draw(canvas);
        }
        t10.setBounds(centerX - numberWidth, top, centerX, bottom);
        t10.draw(canvas);
        t1.setBounds(centerX, top, centerX + numberWidth, bottom);
        t1.draw(canvas);
        int unitWidth = unit.getIntrinsicWidth();
        int unitHeight = unit.getIntrinsicHeight();
        unit.setBounds(centerX + numberWidth, bottom - unitHeight, centerX + numberWidth + unitWidth, bottom);
        unit.draw(canvas);
    }

    private void drawArrayHeartRate(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        if (dataRepository == null) return;;
        int heartRate = dataRepository.getHeartRate();
        Drawable hr100 = drawables.get(heartRate/ 100);
        Drawable hr10 = drawables.get((heartRate % 100) / 10);
        Drawable hr1 = drawables.get((heartRate % 100) % 10);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int width = hr100.getIntrinsicWidth();
        int halfHeight = hr100.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        hr100.setBounds(centerX - ((width * 3) / 2), top, centerX - ((width * 3) / 2) + width, bottom);
        hr100.draw(canvas);
        hr10.setBounds(centerX - ((width * 3) / 2) + width, top, (width * 2) + centerX - ((width * 3) / 2), bottom);
        hr10.draw(canvas);
        hr1.setBounds((width * 2) + centerX - ((width * 3) / 2), top, (width * 3) + centerX - ((width * 3) / 2), bottom);
        hr1.draw(canvas);
    }

    private void drawArraySteps(Canvas canvas, ClockSkinItem item) {
        List<Drawable> drawables = item.getDrawables();
        if (dataRepository == null) return;;
        int steps = dataRepository.getSteps();
        Drawable s10000 = drawables.get(steps / 10000);
        Drawable s1000 = drawables.get((steps % 10000) / 1000);
        Drawable s100 = drawables.get(((steps % 10000) % 1000) / 100);
        Drawable s10 = drawables.get((((steps % 10000) % 1000) % 100) / 10);
        Drawable s1 = drawables.get(((((steps % 10000) % 1000) % 100) % 10) % 10);

        int centerX = viewCenterX + item.getCenterX();
        int centerY = viewCenterY + item.getCenterY();
        int numberWidth = s10.getIntrinsicWidth();
        int numberHalfWidth = s10.getIntrinsicWidth() / 2;
        int numberHalfHeight = s10.getIntrinsicHeight() / 2;
        int top = centerY - numberHalfHeight;
        int bottom = centerY + numberHalfHeight;
        s10000.setBounds((centerX - numberHalfWidth) - (numberWidth * 2), top, (centerX - numberHalfWidth) - numberWidth, bottom);
        s10000.draw(canvas);
        s1000.setBounds((centerX - numberHalfWidth) - numberWidth, top, (centerX - numberHalfWidth), bottom);
        s1000.draw(canvas);
        s100.setBounds(centerX - numberHalfWidth, top, centerX + numberHalfWidth, bottom);
        s100.draw(canvas);
        s10.setBounds((centerX + numberHalfWidth), top, (centerX + numberHalfWidth) + numberWidth, bottom);
        s10.draw(canvas);
        s1.setBounds((centerX + numberHalfWidth) + numberWidth, top, (centerX + numberHalfWidth) + (numberWidth * 2), bottom);
        s1.draw(canvas);
    }

    public void drawArraySpecialSecond(Canvas canvas, ClockSkinItem item, int minute, int second) {
        String colorArray = item.getColorArray();
        int RADIUS = 400 / 2;
        canvas.save();
        canvas.translate((float) viewCenterX, (float) viewCenterY);
        if (colorArray.contains(",")) {
            int bright_color = -16777216 | Integer.valueOf(colorArray.split(",")[0], 16);
            int dark_color = -16777216 | Integer.valueOf(colorArray.split(",")[1], 16);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(10.0f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAlpha(255);
            float y = (float) ((-RADIUS) + 5);
            for (int i = 0; i < 60; i++) {
                if (minute % 2 == 0) {
                    if (i < second) {
                        paint.setColor(bright_color);
                        canvas.drawLine(5.0f, y, 5.0f, y + 15.0f, paint);
                        canvas.rotate(6.0f, 0.0f, 0.0f);
                    } else {
                        paint.setColor(dark_color);
                        canvas.drawLine(5.0f, y, 5.0f, y + 15.0f, paint);
                        canvas.rotate(6.0f, 0.0f, 0.0f);
                    }
                } else if (i >= second) {
                    paint.setColor(bright_color);
                    canvas.drawLine(5.0f, y, 5.0f, y + 15.0f, paint);
                    canvas.rotate(6.0f, 0.0f, 0.0f);
                } else {
                    paint.setColor(dark_color);
                    canvas.drawLine(5.0f, y, 5.0f, y + 15.0f, paint);
                    canvas.rotate(6.0f, 0.0f, 0.0f);
                }
            }
        }
        canvas.restore();
    }

    public interface DataRepository {
        int getWeatherIcon();
        int getSteps();
        int getTargetSteps();
        int getWeatherTemp();
        int getHeartRate();
    }

    public void setDataRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
}
