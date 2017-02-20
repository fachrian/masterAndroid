package fachrian.fachrian_library.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class DateFormatMaster {
    static String default_format = "yyyy-MM-dd HH:mm:ss";
    public static String DMY_format = "dd-MM-yyyy";
    public static String HM_format = "HH:mm";
    public static String DMY_HM_format = "dd-MM-yyyy (HH:mm)";

    public static String convertFormat(String date, String format) {
        SimpleDateFormat fromUser = new SimpleDateFormat(default_format);
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        try {
            return myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
