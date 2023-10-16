package com.example.mobi7.service;

import com.example.mobi7.model.Poi;
import com.example.mobi7.repository.PoiRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PoiService {
  @Autowired private CsvPrototypeService csvPrototypeService;
  @Autowired private PoiRepository poiRepository;

  public Poi savePOI(Poi poi) {
    return poiRepository.save(poi);
  }

  public List<Poi> saveUploadPois(MultipartFile file) {
    return poiRepository.saveAll(csvPrototypeService.readCSV(file, Poi.class));
  }
}
