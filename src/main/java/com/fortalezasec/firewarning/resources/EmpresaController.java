package com.fortalezasec.firewarning.resources;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.fortalezasec.firewarning.Utils.FirewarningApplicationContext;
import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaDTO;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaFavoritaDTO;
import com.fortalezasec.firewarning.services.EmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

  @Autowired
  private EmpresaService empresaService;

  @Autowired
  private FirewarningApplicationContext firewarningApplicationContext;

  @PreAuthorize("permitAll()")
  @GetMapping
  public ResponseEntity<List<EmpresaDTO>> getEmpresas() {
    List<EmpresaDTO> empresas = empresaService.getAll();

    return ResponseEntity.ok().body(empresas);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/favorita")
  public ResponseEntity<EmpresaFavoritaDTO> getEmpresaFavorita() {
    try {
      Usuario usuarioLogado = firewarningApplicationContext.getUsuarioLogado();
      Empresa empresa = empresaService.getByCnpj(usuarioLogado.getEmpresaFavorita().getCnpj());
      EmpresaFavorita empresaFavorita = usuarioLogado.getEmpresaFavorita();
      EmpresaFavoritaDTO dto = new EmpresaFavoritaDTO(empresaFavorita, empresa);

      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa favorita não existe com esse CNPJ");
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/{cnpj}")
  public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable String cnpj) {
    try {
      Empresa empresa = empresaService.getByCnpj(cnpj);
      EmpresaDTO dto = new EmpresaDTO(empresa);

      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa favorita não existe com esse CNPJ");
    }
  }

}
