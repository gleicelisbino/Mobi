package com.example.mobi7.service;

import com.example.mobi7.model.Poi;
import com.example.mobi7.model.Position;
import com.example.mobi7.repository.PoiRepository;
import com.example.mobi7.repository.PositionRepository;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PositionService {

  @Autowired private PoiRepository poiRepository;

  @Autowired private PositionRepository positionRepository;

  @Autowired private CsvPrototypeService csvPrototypeService;
  private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

  public List<Position> savePositions(List<Position> positions) {
    return positionRepository.saveAll(positions);
  }

  public List<Position> saveUploadPositions(MultipartFile file) {
    return this.savePositions(csvPrototypeService.readCSV(file, Position.class));
  }

  public Map<String, Duration> vehiclesInPoi(String poiName, List<String> plateNames) {
    Poi poi = poiRepository.findByName(poiName);
    List<Position> positions = positionRepository.findByPlateIn(plateNames);
    Map<String, Duration> durations = new HashMap<>();

    for (int i = 0; i < positions.size() - 1; i++) {
      Position current = positions.get(i);
      Position next = positions.get(i + 1);

      if (isInPOI(current, poi) && current.isIgnition()) {
        durations.merge(
            current.getPlate(),
            Duration.between(current.getPositionDate(), next.getPositionDate()),
            Duration::plus);
      }
    }
    return durations;
  }

  private boolean isInPOI(Position position, Poi poi) {
    Point positionPoint =
        geometryFactory.createPoint(
            new Coordinate(position.getLongitude(), position.getLatitude()));
    Point poiPoint =
        geometryFactory.createPoint(new Coordinate(poi.getLongitude(), poi.getLatitude()));
    return positionPoint.isWithinDistance(poiPoint, poi.getRay());
  }
}
