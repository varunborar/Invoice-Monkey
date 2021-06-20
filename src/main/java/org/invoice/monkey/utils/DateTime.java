package org.invoice.monkey.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateTime {


    public static Long getCurrentDateTimeStamp()
    {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();

        fmt.format("%tF%tR", cal, cal);
        return Long.parseLong(fmt.toString().replace("-", "").replace(":",""));
    }

    public static String getFormattedDate()
    {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();

        fmt.format("%td-%tm-%ty",cal,cal,cal);
        return fmt.toString();
    }

    public static String getFormattedTime()
    {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();

        fmt.format("%tk:%tM", cal,cal);
        return fmt.toString();
    }

    public static long getTime()
    {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();

        fmt.format("%ts", cal);

        return Long.parseLong(fmt.toString());
    }

    public static long getTime(int hours)
    {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();

        fmt.format("%ts", cal);

        return Long.parseLong(fmt.toString());
    }

    public static void main(String args[])
    {
        System.out.println(getCurrentDateTimeStamp());
    }
}
