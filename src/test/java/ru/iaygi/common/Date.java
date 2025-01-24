package ru.iaygi.common;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Date {

    public static String dateAnyYearFormatted(int year) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Instant changeDateYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime().toInstant();
    }

    public static String registrationDateFormatted(int year) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String dateAndTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");
        String dateAndTime = formatter.format(LocalDateTime.now());

        return dateAndTime;
    }

    public static String timeNow() {
        Time time = Time.valueOf(LocalTime.now());
        return String.valueOf(time);
    }

    public static String dateNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateNow = formatter.format(LocalDate.now());
        return dateNow;
    }

    public boolean afterTime(String instant) {
        java.util.Date date = new java.util.Date();
        Instant currentinstant = date.toInstant();
        return currentinstant.isAfter(Instant.parse(instant));
    }

    public Instant instant() {
        return new java.util.Date().toInstant();
    }

    public boolean compareDateIsBefore(String referenceDate, String testDate) {
        LocalDate referenceLocalDate = LocalDate.parse(referenceDate);
        LocalDate testLocalDate = LocalDate.parse(testDate);

        if (testLocalDate.isBefore(referenceLocalDate)) {
            return true;
        }
        return false;
    }

    public boolean compareDateIsAfter(String referenceDate, String testDate) {
        LocalDate referenceLocalDate = LocalDate.parse(referenceDate);
        LocalDate testLocalDate = LocalDate.parse(testDate);

        if (testLocalDate.isAfter(referenceLocalDate)) {
            return true;
        }
        return false;
    }
}