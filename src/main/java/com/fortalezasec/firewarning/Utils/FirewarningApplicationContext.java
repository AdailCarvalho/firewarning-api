package com.fortalezasec.firewarning.Utils;

import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FirewarningApplicationContext {
  
  @Autowired
  private UsuarioService usuarioService;

  public Usuario getUsuarioLogado() {
    String usuarioLogadoEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    return usuarioService.getByEmail(usuarioLogadoEmail);
  }
}
