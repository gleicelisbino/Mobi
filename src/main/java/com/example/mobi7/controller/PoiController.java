package com.example.mobi7.controller;

import com.example.mobi7.model.Poi;
import com.example.mobi7.service.PoiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/poi")
public class PoiController {
  @Autowired private PoiService poiService;

  @PostMapping("/create")
  public ResponseEntity<Poi> createPOI(@RequestBody Poi poi) {
    return new ResponseEntity<>(poiService.savePOI(poi), HttpStatus.CREATED);
  }

  @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<Poi>> uploadFile(@RequestParam("file") MultipartFile file) {
    return new ResponseEntity<>(poiService.saveUploadPois(file), HttpStatus.CREATED);
  }
}
