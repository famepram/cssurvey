package coid.progressgroup.cssurvey.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DEV01 on 14/01/2016.
 */
public class DateTime {

    public static String GetStrCurrentTime() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenttime = sdf.format(now);
        return currenttime;
    }

    public static String GetStrCurrentDate() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentdate = sdf.format(now);
        return currentdate;
    }

    public static long GetUnixTimeStamp(){
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }
}
