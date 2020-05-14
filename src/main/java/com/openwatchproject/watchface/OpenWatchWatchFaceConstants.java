package com.openwatchproject.watchface;

public class OpenWatchWatchFaceConstants {
    public static final String CLOCK_SKIN_XML = "clock_skin.xml";
    public static final String CLOCK_SKIN_PREVIEW = "clock_skin_model.png";

    public static final int DIRECTION_NORMAL = 1;
    public static final int DIRECTION_REVERSE = 2;

    public static final int ROTATE_NONE = 0;
    public static final int ROTATE_HOUR = 1;
    public static final int ROTATE_MINUTE = 2;
    public static final int ROTATE_SECOND = 3;
    public static final int ROTATE_MONTH = 4;
    public static final int ROTATE_DAY_OF_WEEK = 5;
    public static final int ROTATE_BATTERY = 6;
    public static final int ROTATE_DAY_NIGHT = 7;
    public static final int ROTATE_HOUR_SHADOW = 8;
    public static final int ROTATE_MINUTE_SHADOW = 9;
    public static final int ROTATE_SECOND_SHADOW = 10;
    public static final int ROTATE_BATTERY_CIRCLE = 11; // Not available in WFD
    public static final int ROTATE_DAY = 20;
    public static final int ROTATE_STEPS_TARGET = 101; // Not available in WFD

    public static final int TYPE_ROTATABLE = 0;
    public static final int TYPE_YEAR_MONTH_DAY = 1;
    public static final int TYPE_MONTH_DAY = 2;
    public static final int TYPE_MONTH = 3;
    public static final int TYPE_DAY = 4;
    public static final int TYPE_WEEKDAY = 5;
    public static final int TYPE_HOUR_MINUTE = 6;
    public static final int TYPE_HOUR = 7;
    public static final int TYPE_MINUTE = 8;
    public static final int TYPE_SECOND = 9;
    public static final int TYPE_WEATHER = 10;
    public static final int TYPE_TEMPERATURE = 11;
    public static final int TYPE_STEPS = 12;
    public static final int TYPE_HEART_RATE = 13;
    public static final int TYPE_BATTERY = 14;
    public static final int TYPE_SPECIAL_SECOND = 15;
    public static final int TYPE_YEAR = 16;
    public static final int TYPE_BATTERY_CIRCLE = 17;
    public static final int TYPE_STEPS_CIRCLE = 18;
    public static final int TYPE_MOON_PHASE = 19;
    public static final int TYPE_AM_PM = 20;
    public static final int TYPE_FRAME_ANIMATION = 21;
    public static final int TYPE_ROTATE_ANIMATION = 22;
    public static final int TYPE_SNOW_ANIMATION = 23;
    public static final int TYPE_BATTERY_CIRCLE_PIC = 24;
    public static final int TYPE_PICTURE_HOUR = 30;
    public static final int TYPE_PICTURE_MINUTER = 31;
    public static final int TYPE_PICTURE_SECOND = 32;
    public static final int TYPE_PICTURE_HOUR_DIGITE = 33;
    public static final int TYPE_VALUE_WITH_PROGRESS = 34;
    public static final int TYPE_VALUE_STRING = 35;
    public static final int TYPE_VALUE_WITH_CLIP_PICTURE = 36;
    public static final int TYPE_MONTH_NEW = 37;
    public static final int TYPE_DAY_NEW = 38;
    public static final int TYPE_SECOND_NEW = 39;
    public static final int TYPE_STEPS_NEW = 40;
    public static final int TYPE_KCAL_NEW = 41;
    public static final int TYPE_STEPS_CIRCLE_NEW = 42;
    public static final int TYPE_BATTERY_CIRCLE_NEW = 43;
    public static final int TYPE_KCAL = 54;
    public static final int TYPE_DISTANCE = 60;
    public static final int TYPE_TEXT_PEDOMETER = 97;
    public static final int TYPE_TEXT_HEARTRATE = 98;
    public static final int TYPE_CHARGING = 99;
    public static final int TYPE_TAP_ACTION = 100;
    public static final int TYPE_YEAR_MONTH_DAY_2 = 101;
    public static final int TYPE_DISTANCE_2 = 1001;
    public static final int TYPE_DISTANCE_UNIT = 1002;
    public static final int TYPE_TEMP_UNIT = 1011;

