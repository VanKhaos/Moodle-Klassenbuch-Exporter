package de.thunderfrog.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    /**
     * Epoch Time Format in lesbares Datum umwandeln
     * @param dateTime
     * @return
     */
    public static String parseDateTime(String dateTime){
        LocalDate parsedDate = LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(dateTime)), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return parsedDate.format(formatter);
    }
}
