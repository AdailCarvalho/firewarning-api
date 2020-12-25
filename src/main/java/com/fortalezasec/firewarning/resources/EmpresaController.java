package com.fortalezasec.firewarning.resources;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaDTO;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaFavoritaDTO;
import com.fortalezasec.firewarning.services.EmpresaService;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

  private EmpresaService empresaService;
  private UsuarioService usuarioService;

  @Autowired
  public EmpresaController(EmpresaService empresaService, UsuarioService usuarioService) {
    this.empresaService = empresaService;
    this.usuarioService = usuarioService;
  }

  @PreAuthorize("hasAnyRole('POPULACAO')")
  @GetMapping
  public ResponseEntity<List<EmpresaDTO>> getEmpresas() {
    List<EmpresaDTO> empresas = empresaService.getAll();

    return ResponseEntity.ok().body(empresas);
  }

  @PreAuthorize("hasAnyRole('POPULACAO', 'ADMIN', 'SYSTEM')")
  @GetMapping("/{cnpj}")
  public ResponseEntity<EmpresaFavoritaDTO> getEmpresaFavorita(@PathVariable String cnpj) {
    try {
      Empresa empresa = empresaService.getByCnpj(cnpj);
      String usuarioLogadoEmail = SecurityContextHolder.getContext().getAuthentication().getName();
      Usuario usuarioLogado = usuarioService.getByEmail(usuarioLogadoEmail);
      EmpresaFavorita empresaFavorita = usuarioLogado.getEmpresaFavorita();
      EmpresaFavoritaDTO dto = new EmpresaFavoritaDTO(empresaFavorita, empresa);

      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa favorita não existe com esse CNPJ");
    }
  }

}
