package com.example.mobi7.infrastructure.controller;

import com.example.mobi7.core.model.Position;
import com.example.mobi7.core.service.PositionService;
import com.example.mobi7.usecase.dto.position.PositionRequest;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/positions")
public class PositionController {
  @Autowired private PositionService positionService;

  @PostMapping("/create")
  public ResponseEntity<List<Position>> createPositions(
      @RequestBody List<PositionRequest> positions) {
    return new ResponseEntity<>(positionService.savePositions(positions), HttpStatus.CREATED);
  }

  @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<Position>> uploadFile(@RequestParam("file") MultipartFile file) {
    return new ResponseEntity<>(positionService.saveUploadPositions(file), HttpStatus.CREATED);
  }

  @GetMapping("/vehicle-in-poi")
  public Map<String, Duration> vehiclesInPoi(
      @RequestParam String poiNome, @RequestParam List<String> placas) {
    return positionService.vehiclesInPoi(poiNome, placas);
  }
}
