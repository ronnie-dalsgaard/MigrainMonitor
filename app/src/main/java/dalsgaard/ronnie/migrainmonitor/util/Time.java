package dalsgaard.ronnie.migrainmonitor.util;

import java.util.Calendar;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class Time {
    class TimeStampBuilder {

    }


    class TimeStamp {
        final long time;
        final String _time;

        public TimeStamp(long time) {
            this.time = time;
            this._time = Time.toDateTimeString(time);
        }
    }

    public static String toDateTimeString(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return toDateTimeString(c);
    }

    public static String toDateTimeString(Calendar instance) {
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min =  instance.get(Calendar.MINUTE);
        int sec =  instance.get(Calendar.SECOND);
        int millis = instance.get(Calendar.MILLISECOND);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int month = instance.get(Calendar.MONTH) +1;
        int year = instance.get(Calendar.YEAR);
        return String.format("%d:%d:%d %d/%d-%d", hour, min, sec, day, month, year);
    }
}
