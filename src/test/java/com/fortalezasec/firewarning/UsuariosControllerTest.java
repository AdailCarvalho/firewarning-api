package com.fortalezasec.firewarning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioNewDTO;
import com.fortalezasec.firewarning.security.jwt.JwtUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UsuariosControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  String autheticate(String username, String password) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    String token = jwtUtils.generateJwtToken(authentication);
    return "Bearer " + token;
  };

  @Test
  void shouldCreateUsuarioSimplesReturnStatus201CreatedAndUsuarioDTO() throws Exception {
    String token = autheticate("pattyr@tal.br", "senha");
    Usuario usuario = new Usuario(null, "Fulano", "fulano@email.com", "senha", null);
    MvcResult result = mockMvc.perform(post("/usuarios").header(HttpHeaders.AUTHORIZATION, token)
        .contentType("application/json").content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isCreated()).andReturn();
    String usuarioJsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Usuario usuarioConverted = objectMapper.readValue(usuarioJsonResponse, Usuario.class);
    assertEquals(5L, usuarioConverted.getId());
    assertEquals("Fulano", usuarioConverted.getNome());
    assertEquals("fulano@email.com", usuarioConverted.getEmail());
    assertEquals(null, usuarioConverted.getEmpresaFavorita());
  }

  @Test
  void shouldCreateUsuarioCompletoReturnStatus201CreatedAndUsuarioDTO() throws Exception {
    String token = autheticate("pattyr@tal.br", "senha");
    Usuario usuario = new Usuario(null, "fulano", "fulano@email.com", "senha",
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO)));
    usuario.setEmpresaFavorita(new EmpresaFavorita(null, "05014725000152", "Vazamento de oleo..."));

    MvcResult result = mockMvc.perform(post("/usuarios").header(HttpHeaders.AUTHORIZATION, token)
        .contentType("application/json").content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isCreated()).andReturn();

    String usuarioJsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Usuario usuarioConverted = objectMapper.readValue(usuarioJsonResponse, Usuario.class);
    assertEquals(5L, usuarioConverted.getId());
    assertEquals("fulano", usuarioConverted.getNome());
    assertEquals("fulano@email.com", usuarioConverted.getEmail());
    assertEquals("05014725000152", usuarioConverted.getEmpresaFavorita().getCnpj());
    assertEquals("Vazamento de oleo...", usuarioConverted.getEmpresaFavorita().getComentario());
  }

  @Test
  void shouldReturnAListOfUsuariosAndStatus200Ok() throws Exception {
    String token = autheticate("pattyr@tal.br", "senha");
    MvcResult result = mockMvc
        .perform(get("/usuarios").header(HttpHeaders.AUTHORIZATION, token).contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    String usuariosJsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    UsuarioNewDTO[] usuariosConverted = objectMapper.readValue(usuariosJsonResponse, UsuarioNewDTO[].class);

    assertEquals(1L, usuariosConverted[0].getId());
    assertEquals(2L, usuariosConverted[1].getId());
    assertEquals(3L, usuariosConverted[2].getId());
    assertEquals(4L, usuariosConverted[3].getId());

    assertEquals("josecarlos@etc.br", usuariosConverted[0].getEmail());
    assertEquals("pattyr@tal.br", usuariosConverted[1].getEmail());
    assertEquals("zecantor@texaco.com", usuariosConverted[2].getEmail());
    assertEquals("sysshell@shell.com", usuariosConverted[3].getEmail());

    assertEquals(true, usuariosConverted[0].getTipo().contains(Tipo.POPULACAO));
    assertEquals(true, usuariosConverted[1].getTipo().containsAll(Arrays.asList(Tipo.POPULACAO, Tipo.ADMIN)));
    assertEquals(true, usuariosConverted[2].getTipo().contains(Tipo.ADMIN));
    assertEquals(true, usuariosConverted[3].getTipo().contains(Tipo.SISTEMA));
  }

}
