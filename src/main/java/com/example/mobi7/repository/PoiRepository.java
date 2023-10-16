package com.example.mobi7.repository;

import com.example.mobi7.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {
  Poi findByName(String name);
}
