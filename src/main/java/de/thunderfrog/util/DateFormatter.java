package de.thunderfrog.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    /**
     * Epoch Time Format in lesbares Datum umwandeln
     * @param dateTime
     * @return String parsedDate
     */
    public static String parseDateTime(String dateTime){
        LocalDate parsedDate = LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(dateTime)), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return parsedDate.format(formatter);
    }

    /**
     * Date Format in deutsches Datum Format umwandeln
     * @param date
     * @return
     */
    public static String parseDateTime(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}
