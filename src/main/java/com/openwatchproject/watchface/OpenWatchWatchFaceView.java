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

import java.util.Calendar;
import java.util.TimeZone;

public class OpenWatchWatchFaceView extends View {
    private static final String TAG = "OpenWatchWatchFaceView";

    private Calendar calendar;
    private DataRepository dataRepository = null;
    private OpenWatchWatchFace watchFace = null;

    private int viewWidth;
    private int viewHeight;
    private int viewCenterX;
    private int viewCenterY;
    private float scaleX;
    private float scaleY;
    private float lastTouchX;
    private float lastTouchY;

    private boolean receivedRegistered = false;
    private boolean forceStop = false;
    private boolean shouldRunTicker = false;
    private int framerate = 60;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction()))
                calendar.setTimeZone(TimeZone.getDefault());
        }
    };

    public OpenWatchWatchFaceView(Context context) {
        super(context);
        init();
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OpenWatchWatchFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.calendar = Calendar.getInstance();
        dataRepository = new DataRepository() {
            @Override
            public int getWeatherIcon() {
                return 0;
            }

            @Override
            public int getSteps() {
                return 0;
            }

            @Override
            public int getTargetSteps() {
                return 1;
            }

            @Override
            public int getDistance() {
                return 0;
            }

            @Override
            public int getTargetDistance() {
                return 1;
            }

            @Override
            public int getKCal() {
                return 0;
            }

            @Override
            public int getTargetKCal() {
                return 1;
            }

            @Override
            public int getWeatherTemp() {
                return 0;
            }

            @Override
            public int getHeartRate() {
                return 0;
            }

            @Override
            public boolean isBatteryCharging() {
                return false;
            }

            @Override
            public int getBatteryPercentage() {
                return 50;
            }
        };
    }

    public void setWatchFace(OpenWatchWatchFace watchFace) {
        this.watchFace = watchFace;
        calculateScale();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!receivedRegistered) {
            receivedRegistered = true;

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            getContext().registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (receivedRegistered) {
            receivedRegistered = false;

            getContext().unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.viewWidth = w;
        this.viewHeight = h;
        this.viewCenterX = w / 2;
        this.viewCenterY = h / 2;

        calculateScale();
    }

    private void calculateScale() {
        if (watchFace != null) {
            float watchFaceAspectRatio = ((float) watchFace.getWidth()) / ((float) watchFace.getHeight());
            float displayAspectRatio = ((float) viewWidth) / ((float) viewHeight);
            if (displayAspectRatio > watchFaceAspectRatio) {
                scaleX = ((float) viewHeight) * watchFaceAspectRatio / ((float) watchFace.getWidth());
                scaleY = ((float) viewHeight) / ((float) watchFace.getHeight());
            } else {
                scaleX = ((float) viewWidth) / ((float) watchFace.getWidth());
                scaleY = ((float) viewWidth) / watchFaceAspectRatio / ((float) watchFace.getHeight());
            }
        }
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
            if (watchFace != null)
                watchFace.onTapAction(viewCenterX, viewCenterY, lastTouchX, lastTouchY, getContext());
        }
    };

    public OpenWatchWatchFace getWatchFace() {
        return watchFace;
    }

    private final Runnable ticker = new Runnable() {
        public void run() {
            postInvalidateOnAnimation();

            long now = SystemClock.uptimeMillis();
            long next = now + ((1000 / framerate) - now % (1000 / framerate));

            if (shouldRunTicker)
                getHandler().postAtTime(ticker, next);
        }
    };

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);

        if (!forceStop) {
            if (!shouldRunTicker && isVisible) {
                shouldRunTicker = true;
                ticker.run();
            } else if (shouldRunTicker && !isVisible) {
                shouldRunTicker = false;
                getHandler().removeCallbacks(ticker);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(scaleX, scaleY, viewCenterX, viewCenterY);
        draw(viewCenterX, viewCenterY, canvas);
        canvas.restore();
    }

    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        if (watchFace != null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            watchFace.draw(viewCenterX, viewCenterY, canvas, calendar, dataRepository);
        }
    }

    public void setFramerate(int framerate) {
        if (framerate > 60) {
            framerate = 60;
        }

        this.framerate = framerate;
    }

    public void stopTicker() {
        if (!forceStop) {
            forceStop = true;
            if (shouldRunTicker) {
                shouldRunTicker = false;
                getHandler().removeCallbacks(ticker);
            }
        }
    }

    public void startTicker() {
        if (forceStop) {
            forceStop = false;
            if (!shouldRunTicker) {
                shouldRunTicker = true;
                ticker.run();
            }
        }
    }

    public void setDataRepository(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
}
