package com.mmt.routeplanner.util;

import java.util.Calendar;
import java.util.Date;

public class RouteUtil {

  public static Date getDateTime(Date date , String time){
    String[] hhmm = time.split(":");
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hhmm[0]));
    c.add(Calendar.MINUTE, Integer.parseInt(hhmm[1]));
    return c.getTime();

  }


  public static Date addHours(Date date , Long time){
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.add(Calendar.MINUTE, time.intValue());
    return c.getTime();

  }


  public static void main(String[] args) {
    System.out.println(addHours(new Date(),5l));
  }

  public static Date getDateWithoutTimeUsingCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTime();
  }
}