    public static final int MOVEMENT_SMOOTH = 0;
    public static final int MOVEMENT_QUARTZ = 1;
    public static final int MOVEMENT_OVERSHOOTING_QUARTZ = 2;
    public static final int MOVEMENT_ELASTIC = 3;
    public static final int MOVEMENT_DIGITAL_QUARTZ = 4;

    public static final int VALUE_TYPE_DRAWABLE = 0;
    public static final int VALUE_TYPE_STEP = 1;
    public static final int VALUE_TYPE_KCAL = 2;
    public static final int VALUE_TYPE_BATTERY = 3;
    public static final int VALUE_TYPE_SECOND = 4;
    public static final int VALUE_TYPE_MONTH_AND_DAY = 5;
    public static final int VALUE_TYPE_WEEKDAY = 6;

    public static final String TAG_ARRAY_TYPE = "arraytype";
    public static final String TAG_CENTERX = "centerX";
    public static final String TAG_CENTERY = "centerY";
    public static final String TAG_COLOR = "color";
    public static final String TAG_COLOR_ARRAY = "colorarray";
    public static final String TAG_DIRECTION = "direction";
    public static final String TAG_DRAWABLE = "drawable";
    public static final String TAG_DRAWABLES = "drawables";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_MUL_ROTATE = "mulrotate";
    public static final String TAG_NAME = "name";
    public static final String TAG_OFFSET_ANGLE = "angle";
    public static final String TAG_ROTATE = "rotate";
    public static final String TAG_START_ANGLE = "startAngle";
    public static final String TAG_TEXT_SIZE = "textsize";
    public static final String TAG_DRAWABLE_FILE_TYPE = "xml";
    public static final String TAG_DRAWABLE_TYPE = "png";
    public static final String TAG_ANIMATION_ITEMS = "animationItems";
    public static final String TAG_ANIMATION_ITEM = "animationItem";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_COUNT = "count";
    public static final String TAG_CHILD_FOLDER = "child_folder";
    public static final String TAG_VALUE_TYPE = "value_type";
    public static final String TAG_PROGRESS_DILIVER_ARC = "diliver_arc";
    public static final String TAG_PROGRESS_DILIVER_COUNT = "diliver_count";
    public static final String TAG_PROGRESS_RADIUS = "circle_radius";
    public static final String TAG_PROGRESS_STROKEN = "circle_stroken";
    public static final String TAG_PICTURE_SHADOW = "shadow_picture";
    public static final String TAG_FRAMERATE = "framerate";
    public static final String TAG_RANGE = "range";
    public static final String CURRENT_CLOCK_SKIN_KEY = "current_clock";
    public static final String TAG_CLASS_NAME = "cls";
    public static final String TAG_PACKAGE_NAME = "pkg";
    public static final String TAG_TEXT_COLOR = "textcolor";
    public static final String TAG_WIDTH = "width";
    public static final String TAG_RADIUS = "radius";
    public static final String TAG_ROTATE_MODE = "rotatemode";

    public static final int ANTI_ROTATE_CLOCKWISE = 2;
    public static final int ARRAY_ANIMATED_ARRAY = 62;
    public static final int ARRAY_BATTERY_WITH_CIRCLE = 17;
    public static final int ARRAY_BATTERY_WITH_CIRCLE_PIC = 24;
    public static final int ARRAY_STEPS_WITH_CIRCLE = 18;
    public static final int ARRAY_TOUCH = 100;
    public static final int ARRAY_WATCHMAKER = 100;
    public static final int ARRAY_WATCHMAKER_HM = 210;
    public static final int ARRAY_WATCHMAKER_MARKERS = 220;
    public static final int ARRAY_WATCHMAKER_TEXTCURVED = 240;
    public static final int ARRAY_WATCHMAKER_TEXTRING = 230;
    public static final int PICTUTE_SHADOW_CENTERY = 2;
    public static final int ROTATE_CLOCKWISE = 1;
    public static final int ROTATE_HOUR_BG = 8;
    public static final int ROTATE_MINUTE_BG = 9;
    public static final int ROTATE_SECOND_BG = 10;
}
