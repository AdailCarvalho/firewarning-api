package com.fortalezasec.firewarning.services;

import java.util.ArrayList;
import java.util.List;

import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.UsuarioDTO;
import com.fortalezasec.firewarning.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  
  private UsuarioRepository repository;

  @Autowired
  public UsuarioService(UsuarioRepository repository) {
    this.repository = repository;
  }

  public List<UsuarioDTO> getUsuarios() {
    List<Usuario> usuarios = new ArrayList<>();
    List<UsuarioDTO> usuariosDTO = new ArrayList<>();
    try {
      usuarios = repository.findAll();
      usuariosDTO = toDTO(usuarios);
    } catch (Exception e) {
      System.out.println(e);
    }

    return usuariosDTO;
  }

  private List<UsuarioDTO> toDTO(List<Usuario> usuarios) {
    List<UsuarioDTO> usuariosDTO = new ArrayList<>();
    usuarios.stream().forEach(u -> {
      usuariosDTO.add(new UsuarioDTO(u));
    });
    return usuariosDTO;
  }

}
