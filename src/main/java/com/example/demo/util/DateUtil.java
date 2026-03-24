package com.example.demo.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date-related operations
 */
public class DateUtil {

    private DateUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Calculate number of days between two dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Calculate number of days including both start and end date
     */
    public static long daysBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    /**
     * Check if date is weekend (Saturday or Sunday)
     */
    public static boolean isWeekend(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * Check if date is weekday
     */
    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }

    /**
     * Get number of weekdays between two dates
     */
    public static long getWeekdaysBetween(LocalDate startDate, LocalDate endDate) {
        long weekdays = 0;
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            if (isWeekday(current)) {
                weekdays++;
            }
            current = current.plusDays(1);
        }

        return weekdays;
    }

    /**
     * Check if date is in past
     */
    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    /**
     * Check if date is in future
     */
    public static boolean isFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    /**
     * Check if date is today
     */
    public static boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    /**
     * Get age from date of birth
     */
    public static int getAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Get month name from month number
     */
    public static String getMonthName(int month) {
        return YearMonth.of(LocalDate.now().getYear(), month).getMonth().name();
    }

    /**
     * Check if two dates overlap
     */
    public static boolean overlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
