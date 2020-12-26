package com.fortalezasec.firewarning;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortalezasec.firewarning.domain.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuariosControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void deveRetornarStatus201Created() throws Exception {

    Usuario usuario = new Usuario(null, "Fulano", "fulano@email.com", "senha", null);
    mockMvc.perform(post("/usuarios").contentType("application/json").content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isCreated());
  }

  @Test
  void deveRetornarStatus200Ok() throws Exception {

    mockMvc.perform(get("/usuarios").with(httpBasic("zecantor@texaco.com", "senha")).contentType("application/json")
        .content(objectMapper.writeValueAsString("usuariosController"))).andExpect(status().isOk());
  }

}
