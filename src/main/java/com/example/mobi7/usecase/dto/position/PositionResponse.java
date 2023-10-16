package com.example.mobi7.usecase.dto.position;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PositionResponse {
  private String placa;
  private LocalDateTime data_posicao;
  private float velocidade;
  private double longitude;
  private double latitude;
  private boolean ignicao;
}
