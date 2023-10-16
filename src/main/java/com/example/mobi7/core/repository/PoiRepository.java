package com.example.mobi7.core.repository;

import com.example.mobi7.core.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {
  Poi findByNome(String nome);
}
