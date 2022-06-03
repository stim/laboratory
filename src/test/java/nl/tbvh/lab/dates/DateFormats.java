package nl.tbvh.lab.dates;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class DateFormats {
    @Test
    public void monthnameUsingDateTimeFormatter() throws Exception {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d MMM", new Locale("nl"));

        String dateText = LocalDate.now().format(dateFormatter);

        assertEquals("woensdag 25 april", dateText);

    }
}
