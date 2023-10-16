package com.example.mobi7.core.service;

import com.example.mobi7.core.model.Poi;
import com.example.mobi7.core.repository.PoiRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PoiService {
  @Autowired private CsvPrototypeService csvPrototypeService;
  @Autowired private PoiRepository poiRepository;

  public List<Poi> savePOI(List<Poi> pois) {
    return poiRepository.saveAll(pois);
  }

  public List<Poi> saveUploadPois(MultipartFile file) {
    return poiRepository.saveAll(csvPrototypeService.readCSV(file, Poi.class));
  }
}
