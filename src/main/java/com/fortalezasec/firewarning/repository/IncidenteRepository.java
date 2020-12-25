package com.fortalezasec.firewarning.repository;

import com.fortalezasec.firewarning.domain.Incidente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long>{
  
}
