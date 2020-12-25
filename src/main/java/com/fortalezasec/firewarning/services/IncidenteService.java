package com.fortalezasec.firewarning.services;

import javax.validation.ConstraintViolationException;

import com.fortalezasec.firewarning.domain.Incidente;
import com.fortalezasec.firewarning.repository.IncidenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncidenteService {

  @Autowired
  private IncidenteRepository repository;

  public Incidente getById(Long id) {
    return repository.findById(id).get();
  }

  public Incidente insert(Incidente incidente) {
    Incidente entity = repository.save(incidente);

    return entity;
  }

  public Incidente update(Long id, Incidente incidente) {
    Incidente entity = repository.findById(id).get();
    entity = updateEntityWithIncident(entity, incidente);
    try {
      return repository.save(entity);
    } catch (Exception e) {
      throw new ConstraintViolationException(e.getMessage(), null);
    }
  }

  private Incidente updateEntityWithIncident(Incidente entity, Incidente incidente) {
    entity.setComentario(incidente.getComentario());
    entity.setNivelPerigo(incidente.getNivelPerigo());
    entity.setStatus(incidente.getStatus());
    entity.setDataResolucao(incidente.getDataResolucao());

    return entity;
  }
}
