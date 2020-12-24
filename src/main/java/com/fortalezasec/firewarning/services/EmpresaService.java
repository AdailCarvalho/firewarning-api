package com.fortalezasec.firewarning.services;

import java.util.ArrayList;
import java.util.List;

import com.fortalezasec.firewarning.Utils.CustomValidators;
import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaDTO;
import com.fortalezasec.firewarning.repository.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

  private EmpresaRepository repository;
  private CustomValidators validators;

  @Autowired
  public EmpresaService(EmpresaRepository repository, CustomValidators validators) {
    this.repository = repository;
    this.validators = validators;
  }

  public List<EmpresaDTO> getAll() {
    List<Empresa> empresas = repository.findAll();
    List<EmpresaDTO> empresasDTO = new ArrayList<>();
    empresasDTO = fromDTO(empresas);
    return empresasDTO;
  }

  public Empresa getById(Long id) {
    return repository.findById(id).get();
  }

  public Empresa getByCnpj(String cnpj) throws Exception {
    if (validators.validarCnpj(cnpj)) {
      return repository.findByCnpj(cnpj).get();
    } else {
      throw new Exception();
    }
  }

  private List<EmpresaDTO> fromDTO(List<Empresa> empresas) {
    List<EmpresaDTO> empresasDTO = new ArrayList<>();
    empresas.stream().forEach(u -> {
      empresasDTO.add(new EmpresaDTO(u));
    });
    return empresasDTO;
  }
}
