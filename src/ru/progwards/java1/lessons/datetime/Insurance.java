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
        duration = Duration.between(expiration, start);
    }

    public void setDuration(int months, int days, int hours) {
        duration = Duration.between(start.plusMonths(months).plusDays(days).plusHours(hours), start);
    }

    public void setDuration(String strDuration, FormatStyle style) {
        switch (style) {
            case SHORT: duration = Duration.ofMillis(Long.parseLong(strDuration));
                        break;
            case LONG: LocalDateTime ld1 = LocalDateTime.parse(strDuration);
                        duration = Duration.between(start.plusYears(ld1.getYear()).plusMonths(ld1.getMonthValue()).plusDays(ld1.getDayOfMonth())
                                    .plusHours(ld1.getHour()).plusMinutes(ld1.getMinute()), start);
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
        Insurance insurance2 = new Insurance(ZonedDateTime.now());
        System.out.println(insurance2.toString());
        Insurance insurance = new Insurance("2019-12-25T12:13:14.444+03:00", FormatStyle.FULL);
//        insurance.setDuration(1,1,1);
 //       insurance.setDuration("0000-06-03T10:00:00", FormatStyle.LONG);
        insurance.setDuration(ZonedDateTime.parse("2020-02-28T13:23:34.444+03:00"));
 //       insurance.setDuration(Duration.ofDays(30));
        System.out.println(insurance.duration);
        System.out.println(insurance.checkValid(ZonedDateTime.now()));
        System.out.println(insurance.toString());

    }
}
