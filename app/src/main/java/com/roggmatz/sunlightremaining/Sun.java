package com.roggmatz.sunlightremaining;

import android.util.Log;
/**
 * Created by Rogger on 11/16/2015.
 */
public class Sun {
    private int sunriseHour;
    private int sunriseMinute;
    private int sunsetHour;
    private int sunsetMinute;

    public Sun() {

    }

    public void setSunrise(int hour, int minute) {
        sunriseHour = hour;
        sunriseMinute = minute;
    }
    public void setSunset(int hour, int minute) {
        sunsetHour = hour;
        sunsetMinute = minute;
    }
    public int getSunrise(char value) {
        switch (value) {
            case 'h':
                return sunriseHour;
            case 'm':
                return sunriseMinute;
            default:
              Log.i("Sun.class", "Incorrect parameter for getSunrise()");
                return -1;
        }
    }
    public int getSunset(char value) {
        switch (value) {
            case 'h':
                return sunsetHour;
            case 'm':
                return sunsetMinute;
            default:
                Log.i("Sun.class", "Incorrect parameter for getSunset()");
                return -1;
        }
    }
}
