package com.example.mobi7.usecase.dto.position;

import lombok.Data;

@Data
public class PositionRequest {
  private String placa;
  private String data_posicao;
  private float velocidade;
  private double longitude;
  private double latitude;
  private boolean ignicao;
}
