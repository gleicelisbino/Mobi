package com.example.mobi7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Mobi7Application {

  public static void main(String[] args) {
    SpringApplication.run(Mobi7Application.class, args);
  }
}
