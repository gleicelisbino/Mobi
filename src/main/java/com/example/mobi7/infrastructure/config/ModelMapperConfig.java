package com.example.mobi7.infrastructure.config;

import com.example.mobi7.infrastructure.converter.StringToLocalDateTimeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addConverter(new StringToLocalDateTimeConverter());
    return modelMapper;
  }
}
