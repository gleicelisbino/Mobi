package com.example.mobi7.infrastructure.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

  @Override
  public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {

    String source = context.getSource();
    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss").withLocale(Locale.ENGLISH);
    return LocalDateTime.from(
        dateTimeFormatter.parse(
            source.replaceAll("^[^ ]* |GMT-0200 \\(Hora oficial do Brasil\\)", "").trim()));
  }
}
