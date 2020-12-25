package com.fortalezasec.firewarning.services;

import java.util.ArrayList;
import java.util.List;

import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaDTO;
import com.fortalezasec.firewarning.repository.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

  @Autowired
  private EmpresaRepository repository;

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
    return repository.findByCnpj(cnpj).get();
  }

  private List<EmpresaDTO> fromDTO(List<Empresa> empresas) {
    List<EmpresaDTO> empresasDTO = new ArrayList<>();
    empresas.stream().forEach(u -> {
      empresasDTO.add(new EmpresaDTO(u));
    });
    return empresasDTO;
  }
}
