package com.fortalezasec.firewarning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioDTO;
import com.fortalezasec.firewarning.services.UsuarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTest {

  @Autowired
  private UsuarioService service;

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  @Test
  void deveCadastrarUmNovoUsuario() throws Exception {

    Usuario novoUsuario = new Usuario(null, "Fulano", "fulano@email.com", "senha", null);

    novoUsuario = service.insert(novoUsuario);

    assertEquals(novoUsuario.getId(), 5L);
    assertEquals(novoUsuario.getNome(), "Fulano");
    assertEquals(novoUsuario.getEmail(), "fulano@email.com");
    assertEquals(true, bcrypt.matches("senha", novoUsuario.getSenha()));
    assertEquals(null, novoUsuario.getEmpresaFavorita());
    assertEquals(true, novoUsuario.getPerfis().contains((Tipo.POPULACAO)));
  }

  @Test
  void deveObterUmaListaDeUsuariosDTO() throws Exception {

    List<UsuarioDTO> resposta = service.getUsuarios();

    UsuarioDTO usuario1 = new UsuarioDTO(1L, "José Carlos", "josecarlos@etc.br",
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO)));
    UsuarioDTO usuario2 = new UsuarioDTO(2L, "Patrícia Ramos", "pattyr@tal.br",
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO, Tipo.ADMIN)));
    UsuarioDTO usuario3 = new UsuarioDTO(3L, "Zé Cantor", "zecantor@texaco.com",
        new HashSet<Tipo>(Arrays.asList(Tipo.ADMIN)));
    UsuarioDTO usuario4 = new UsuarioDTO(4L, "SYS_SHELL", "sysshell@shell.com",
        new HashSet<Tipo>(Arrays.asList(Tipo.SISTEMA)));

    assertEqualsUsuarioDTO(resposta.get(0), usuario1);
    assertEqualsUsuarioDTO(resposta.get(1), usuario2);
    assertEqualsUsuarioDTO(resposta.get(2), usuario3);
    assertEqualsUsuarioDTO(resposta.get(3), usuario4);
  }

  public void assertEqualsUsuarioDTO(UsuarioDTO usuario, UsuarioDTO usuarioDTO) {
    assertEquals(usuario.getId(), usuarioDTO.getId());
    assertEquals(usuario.getEmail(), usuarioDTO.getEmail());
    assertEquals(usuario.getNome(), usuarioDTO.getNome());
    assertEquals(usuario.getTipo().equals(usuarioDTO.getTipo()), true);
  }
}
