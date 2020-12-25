package com.fortalezasec.firewarning.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaFavoritaNewDTO;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioDTO;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioNewDTO;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

  private UsuarioService service;

  @Autowired
  public UsuariosController(UsuarioService service) {
    this.service = service;
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
    List<UsuarioDTO> usuarios = service.getUsuarios();
    return ResponseEntity.ok().body(usuarios);
  }

  @PostMapping
  public ResponseEntity<UsuarioNewDTO> save(@Valid @RequestBody Usuario usuario) throws Exception {

    Usuario entity = service.insert(usuario);
    UsuarioNewDTO dto = new UsuarioNewDTO(entity);
    
    if (entity.getEmpresaFavorita() != null) {
      EmpresaFavoritaNewDTO empresaDTO = new EmpresaFavoritaNewDTO(entity.getEmpresaFavorita());
      dto.setEmpresaFavoritaDTO(empresaDTO);
    }

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);

  }

}
