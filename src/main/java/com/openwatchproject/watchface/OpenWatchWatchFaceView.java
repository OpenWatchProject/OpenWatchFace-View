package com.openwatchproject.watchface;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.openwatchproject.watchface.item.TapActionItem;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class OpenWatchWatchFaceView extends View {
    private static final String TAG = "OpenWatchWatchFaceView";

    private Calendar calendar;
    private DataRepository dataRepository;
    private OpenWatchWatchFace watchFace;

    private int viewWidth;
    private int viewHeight;
    private int viewCenterX;
    private int viewCenterY;
    private float scaleX;
    private float scaleY;
    private float lastTouchX;
    private float lastTouchY;

    private boolean receivedRegistered;
    private boolean forceStop = false;
    private boolean shouldRunTicker = false;
    private int framerate = 60;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_TIMEZONE_CHANGED:
                    updateTimeZone();
                    break;
            }
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
        this.dataRepository = null;
        this.watchFace = null;
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
            getContext().unregisterReceiver(receiver);

            receivedRegistered = false;
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
            if (watchFace != null) {
                List<TapActionItem> touchClockSkinItems = watchFace.getTapActionItems();
                for (TapActionItem item : touchClockSkinItems) {
                    int centerX = viewCenterX + item.getCenterX();
                    int centerY = viewCenterY + item.getCenterY();
                    int range = item.getRange();

                    if (distance(lastTouchX, lastTouchY, centerX, centerY) <= range) {
                        String packageName = item.getPackageName();
                        String className = item.getClassName();

                        Intent i = new Intent();
                        i.setComponent(new ComponentName(packageName, className));
                        try {
                            getContext().startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            Log.d(TAG, "onTouch: tried to open a non-existent activity: packageName = " + packageName + ", className = " + className);
                        }
                        break;
                    }
                }
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

    private void updateTimeZone() {
        calendar.setTimeZone(TimeZone.getDefault());
    }

    private final Runnable ticker = new Runnable() {
        public void run() {
            postInvalidateOnAnimation();

            long now = SystemClock.uptimeMillis();
            long next = now + ((1000 / framerate) - now % (1000 / framerate));

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
