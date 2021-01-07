package com.mmt.routeplanner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class RouteUtil {

  public static final String RELATIONSHIP = "%s~#~%s~#~%s";
  public static final String DELIMETER = "~#~";
  public static final String CHEAPEST = "CHEAPEST";
  public static final String SHORTEST = "SHORTEST";

  /**
   * The constant PARTITION_ID.
   */
  public static final String PARTITION_ID = "kafka_receivedPartitionId";
  /**
   * The constant KEY.
   */
  public static final String KEY = "kafka_receivedMessageKey";
  /**
   * The constant TOPIC.
   */
  public static final String TOPIC = "kafka_receivedTopic";
  /**
   * The constant OFFSET.
   */
  public static final String OFFSET = "kafka_offset";

  /**
   * The constant KAFKA_KEY.
   */
  public static final String KAFKA_KEY = "Kafka";

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


//  public static void main(String[] args) {
//   log.info(addHours(new Date(),5l));
//  }

  public static Date getDateWithoutTimeUsingCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTime();
  }

  public static Date atStartOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return localDateTimeToDate(startOfDay);
  }

  public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  private static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * This method will take Json string and return Entity object.
   *
   * @param <T>          the generic type
   * @param jsonTxt      the json txt
   * @param resultClass  the result class
   * @param objectMapper the object mapper
   *
   * @return the t
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static <T> T jsonToEntity(String jsonTxt, Class<T> resultClass, ObjectMapper objectMapper)
      throws IOException {
    T resultObject = objectMapper.readValue(jsonTxt, resultClass);
    if (resultObject == null) {
      throw new IllegalArgumentException("null resultObject after JSON to Object conversion");
    }
    return resultObject;
  }
}
