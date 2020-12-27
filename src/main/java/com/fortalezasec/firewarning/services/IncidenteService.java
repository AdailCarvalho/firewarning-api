package com.fortalezasec.firewarning.services;

import java.util.List;

import com.fortalezasec.firewarning.Utils.CustomValidators;
import com.fortalezasec.firewarning.domain.Incidente;
import com.fortalezasec.firewarning.domain.NivelPerigo;
import com.fortalezasec.firewarning.domain.Status;
import com.fortalezasec.firewarning.repository.IncidenteRepository;
import com.fortalezasec.firewarning.services.Errors.TypeDoNotExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncidenteService {

  @Autowired
  CustomValidators validators;

  @Autowired
  private IncidenteRepository repository;

  public Incidente getById(Long id) {
    return repository.findById(id).get();
  }

  public List<Incidente> getAll() {
    return repository.findAll();
  }

  public Incidente insert(Incidente incidente) {
    Incidente entity = repository.save(incidente);

    return entity;
  }

  public Incidente update(Long id, Incidente incidente) {
    Incidente entity = repository.findById(id).get();
    entity = updateEntityWithIncident(entity, incidente);

    return repository.save(entity);

  }

  public List<Incidente> filterBy(String tipo, String valor) throws TypeDoNotExistsException {
    switch (tipo) {
      case "cnpj":
        validators.validarCnpj(valor);
        return repository.findAllByCnpjEmpresa(valor);
      case "nivelPerigo":
        NivelPerigo nivelPerigo = NivelPerigo.getByDescricao(valor);
        return repository.findAllByNivelPerigo(nivelPerigo);
      case "status":
        Status status = Status.getStatusByDescricao(valor);
        return repository.findAllByStatus(status);
      default:
        throw new TypeDoNotExistsException("O tipo especificado não era esperado");
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
