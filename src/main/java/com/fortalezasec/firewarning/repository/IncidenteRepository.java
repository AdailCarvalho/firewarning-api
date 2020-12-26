package com.fortalezasec.firewarning.repository;

import java.util.List;

import com.fortalezasec.firewarning.domain.Incidente;
import com.fortalezasec.firewarning.domain.NivelPerigo;
import com.fortalezasec.firewarning.domain.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long>{
  List<Incidente> findAllByCnpjEmpresa(String cnpjEmpresa);
  List<Incidente> findAllByNivelPerigo(NivelPerigo nivelPerigo);
  List<Incidente> findAllByStatus(Status status);
}
