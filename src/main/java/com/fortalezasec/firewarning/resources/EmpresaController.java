package com.fortalezasec.firewarning.resources;

import java.util.List;

import com.fortalezasec.firewarning.domain.EmpresaDTO;
import com.fortalezasec.firewarning.services.EmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/empresas")
public class EmpresaController {
  
  private EmpresaService empresaService;

  @Autowired
  public EmpresaController(EmpresaService empresaService) {
    this.empresaService = empresaService;
  }

  @PreAuthorize("hasAnyRole('POPULACAO')")
  @GetMapping
  public ResponseEntity<List<EmpresaDTO>> getEmpresas() {
    List<EmpresaDTO> empresas = empresaService.getAll();

    return ResponseEntity.ok().body(empresas);
  }

}
