package com.fortalezasec.firewarning.services;

import java.util.Arrays;
import java.util.HashSet;

import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  private UsuarioRepository usuarioRepository;

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  @Autowired
  public DBService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }
  
  public void instatiateTestDatabase() throws Exception {
    Usuario usuario1 = new Usuario(null, "José Carlos", "josecarlos@etc.br", bcrypt.encode("senha"), new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO)));
    Usuario usuario2 = new Usuario(null, "Patrícia Ramos", "pattyr@tal.br", bcrypt.encode("senha"), new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO, Tipo.ADMIN)));
    Usuario usuario3 = new Usuario(null, "Zé Cantor", "zecantor@texaco.com", bcrypt.encode("senha"), new HashSet<Tipo>(Arrays.asList(Tipo.ADMIN)));
    Usuario usuario4 = new Usuario(null, "SYS_SHELL", "sysshell@shell.com", bcrypt.encode("senha"), new HashSet<Tipo>(Arrays.asList(Tipo.SISTEMA)));

    usuarioRepository.save(usuario1);
    usuarioRepository.save(usuario2);
    usuarioRepository.save(usuario3);
    usuarioRepository.save(usuario4);
  }

}
