package com.fortalezasec.firewarning.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fortalezasec.firewarning.CustomExceptions.UserAlreadyExistsException;
import com.fortalezasec.firewarning.Utils.CustomValidators;
import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioDTO;
import com.fortalezasec.firewarning.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  @Autowired
  private CustomValidators validators;

  @Autowired
  private UsuarioRepository repository;

  public List<UsuarioDTO> getUsuarios() {
    List<Usuario> usuarios = new ArrayList<>();
    List<UsuarioDTO> usuariosDTO = new ArrayList<>();
    try {
      usuarios = repository.findAll();
      usuariosDTO = toDTOs(usuarios);
    } catch (Exception e) {
      System.out.println(e);
    }

    return usuariosDTO;
  }

  public UsuarioDTO getById(Long id) {
    Usuario usuario = repository.findById(id).get();
    UsuarioDTO usuarioDTO = toDTO(usuario);

    return usuarioDTO;
  }

  public Usuario getByEmail(String email) {
    Usuario usuario = repository.findByEmail(email);

    return usuario;
  }

  public Usuario insert(Usuario usuario) throws Exception {
    if (usuario.getEmpresaFavorita() != null)
      validators.validarCnpj(usuario.getEmpresaFavorita().getCnpj());

    // Ok, usuario não existe ainda
    if (getByEmail(usuario.getEmail()) == null) {
      Set<Tipo> perfis = new HashSet<>(Arrays.asList(Tipo.POPULACAO));
      usuario.setPerfis(perfis);
      usuario.setSenha(bcrypt.encode((usuario.getSenha())));
      return repository.save(usuario);
    } else {
      throw new UserAlreadyExistsException("Já existe um usuário com o email informado");
    }

  }

  public UsuarioDTO toDTO(Usuario usuario) {
    UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
    return usuarioDTO;
  }

  public List<UsuarioDTO> toDTOs(List<Usuario> usuarios) {
    List<UsuarioDTO> usuariosDTO = new ArrayList<>();
    usuarios.stream().forEach(u -> {
      usuariosDTO.add(new UsuarioDTO(u));
    });
    return usuariosDTO;
  }

}
