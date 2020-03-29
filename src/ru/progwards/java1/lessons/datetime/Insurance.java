package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;

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
                        duration = Duration.between(start, start.plusYears(ld1.getYear()).plusMonths(ld1.getMonthValue()).plusDays(ld1.getDayOfMonth())
                                    .plusHours(ld1.getHour()).plusMinutes(ld1.getMinute()));
                        System.out.println(duration);
                        break;
            case FULL: duration = Duration.parse(strDuration);
        }
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        if (duration != null) {
            ZonedDateTime finish = start.plus(duration);
            return dateTime.isBefore(finish);
        } else return true;
    }

    @Override
    public String toString() {
        if (checkValid(ZonedDateTime.now()))
            return "Insurance issued on " + start + " is valid";
        else
            return "Insurance issued on " + start + " is not valid";
    }

    public static void main(String[] args) {
        Insurance insurance2 = new Insurance("2020-03-28T00:34:12.141340+03:00[Europe/Moscow]", FormatStyle.FULL);
        System.out.println(insurance2.toString());
        Insurance insurance = new Insurance("2020-03-30T22:26:12.684741+03:00[Europe/Moscow]", FormatStyle.FULL);
//        insurance.setDuration(1,1,1);
 //       insurance.setDuration("0000-06-03T10:00:00", FormatStyle.LONG);
 //       insurance2.setDuration(ZonedDateTime.parse("2020-04-01T00:34:12.142489+03:00[Europe/Moscow]"));
        insurance2.setDuration(Duration.ofDays(1));
        System.out.println(insurance2.duration);
        System.out.println(insurance2.checkValid(ZonedDateTime.now()));
        System.out.println(insurance2.toString());

    }
}
