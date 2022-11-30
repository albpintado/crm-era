package com.albpintado.crmera.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
  public static LocalDate createLocalDate(String stringDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    formatter = formatter.withLocale(Locale.US);
    LocalDate date = LocalDate.parse(stringDate, formatter);

    return date;
  }
}
