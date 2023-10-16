package com.example.mobi7.repository;

import com.example.mobi7.model.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
  List<Position> findByPlateIn(List<String> plates);
}
