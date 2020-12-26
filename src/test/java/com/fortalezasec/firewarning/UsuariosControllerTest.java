package com.fortalezasec.firewarning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.UsuarioNewDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

  @Test
  void shouldCreateUsuarioSimplesReturnStatus201CreatedAndUsuarioDTO() throws Exception {
    Usuario usuario = new Usuario(null, "Fulano", "fulano@email.com", "senha", null);
    MvcResult result = mockMvc
        .perform(post("/usuarios").with(httpBasic("pattyr@tal.br", "senha")).contentType("application/json").content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isCreated()).andReturn();
    String usuarioJsonResponse = result.getResponse().getContentAsString();

    Usuario usuarioConverted = objectMapper.readValue(usuarioJsonResponse, Usuario.class);
    assertEquals(5L, usuarioConverted.getId());
    assertEquals("Fulano", usuarioConverted.getNome());
    assertEquals("fulano@email.com", usuarioConverted.getEmail());
    assertEquals(null, usuarioConverted.getEmpresaFavorita());
  }

  @Test
  void shouldCreateUsuarioCompletoReturnStatus201CreatedAndUsuarioDTO() throws Exception {
    Usuario usuario = new Usuario(null, "fulano", "fulano@email.com", "senha",
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO)));
    usuario.setEmpresaFavorita(new EmpresaFavorita(null, "05014725000152", "Vazamento de oleo..."));

    MvcResult result = mockMvc
        .perform(post("/usuarios").with(httpBasic("zecantor@texaco.com", "senha")).contentType("application/json").content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isCreated()).andReturn();

    String usuarioJsonResponse = result.getResponse().getContentAsString();

    Usuario usuarioConverted = objectMapper.readValue(usuarioJsonResponse, Usuario.class);
    assertEquals(5L, usuarioConverted.getId());
    assertEquals("fulano", usuarioConverted.getNome());
    assertEquals("fulano@email.com", usuarioConverted.getEmail());
    assertEquals("05014725000152", usuarioConverted.getEmpresaFavorita().getCnpj());
    assertEquals("Vazamento de oleo...", usuarioConverted.getEmpresaFavorita().getComentario());
  }

  @Test
  void shouldReturnAListOfUsuariosAndStatus200Ok() throws Exception {
    MvcResult result = mockMvc
        .perform(get("/usuarios").with(httpBasic("zecantor@texaco.com", "senha")).contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    String usuariosJsonResponse = result.getResponse().getContentAsString();

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
