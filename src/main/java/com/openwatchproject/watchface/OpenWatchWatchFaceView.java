package com.openwatchproject.watchface;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OpenWatchWatchFaceView extends View {
    private static final String TAG = "OpenWatchWatchFaceView";

    private DataRepository dataRepository;

    private float lastTouchX;
    private float lastTouchY;

    private Context context;
    private Calendar calendar;
    private OpenWatchWatchFace watchFace;
    //private List<ClockSkinItem> touchItems;

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

    public void setWatchFace(OpenWatchWatchFace watchFace) {
        //this.touchItems = new ArrayList<>();
        this.watchFace = watchFace;
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
            if (watchFace != null) {
                /*List<ClockSkinItem> touchClockSkinItems = clockSkin.getTouchClockSkinItems();
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
                }*/
            }
        }
    };

    public OpenWatchWatchFace getWatchFace() {
        return watchFace;
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
        /*for (ClockSkinItem clockSkinItem : watchFace.getClockSkinItems()) {
            clockSkinItem.setTimeZone(timeZone);
        }*/
    }

    private final Runnable ticker = new Runnable() {
        public void run() {
            postInvalidateOnAnimation();

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

    @Override
    protected void onDraw(Canvas canvas) {
        if (watchFace != null) {
            watchFace.setCalendarTime(System.currentTimeMillis());
            watchFace.draw(viewCenterX, viewCenterY, canvas);
        }
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
