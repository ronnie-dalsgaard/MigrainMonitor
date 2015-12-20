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
    public static String toDateString(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return toDateString(c);
    }

    public static String toDateTimeString(Calendar instance) {
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min =  instance.get(Calendar.MINUTE);
        int sec =  instance.get(Calendar.SECOND);
        int millis = instance.get(Calendar.MILLISECOND);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int month = instance.get(Calendar.MONTH) +1;
        int year = instance.get(Calendar.YEAR);
        return String.format("%02d/%02d-%d %02d:%02d:%02d", day, month, year, hour, min, sec);
    }

    public static String toDateString(Calendar instance) {
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min =  instance.get(Calendar.MINUTE);
        int sec =  instance.get(Calendar.SECOND);
        int millis = instance.get(Calendar.MILLISECOND);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int month = instance.get(Calendar.MONTH) +1;
        int year = instance.get(Calendar.YEAR);
        return String.format("%02d/%02d-%d", day, month, year);
    }

    public static boolean sameDay(long t1, long t2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(t1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(t2);

        for(int field : new int[] { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH } ){
            if(cal1.get(field) != cal2.get(field)) return false;
        }

        // FIXME for debug
        if(Math.abs(cal1.get(Calendar.SECOND) - cal2.get(Calendar.SECOND)) > 3) return false;

        return true;
    }
}
