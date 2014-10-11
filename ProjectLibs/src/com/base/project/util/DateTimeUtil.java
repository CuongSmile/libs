package com.base.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {
    public static String getNow() {
        return getDateTimeFormat(System.currentTimeMillis(), "dd-MM-yyyy");
    }

    public static String getTime(long time) {
        return getDateTimeFormat(time, "mm:ss");
    }

    public static String getDateTimeFormat(long time_milis, String format) {
        String time = "";
        Date date = new Date(time_milis);
        try {
            SimpleDateFormat SDF = new SimpleDateFormat(format, Locale.US);
            time = SDF.format(date);
        } catch (Exception e) {}
        return time;
    }

    public static String getTimeFromMilis(long time) {
        String ret;
        long hour, minutes, seconds;
        hour = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS);
        if (hour > 0) {
            time = time - hour * 3600 * 1000;
        }
        minutes = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS);
        if (minutes > 0) {
            time = time - minutes * 60 * 1000;
        }

        seconds = TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS);

        if (hour > 0) {
            ret = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        } else {
            ret = String.format("%02d:%02d", minutes, seconds);
        }
        return ret;
    }
}
