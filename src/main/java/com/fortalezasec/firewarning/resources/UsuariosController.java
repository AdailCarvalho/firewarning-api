package com.fortalezasec.firewarning.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.fortalezasec.firewarning.Utils.FirewarningApplicationContext;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaFavoritaNewDTO;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioDTO;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioNewDTO;
import com.fortalezasec.firewarning.repository.UsuarioRepository;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

  @Autowired
  private UsuarioService service;

  @Autowired
  private UsuarioRepository repository;

  @Autowired
  private FirewarningApplicationContext fireWarnAppContext;

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
      dto.setEmpresaFavorita(empresaDTO);
    }

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
    return ResponseEntity.created(uri).body(dto);
  }

  @PutMapping
  @PreAuthorize("permitAll()")
  public ResponseEntity<UsuarioNewDTO> setEmpresaFavorita(@RequestParam String cnpj, @RequestParam String comentario) {
    try {
      Usuario usuario = fireWarnAppContext.getUsuarioLogado();
      EmpresaFavorita empresaFavorita = new EmpresaFavorita(null, cnpj, comentario);
      usuario.setEmpresaFavorita(empresaFavorita);

      usuario = repository.save(usuario);
      UsuarioNewDTO dto = new UsuarioNewDTO(usuario);
      EmpresaFavoritaNewDTO empDto = new EmpresaFavoritaNewDTO(usuario.getEmpresaFavorita());
      dto.setEmpresaFavorita(empDto);
      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new UsernameNotFoundException("");
    }
  }

}
