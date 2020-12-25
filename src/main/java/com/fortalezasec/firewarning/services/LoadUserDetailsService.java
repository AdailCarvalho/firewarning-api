package com.fortalezasec.firewarning.services;

import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.repository.UsuarioRepository;
import com.fortalezasec.firewarning.security.UserSec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoadUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      Usuario usuario = repository.findByEmail(username);
      UserDetails uDetails = new UserSec(usuario.getEmail(), usuario.getSenha(), usuario.getPerfis());
      return uDetails;
    } catch (Exception e) {
      throw new UsernameNotFoundException("Usuário não encontrado");
    }

  }

}
