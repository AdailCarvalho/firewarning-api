package com.fortalezasec.firewarning.services;

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

  public Incidente update(Incidente incidente) {
    Incidente entity = repository.findById(incidente.getId()).get();
    entity = updateEntityWithIncident(entity, incidente);
    return repository.save(entity);
  }

  private Incidente updateEntityWithIncident(Incidente entity, Incidente incidente) {
    entity.setComentario(incidente.getComentario());
    entity.setNivelPerigo(incidente.getNivelPerigo());
    entity.setData(incidente.getData());
    entity.setStatus(incidente.getStatus());
    entity.setDataResolucao(incidente.getDataResolucao());

    return entity;
  }
}
