package com.openwatchproject.watchface;

public interface DataRepository {
    int getWeatherIcon();
    int getSteps();
    int getTargetSteps();
    int getWeatherTemp();
    int getHeartRate();
    boolean isBatteryCharging();
    int getBatteryPercentage();
}
