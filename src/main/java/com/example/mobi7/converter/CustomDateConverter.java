package com.example.mobi7.converter;

import com.example.mobi7.model.Position;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDateConverter extends AbstractBeanField<Position, String> {

  @Override
  protected Object convert(String value) throws CsvDataTypeMismatchException {
    try {
      DateTimeFormatter dateTimeFormatter =
          DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss").withLocale(Locale.ENGLISH);
      return LocalDateTime.from(
          dateTimeFormatter.parse(
              value.replaceAll("^[^ ]* |GMT-0200 \\(Hora oficial do Brasil\\)", "").trim()));
    } catch (IllegalArgumentException e) {
      throw new CsvDataTypeMismatchException("Failed to parse: " + value);
    }
  }
}
