package com.example.mobi7.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvPrototypeService implements Cloneable {

  public CsvPrototypeService clone() {
    try {
      return (CsvPrototypeService) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public <T> List<T> readCSV(MultipartFile file, Class<T> type) {
    try (Reader reader = new InputStreamReader(file.getInputStream())) {

      HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
      strategy.setType(type);

      return new CsvToBeanBuilder<T>(reader)
          .withType(type)
          .withMappingStrategy(strategy)
          .withIgnoreLeadingWhiteSpace(true)
          .build()
          .parse();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to parse CSV file", e);
    }
  }
}
