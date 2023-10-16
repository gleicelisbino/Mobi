package com.example.mobi7.core.repository;

import com.example.mobi7.core.model.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

  List<Position> findByPlacaIn(List<String> plates);
}
