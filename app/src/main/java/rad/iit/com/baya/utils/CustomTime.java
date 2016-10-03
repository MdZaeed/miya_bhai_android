package rad.iit.com.baya.utils;

import java.util.Calendar;

/**
 * Created by iit on 10/3/2016.
 */
public class CustomTime {


    public static String toStandardFormat(Calendar calendar) {
        return calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"T00:00:00Z";
    }
}
