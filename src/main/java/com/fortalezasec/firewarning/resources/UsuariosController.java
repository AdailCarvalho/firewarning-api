package com.fortalezasec.firewarning.resources;

import java.util.List;

import com.fortalezasec.firewarning.domain.UsuarioDTO;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/usuarios")
public class UsuariosController {
  
  private UsuarioService service;
  
  public UsuariosController(UsuarioService service) {
    this.service = service;
  }
  
  @GetMapping()
  public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
    List<UsuarioDTO> usuarios = service.getUsuarios();
    return ResponseEntity.ok().body(usuarios);
  }
  
}
