package com.example.mobi7.model;

import com.example.mobi7.converter.CustomDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Position {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CsvBindByName(column = "placa")
  private String plate;

  @CsvCustomBindByName(column = "data_posicao", converter = CustomDateConverter.class)
  private LocalDateTime positionDate;

  @CsvBindByName(column = "velocidade")
  private float velocity;

  @CsvBindByName(column = "longitude")
  private double longitude;

  @CsvBindByName(column = "latitude")
  private double latitude;

  @CsvBindByName(column = "ignicao")
  private boolean ignition;
}
