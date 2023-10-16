package com.example.mobi7.controller;

import com.example.mobi7.model.Position;
import com.example.mobi7.service.PositionService;
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
@RequestMapping("/position")
public class PositionController {
  @Autowired private PositionService positionService;

  @PostMapping("/create")
  public ResponseEntity<List<Position>> createPositions(@RequestBody List<Position> positions) {
    return new ResponseEntity<>(positionService.savePositions(positions), HttpStatus.CREATED);
  }

  @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<Position>> uploadFile(@RequestParam("file") MultipartFile file) {
    return new ResponseEntity<>(positionService.saveUploadPositions(file), HttpStatus.CREATED);
  }

  @GetMapping("/vehicle-in-poi")
  public Map<String, Duration> vehiclesInPoi(
      @RequestParam String poiName, @RequestParam List<String> plates) {
    return positionService.vehiclesInPoi(poiName, plates);
  }
}
