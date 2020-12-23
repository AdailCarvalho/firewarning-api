package com.fortalezasec.firewarning.domain;

import java.io.Serializable;
import java.util.Set;

public class UsuarioDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String nome;
  private String email;
  private Set<Tipo> tipo;

  public UsuarioDTO() {
  }

  public UsuarioDTO(Usuario usuario) {
    id = usuario.getId();
    nome = usuario.getNome();
    email = usuario.getEmail();
    tipo = usuario.getPerfis();
  }

  public UsuarioDTO(Long id, String nome, String email, Set<Tipo> tipo) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.tipo = tipo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Tipo> getTipo() {
    return tipo;
  }

  public void setTipo(Set<Tipo> tipo) {
    this.tipo = tipo;
  }

}
