package com.example.demo.Util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateTimeConverter {
    public static String DateTimeConvertFromString(String dateTimeString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeString);
        String formattedDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        return formattedDateTime;
    }
    public static String DateTimeConvertFromInstant(Instant instant) {
        if (instant == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(instant.atZone(ZoneId.of("America/New_York")));
    }

    public static String InstantToString(Instant instant, String pattern) {
        if (instant == null) return null;
        return instant.atZone(ZoneId.of("America/New_York"))
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Instant nowNyc() {
        return ZonedDateTime.now(ZoneId.of("America/New_York")).toInstant();
    }

    public static String DateTimeConvertFromObject(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Instant instant) return DateTimeConvertFromInstant(instant);
        if (obj instanceof Timestamp ts) return DateTimeConvertFromInstant(ts.toInstant());
        return obj.toString();
    }
}
