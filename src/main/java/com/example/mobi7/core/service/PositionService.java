package com.example.mobi7.core.service;

import com.example.mobi7.core.model.Poi;
import com.example.mobi7.core.model.Position;
import com.example.mobi7.core.repository.PoiRepository;
import com.example.mobi7.core.repository.PositionRepository;
import com.example.mobi7.usecase.dto.position.PositionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PositionService {

  @Autowired private PoiRepository poiRepository;

  @Autowired private PositionRepository positionRepository;

  @Autowired private ModelMapper modelMapper;

  @Autowired private CsvPrototypeService csvPrototypeService;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @Autowired private ObjectMapper objectMapper;

  private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

  public List<Position> savePositions(List<PositionRequest> positionRequests) {

    List<Position> positionsToSave =
        positionRequests.stream()
            .map(req -> modelMapper.map(req, Position.class))
            .collect(Collectors.toList());

    return positionRepository.saveAll(positionsToSave);
  }

  public List<Position> saveUploadPositions(MultipartFile file) {
    return this.savePositions(csvPrototypeService.readCSV(file, PositionRequest.class));
  }

  public Map<String, Duration> vehiclesInPoi(String poiName, List<String> plateNames) {
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    String cacheKey = poiName + plateNames.toString();
    String cached = ops.get(cacheKey);
    Map<String, Duration> result;
    if (cached == null) {
      result = getVehiclesInPoi(poiName, plateNames);
      try {
        ops.set(cacheKey, objectMapper.writeValueAsString(result), 10, TimeUnit.MINUTES);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error to cache", e);
      }
    } else {
      try {
        result = objectMapper.readValue(cached, new TypeReference<Map<String, Duration>>() {});
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error to cache", e);
      }
    }
    return result;
  }

  public Map<String, Duration> getVehiclesInPoi(String poiName, List<String> plateNames) {
    CompletableFuture<Poi> poiFuture =
        CompletableFuture.supplyAsync(() -> poiRepository.findByNome(poiName));
    CompletableFuture<List<Position>> positionsFuture =
        CompletableFuture.supplyAsync(() -> positionRepository.findByPlacaIn(plateNames));

    Poi poi = poiFuture.join();
    List<Position> positions = positionsFuture.join();
    Map<String, Duration> durations = new HashMap<>();

    for (int i = 0; i < positions.size() - 1; i++) {
      Position position = positions.get(i);

      if (isInPOI(position, poi) && position.isIgnicao()) {
        durations.merge(
            position.getPlaca(),
            Duration.between(position.getData_posicao(), positions.get(i + 1).getData_posicao()),
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
    return positionPoint.isWithinDistance(poiPoint, poi.getRaio());
  }
}
