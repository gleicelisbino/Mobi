package com.example.mobi7.model;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Poi {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CsvBindByName(column = "nome")
  private String name;

  @CsvBindByName(column = "latitude")
  private double latitude;

  @CsvBindByName(column = "longitude")
  private double longitude;

  @CsvBindByName(column = "raio")
  private double ray;
}
