package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL};
    private ZonedDateTime start; // - дата-время начала действия страховки.
    private Duration duration; // - продолжительность действия.

    public Insurance(ZonedDateTime start) {
        this.start = start;
    }

    public Insurance(String strStart, FormatStyle style) {
        switch (style) {
            case SHORT:
                LocalDate ld = LocalDate.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE);
                this.start = ZonedDateTime.of(ld, LocalTime.MIDNIGHT,ZoneId.systemDefault());
                break;
            case LONG:
                LocalDateTime ldt = LocalDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                this.start = ZonedDateTime.of(ldt, ZoneId.systemDefault());
                break;
            case FULL:
                this.start = ZonedDateTime.parse(strStart, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public void setDuration(ZonedDateTime expiration) {
        duration = Duration.between(start, expiration);
    }

    public void setDuration(int months, int days, int hours) {
        duration = Duration.between(start, start.plusMonths(months).plusDays(days).plusHours(hours));
    }

    public void setDuration(String strDuration, FormatStyle style) {
        switch (style) {
            case SHORT: duration = Duration.ofMillis(Long.parseLong(strDuration));
                        break;
            case LONG: LocalDateTime ld1 = LocalDateTime.parse(strDuration);
                        ZonedDateTime finish = start.plusYears(ld1.getYear()).plusMonths(ld1.getMonthValue()).plusDays(ld1.getDayOfMonth())
                                               .plusHours(ld1.getHour()).plusMinutes(ld1.getMinute());
                        duration = Duration.between(start, finish);
                        break;
            case FULL: duration = Duration.parse(strDuration);
        }
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        if (duration != null) {
            ZonedDateTime finish = start.plus(duration);
            return dateTime.isAfter(start) && dateTime.isBefore(finish);
        } else return dateTime.isAfter(start);
    }

    @Override
    public String toString() {
        if (checkValid(ZonedDateTime.now()))
            return "Insurance issued on " + start + " is valid";
        else
            return "Insurance issued on " + start + " is not valid";
    }

    public static void main(String[] args) {
        Insurance insurance2 = new Insurance("2020-02-29T10:28:13.058516+03:00[Europe/Moscow]", FormatStyle.FULL);
     //   System.out.println(insurance2.toString());
 //       Insurance insurance = new Insurance("2020-03-31T01:22:13.145329+03:00[Europe/Moscow]", FormatStyle.FULL);
 //       insurance2.setDuration(ZonedDateTime.parse("2020-04-01T00:34:12.142489+03:00[Europe/Moscow]"));
        insurance2.setDuration("0000-01-01T00:00:00", Insurance.FormatStyle.LONG);
        System.out.println(insurance2.duration);
 //       System.out.println(insurance2.checkValid(ZonedDateTime.now()));
        System.out.println(insurance2.toString());
 //       System.out.println(insurance.toString());

    }
}
